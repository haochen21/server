package com.km086.server.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.km086.server.model.Constants;
import com.km086.server.model.security.Customer;
import com.km086.server.model.security.Merchant;
import com.km086.server.model.security.NameDecodeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CART", indexes = {@Index(name = "IDX_CART_MERCHANT", columnList = "MERCHANT_ID"),
        @Index(name = "IDX_CART_CUSTOMER", columnList = "CUSTOMER_ID"), @Index(name = "IDX_CART_NO", columnList = "NO"),
        @Index(name = "IDX_WEIXIN_TRANSACTIONID", columnList = "TRANSACTIONID")})
public class Cart implements Serializable, Delayed {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "NO", unique = true, nullable = false)
    protected String no;

    @Column(name = "TRANSACTIONID")
    protected String transactionId;

    @Column(name = "NAME")
    @JsonSerialize(using = NameDecodeSerializer.class, as = String.class)
    protected String name;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "ADDRESS")
    protected String address;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    protected Merchant merchant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    protected Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected CartStatus status;

    @NotNull
    @Column(name = "NEEDPAY")
    protected Boolean needPay;

    @NotNull
    @Column(name = "TOTALPRICE")
    protected BigDecimal totalPrice;

    @Column(name = "PAYTIMELIMT")
    protected Integer payTimeLimit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYTIME")
    protected Date payTime;

    @Column(name = "TAKETIMELIMT")
    protected Integer takeTimeLimit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAKETIME")
    protected Date takeTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAKEBEGINTIME")
    protected Date takeBeginTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAKEENDTIME")
    protected Date takeEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.UpdateTimestamp
    protected Date updatedOn;

    @Column(name = "REMARK")
    @Size(min = 0, max = 255)
    protected String remark;

    // 外卖
    @Column(name = "TAKEOUT")
    protected Boolean takeOut;

    @Column(name = "TAKENO")
    protected String takeNo;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    protected Collection<CartItem> cartItems = new ArrayList<CartItem>();

    @Version
    protected long version;

    @Transient
    @JsonSerialize
    protected Boolean cardUsed = true;

    @Transient
    protected int payingNumber = 0;

    @Transient
    protected long triggerTime;

    private static final long serialVersionUID = -5938391683195581548L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((no == null) ? 0 : no.hashCode());
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
        Cart other = (Cart) obj;
        if (no == null) {
            if (other.no != null)
                return false;
        } else if (!no.equals(other.no))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [no=" + no + ", needPay=" + needPay + ", orderStatus=" + status + "]";
    }

    public void setDelayTime(long delayTime) {
        this.triggerTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed arg) {
        Cart that = (Cart) arg;
        if (triggerTime < that.triggerTime)
            return -1;
        if (triggerTime > that.triggerTime)
            return 1;
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delayed = unit.convert(triggerTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        return delayed;
    }

}
