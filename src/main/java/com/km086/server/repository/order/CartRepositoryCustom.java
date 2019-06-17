package com.km086.server.repository.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.km086.server.model.order.Cart;
import com.km086.server.model.order.CartFilter;
import com.km086.server.model.order.CartProductStat;
import com.km086.server.model.order.CartStatusStat;

public interface CartRepositoryCustom {

    List<Cart> findByFilter(CartFilter filter, Integer startIndex, Integer pageSize);

    Long countByFilter(CartFilter filter);

    List<CartStatusStat> statByStatus(CartFilter filter);

    List<CartProductStat> statByProduct(CartFilter filter);

    Long statCartNumber(CartFilter filter);

    BigDecimal statCartEarning(CartFilter filter);

    Map<String, BigDecimal> statEarningByCreatedon(CartFilter filter);
}
