package guru.springframework.spring6restmvc.repository;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
        beerRepository.save(Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
        beerRepository.save(Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
        beerRepository.save(Beer.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());


    }

    @Test
    void testGetBeerListByName() {
        List<Beer> beers = beerRepository.findBeersByBeerNameContaining("ank");

        assertThat(beers.size()).isEqualTo(1);
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.GOSE)
                .price(new BigDecimal(100))
                .upc("UPC")
                .build());
        beerRepository.flush();
        assertThat(savedBeer.getBeerName()).isEqualTo("My Beer");
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
                beerRepository.save(Beer.builder()
                .beerName("My                                             Beer")
                .beerStyle(BeerStyle.GOSE)
                .price(new BigDecimal(100))
                .upc("UPC")
                .build());
                beerRepository.flush();
        });
    }
}