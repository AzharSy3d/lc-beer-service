package com.azharuworld.lcbeerservice.services;

import com.azharuworld.lcbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    Object getById(UUID beerId);

    Object saveNewBeer(BeerDto beerDto);

    Object updateBeer(UUID beerId, BeerDto beerDto);
}
