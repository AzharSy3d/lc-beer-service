package com.azsyed.brewery.model.events;

import com.azsyed.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {
    public static long serialVersionUID = 370150518116847546L;
    private BeerDto beerDto;
    }
