package com.km086.server.repository.store;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.km086.server.model.store.Product;
import com.km086.server.model.store.ProductStatus;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

    @Query(value = "select p from Product p where p.merchant.id = :merchantId")
    List<Product> findAllByMerchant(@Param("merchantId") Long merchantId);

    @Query(value = "select p from Product p where p.merchant.id = :merchantId and p.status = :status")
    List<Product> findByMerchant(@Param("merchantId") Long merchantId, @Param("status") ProductStatus status);

    @Query(value = "select p from Product p LEFT JOIN FETCH p.category where p.merchant.id = :merchantId and p.status != 'DELETE'")
    List<Product> findByMerchantWithCategory(@Param("merchantId") Long merchantId);

    @Query(value = "select p from Product p where p.merchant.id = :merchantId and p.status != 'DELETE'")
    List<Product> findByMerchant(@Param("merchantId") Long merchantId);

    @Query(value = "select p from Product p LEFT JOIN FETCH p.category where p.id = :id and p.status != 'DELETE'")
    Product findWithMerchant(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE Product p set p.imageSource = :imageSource where p.id = :id and p.status != 'DELETE'")
    void updateImageSource(@Param("id") Long id, @Param("imageSource") String imageSource);

    @Query(value = "select p from Product p where p.merchant.id = :merchantId and p.status != 'DELETE' and ( p.code like %:code% or p.name like %:code%)")
    List<Product> quickSearch(@Param("merchantId") Long merchantId, @Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p where p.merchant.id = :merchantId and p.name = :name")
    Boolean existsByName(@Param("merchantId") Long merchantId, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE Product p set p.needPay = :needPay where p.merchant.id = :merchantId")
    void updateNeedPay(@Param("needPay") Boolean needPay, @Param("merchantId") Long merchantId);
}
