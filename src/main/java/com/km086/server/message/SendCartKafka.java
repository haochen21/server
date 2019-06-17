package com.km086.server.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import com.km086.server.model.order.Cart;
import com.km086.server.model.order.CartStatus;
import com.km086.server.service.OrderService;

@Component
public class SendCartKafka {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    OrderService orderService;

    private final static Logger logger = LoggerFactory.getLogger(SendCartKafka.class);

    public SendCartKafka() {

    }

    public void send(Cart cart) {
        try {
            Cart jsonCart = orderService.findWithJsonData(cart.getId());

            ObjectMapper mapper = new ObjectMapper();
            Hibernate5Module model = new Hibernate5Module();
            model.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
            mapper.registerModule(model);
            String cartJson = mapper.writeValueAsString(jsonCart);

            String topic = "order";
            kafkaTemplate.send(topic, cartJson);

            if (jsonCart.getMerchant().getPrintNo() != null && !jsonCart.getMerchant().getPrintNo().equals("") && cart.getStatus() == CartStatus.CONFIRMED) {
                String[] printNos = jsonCart.getMerchant().getPrintNo().split(",");
                for (String printNo : printNos) {
                    String printTopic = "print-" + printNo;
                    logger.info("printNo is: {}", printNo);
                    kafkaTemplate.send(printTopic, cartJson);
                }
            }
        } catch (Exception ex) {
            logger.info("kafka cart json fail!", ex);
        }
    }

    public void manualPrint(Long cartId) {
        try {
            Cart jsonCart = orderService.findWithJsonData(cartId);

            ObjectMapper mapper = new ObjectMapper();
            Hibernate5Module model = new Hibernate5Module();
            model.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
            mapper.registerModule(model);
            String cartJson = mapper.writeValueAsString(jsonCart);

            if (jsonCart.getMerchant().getPrintNo() != null && !jsonCart.getMerchant().getPrintNo().equals("") && jsonCart.getStatus() == CartStatus.CONFIRMED) {
                String[] printNos = jsonCart.getMerchant().getPrintNo().split(",");
                for (String printNo : printNos) {
                    String printTopic = "print-" + printNo;
                    kafkaTemplate.send(printTopic, cartJson);
                    logger.info("manual print topic {},cart {}", printTopic, jsonCart.getId());
                }
            }
        } catch (Exception ex) {
            logger.info("kafka cart json fail!", ex);
        }
    }
}
