package com.azharuworld.lcbeerservice.events;

import com.azharuworld.lcbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@RequiredArgsConstructor
public class BeerEvent implements Serializable {
    public static long serialVersionUID = 370150518116847546L;
    private final BeerDto beerDto;

    }
