package com.km086.server.model.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OrderResult implements Serializable {

    private boolean result;

    private String error;

    private Cart cart;

    private static final long serialVersionUID = 2027877347801446306L;

    @Override
    public String toString() {
        return "OrderResult [result=" + result + ", error=" + error + "]";
    }
}
