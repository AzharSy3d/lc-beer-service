package com.azsyed.lcbeerservice.services;

import com.azsyed.brewery.model.BeerDto;
import com.azsyed.brewery.model.BeerPagedList;
import com.azsyed.brewery.model.BeerStyleEnum;
import com.azsyed.lcbeerservice.model.Beer;
import com.azsyed.lcbeerservice.repositories.BeerRepository;
import com.azsyed.lcbeerservice.web.controller.NotFoundException;
import com.azsyed.lcbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    
    /**
     * Lists beers based on the provided filters and pagination parameters. Optionally includes inventory on hand information.
     *
     * @param beerName        The name of the beer to filter by (case-insensitive).
     * @param beerStyle       The style of the beer to filter by.
     * @param pageRequest     Pagination details such as page number and size.
     * @param showInventoryOnHand Whether to include inventory on hand information in the results.
     * @return A BeerPagedList containing the filtered list of beers and pagination metadata.
     */
    
    /**
     * Lists beers based on the provided criteria.
     *
     * @param beerName       The name of the beer to filter by, or null for no filtering.
     * @param beerStyle      The style of the beer to filter by, or null for no filtering.
     * @param pageRequest    The pagination details to control the results returned.
     * @param showInventoryOnHand  Whether to include inventory on hand in the results.
     * @return A PagedList containing the filtered beers.
     */
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        System.out.println("I am called listCache");
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest.of(beerPage
                            .getPageable()
                            .getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());


        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest.of(beerPage
                            .getPageable()
                            .getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    
    /**
     * Retrieves detailed information about a beer by its unique ID, optionally including inventory on hand details.
     *
     * @param beerId The unique identifier of the beer (UUID format).
     * @param showInventoryOnHand If true, includes inventory on hand details in the response; otherwise, excludes them.
     * @return A BeerDto object containing detailed information about the beer. Returns null if no beer is found with the given ID.
     */
    
    /**
     * Fetches a beer details by its unique identifier from the database.
     *
     * @param beerId The unique identifier of the beer (UUID format).
     * @param showInventoryOnHand Flag indicating whether to include inventory on hand in the response.
     * @return A BeerDto object containing the requested beer's details; throws NotFoundException if the beer is not found.
     */
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        System.out.println("I am called beerCache");
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        } else {
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
    }




    @Override
    
    /**
     * Saves a new beer to the database.
     *
     * @param beerDto The data transfer object containing details of the beer to be saved.
     * @return The saved BeerDto object with any server-generated fields (e.g., ID) populated.
     */
    
    /**
     * Saves a new beer record to the database using the provided BeerDto object.
     *
     * @param beerDto The data transfer object containing details about the new beer to be saved.
     * @return The updated BeerDto object with any generated or modified fields, such as an assigned ID.
     */
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    
    /**
     * Updates an existing beer record in the database with new information provided.
     *
     * @param beerId The unique identifier of the beer to be updated (UUID format).
     * @param beerDto A data transfer object containing the updated details of the beer.
     * @return An updated BeerDto object representing the modified beer record.
     */
    
    /**
     * Updates an existing beer in the database with the provided details.
     *
     * @param beerId The unique identifier of the beer to update (UUID format).
     * @param beerDto A DTO containing the updated information for the beer.
     * @return The updated BeerDto object if successful; throws BeerNotFoundException otherwise.
     */
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto)
    {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }


    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == false")
    @Override
    
    /**
     * Retrieves the beer details associated with a given UPC code.
     *
     * @param upc The unique product code of the beer.
     * @param showInventoryOnHand Indicates whether to include inventory on hand information in the response.
     * @return A BeerDto object containing the beer details, or null if no beer is found with the specified UPC.
     */
    
    /**
     * Fetches a BeerDto based on its Unique Product Code (UPC).
     *
     * @param upc The UPC code of the beer to retrieve.
     * @param showInventoryOnHand Indicates whether to include inventory information in the response.
     * @return A BeerDto object containing details about the specified beer, including inventory if requested.
     */
    public  BeerDto getByUPC(String upc, Boolean showInventoryOnHand){

        if(showInventoryOnHand){
            return  beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc));
        }else{
            return  beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
        }
    }
}
