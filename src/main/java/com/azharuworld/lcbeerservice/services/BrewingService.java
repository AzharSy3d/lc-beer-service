package com.azharuworld.lcbeerservice.services;

import com.azharuworld.lcbeerservice.config.JmsConfig;
import com.azharuworld.lcbeerservice.events.BrewBeerEvent;
import com.azharuworld.lcbeerservice.model.Beer;
import com.azharuworld.lcbeerservice.repositories.BeerRepository;
import com.azharuworld.lcbeerservice.services.inventory.BeerInventoryService;
import com.azharuworld.lcbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {
    private final BeerRepository beerRepository;
    private final JmsTemplate  jmsTemplate;
    private final BeerMapper  beerMapper;
    private final BeerInventoryService beerInventoryService;

    @Scheduled(fixedDelay=2000)
    public void checkForInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("minOnHand is :"+beer.getMinOnHand());
            log.debug("qtyInHand is :"+invQOH);

            if(beer.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }


        });
    }


}
