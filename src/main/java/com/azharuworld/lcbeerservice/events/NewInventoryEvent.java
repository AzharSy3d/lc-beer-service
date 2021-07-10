package com.azharuworld.lcbeerservice.events;

import com.azharuworld.lcbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
