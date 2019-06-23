package com.km086.server.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.km086.server.model.Constants;
import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.OpenRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT", uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_PRODUCT_MERCHANT_NAME", columnNames = {"MERCHANT_ID", "NAME"})}, indexes = {
        @Index(name = "IDX_PRODUCT_MERCHANT", columnList = "MERCHANT_ID")})
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "UNITPRICE", nullable = false)
    protected BigDecimal unitPrice;

    @Column(name = "DESCRIPTION")
    @Size(min = 0, max = 255)
    protected String description;

    @Column(name = "UNITSINSTOCK")
    protected Long unitsInStock;

    @Column(name = "UNITSINORDER")
    protected Long unitsInOrder;

    @NotNull
    @Column(name = "INFINITE", nullable = false)
    protected Boolean infinite;

    @NotNull
    @Column(name = "NEEDPAY", nullable = false)
    protected Boolean needPay;

    //允许在当前营业时间段下单
    @NotNull
    @Column(name = "OPENRANGE", nullable = false)
    protected Boolean openRange;

    @NotNull
    @Column(name = "PAYTIMELIMT", nullable = false)
    protected Integer payTimeLimit;

    @NotNull
    @Column(name = "TAKETIMELIMT", nullable = false)
    protected Integer takeTimeLimit;

    @Column(name = "IMAGESOURCE")
    @Size(max = 255)
    protected String imageSource;

    @Column(name = "CODE")
    @Size(max = 10)
    protected String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp
    protected Date updatedOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected ProductStatus status;

    @Column(name = "SEQUENCE")
    protected Integer sequence;

    @Column(name = "PROPERTIES")
    @Convert(converter = ProductPropertyConverter.class)
    protected List<ProductProperty> productProperties = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    protected Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    protected Merchant merchant;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "PRODUCT_OPENRANGE", joinColumns = @JoinColumn(name = "PRODUCT_ID"), inverseJoinColumns = @JoinColumn(name = "OPENRANGE_ID"))
    protected Set<OpenRange> openRanges = new HashSet<OpenRange>();

    @Version
    protected long version;

    @Transient
    @JsonSerialize
    protected Long takeNumber;

    @Transient
    @JsonSerialize
    protected Long unTakeNumber;

    private static final long serialVersionUID = 3277060162706927687L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (merchant == null) {
            if (other.merchant != null)
                return false;
        } else if (!merchant.equals(other.merchant))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", infinite=" + infinite + ", needPay=" + needPay + ", unitsInStock="
                + unitsInStock + ", unitsInOrder=" + unitsInOrder + ", status=" + status + ", merchant=" + merchant
                + "]";
    }
}
