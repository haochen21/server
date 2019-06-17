package com.km086.server.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MerchantLogin implements Serializable {

    private Merchant merchant;

    private LoginResult result;

    private static final long serialVersionUID = 6323639586206221804L;

    @Override
    public String toString() {
        return "Login [merchant=" + merchant + ", result=" + result.getDescription() + "]";
    }
}
