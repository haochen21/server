package com.km086.server.service;

import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.OpenRange;
import com.km086.server.model.security.OpenRangeType;
import com.km086.server.model.store.Category;
import com.km086.server.model.store.Product;
import com.km086.server.model.store.ProductProperty;
import com.km086.server.model.store.ProductStatus;
import com.km086.server.repository.security.OpenRangeRepository;
import com.km086.server.repository.store.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * delete from product_openrange where product_id in ( select id from product where merchant_id =4009548)
 *
 * delete from cartitem where product_id in ( select id from product where merchant_id =4009548)
 *
 * delete from cart where merchant_id =4009548
 *
 * delete from product where merchant_id =4009548
 */

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

    @Transactional
    @Override
    public void parse(Long merchantId, InputStream inputStream) {
        log.info("start parse excel......");
        try {
            Merchant merchant = securityService.findMerchant(merchantId);
            log.info("merchant id is: {}.", merchant.getId());

            Workbook workbook = new XSSFWorkbook(inputStream);
            // 第一个sheet代表营业时间
            List<OpenRange> openRanges = new ArrayList<>();
            Sheet openRangeSheet = workbook.getSheetAt(0);
            for (Row row : openRangeSheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                int sequence = (int) row.getCell(0).getNumericCellValue();
                Date startTime = row.getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getDateCellValue();
                Date endTime = row.getCell(2, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getDateCellValue();
                OpenRange openRange = new OpenRange();
                openRange.setBeginTime(startTime);
                openRange.setEndTime(endTime);
                openRange.setType(OpenRangeType.ON);
                openRange.setSequence(sequence);
                openRange.setMerchant(merchant);

                openRanges.add(openRange);
            }
            Map<Integer, OpenRange> openRangeMap = processOpenRange(merchantId, openRanges);

            // 第二个sheet代表商品属性
            List<ProductProperty> productProperties = new ArrayList<>();
            Sheet productPropertySheet = workbook.getSheetAt(1);

            for (Row row : productPropertySheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                int sequence = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                String values = row.getCell(2, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                String defaultValue = row.getCell(3, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();

                ProductProperty productProperty = new ProductProperty();
                productProperty.setSequence(sequence);
                productProperty.setName(name);
                productProperty.setValues(Arrays.stream(values.split("=")).collect(Collectors.toList()));
                productProperty.setDefaultValue(defaultValue);

                productProperties.add(productProperty);
            }
            Map<Integer, ProductProperty> productPropertyMap =  productProperties.stream().collect(Collectors.toMap(ProductProperty::getSequence,
                    Function.identity()));

            // 第三个sheet代表商品，这里先处理分类
            Map<String, Category> rawCcategoryMap = new HashMap<>();
            Sheet categorySheet = workbook.getSheetAt(2);
            for (Row row : categorySheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String name = row.getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                if (name.equals("")) {
                    log.error("name is null.....");
                    continue;
                }
                if (rawCcategoryMap.containsKey(name)) {
                    continue;
                }
                int sequence = (int) row.getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getNumericCellValue();
                Category category = new Category();
                category.setName(name);
                category.setSequence(sequence);
                category.setMerchant(merchant);

                rawCcategoryMap.put(name, category);
            }
            Map<String, Category> categoryMap = processCategory(merchantId, rawCcategoryMap.values());


            // 处理商品
            List<Product> products = new ArrayList<>();
            Sheet productSheet = workbook.getSheetAt(2);
            for (Row row : productSheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Product product = new Product();

                String categoryName = row.getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                String name = row.getCell(2, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                int sequence = (int) row.getCell(3).getNumericCellValue();
                BigDecimal unitPrice = BigDecimal.valueOf(row.getCell(4).getNumericCellValue());
                DataFormatter formatter = new DataFormatter();
                String code = "";
                if (row.getCell(5) != null) {
                    code = formatter.formatCellValue(row.getCell(5, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK));
                }
                String propertySequence = "";
                if (row.getCell(6) != null) {
                    propertySequence = formatter.formatCellValue(row.getCell(6, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK));
                }

                String openRangeSequence = "";
                if (row.getCell(7) != null) {
                    openRangeSequence = formatter.formatCellValue(row.getCell(7, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK));
                }

                String description = "";
                if (row.getCell(8) != null) {
                    description = row.getCell(8, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
                }
                product.setName(name);
                product.setUnitPrice(unitPrice);
                product.setDescription(description);
                product.setUnitsInStock(null);
                product.setInfinite(true);
                product.setNeedPay(true);
                // 允许在当前营业时间段下单
                product.setOpenRange(true);
                product.setPayTimeLimit(10);
                product.setTakeTimeLimit(0);
                product.setStatus(ProductStatus.ONLINE);
                product.setSequence(sequence);
                product.setCode(code);
                product.setMerchant(merchant);
                product.setUnitsInOrder(0L);
                product.setUnitsInStock(0L);
                product.setCategory(categoryMap.get(categoryName));
                if (!propertySequence.equals("")) {
                    String[] propertySequences = propertySequence.split("=");
                    for (String sequenceStr : propertySequences) {
                        int propertySequenceValue = Integer.parseInt(sequenceStr);
                        if (productPropertyMap.containsKey(propertySequenceValue)) {
                            product.getProductProperties().add(productPropertyMap.get(propertySequenceValue));
                        }
                    }
                }
                if (!openRangeSequence.equals("")) {
                    String[] openRangeSequences = openRangeSequence.split("=");
                    for (String sequenceStr : openRangeSequences) {
                        int openRangeValue = Integer.parseInt(sequenceStr);
                        if (openRangeMap.containsKey(openRangeValue)) {
                            openRangeMap.get(openRangeValue).setMerchant(merchant);
                            openRangeMap.get(openRangeValue).setProducts(null);
                            product.getOpenRanges().add(openRangeMap.get(openRangeValue));
                        }
                    }
                }

                products.add(product);
            }

            processProduct(merchantId, products);
        } catch (Exception ex) {
            log.error("", ex);
            throw new RuntimeException(ex.getMessage());
        }

        log.info("end parse excel.......");
    }

    private Map<String, Category> processCategory(Long merchantId, Collection<Category> categories) {
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
                    dbProduct.setProductProperties(product.getProductProperties());
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
