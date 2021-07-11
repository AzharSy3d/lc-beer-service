package com.azsyed.lcbeerservice.web.mappers;

import com.azsyed.lcbeerservice.model.Beer;
import com.azsyed.lcbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
