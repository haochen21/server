package com.km086.server.process;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import com.km086.server.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.km086.server.message.SendCartJsonExecutor;
import com.km086.server.model.order.Cart;
import com.km086.server.model.order.CartStatus;
import com.km086.server.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class NoNeedPayCartMonitor {

    @Autowired
    protected OrderService cartService;

    @Autowired
    SendCartJsonExecutor sendCartJsonExecutor;

    protected DelayQueue<Cart> cartQueue;

    protected ExecutorService executor;

    @Autowired
    private ConfigProperties configProperties;

    private final static Logger logger = LoggerFactory.getLogger(NoNeedPayCartMonitor.class);

    public NoNeedPayCartMonitor() {

    }

    @PostConstruct
    public void start() {
        cartQueue = new DelayQueue<>();
        executor = Executors.newFixedThreadPool(configProperties.getNoNeedPayProcessNum());
        processCart();
        addNotClosedCartToQueue();
    }

    protected void processCart() {
        for (int i = 0; i < configProperties.getNoNeedPayProcessNum(); i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.interrupted()) {
                        Cart cart = null;
                        try {
                            cart = cartQueue.take();
                            Cart dbCart = cartService.cancelCart(cart.getId());
                            sendCartJsonExecutor.addCartToQueue(dbCart);
                            logger.info("canel cart," + dbCart);
                        } catch (Exception ex) {
                            logger.info("cart process error", ex);
                            if (cart != null) {
                                cart.setDelayTime(0);
                                cartQueue.put(cart);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * ϵͳ�ر�ʱ��һЩ�ͻ�����û�б����� ϵͳ����ʱ��װ�ؿͻ�����
     */
    protected void addNotClosedCartToQueue() {
        List<CartStatus> statuses = new ArrayList<>();
        statuses.add(CartStatus.CONFIRMED);
        // ����Ҫ֧������Ĭ��״̬Ϊ��CONFIRMED
        List<Cart> carts = cartService.findCartByPayAndStatus(false, statuses);

        for (Cart cart : carts) {
            addCartToQueue(cart);
        }
    }

    public void addCartToQueue(Cart cart) {
        if (!cart.getTakeOut()) {
            //只针对到店付
            Instant now = Instant.now();
            long delay = 0;
            if (now.isAfter(cart.getTakeEndTime().toInstant())) {
                delay = 0;
            } else {
                delay = Duration.between(now, cart.getTakeEndTime().toInstant()).toMillis();
            }
            cart.setDelayTime(delay);
            cartQueue.put(cart);
            logger.info("add cart to process queue," + cart.toString() + ",delay millseconds is: " + delay
                    + ",queue size is:" + cartQueue.size());
        }
    }

    public void removeCartInQueue(Cart cart) {
        cartQueue.remove(cart);
        logger.info("remove cart in queue," + cart.toString() + ",queue size is:" + cartQueue.size());
    }
}
