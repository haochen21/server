package com.km086.server.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.km086.server.model.security.OrderAddress;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressRepository extends BaseRepository<OrderAddress, Long> {

    @Query(value = "select oa from OrderAddress oa where oa.customer.id = :id and oa.address = :address")
    OrderAddress findByAddress(@Param("id") Long id, @Param("address") String address);

    @Query(value = "select oa from OrderAddress oa where oa.customer.id = :id")
    List<OrderAddress> findByCustomer(@Param("id") Long id);
}
