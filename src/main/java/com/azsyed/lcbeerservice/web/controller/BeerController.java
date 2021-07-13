package com.azsyed.lcbeerservice.web.controller;


import com.azsyed.brewery.model.BeerDto;
import com.azsyed.brewery.model.BeerPagedList;
import com.azsyed.brewery.model.BeerStyleEnum;
import com.azsyed.lcbeerservice.services.BeerService;
import com.azsyed.lcbeerservice.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private final BeerService beerService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(required = false) Integer pageNumber,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) String beerName,
                                                   @RequestParam(required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(required = false) Boolean showInventoryOnHand) {

        showInventoryOnHand = defaultShowInventoryOnHand(showInventoryOnHand);

        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize < 1){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<>(beerService.listBeers(beerName,beerStyle, PageRequest.of(pageNumber,pageSize),showInventoryOnHand),HttpStatus.OK);
    }


    @GetMapping("/upc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUPC(@PathVariable("upc") String upc,
                                                @RequestParam(required = false) Boolean showInventoryOnHand){
        return new ResponseEntity<>(beerService.getByUPC(upc,DataUtil.nullSafe(showInventoryOnHand)),HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId,
                                           @RequestParam(required = false) Boolean showInventoryOnHand) {

        return new ResponseEntity<>(beerService.getById(beerId, DataUtil.nullSafe(showInventoryOnHand)), HttpStatus.OK);
    }



    @PutMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto),HttpStatus.CREATED);
    }

    @PostMapping("/{beerId}")
    public  ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeer(beerId,beerDto),HttpStatus.NO_CONTENT);
    }

    private Boolean defaultShowInventoryOnHand(Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return showInventoryOnHand;
    }
}
