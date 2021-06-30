package com.azharuworld.lcbeerservice.bootstrap;

import com.azharuworld.lcbeerservice.model.Beer;
import com.azharuworld.lcbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
    }

    private void loadBeerData() {

        if(beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("King Fisher")
                    .beerStyle("PALE_ALE")
                    .upc(21213213L)
                    .price(BigDecimal.valueOf(12.34))
                    .minOnHand(200)
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("COLA")
                    .beerStyle("IPA")
                    .upc(212132133L)
                    .price(BigDecimal.valueOf(14.34))
                    .minOnHand(200)
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Vodka")
                    .beerStyle("IPA")
                    .upc(212132135L)
                    .price(BigDecimal.valueOf(13.44))
                    .minOnHand(200)
                    .build());
        }
    }
}
