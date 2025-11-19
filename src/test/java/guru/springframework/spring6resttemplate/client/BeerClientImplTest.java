package guru.springframework.spring6resttemplate.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void testListAllBeers() {
        beerClient.listBeers(null, null, null, null, null);
    }

    @Test
    void testListBeersByName() {
        beerClient.listBeers("ALE", null, null, null, null);
    }
}
