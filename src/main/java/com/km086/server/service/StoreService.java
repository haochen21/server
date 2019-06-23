package com.km086.server.service;

import java.util.List;

import com.km086.server.model.store.Category;
import com.km086.server.model.store.Product;
import com.km086.server.model.store.ProductStatus;

public interface StoreService {

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product findProduct(Long productId);

    Product findWithMerchant(Long id);

    List<Product> findProductsByMerchant(Long merchantId);

    void updateImageSource(Long id, String imageSource);

    List<Product> findProductsByMerchant(Long merchantId, ProductStatus status);

    List<Product> quickSearch(Long merchantId, String code);

    boolean existProductByName(Long merchantId, String name);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);

    Category findCategory(Long categoryId);

    List<Category> findCategorysByMerchant(Long merchantId);

    void updateNeedPay(boolean needPay,Long merchantId);
}
