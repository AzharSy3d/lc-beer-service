package com.azsyed.lcbeerservice.services.bewinng;

import com.azsyed.lcbeerservice.config.JmsConfig;
import com.azsyed.common.events.BrewBeerEvent;
import com.azsyed.lcbeerservice.model.Beer;
import com.azsyed.lcbeerservice.repositories.BeerRepository;
import com.azsyed.lcbeerservice.services.inventory.BeerInventoryService;
import com.azsyed.lcbeerservice.web.mappers.BeerMapper;
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

    @Scheduled(fixedDelay=3000)
    public void checkForInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());
            log.info("checkForInventory Scheduled");
            log.info("minOnHand is :"+beer.getMinOnHand());


            if(beer.getMinOnHand() >= invQOH){
                log.info("qtyInHand is :"+invQOH);
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }


        });
    }


}
