package com.azsyed.lcbeerservice.services.bewinng;

import com.azsyed.lcbeerservice.config.JmsConfig;
import com.azsyed.model.events.BrewBeerEvent;
import com.azsyed.model.events.NewInventoryEvent;
import com.azsyed.lcbeerservice.model.Beer;
import com.azsyed.lcbeerservice.repositories.BeerRepository;
import com.azsyed.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listener(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        Beer beer = beerRepository.getById(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());
        log.info("BREWING_REQUEST_QUEUE Listening");
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }

}
