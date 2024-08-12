package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeersByName(String beerName);

    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID uuid);

    Optional<BeerDTO> patchBeerById(UUID uuid, BeerDTO beer);

    List<BeerDTO> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle);

    List<BeerDTO> listBeersByStyle(BeerStyle beerStyle);
}
