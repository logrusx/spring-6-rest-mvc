package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.controller.NotFoundException;
import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return beerRepository
                .findById(id)
                .map(beerMapper::beerToBeerDtoMapper);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDtoMapper)
                .toList();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        Beer saved = beerMapper.beerDtoToBeer(beer);
        saved = beerRepository.save(saved);
        return beerMapper.beerToBeerDtoMapper(saved);
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> ref = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse( existing -> {
            existing.setBeerName(beer.getBeerName());
            existing.setBeerStyle(beer.getBeerStyle());
            existing.setPrice(beer.getPrice());
            existing.setUpc(beer.getUpc());
            existing = beerRepository.save(existing);
            ref.set(Optional.of(beerMapper.beerToBeerDtoMapper(existing)));
        }, () -> {throw new NotFoundException("Id not found");});
        return ref.get();
    }

    @Override
    public Boolean deleteById(UUID uuid) {
        if (!beerRepository.existsById(uuid))
            return false;
        else beerRepository.deleteById(uuid);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> ref = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse( existing -> {
            if (StringUtils.hasText(beer.getBeerName())){
                existing.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null){
                existing.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                existing.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                existing.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                existing.setQuantityOnHand(beer.getQuantityOnHand());
            }
            ref.set(Optional.of(beerMapper.beerToBeerDtoMapper(beerRepository.save(existing))));
        }, () -> ref.set(Optional.empty()));
        return ref.get();
    }
}
