package com.azsyed.lcbeerservice.services.order;

import com.azsyed.brewery.model.events.ValidateOrderRequest;
import com.azsyed.brewery.model.events.ValidateOrderResult;
import com.azsyed.lcbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

@RequiredArgsConstructor
public class ValidateOrderListener {
    private final BeerOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest){
        Boolean isValid = validator.validateOrder(validateOrderRequest.getBeerOrder());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                    .orderId(validateOrderRequest.getBeerOrder().getId())
                    .isValid(isValid).build());
    }
}
