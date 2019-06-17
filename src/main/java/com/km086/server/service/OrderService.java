package com.km086.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.km086.server.exception.BuyEmptyProductException;
import com.km086.server.exception.CartPaidException;
import com.km086.server.exception.CartStatusException;
import com.km086.server.exception.MerchantDiscountException;
import com.km086.server.exception.OffTimeException;
import com.km086.server.exception.ProductPriceException;
import com.km086.server.exception.TakeTimeException;
import com.km086.server.model.order.Cart;
import com.km086.server.model.order.CartFilter;
import com.km086.server.model.order.CartStatus;
import com.km086.server.model.order.CartStatusStat;
import com.km086.server.model.store.Product;

public interface OrderService {

    Cart purchaseCart(Cart cart) throws BuyEmptyProductException, ProductPriceException, TakeTimeException, MerchantDiscountException, OffTimeException;

    Cart payingCart(Long cartId) throws CartStatusException;

    Cart payingFailCart(Long cartId) throws CartStatusException;

    Cart paidCart(Long cartId) throws CartStatusException;

    Cart weixinPaidCart(String no, String transactionId) throws CartStatusException, CartPaidException;

    Cart deliverCart(Long cartId) throws CartStatusException;

    Cart cancelCart(Long cartId) throws CartStatusException;

    void deleteCart(Long cartId);

    Cart findCartByNo(String no);

    Cart findWithJsonData(Long id);

    List<Cart> findCartByOrder(Long merchantId, Long customerId, List<CartStatus> statuses);

    List<Cart> findCartByStatus(List<CartStatus> statuses);

    List<Cart> findCartByPayAndStatus(Boolean needPay, List<CartStatus> statuses);

    List<Cart> findCartByFilter(CartFilter filter, Pageable pageable);

    Page<Cart> pageCartByFilter(CartFilter filter, Pageable pageable);

    List<CartStatusStat> statCartByStatus(CartFilter filter);

    List<Product> statCartByProduct(CartFilter filter);

    Long statCartNumber(CartFilter filter);

    BigDecimal statCartEarning(CartFilter filter);

    Map<String, BigDecimal> statEarningByCreatedon(CartFilter filter);
}
