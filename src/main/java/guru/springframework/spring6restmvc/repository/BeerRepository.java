package guru.springframework.spring6restmvc.repository;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findBeersByBeerNameContaining(String beerName);

    List<Beer> findBeersByBeerStyle(BeerStyle beerStyle);
    List<Beer> findBeersByBeerNameContainingAndBeerStyle(String beerName, BeerStyle beerStyle);
}
