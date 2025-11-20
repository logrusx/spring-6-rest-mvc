package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void testListAllBeers() {
        beerClient.listBeers(null, null, null, null, null);
    }

    @Test
    void testGetBeerById() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers();
        BeerDTO dto = beerDTOS.getContent().getFirst();
        BeerDTO byId = beerClient.getBeerById(dto.getId());
        assertNotNull(byId);
    }

    @Test
    void testListBeersByName() {
        beerClient.listBeers("ALE", null, null, null, null);
    }
}
