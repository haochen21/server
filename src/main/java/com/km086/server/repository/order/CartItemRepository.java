package com.km086.server.repository.order;

import com.km086.server.model.order.CartItem;
import com.km086.server.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem, Long> {

}
