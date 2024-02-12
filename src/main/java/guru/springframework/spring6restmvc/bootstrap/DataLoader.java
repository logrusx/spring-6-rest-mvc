package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import guru.springframework.spring6restmvc.service.BeerCSVService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Profile("localmysql")
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;
    private final BeerCSVService beerCSVService;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadCsvData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() > 0)
            return;
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> records = beerCSVService.convertCsv(file);
        records.forEach(beerCSVRecord -> {
            BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                case "American Pale Lager" -> BeerStyle.LAGER;
                case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                        BeerStyle.ALE;
                case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                case "American Porter" -> BeerStyle.PORTER;
                case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                case "English Pale Ale" -> BeerStyle.PALE_ALE;
                default -> BeerStyle.PILSNER;
            };

            beerRepository.save(Beer.builder()
                    .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 20))
                    .beerStyle(beerStyle)
                    .price(BigDecimal.TEN)
                    .upc(beerCSVRecord.getRow().toString())
                    .quantityOnHand(beerCSVRecord.getCount())
                    .build());
        });
    }
}

