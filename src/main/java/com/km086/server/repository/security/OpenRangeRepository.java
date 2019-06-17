package com.km086.server.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.km086.server.model.security.OpenRange;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenRangeRepository extends BaseRepository<OpenRange, Long> {

    @Query(value = "select openRange from OpenRange openRange where openRange.merchant.id = :merchantId")
    List<OpenRange> findByMerchant(@Param("merchantId") Long merchantId);

}
