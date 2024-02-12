package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCSVServiceImplTest {

    private final BeerCSVService beerCSVService = new BeerCSVServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> records = beerCSVService.convertCsv(file);
        System.out.println(records.size());
        assertThat(records.size()).isGreaterThan(0);
    }
}