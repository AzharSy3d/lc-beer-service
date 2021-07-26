package com.azsyed.lcbeerservice.services.inventory;

import com.azsyed.lcbeerservice.services.inventory.domain.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("local-discovery")
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerInventoryServiceFeignClientImpl implements BeerInventoryService {

    private final InventoryServiceFeignClient beerInventoryServiceFeign;

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> responseEntity = beerInventoryServiceFeign.getOnhandInventory(beerId);

        Integer quantityOnHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.info("BeerId :"+beerId+" OnHand is :"+quantityOnHand);

        return quantityOnHand;
    }
}
