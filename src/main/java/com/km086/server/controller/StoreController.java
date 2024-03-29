package com.km086.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.km086.server.config.ConfigProperties;
import com.km086.server.service.ProductParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import com.km086.server.model.security.Merchant;
import com.km086.server.model.store.Category;
import com.km086.server.model.store.Product;
import com.km086.server.service.SecurityService;
import com.km086.server.service.StoreService;

@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    SecurityService securityService;

    @Autowired
    StoreService storeService;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    ProductParseService productParseService;

    private final static Logger logger = LoggerFactory.getLogger(StoreController.class);

    @RequestMapping(value = "/category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Category createCategory(@RequestBody Category category) {
        logger.info("create category: " + category.toString());
        Merchant merchant = securityService.findMerchant(category.getMerchant().getId());
        category.setMerchant(merchant);
        category = storeService.saveCategory(category);
        return category;
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Category modifyCategory(@RequestBody Category category) {
        category = storeService.updateCategory(category);
        return category;
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Boolean removeCategory(@PathVariable Long id) {
        try {
            storeService.deleteCategory(id);
            return true;
        } catch (Exception ex) {
            logger.info("remove category error...", ex);
            return false;
        }
    }

    @RequestMapping(value = "/category/merchant/{merchantId}", method = RequestMethod.GET)
    public List<Category> findCategoryByMerchant(@PathVariable Long merchantId) {
        List<Category> categorys = storeService.findCategorysByMerchant(merchantId);
        return categorys;
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET, produces = "application/json")
    public Category findCategory(@PathVariable Long id) {
        Category category = storeService.findCategory(id);
        return category;
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody Product product) {
        logger.info("create product: " + product.toString());
        Merchant merchant = securityService.findMerchant(product.getMerchant().getId());
        product.setMerchant(merchant);
        product = storeService.saveProduct(product);
        return product;
    }

    @RequestMapping(value = "/product", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Product modifyProduct(@RequestBody Product product) {
        product = storeService.updateProduct(product);
        return product;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = "application/json")
    public Product findProduct(@PathVariable Long id) {
        Product product = storeService.findWithMerchant(id);
        return product;
    }

    @RequestMapping(value = "/product/merchant/{merchantId}", method = RequestMethod.GET)
    public List<Product> findProductByMerchant(@PathVariable Long merchantId) {
        List<Product> products = storeService.findProductsByMerchant(merchantId);
        return products;
    }

    @RequestMapping(value = "/product/quicksearch/{merchantId}/{code}", method = RequestMethod.GET)
    public List<Product> quickSearch(@PathVariable Long merchantId, @PathVariable String code) {
        List<Product> products = storeService.quickSearch(merchantId, code);
        return products;
    }

    @RequestMapping(value = "/product/exist/{merchantId}/{name}", method = RequestMethod.GET)
    public @ResponseBody
    Boolean existsProductByName(@PathVariable Long merchantId, @PathVariable String name) {
        return storeService.existProductByName(merchantId, name);
    }

    @RequestMapping(value = "/product/needPay", method = RequestMethod.PUT)
    public void updateProductNeedPayByMerchant(@RequestParam(value = "merchantId", required = true) Long merchantId,
                                               @RequestParam(value = "needPay", required = true) boolean needPay) {
        storeService.updateNeedPay(needPay, merchantId);
    }

    @CrossOrigin
    @RequestMapping(value = "/product/image", method = RequestMethod.POST)
    public @ResponseBody
    String uploadProductImage(@RequestParam("file") MultipartFile file,
                              @RequestParam("loginName") String loginName, @RequestParam("productId") Long productId) {
        logger.info("upload file,loginName: " + loginName + ",productId: " + productId);
        String fileName = null;
        if (!file.isEmpty()) {
            try {
                fileName = loginName + "-" + productId + "-" + file.getOriginalFilename();

                File dir = new File(configProperties.getImageDir());
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                int endIndex = fileName.lastIndexOf(".");
                fileName = fileName.substring(0, endIndex);
                Thumbnails.of(file.getInputStream()).size(400, 400).outputFormat("png").toFile(new File(dir, fileName + "-md"));
                Thumbnails.of(file.getInputStream()).size(50, 50).outputFormat("png").toFile(new File(dir, fileName + "-xs"));
                Thumbnails.of(file.getInputStream()).size(62, 62).outputFormat("png").toFile(new File(dir, fileName + "-sm"));
                storeService.updateImageSource(productId, fileName);
                return fileName;
            } catch (Exception ex) {
                logger.info("upload file error...", ex);
                return "";
            }
        } else {
            return "";
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/product/image/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadUserAvatarImage(@PathVariable String fileName)
            throws IOException {
        File image = new File(configProperties.getImageDir() + File.separator + fileName + ".png");
        InputStream in = new FileInputStream(image);
        return ResponseEntity.ok().contentLength(in.available())
                .contentType(MediaType.parseMediaType("image/png"))
                .body(new InputStreamResource(in));
    }

    @CrossOrigin
    @RequestMapping(value = "/upload/excel", method = RequestMethod.POST)
    @ResponseBody
    public String uploadProductExcel(@RequestParam("file") MultipartFile file,
                                     @RequestParam("merchantId") Long merchantId) {
        logger.info("upload excel,merchantId: {}.", merchantId);
        try {
            productParseService.parse(merchantId, file.getInputStream());
        } catch (Exception ex) {
            return "error";
        }
        return "success";
    }
}
