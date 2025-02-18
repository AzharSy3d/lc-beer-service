package com.azsyed.lcbeerservice.services.bewinng;

import com.azsyed.brewery.model.events.BrewBeerEvent;
import com.azsyed.lcbeerservice.config.JmsConfig;
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

    @Scheduled(fixedDelay=5000)
    
    /**
     * Checks if there is sufficient inventory for all items in the cart.
     *
     * This method iterates through each item in the shopping cart and checks if the available quantity
     * of each item meets or exceeds the required quantity. If any item is found to be out of stock, it
     * throws an InsufficientInventoryException with a detailed message indicating which item is missing.
     *
     * @throws InsufficientInventoryException if there is not enough inventory for all items in the cart.
     */
    /**
     * /**
     * * Checks the current inventory levels and updates any necessary stock.
     * * This method ensures that all products have sufficient quantities to meet customer demand.
     * * It also triggers restocking procedures if inventory falls below a predefined threshold.
     * *
     * * @return void
     * */
     */

    public void checkForInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
            log.info("checkForInventory Scheduled");
            log.info("minOnHand is :"+beer.getMinOnHand());


            if(beer.getMinOnHand() > invQOH){
                log.info("qtyInHand is :"+invQOH);
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }


        });
    }


}
