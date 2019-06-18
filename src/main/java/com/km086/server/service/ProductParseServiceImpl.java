package com.km086.server.service;

import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.OpenRange;
import com.km086.server.model.security.OpenRangeType;
import com.km086.server.model.store.Category;
import com.km086.server.model.store.Product;
import com.km086.server.model.store.ProductStatus;
import com.km086.server.repository.security.OpenRangeRepository;
import com.km086.server.repository.store.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductParseServiceImpl implements ProductParseService {

    @Autowired
    SecurityService securityService;

    @Autowired
    StoreService storeService;

    @Autowired
    OpenRangeRepository openRangeRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void parse(Long merchantId, InputStream inputStream) {
        log.info("start parse excel......");
        try {
            Merchant merchant = securityService.findMerchant(merchantId);
            log.info("merchant id is: {}.", merchant.getId());

            Workbook workbook = new XSSFWorkbook(inputStream);
            // 第一个sheet代表分类
            List<Category> categoryList = new ArrayList<>();
            Sheet categorySheet = workbook.getSheetAt(0);
            for (Row row : categorySheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String name = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                if (name.equals("")) {
                    log.error("name is null.....");
                    continue;
                }
                int sequence = (int) row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue();
                Category category = new Category();
                category.setName(name);
                category.setSequence(sequence);
                category.setMerchant(merchant);

                categoryList.add(category);
            }
            Map<String, Category> categoryMap = processCategory(merchantId, categoryList);
            for (Row row : categorySheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String name = row.getCell(0).getStringCellValue();
                int sequence = (int) row.getCell(1).getNumericCellValue();
                Category category = new Category();
                category.setName(name);
                category.setSequence(sequence);
                category.setMerchant(merchant);

                categoryList.add(category);
            }

            // 第二个sheet代表营业时间
            List<OpenRange> openRanges = new ArrayList<>();
            Sheet openRangeSheet = workbook.getSheetAt(1);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            for (Row row : openRangeSheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                int sequence = (int) row.getCell(1).getNumericCellValue();
                Date startTime = format.parse(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue() + ":00");
                Date endTime = format.parse(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue() + ":59");
                OpenRange openRange = new OpenRange();
                openRange.setBeginTime(startTime);
                openRange.setEndTime(endTime);
                openRange.setType(OpenRangeType.ON);
                openRange.setSequence(sequence);
                openRange.setMerchant(merchant);

                openRanges.add(openRange);
            }
            Map<Integer, OpenRange> openRangeMap = processOpenRange(merchantId, openRanges);

            // 第三个sheet代表产品
            List<Product> products = new ArrayList<>();
            Sheet productSheet = workbook.getSheetAt(2);
            for (Row row : productSheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Product product = new Product();

                String name = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                if (name.equals("")) {
                    log.error("name is null.....");
                    continue;
                }
                BigDecimal unitPrice = BigDecimal.valueOf(row.getCell(1).getNumericCellValue());
                boolean needPay = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue().equals("是") ? true : false;
                String code = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                int sequence = (int) row.getCell(1).getNumericCellValue();
                int payTimeLimit = (int) row.getCell(1).getNumericCellValue();
                ProductStatus productStatus = ProductStatus.getStatus(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue());
                String categoryName = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                String openRangeSequence = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                String description = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();

                product.setName(name);
                product.setUnitPrice(unitPrice);
                product.setDescription(description);
                product.setUnitsInStock(null);
                product.setInfinite(true);
                product.setNeedPay(needPay);
                product.setOpenRange(true);
                product.setPayTimeLimit(payTimeLimit);
                product.setTakeTimeLimit(null);
                product.setStatus(productStatus);
                product.setSequence(sequence);
                product.setCode(code);
                product.setMerchant(merchant);
                if (categoryMap.containsKey(categoryName)) {
                    product.setCategory(categoryMap.get(categoryName));
                }
                if (!openRangeSequence.equals("")) {
                    String[] openRangeSequences = openRangeSequence.split(",");
                    for (String sequenceStr : openRangeSequences) {
                        int openRangeValue = Integer.parseInt(sequenceStr);
                        if (openRangeMap.containsKey(openRangeValue)) {
                            product.getOpenRanges().add(openRangeMap.get(openRangeValue));
                        }
                    }
                }

                products.add(product);
            }

            processProduct(merchantId, products);
        } catch (Exception ex) {
            log.error("", ex);
        }

        log.info("end parse excel.......");
    }

    private Map<String, Category> processCategory(Long merchantId, List<Category> categories) {
        Map<String, Category> categoryMap = new HashMap<>();
        List<Category> dbCategories = storeService.findCategorysByMerchant(merchantId);
        Map<String, Category> dbCategoryMap = dbCategories.stream()
                .collect(Collectors.toMap(Category::getName, Function.identity()));
        categories.stream()
                .forEach(category -> {
                    if (dbCategoryMap.containsKey(category.getName())) {
                        Category dbCategory = dbCategoryMap.get(category.getName());
                        if (dbCategory.getSequence() != category.getSequence()) {
                            dbCategory.setSequence(category.getSequence());
                            dbCategory = storeService.updateCategory(dbCategory);
                        }
                        categoryMap.put(dbCategory.getName(), dbCategory);
                    } else {
                        Category dbCategory = storeService.saveCategory(category);
                        categoryMap.put(dbCategory.getName(), dbCategory);
                    }
                });

        return categoryMap;
    }

    public Map<Integer, OpenRange> processOpenRange(Long merchantId, List<OpenRange> openRanges) {
        Map<Integer, OpenRange> openRangeMap = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        List<OpenRange> dbOpenRanges = openRangeRepository.findByMerchant(merchantId);
        for (OpenRange openRange : openRanges) {
            String startTime = format.format(openRange.getBeginTime());
            String endTime = format.format(openRange.getEndTime());

            boolean exist = false;
            for (OpenRange dbOpenRange : dbOpenRanges) {
                String dbStartTime = format.format(dbOpenRange.getBeginTime());
                String dbEndTime = format.format(dbOpenRange.getEndTime());

                if (dbStartTime.equals(startTime) && dbEndTime.equals(endTime)) {
                    exist = true;
                    openRangeMap.put(openRange.getSequence(), dbOpenRange);
                    break;
                }
            }
            if (!exist) {
                OpenRange dbOpenRange = openRangeRepository.save(openRange);
                openRangeMap.put(openRange.getSequence(), dbOpenRange);
            }
        }

        return openRangeMap;
    }

    public void processProduct(Long merchantId, List<Product> products) {
        List<Product> dbProducts = productRepository.findAllByMerchant(merchantId);

        for (Product product : products) {
            boolean exist = false;
            for (Product dbProduct : dbProducts) {
                if (product.getName().equals(dbProduct.getName())) {
                    dbProduct.setName(product.getName());
                    dbProduct.setUnitPrice(product.getUnitPrice());
                    dbProduct.setDescription(product.getDescription());
                    dbProduct.setUnitsInStock(product.getUnitsInStock());
                    dbProduct.setInfinite(product.getInfinite());
                    dbProduct.setNeedPay(product.getNeedPay());
                    dbProduct.setOpenRange(product.getOpenRange());
                    dbProduct.setPayTimeLimit(product.getPayTimeLimit());
                    dbProduct.setTakeTimeLimit(product.getTakeTimeLimit());
                    dbProduct.setStatus(product.getStatus());
                    dbProduct.setCategory(product.getCategory());
                    dbProduct.setOpenRanges(product.getOpenRanges());
                    dbProduct.setSequence(product.getSequence());
                    dbProduct.setCode(product.getCode());
                    productRepository.save(dbProduct);

                    exist = true;
                    break;
                }
            }
            if (!exist) {
                productRepository.save(product);
            }
        }

    }
}
