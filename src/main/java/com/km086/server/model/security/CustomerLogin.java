package com.km086.server.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CustomerLogin implements Serializable {

    private Customer customer;

    private LoginResult result;

    private static final long serialVersionUID = 6323639586206221804L;

    @Override
    public String toString() {
        return "Login [customer=" + customer + ", result=" + result.getDescription() + "]";
    }
}
