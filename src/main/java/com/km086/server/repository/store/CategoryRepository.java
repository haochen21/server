package com.km086.server.repository.store;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.km086.server.model.store.Category;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

    @Query(value = "select c from Category c where c.merchant.id = :merchantId")
    List<Category> findByMerchant(@Param("merchantId") Long merchantId);
}
