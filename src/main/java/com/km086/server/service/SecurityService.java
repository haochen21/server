package com.km086.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.km086.server.model.security.Customer;
import com.km086.server.model.security.CustomerLogin;
import com.km086.server.model.security.Device;
import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.MerchantLogin;
import com.km086.server.model.security.OpenRange;

public interface SecurityService {

    Customer findCustomerByOpenId(String openId);

    Merchant findMerchantByOpenId(String openId);

    Boolean existsCustomerByOpenId(String openId);

    Boolean existsMerchantByOpenId(String openId);

    Merchant saveMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    Merchant findMerchant(Long merchantId);

    Merchant findMerchantWithIntroduce(Long merchantId);

    Merchant findMerchantWithOpenRange(Long id);

    Merchant findMerchantByDeviceNo(String deviceNo);

    void updateMerchantOpen(Long id, Boolean open);

    void updateMerchantTakeOut(Long id, Boolean takeOut);

    void updateMerchantImageSource(Long id, String imageSource);

    void updateMerchantQrCode(Long id, String qrCode);

    void updateMerchantIntroduce(Long id, String introduce);

    void registerMerchant(Long id, String deviceNo, String phone);

    Merchant updateOpenRange(Long merchantId, Collection<OpenRange> ranges);

    CustomerLogin customerLogin(String loginName, String password);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer findCustomer(Long customerId);

    Customer findCustomerByCardNo(String cardNo);

    List<Customer> findCustomerByPhone(String phone);

    Customer findCustomerByFullPhone(String phone);

    Customer findCustomerWithOrderAddress(Long customerId);

    Boolean updateCustomerPhone(Long id, String phone);

    MerchantLogin merchantLogin(String loginName, String password);

    void modifyCustomerPassword(Long id, String password);

    void modifyMerchantPassword(Long id, String password);

    Boolean existsCustomerByLoginName(String loginName);

    Boolean existsMerchantByLoginName(String loginName);

    Device createDevice(Device device);

    void deleteDevice(Device device);

    Device findByNo(String no);

    Device findByPhone(String phone);

    Boolean existsDeviceByNo(String no);

    Boolean existsDeviceByPhone(String phone);

    Boolean existsByCardNo(String cardNo);

    Boolean existsCustomerByPhone(String phone);

    Boolean existsMerchantByPhone(String phone);

    Set<Merchant> saveMerchantsOfCustomer(Long customerId, Set<Long> merchantIds);

    Set<Merchant> findMerchantsOfCustomer(Long customerId);

    void addMerchantOfCustomer(Long customerId, Long merchantId);

    List<Merchant> findMerchantsByName(String name);

    List<OpenRange> findOpenRangeByMerchant(Long merchantId);
}
