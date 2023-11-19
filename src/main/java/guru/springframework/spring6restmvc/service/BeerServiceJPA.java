package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BeerServiceJPA implements BeerService {
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beer) {
        return null;
    }

    @Override
    public BeerDTO deleteById(UUID uuid) {
        return null;
    }

    @Override
    public BeerDTO patchBeerById(UUID uuid, BeerDTO beer) {
        return null;
    }
}
