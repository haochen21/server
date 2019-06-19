package com.km086.server.model.security;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.km086.server.model.Constants;
import com.km086.server.model.order.Cart;
import com.km086.server.model.store.Category;
import com.km086.server.model.store.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MERCHANT", indexes = {@Index(name = "IDX_MERCHANT_LOGINNAME", columnList = "LOGINNAME"),
        @Index(name = "IDX_MERCHANT_DEVICENO", columnList = "DEVICENO"),
        @Index(name = "IDX_MERCHANT_PHONE", columnList = "PHONE"),
        @Index(name = "IDX_MERCHANT_OPENID", columnList = "OPENID")})
public class Merchant implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "LOGINNAME", unique = true, nullable = false)
    @JsonSerialize(using = NameDecodeSerializer.class, as = String.class)
    protected String loginName;

    @Column(name = "OPENID")
    protected String openId;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "APPROVED")
    protected Boolean approved;

    @NotNull
    @Column(name = "PSW", nullable = false)
    protected String password;

    @Column(name = "DEVICENO", unique = true)
    protected String deviceNo;

    @Column(name = "PRINTNO")
    @Size(min = 0, max = 255)
    protected String printNo;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "MAIL")
    protected String mail;

    @Column(name = "CITY")
    protected String city;

    @Column(name = "PROVINCE")
    protected String province;

    @Column(name = "COUNTRY")
    protected String country;

    @Column(name = "HEADIMGURL")
    protected String headImgUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    protected Date createdOn;

    @Column(name = "SHORTNAME")
    protected String shortName;

    @Column(name = "ADDRESS")
    protected String address;

    @Column(name = "DESCRIPTION")
    @Size(min = 0, max = 255)
    protected String description;

    @Column(name = "OPEN")
    protected Boolean open;

    @Column(name = "TAKEBYPHONE")
    protected Boolean takeByPhone;

    @Column(name = "TAKEBYPHONESUFFIX")
    protected Boolean takeByPhoneSuffix;

    @Column(name = "IMAGESOURCE")
    @Size(min = 0, max = 255)
    protected String imageSource;

    @Column(name = "QRCODE")
    @Size(min = 0, max = 255)
    protected String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISCOUNTTYPE")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected DiscountType discountType;

    // 百分比折扣
    @Column(name = "DISCOUNT")
    protected Float discount;

    // 减价折扣
    @Column(name = "AMOUNT")
    protected Float amount;

    // 外卖
    @Column(name = "TAKEOUT")
    protected Boolean takeOut;

    // 最小订单数额
    @Column(name = "MINIMUMORDER")
    protected BigDecimal minimumOrder;

    // 打包费用
    @Column(name = "PACKAGEFEE")
    protected BigDecimal packageFee;

    @Column(name = "PARENTID")
    protected Long parentId;

    @Column(name = "SHOWINTRODUCE")
    protected Boolean showIntroduce;

    @Column(name = "PRINTVOICE")
    protected Boolean printVoice;

    @OneToMany(mappedBy = "merchant", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    protected Collection<Category> categorys = new ArrayList<Category>();

    @OneToMany(mappedBy = "merchant", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    protected Collection<Product> products = new ArrayList<Product>();

    @OneToMany(mappedBy = "merchant", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    protected Collection<Cart> carts = new ArrayList<Cart>();

    @OneToMany(mappedBy = "merchant", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference
    protected Collection<OpenRange> openRanges = new ArrayList<OpenRange>();

    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    protected MerchantIntro introduce;

    private static final long serialVersionUID = -1573726069064463313L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((deviceNo == null) ? 0 : deviceNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Merchant other = (Merchant) obj;
        if (deviceNo == null) {
            if (other.deviceNo != null)
                return false;
        } else if (!deviceNo.equals(other.deviceNo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Merchant [loginName=" + loginName + ",deviceNo=" + deviceNo + ", shortName=" + shortName + "]";
    }
}
