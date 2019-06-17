package com.km086.server.repository.security;

import com.km086.server.model.security.Merchant;

public interface MerchantRepositoryCustom {

    Merchant login(String login);
}
