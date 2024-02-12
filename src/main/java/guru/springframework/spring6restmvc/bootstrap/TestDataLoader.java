package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Profile("TEST")
@Component
@RequiredArgsConstructor
public class TestDataLoader implements ApplicationRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadBeers();
        loadCustomers();
    }

    private void loadCustomers() {
        if (customerRepository.count() > 0)
            return;
        customerRepository.save(Customer.builder()
                .customerName("Joro 1")
                .build());
        customerRepository.save(Customer.builder()
                .customerName("Joro 2")
                .build());
        customerRepository.save(Customer.builder()
                .customerName("Joro 3")
                .build());
    }


    private void loadBeers() {
        if (beerRepository.count() > 0)
            return;
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
}

