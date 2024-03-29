package com.km086.server.service;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km086.server.cache.CustomerCache;
import com.km086.server.config.ConfigProperties;
import com.km086.server.model.security.Customer;
import com.km086.server.model.security.CustomerLogin;
import com.km086.server.model.security.Device;
import com.km086.server.model.security.LoginResult;
import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.MerchantIntro;
import com.km086.server.model.security.MerchantLogin;
import com.km086.server.model.security.NickNameEnCode;
import com.km086.server.model.security.OpenRange;
import com.km086.server.model.security.Password;
import com.km086.server.model.store.Product;
import com.km086.server.repository.security.CustomerRepository;
import com.km086.server.repository.security.DeviceRepository;
import com.km086.server.repository.security.MerchantRepository;
import com.km086.server.repository.security.OpenRangeRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    OpenRangeRepository openRangeRepository;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    CustomerCache customerCache;
    
    private final static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public Customer findCustomerByOpenId(String openId) {
    	if(customerCache.findCustomer(openId).isPresent()) {
    		return customerCache.findCustomer(openId).get();
    	}else {
    		logger.info("openId: {} is null....",openId);
    		return null;
    	}
    }

    @Override
    public Merchant findMerchantByOpenId(String openId) {
        return merchantRepository.findByOpenId(openId);
    }

    @Override
    public Boolean existsCustomerByOpenId(String openId) {
        return customerRepository.existsByOpenId(openId);
    }

    @Override
    public Boolean existsMerchantByOpenId(String openId) {
        return merchantRepository.existsByOpenId(openId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Merchant saveMerchant(Merchant merchant) {
        String pwd = Password.PASSWORD.MD5(merchant.getPassword());
        merchant.setPassword(pwd);
        return merchantRepository.save(merchant);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Merchant updateMerchant(Merchant merchant) {
        Merchant dbMerchant = merchantRepository.findById(merchant.getId()).get();

        dbMerchant.setName(merchant.getName());
        if (merchant.getDeviceNo() != null) {
            dbMerchant.setDeviceNo(merchant.getDeviceNo());
        }
        dbMerchant.setPhone(merchant.getPhone());
        dbMerchant.setMail(merchant.getMail());
        dbMerchant.setShortName(merchant.getShortName());
        dbMerchant.setAddress(merchant.getAddress());
        dbMerchant.setDescription(merchant.getDescription());
        if (merchant.getOpen() != null) {
            dbMerchant.setOpen(merchant.getOpen());
        }
        dbMerchant.setTakeByPhone(merchant.getTakeByPhone());
        dbMerchant.setTakeByPhoneSuffix(merchant.getTakeByPhoneSuffix());
        if (merchant.getDiscountType() != null) {
            dbMerchant.setDiscountType(merchant.getDiscountType());
        }
        if (merchant.getDiscount() != null) {
            dbMerchant.setDiscount(merchant.getDiscount());
        }
        if (merchant.getAmount() != null) {
            dbMerchant.setAmount(merchant.getAmount());
        }
        dbMerchant.setMinimumOrder(merchant.getMinimumOrder());
        dbMerchant.setShowIntroduce(merchant.getShowIntroduce());
        dbMerchant.setPackageFee(merchant.getPackageFee());
        dbMerchant.setPrintVoice(merchant.getPrintVoice());
        return merchantRepository.save(dbMerchant);
    }

    @Override
    public Merchant findMerchant(Long merchantId) {
        return merchantRepository.findById(merchantId).get();
    }

    @Override
    public Merchant findMerchantWithIntroduce(Long merchantId) {
        Merchant merchant = merchantRepository.findWithIntroduce(merchantId);
        return merchant;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchantOpen(Long id, Boolean open) {
        merchantRepository.updateOpen(id, open);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchantTakeOut(Long id, Boolean takeOut) {
        merchantRepository.updateTakeOut(id, takeOut);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchantQrCode(Long id, String qrCode) {
        merchantRepository.updateQrCode(id, qrCode);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchantIntroduce(Long id, String introduce) {
        Merchant merchant = merchantRepository.findById(id).get();
        MerchantIntro merchantIntro = merchant.getIntroduce();
        if (merchantIntro == null) {
            merchantIntro = new MerchantIntro();
            merchantIntro.setIntroduce(introduce);
            merchant.setIntroduce(merchantIntro);
        } else {
            merchantIntro.setIntroduce(introduce);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void registerMerchant(Long id, String deviceNo, String phone) {
        merchantRepository.register(id, deviceNo, phone);
    }

    @Override
    public Merchant findMerchantByDeviceNo(String deviceNo) {
        return merchantRepository.findByDeviceNo(deviceNo);
    }

    @Override
    public CustomerLogin customerLogin(String loginName, String password) {
        CustomerLogin login = new CustomerLogin();
        String encodeLoginName = NickNameEnCode.INSTANCE.encode(loginName);
        Customer customer = customerRepository.findByLoginName(encodeLoginName);

        if (customer == null) {
            login.setResult(LoginResult.LOGINNAMEERROR);
        } else {
            String pwd = Password.PASSWORD.MD5(password);
            if (!customer.getPassword().equals(pwd)) {
                login.setResult(LoginResult.PASSWORDERROR);
            } else {
                login.setResult(LoginResult.AUTHORIZED);
                customer.setPassword("");
                login.setCustomer(customer);
            }
        }
        return login;
    }

    @Override
    public MerchantLogin merchantLogin(String loginName, String password) {
        MerchantLogin login = new MerchantLogin();
        Merchant merchant;
        try {
            merchant = merchantRepository.login(loginName);
        } catch (Exception ex) {
            merchant = null;
        }
        if (merchant == null) {
            login.setResult(LoginResult.LOGINNAMEERROR);
        } else if (merchant.getApproved() == null || !merchant.getApproved()) {
            login.setResult(LoginResult.APPROVEDERROR);
        } else {
            String superPassword = configProperties.getSuperPassword();
            String pwd = Password.PASSWORD.MD5(password);
            if (!password.equals(superPassword) && !merchant.getPassword().equals(pwd)) {
                login.setResult(LoginResult.PASSWORDERROR);
            } else {
                login.setResult(LoginResult.AUTHORIZED);
                merchant.setPassword("");
                login.setMerchant(merchant);
            }
        }
        return login;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Customer saveCustomer(Customer customer) {
        String pwd = Password.PASSWORD.MD5(customer.getPassword());
        customer.setPassword(pwd);
        Long customerId = customerRepository.save(customer).getId();
        Customer dbCustomer = customerRepository.findById(customerId).get();
        customerCache.addOrUpdateCustomer(dbCustomer);
        return dbCustomer;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Customer updateCustomer(Customer customer) {
        Customer dbCustomer = customerRepository.findById(customer.getId()).get();

        dbCustomer.setName(customer.getName());
        if (customer.getLoginName() != null) {
            dbCustomer.setLoginName(customer.getLoginName());
        }
        if (customer.getCity() != null) {
            dbCustomer.setCity(customer.getCity());
        }
        if (customer.getProvince() != null) {
            dbCustomer.setProvince(customer.getProvince());
        }
        if (customer.getCountry() != null) {
            dbCustomer.setCountry(customer.getCountry());
        }
        if (customer.getHeadImgUrl() != null) {
            dbCustomer.setHeadImgUrl(customer.getHeadImgUrl());
        }
        dbCustomer.setCardNo(customer.getCardNo());
        dbCustomer.setPhone(customer.getPhone());
        dbCustomer.setMail(customer.getMail());
        dbCustomer.setCardUsed(customer.getCardUsed());
        dbCustomer.setAddress(customer.getAddress());       
        
        dbCustomer.getMerchants().size();

        dbCustomer = customerRepository.save(dbCustomer);
        
        customerCache.addOrUpdateCustomer(dbCustomer);
        
        return dbCustomer;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean updateCustomerPhone(Long id, String phone) {
        boolean isPhoneExist = customerRepository.existsByPhone(phone);
        if (isPhoneExist) {
            return false;
        } else {
            customerRepository.updatePhone(id, phone);
        }
        return true;
    }

    @Override
    public Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Override
    public Customer findCustomerByCardNo(String cardNo) {
        return customerRepository.findByCardNo(cardNo);
    }

    @Override
    public List<Customer> findCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public Customer findCustomerByFullPhone(String phone) {
        return customerRepository.findByFullPhone(phone);
    }

    @Override
    public Customer findCustomerWithOrderAddress(Long customerId) {
        return customerRepository.findWithOrderAddress(customerId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyCustomerPassword(Long id, String password) {
        String pwd = Password.PASSWORD.MD5(password);
        Customer customer = customerRepository.findById(id).get();
        customer.setPassword(pwd);
        customer.getMerchants().size();
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyMerchantPassword(Long id, String password) {
        String pwd = Password.PASSWORD.MD5(password);
        Merchant merchant = merchantRepository.findById(id).get();
        merchant.setPassword(pwd);
        merchantRepository.save(merchant);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Merchant updateOpenRange(Long merchantId, Collection<OpenRange> ranges) {
        Merchant merchant = merchantRepository.getReference(Merchant.class, merchantId);
        List<OpenRange> dbOpenRanges = openRangeRepository.findByMerchant(merchantId);

        List<OpenRange> deleteOpenRanges = new ArrayList<>();
        for (OpenRange openRange : dbOpenRanges) {
            boolean exist = false;
            for (OpenRange range : ranges) {
                if (range.getId() != null && range.getId().equals(openRange.getId())) {
                    openRange.setBeginTime(range.getBeginTime());
                    openRange.setEndTime(range.getEndTime());
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                deleteOpenRanges.add(openRange);
            }
        }
        for (OpenRange openRange : deleteOpenRanges) {
            for (Product product : openRange.getProducts()) {
                product.getOpenRanges().remove(openRange);
            }
            openRangeRepository.delete(openRange);
        }

        for (OpenRange range : ranges) {
            if (range.getId() == null) {
                range.setMerchant(merchant);
                range = openRangeRepository.save(range);
            }
        }
        openRangeRepository.getEm().flush();
        openRangeRepository.getEm().clear();

        Merchant dbMerchant = merchantRepository.findWithOpenRange(merchantId);
        LocalTime zeroTime = LocalTime.now();
        zeroTime = zeroTime.with(ChronoField.HOUR_OF_DAY, 0);
        zeroTime = zeroTime.with(ChronoField.MINUTE_OF_HOUR, 0);
        zeroTime = zeroTime.with(ChronoField.SECOND_OF_MINUTE, 0);
        zeroTime = zeroTime.with(ChronoField.MILLI_OF_SECOND, 0);
        for (OpenRange op : dbMerchant.getOpenRanges()) {
            if (op.getBeginTime() == null) {
                op.setBeginTime(java.sql.Time.valueOf(zeroTime));
            }
            if (op.getEndTime() == null) {
                op.setEndTime(java.sql.Time.valueOf(zeroTime));
            }
        }
        return dbMerchant;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchantImageSource(Long id, String imageSource) {
        merchantRepository.updateImageSource(id, imageSource);
    }

    @Override
    public Merchant findMerchantWithOpenRange(Long id) {
        Merchant merchant = merchantRepository.findWithOpenRange(id);
        LocalTime zeroTime = LocalTime.now();
        zeroTime = zeroTime.with(ChronoField.HOUR_OF_DAY, 0);
        zeroTime = zeroTime.with(ChronoField.MINUTE_OF_HOUR, 0);
        zeroTime = zeroTime.with(ChronoField.SECOND_OF_MINUTE, 0);
        zeroTime = zeroTime.with(ChronoField.MILLI_OF_SECOND, 0);
        for (OpenRange op : merchant.getOpenRanges()) {
            if (op.getBeginTime() == null) {
                op.setBeginTime(java.sql.Time.valueOf(zeroTime));
            }
            if (op.getEndTime() == null) {
                op.setEndTime(java.sql.Time.valueOf(zeroTime));
            }
        }
        return merchant;
    }

    @Override
    public Boolean existsCustomerByLoginName(String loginName) {
        return customerRepository.existsByLoginName(loginName);
    }

    @Override
    public Boolean existsMerchantByLoginName(String loginName) {
        return merchantRepository.existsByLoginName(loginName);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }

    @Override
    public Device findByPhone(String phone) {
        return deviceRepository.findByPhone(phone);
    }

    @Override
    public Device findByNo(String no) {
        return deviceRepository.findByNo(no);
    }

    @Override
    public Boolean existsDeviceByNo(String no) {
        return deviceRepository.existsByNo(no);
    }

    @Override
    public Boolean existsDeviceByPhone(String phone) {
        return deviceRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByCardNo(String cardNo) {
        return customerRepository.existsByCardNo(cardNo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Set<Merchant> saveMerchantsOfCustomer(Long customerId, Set<Long> merchantIds) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.getMerchants().clear();
        for (Long merchantId : merchantIds) {
            Merchant m = merchantRepository.findById(merchantId).get();
            customer.getMerchants().add(m);
        }
        customerRepository.save(customer);
        return customer.getMerchants();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addMerchantOfCustomer(Long customerId, Long merchantId) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.getMerchants();
        Merchant m = merchantRepository.findById(merchantId).get();
        if (m != null) {
            List<Merchant> childMerchants = merchantRepository.findByParentId(merchantId);
            if (childMerchants != null && childMerchants.size() > 0) {
                customer.getMerchants().addAll(childMerchants);
            } else {

                customer.getMerchants().add(m);
            }
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Set<Merchant> findMerchantsOfCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.getMerchants().size();
        return customer.getMerchants();
    }

    @Override
    public List<Merchant> findMerchantsByName(String name) {
        return merchantRepository.findByName(name);
    }

    @Override
    public Boolean existsCustomerByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsMerchantByPhone(String phone) {
        return merchantRepository.existsByPhone(phone);
    }

    @Override
    public List<OpenRange> findOpenRangeByMerchant(Long merchantId) {
        return openRangeRepository.findByMerchant(merchantId);
    }

}
