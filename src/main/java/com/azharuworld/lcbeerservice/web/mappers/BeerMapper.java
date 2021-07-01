package com.azharuworld.lcbeerservice.web.mappers;

import com.azharuworld.lcbeerservice.model.Beer;
import com.azharuworld.lcbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    public BeerDto beerToBeerDto(Beer beer);
    public Beer beerDtoToBeer(BeerDto beerDto);
}
