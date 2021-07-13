package com.azsyed.lcbeerservice.services;

import com.azsyed.model.BeerDto;
import com.azsyed.model.BeerPagedList;
import com.azsyed.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    BeerDto getByUPC(String upc, Boolean showInventoryOnHand);
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) ;
}
