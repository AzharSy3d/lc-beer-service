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
     * Certainly! Below is the Javadoc for the `listBeers` method:
     * 
     * 
     * /**
     * * Retrieves a paged list of beers based on the specified criteria.
     * *
     * * @param beerName             The name of the beer to search for. Can be null if not applicable.
     * * @param beerStyle            The style of the beer, e.g., IPA, Lager. Can be null if not applicable.
     * * @param pageRequest          A {@link PageRequest} object containing pagination and sorting information.
     * * @param showInventoryOnHand  If true, includes the current inventory on hand for each beer; otherwise, excludes it.
     * * @return                     A {@link BeerPagedList} containing the paged list of beers that match the criteria.
     * */
     * BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
     * 
     * 
     * ### Detailed Explanation:
     * 
     * - **@param beerName**: The name of the beer to search for. This parameter is optional and can be `null` if you do not want to filter by beer name.
     * 
     * - **@param beerStyle**: The style of the beer, such as IPA or Lager. This parameter is also optional and can be `null` if you do not want to filter by beer style.
     * 
     * - **@param pageRequest**: A {@link PageRequest} object that contains information about pagination and sorting, such as the page number and sort criteria.
     * 
     * - **@param showInventoryOnHand**: A boolean flag indicating whether to include the current inventory on hand for each beer in the response. If set to `true`, the inventory data will be included; if set to `false`, it will be excluded.
     * 
     * - **@return**: Returns a {@link BeerPagedList} object, which is a paged list containing the beers that match the specified criteria. The pagination and sorting information is taken from the provided {@link PageRequest} object.
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
                            /**
                             * Certainly! Below is the Javadoc for the `getById` method:
                             * 
                             * 
                             * /**
                             * * Retrieves a beer by its unique identifier.
                             * *
                             * * @param beerId             the unique identifier of the beer to retrieve
                             * * @param showInventoryOnHand whether to include the current inventory on hand in the response
                             * * @return a {@link BeerDto} representing the retrieved beer, or null if no beer is found with the given ID
                             * * @throws IllegalArgumentException if the provided beerId is null
                             * */
                             * BeerDto getById(UUID beerId, Boolean showInventoryOnHand) throws IllegalArgumentException;
                             * 
                             * 
                             * ### Explanation:
                             * - **@param beerId**: The unique identifier of the beer to retrieve. This parameter cannot be null and must be a valid UUID.
                             * - **@param showInventoryOnHand**: A boolean indicating whether to include the current inventory on hand in the response.
                             * - **@return**: A `BeerDto` object representing the retrieved beer. If no beer is found with the given ID, the method returns `null`.
                             * - **@throws IllegalArgumentException**: If the provided `beerId` is `null`.
                             /**
                              * /**
                              * * Saves a new beer to the system.
                              * *
                              * * @param beerDto The data transfer object containing the details of the beer to be saved.
                              * * @return The saved {@link BeerDto} with any necessary updates or generated values such as an ID.
                              * */
                              * @PreAuthorize("@ss.hasPermi('system:beer:add')")
                              * @PostMapping("/save")
                              * public AjaxResult saveNewBeer(@RequestBody BeerDto beerDto) {
                              * return toAjax(beerService.saveNewBeer(beerDto));
                              * }
                              */

                             /**
                              * /**
                              * * Updates an existing beer in the system.
                              * *
                              * * @param beerId The unique identifier of the beer to be updated.
                              * * @param beerDto The DTO containing the new details of the beer.
                              * * @return The updated BeerDto object if successful, or null otherwise.
                              * * @throws IllegalArgumentException If the provided beerId is null or not a valid UUID.
                              * * @throws ResourceNotFoundException If no beer exists with the given beerId.
                              * */
                              * @PutMapping("/{beerId}")
                              * public BeerDto updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
                              * // Implementation of updating a beer
                              * }
                              */

                             */

                            .getPageable()
                            .getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());


        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    /**
                     * Certainly! Below is the Javadoc for the `getByUPC` method:
                     * 
                     * 
                     * /**
                     * * Retrieves a {@link BeerDto} object based on the provided UPC (Universal Product Code).
                     * *
                     * * @param upc                The UPC of the beer to retrieve.
                     * * @param showInventoryOnHand  A flag indicating whether to include inventory information in the response.
                     * * @return                   A {@link BeerDto} object corresponding to the given UPC, or null if no such beer exists.
                     * * @throws IllegalArgumentException If the provided UPC is null or empty.
                     * */
                     * public BeerDto getByUPC(String upc, Boolean showInventoryOnHand) {
                     * // Method implementation goes here
                     * }
                     * 
                     * 
                     * ### Explanation:
                     * 
                     * - **@param upc**: The parameter `upc` represents the Universal Product Code of the beer. It is a required field and cannot be null or empty.
                     * 
                     * - **@param showInventoryOnHand**: This boolean flag indicates whether the method should include inventory information in the returned `BeerDto`. If true, the method will return the inventory details; otherwise, it will not.
                     * 
                     * - **@return**: The method returns a `BeerDto` object corresponding to the given UPC. If no beer with the specified UPC exists, it returns null.
                     * 
                     * - **@throws IllegalArgumentException**: This exception is thrown if the provided UPC is null or empty.
                     */

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
    public  BeerDto getByUPC(String upc, Boolean showInventoryOnHand){

        if(showInventoryOnHand){
            return  beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc));
        }else{
            return  beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
        }
    }
}
