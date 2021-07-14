package com.azsyed.lcbeerservice.services.order;

import com.azsyed.brewery.model.events.BeerOrderDto;
import com.azsyed.lcbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidator {
    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrder){
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrder.getBeerOrderLines().forEach(orderLine ->{
            if(beerRepository.findByUpc(orderLine.getUpc())==null){
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.get()==0;
    }
}
