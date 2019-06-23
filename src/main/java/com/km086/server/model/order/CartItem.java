package com.km086.server.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.km086.server.model.Constants;
import com.km086.server.model.store.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CARTITEM", uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_CARDITEM_PRODUCT_CART", columnNames = {"PRODUCT_ID", "CART_ID"})}, indexes = {
        @Index(name = "IDX_CARDITEM_CART", columnList = "CART_ID")})
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity;

    @NotNull
    @Column(name = "UNITPRICE", nullable = false)
    protected BigDecimal unitPrice;

    @NotNull
    @Column(name = "TOTALPRICE", nullable = false)
    protected BigDecimal totalPrice;

    @Column(name = "SELECTPROPERTIES")
    @Convert(converter = SelectProductPropertyConverter.class)
    protected List<SelectProductProperty> selectProductProperties = new ArrayList<>();

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    protected Product product;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID", nullable = false)
    @JsonBackReference
    protected Cart cart;

    private static final long serialVersionUID = 6852793237053469465L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cart == null) ? 0 : cart.getNo().hashCode());
        result = prime * result + ((product == null) ? 0 : product.getId().hashCode());
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
        CartItem other = (CartItem) obj;
        if (cart == null) {
            if (other.cart != null)
                return false;
        } else if (!cart.getNo().equals(other.cart.getNo()))
            return false;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.getId().equals(other.product.getId()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LineItem [name=" + name + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", product=" + product
                + ", cart=" + cart + "]";
    }

}
