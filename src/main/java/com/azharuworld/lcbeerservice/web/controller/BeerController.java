package com.azharuworld.lcbeerservice.web.controller;


import com.azharuworld.lcbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        //todo Impl
        return new ResponseEntity<>(BeerDto.builder().build(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        //todo Impl
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{beerId}")
    public  ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto){
        //todo Impl
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}