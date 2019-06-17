package com.km086.server.repository.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.km086.server.model.security.Device;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends BaseRepository<Device, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Device d where d.no = :no")
    Boolean existsByNo(@Param("no") String no);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Device d where d.phone = :phone")
    Boolean existsByPhone(@Param("phone") String phone);

    @Query(value = "select d from Device d where d.no = :no")
    Device findByNo(@Param("no") String no);

    @Query(value = "select d from Device d where d.phone = :phone")
    Device findByPhone(@Param("phone") String phone);
}
