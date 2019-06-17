package com.km086.server.exception;

import com.km086.server.model.security.Merchant;

public class MerchantDiscountException extends Exception {

    private static final long serialVersionUID = -8505295394797901176L;

    public MerchantDiscountException(Merchant m, Float discount) {
        super("There is not equal discount," + m.getId() + ": " + m.getDiscount() + ", cartItem discount is:"
                + discount);
    }
}
