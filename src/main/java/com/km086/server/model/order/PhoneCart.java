package com.km086.server.model.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.km086.server.model.order.PhoneCart.Content.CartItem;
import com.km086.server.model.order.PhoneCart.Content.Customer;
import com.km086.server.model.order.PhoneCart.Content.Merchant;
import com.km086.server.model.security.NickNameEnCode;

public class PhoneCart {
    /**
     * content : [{"id":1071,"no":"57f2f658581b525a6fe88537","transactionId":"4003242001201610045744250044","merchant":{"id":961,"loginName":"黄林店铺","openId":null,"name":"黄林店铺","password":"9A0E4BFD7B4E92D635C0F2D478BAB2E8","phone":"18016388149","mail":"930109220@qq.com","city":null,"province":null,"country":null,"headImgUrl":null,"account":null,"createdOn":1475072461000,"type":"M","deviceNo":"36074020000426","shortName":"黄林店铺","address":"上海浦东","description":null,"open":true,"takeByPhone":true,"takeByPhoneSuffix":true,"imageSource":null,"qrCode":null,"categorys":null,"products":null,"carts":null,"openRanges":null},"customer":{"id":933,"loginName":"黄林","openId":"oV3Nlt1UHRWT_uzzJm5RfNNLf5FE","name":"黄林","password":"E10ADC3949BA59ABBE56E057F20F883E","phone":"18016388149","mail":"930109220@qq.com","city":"Pudong New District","province":"Shanghai","country":"China","headImgUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEL62gBMwH22RhicE6HILpwgdSJvGYEXYqlh0rV6O6JAyjbacP2BxcYicKiaTVvMIIpTXicj7xdP4E3XXQ/0","account":null,"createdOn":1474986202000,"type":"C","cardNo":"2803627074","cardUsed":true,"carts":null,"merchants":null},"status":3,"needPay":true,"totalPrice":0.01,"payTimeLimit":10,"payTime":1475541168000,"takeTimeLimit":20,"takeTime":1475541768000,"takeBeginTime":1475542500000,"takeEndTime":1475555159000,"createdOn":1475540568000,"updatedOn":1475540579000,"cartItems":[{"id":1076,"name":"目录1下的商品","quantity":1,"unitPrice":0.01,"totalPrice":0.01,"product":null}],"version":1,"cardUsed":null}]
     * totalPages : 1
     * totalElements : 1
     * last : true
     * numberOfElements : 1
     * first : true
     * sort : null
     * size : 1
     * number : 0
     */
    private int totalPages;
    private long totalElements;
    private boolean last;
    private int numberOfElements;
    private boolean first;
    private Object sort;
    private int size;
    private int number;
    /**
     * id : 1071
     * no : 57f2f658581b525a6fe88537
     * transactionId : 4003242001201610045744250044
     * merchant : {"id":961,"loginName":"黄林店铺","openId":null,"name":"黄林店铺","password":"9A0E4BFD7B4E92D635C0F2D478BAB2E8","phone":"18016388149","mail":"930109220@qq.com","city":null,"province":null,"country":null,"headImgUrl":null,"account":null,"createdOn":1475072461000,"type":"M","deviceNo":"36074020000426","shortName":"黄林店铺","address":"上海浦东","description":null,"open":true,"takeByPhone":true,"takeByPhoneSuffix":true,"imageSource":null,"qrCode":null,"categorys":null,"products":null,"carts":null,"openRanges":null}
     * customer : {"id":933,"loginName":"黄林","openId":"oV3Nlt1UHRWT_uzzJm5RfNNLf5FE","name":"黄林","password":"E10ADC3949BA59ABBE56E057F20F883E","phone":"18016388149","mail":"930109220@qq.com","city":"Pudong New District","province":"Shanghai","country":"China","headImgUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEL62gBMwH22RhicE6HILpwgdSJvGYEXYqlh0rV6O6JAyjbacP2BxcYicKiaTVvMIIpTXicj7xdP4E3XXQ/0","account":null,"createdOn":1474986202000,"type":"C","cardNo":"2803627074","cardUsed":true,"carts":null,"merchants":null}
     * status : 3
     * needPay : true
     * totalPrice : 0.01
     * payTimeLimit : 10
     * payTime : 1475541168000
     * takeTimeLimit : 20
     * takeTime : 1475541768000
     * takeBeginTime : 1475542500000
     * takeEndTime : 1475555159000
     * createdOn : 1475540568000
     * updatedOn : 1475540579000
     * cartItems : [{"id":1076,"name":"目录1下的商品","quantity":1,"unitPrice":0.01,"totalPrice":0.01,"product":null}]
     * version : 1
     * cardUsed : null
     */
    private List<Content> content;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public static class Content {
        private long id;
        private String no;
        private String transactionId;
        /**
         * id : 961
         * loginName : 黄林店铺
         * openId : null
         * name : 黄林店铺
         * password : 9A0E4BFD7B4E92D635C0F2D478BAB2E8
         * phone : 18016388149
         * mail : 930109220@qq.com
         * city : null
         * province : null
         * country : null
         * headImgUrl : null
         * account : null
         * createdOn : 1475072461000
         * type : M
         * deviceNo : 36074020000426
         * shortName : 黄林店铺
         * address : 上海浦东
         * description : null
         * open : true
         * takeByPhone : true
         * takeByPhoneSuffix : true
         * imageSource : null
         * qrCode : null
         * categorys : null
         * products : null
         * carts : null
         * openRanges : null
         */
        private Merchant merchant;
        /**
         * id : 933
         * loginName : 黄林
         * openId : oV3Nlt1UHRWT_uzzJm5RfNNLf5FE
         * name : 黄林
         * password : E10ADC3949BA59ABBE56E057F20F883E
         * phone : 18016388149
         * mail : 930109220@qq.com
         * city : Pudong New District
         * province : Shanghai
         * country : China
         * headImgUrl : http://wx.qlogo.cn/mmopen/PiajxSqBRaEL62gBMwH22RhicE6HILpwgdSJvGYEXYqlh0rV6O6JAyjbacP2BxcYicKiaTVvMIIpTXicj7xdP4E3XXQ/0
         * account : null
         * createdOn : 1474986202000
         * type : C
         * cardNo : 2803627074
         * cardUsed : true
         * carts : null
         * merchants : null
         */

        private Customer customer;
        private int status;
        private boolean needPay;
        private BigDecimal totalPrice;
        private int payTimeLimit;
        private long payTime;
        private int takeTimeLimit;
        private long takeTime;
        private long takeBeginTime;
        private long takeEndTime;
        private long createdOn;
        private long updatedOn;
        private long version;
        private boolean cardUsed;
        /**
         * id : 1076
         * name : 目录1下的商品
         * quantity : 1
         * unitPrice : 0.01
         * totalPrice : 0.01
         * product : null
         */
        private List<CartItem> cartItems;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
            this.merchant = merchant;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isNeedPay() {
            return needPay;
        }

        public void setNeedPay(boolean needPay) {
            this.needPay = needPay;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getPayTimeLimit() {
            return payTimeLimit;
        }

        public void setPayTimeLimit(int payTimeLimit) {
            this.payTimeLimit = payTimeLimit;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public int getTakeTimeLimit() {
            return takeTimeLimit;
        }

        public void setTakeTimeLimit(int takeTimeLimit) {
            this.takeTimeLimit = takeTimeLimit;
        }

        public long getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(long takeTime) {
            this.takeTime = takeTime;
        }

        public long getTakeBeginTime() {
            return takeBeginTime;
        }

        public void setTakeBeginTime(long takeBeginTime) {
            this.takeBeginTime = takeBeginTime;
        }

        public long getTakeEndTime() {
            return takeEndTime;
        }

        public void setTakeEndTime(long takeEndTime) {
            this.takeEndTime = takeEndTime;
        }

        public long getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(long createdOn) {
            this.createdOn = createdOn;
        }

        public long getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(long updatedOn) {
            this.updatedOn = updatedOn;
        }

        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }

        public boolean getCardUsed() {
            return cardUsed;
        }

        public void setCardUsed(boolean cardUsed) {
            this.cardUsed = cardUsed;
        }

        public List<CartItem> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }

        public static class Merchant {
            private long id;
            private String loginName;
            private Object openId;
            private String name;
            private String password;
            private String phone;
            private String mail;
            private Object city;
            private Object province;
            private Object country;
            private Object headImgUrl;
            private Object account;
            private long createdOn;
            private String type;
            private String deviceNo;
            private String shortName;
            private String address;
            private Object description;
            private boolean open;
            private boolean takeByPhone;
            private boolean takeByPhoneSuffix;
            private Object imageSource;
            private Object qrCode;
            private Object categorys;
            private Object products;
            private Object carts;
            private Object openRanges;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public Object getOpenId() {
                return openId;
            }

            public void setOpenId(Object openId) {
                this.openId = openId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getCountry() {
                return country;
            }

            public void setCountry(Object country) {
                this.country = country;
            }

            public Object getHeadImgUrl() {
                return headImgUrl;
            }

            public void setHeadImgUrl(Object headImgUrl) {
                this.headImgUrl = headImgUrl;
            }

            public Object getAccount() {
                return account;
            }

            public void setAccount(Object account) {
                this.account = account;
            }

            public long getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(long createdOn) {
                this.createdOn = createdOn;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDeviceNo() {
                return deviceNo;
            }

            public void setDeviceNo(String deviceNo) {
                this.deviceNo = deviceNo;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public boolean isOpen() {
                return open;
            }

            public void setOpen(boolean open) {
                this.open = open;
            }

            public boolean isTakeByPhone() {
                return takeByPhone;
            }

            public void setTakeByPhone(boolean takeByPhone) {
                this.takeByPhone = takeByPhone;
            }

            public boolean isTakeByPhoneSuffix() {
                return takeByPhoneSuffix;
            }

            public void setTakeByPhoneSuffix(boolean takeByPhoneSuffix) {
                this.takeByPhoneSuffix = takeByPhoneSuffix;
            }

            public Object getImageSource() {
                return imageSource;
            }

            public void setImageSource(Object imageSource) {
                this.imageSource = imageSource;
            }

            public Object getQrCode() {
                return qrCode;
            }

            public void setQrCode(Object qrCode) {
                this.qrCode = qrCode;
            }

            public Object getCategorys() {
                return categorys;
            }

            public void setCategorys(Object categorys) {
                this.categorys = categorys;
            }

            public Object getProducts() {
                return products;
            }

            public void setProducts(Object products) {
                this.products = products;
            }

            public Object getCarts() {
                return carts;
            }

            public void setCarts(Object carts) {
                this.carts = carts;
            }

            public Object getOpenRanges() {
                return openRanges;
            }

            public void setOpenRanges(Object openRanges) {
                this.openRanges = openRanges;
            }
        }

        public static class Customer {
            private long id;
            private String loginName;
            private String openId;
            private String name;
            private String password;
            private String phone;
            private String mail;
            private String city;
            private String province;
            private String country;
            private String headImgUrl;
            private Object account;
            private long createdOn;
            private String type;
            private String cardNo;
            private boolean cardUsed;
            private Object carts;
            private Object merchants;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getHeadImgUrl() {
                return headImgUrl;
            }

            public void setHeadImgUrl(String headImgUrl) {
                this.headImgUrl = headImgUrl;
            }

            public Object getAccount() {
                return account;
            }

            public void setAccount(Object account) {
                this.account = account;
            }

            public long getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(long createdOn) {
                this.createdOn = createdOn;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public boolean isCardUsed() {
                return cardUsed;
            }

            public void setCardUsed(boolean cardUsed) {
                this.cardUsed = cardUsed;
            }

            public Object getCarts() {
                return carts;
            }

            public void setCarts(Object carts) {
                this.carts = carts;
            }

            public Object getMerchants() {
                return merchants;
            }

            public void setMerchants(Object merchants) {
                this.merchants = merchants;
            }
        }

        public static class CartItem {
            private long id;
            private String name;
            private int quantity;
            private BigDecimal unitPrice;
            private BigDecimal totalPrice;
            private Object product;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public BigDecimal getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(BigDecimal unitPrice) {
                this.unitPrice = unitPrice;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }

            public Object getProduct() {
                return product;
            }

            public void setProduct(Object product) {
                this.product = product;
            }
        }
    }
    
    public static PhoneCart getPhoneCart(Page<Cart> page) {
    	PhoneCart phoneCart = new PhoneCart();
    	phoneCart.setTotalPages(page.getTotalPages());    	
        phoneCart.setTotalElements(page.getTotalElements());
        phoneCart.setLast(page.isLast());
        phoneCart.setNumberOfElements(page.getNumberOfElements());
        phoneCart.setFirst(page.isFirst());
        phoneCart.setSort(null);
        phoneCart.setSize(page.getSize());
        phoneCart.setNumber(page.getNumber());
        
        List<Content> contents = new ArrayList<>();
        for(Cart cart : page.getContent()) {
        	 Content content = new Content();        	 
        	 content.setId(cart.getId());
        	 content.setNo(cart.getNo());             
             content.setTransactionId(cart.getTransactionId());
             content.setStatus(cart.getStatus().ordinal());
             content.setNeedPay(cart.getNeedPay());
             content.setTotalPrice(cart.getTotalPrice());
             content.setPayTimeLimit(cart.getPayTimeLimit());
             content.setPayTime(cart.getPayTime().getTime());
             content.setTakeTimeLimit(cart.getTakeTimeLimit());
             content.setTakeTime(cart.getTakeTime().getTime());
             content.setTakeBeginTime(cart.getTakeBeginTime().getTime());
             content.setTakeEndTime(cart.getTakeEndTime().getTime());
             content.setCreatedOn(cart.getCreatedOn().getTime());
             content.setUpdatedOn(cart.getUpdatedOn().getTime());
             content.setVersion(cart.getVersion());
             content.setCardUsed(cart.getCardUsed());
             
             Merchant merchant = new Merchant();
             merchant.setId(cart.getMerchant().getId());
             merchant.setLoginName(NickNameEnCode.INSTANCE.decode(cart.getMerchant().getLoginName()));
             merchant.setOpenId(cart.getMerchant().getOpenId());
             merchant.setName(cart.getMerchant().getName());
             merchant.setPassword(cart.getMerchant().getPassword());
             merchant.setPhone(cart.getMerchant().getPhone());
             merchant.setMail(cart.getMerchant().getMail());
             merchant.setCity(cart.getMerchant().getCity());
             merchant.setProvince(cart.getMerchant().getProvince());
             merchant.setCountry(cart.getMerchant().getCountry());
             merchant.setHeadImgUrl(cart.getMerchant().getHeadImgUrl());
             merchant.setAccount(cart.getMerchant().getAmount());             
             merchant.setCreatedOn(cart.getMerchant().getCreatedOn().getTime());             
             merchant.setType(null);            
             merchant.setDeviceNo(cart.getMerchant().getDeviceNo());
             merchant.setShortName(cart.getMerchant().getShortName());
             merchant.setAddress(cart.getMerchant().getAddress());
             merchant.setDescription(cart.getMerchant().getDescription());
             merchant.setOpen(cart.getMerchant().getOpen());
             merchant.setTakeByPhone(cart.getMerchant().getTakeByPhone());
             merchant.setTakeByPhoneSuffix(cart.getMerchant().getTakeByPhoneSuffix());
             merchant.setImageSource(cart.getMerchant().getImageSource());
             merchant.setQrCode(cart.getMerchant().getQrCode());
             merchant.setCategorys(null);            
             merchant.setProducts(null);
             merchant.setCarts(null);
             merchant.setOpenRanges(null);
             
             content.setMerchant(merchant);
             
             Customer customer = new Customer();
             customer.setId(cart.getCustomer().getId());
             customer.setLoginName(NickNameEnCode.INSTANCE.decode(cart.getCustomer().getLoginName()));
             customer.setOpenId(cart.getCustomer().getOpenId());
             customer.setName(NickNameEnCode.INSTANCE.decode(cart.getCustomer().getName()));
             customer.setPassword(cart.getCustomer().getPassword());
             customer.setPhone(cart.getCustomer().getPhone());
             customer.setMail(cart.getCustomer().getMail());
             customer.setCity(cart.getCustomer().getCity());
             customer.setProvince(cart.getCustomer().getProvince());
             customer.setCountry(cart.getCustomer().getCountry());
             customer.setHeadImgUrl(cart.getCustomer().getHeadImgUrl());
             customer.setAccount(cart.getCustomer().getAccount());
             customer.setCreatedOn(cart.getCustomer().getCreatedOn().getTime());
             customer.setType("");
             customer.setCardNo(cart.getCustomer().getCardNo());
             customer.setCardUsed(cart.getCustomer().getCardUsed());
             customer.setCarts(null);
             customer.setMerchants(null);

             content.setCustomer(customer);
             
             List<CartItem> cartItems = new ArrayList<>();
             for(com.km086.server.model.order.CartItem ci : cart.getCartItems()) {
            	 CartItem cartItem = new CartItem();
            	 cartItem.setId(ci.getId());
            	 cartItem.setName(ci.getName());
            	 cartItem.setQuantity(ci.getQuantity());
            	 cartItem.setUnitPrice(ci.getUnitPrice());
            	 cartItem.setTotalPrice(ci.getTotalPrice());
            	 cartItem.setProduct(null);
            	 
            	 cartItems.add(cartItem);
             }
             content.setCartItems(cartItems);
             
             contents.add(content);
        }
        
        phoneCart.setContent(contents);
        
    	return phoneCart;
    }
}

