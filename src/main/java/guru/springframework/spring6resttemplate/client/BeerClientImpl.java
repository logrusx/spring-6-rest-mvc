package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    public static final String BASE_BEER_PATH = "/api/v1/beer";
    public static final String BEER_ID = "/{beerId}";
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(BASE_BEER_PATH);

        uriComponentsBuilder.queryParamIfPresent("beerName", Optional.ofNullable(beerName));
        uriComponentsBuilder.queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle));
        uriComponentsBuilder.queryParamIfPresent("showInventory", Optional.ofNullable(showInventory));
        uriComponentsBuilder.queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber));
        uriComponentsBuilder.queryParamIfPresent("pageSize", Optional.ofNullable(pageSize));

        ResponseEntity<BeerDTOPageImpl> response =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        return response.getBody();
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(BASE_BEER_PATH + BEER_ID, BeerDTO.class, beerId);
    }
}
