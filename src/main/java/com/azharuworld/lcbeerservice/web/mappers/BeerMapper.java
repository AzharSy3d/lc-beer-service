package com.azharuworld.lcbeerservice.web.mappers;

import com.azharuworld.lcbeerservice.model.Beer;
import com.azharuworld.lcbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper{

    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
