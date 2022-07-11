package pl.pawel.flatcalculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.util.List.of;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static pl.pawel.flatcalculator.service.OtodomRequestBody.Estate.FLAT;
import static pl.pawel.flatcalculator.service.OtodomRequestBody.GeoAttributesEnum.WARSZAWA;
import static pl.pawel.flatcalculator.service.OtodomRequestBody.Market.SECONDARY;
import static pl.pawel.flatcalculator.service.OtodomRequestBody.OwnerType.ALL;
import static pl.pawel.flatcalculator.service.OtodomRequestBody.Transaction.SELL;

@RequiredArgsConstructor
@Service
@Slf4j
public class OtodomDataFetchService implements DataFetchService {

    private static final String API_URL = "https://www.otodom.pl/api/query";
    private static final String BODY = "{\"query\":\"query CountAds($filterAttributes: FilterAttributes, $filterLocations: FilterLocations) {\\n  countAds(filterAttributes: $filterAttributes, filterLocations: $filterLocations) {\\n    ... on CountAds {\\n      count\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\",\"operationName\":\"CountAds\",\"variables\":{\"filterAttributes\":{\"market\":\"SECONDARY\",\"estate\":\"FLAT\",\"transaction\":\"SELL\",\"distanceRadius\":0,\"ownerTypeSingleSelect\":\"ALL\",\"hasRemoteServices\":false},\"filterLocations\":{\"byGeoAttributes\":[{\"id\":\"cities_6-26\"}]}}}";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void fetchData() {
        log.info("fetch data from otodom");
        fetchDataWithRestTemplate();

        try {
            fetchDataWithHttpClient();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchDataWithHttpClient() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(BODY))
                .uri(new URI(API_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Response from HttpClient: " + httpResponse.body());
    }

    private void fetchDataWithRestTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(buildRequestBody(), headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(API_URL, httpEntity, String.class);

        log.info("Response from RestTemplate: " + stringResponseEntity);
    }

    private OtodomRequestBody buildRequestBody() {
        OtodomRequestBody.Variable.FilterAttributes filterAttributes = OtodomRequestBody.Variable.FilterAttributes.builder()
                .market(SECONDARY)
                .estate(FLAT)
                .transaction(SELL)
                .ownerTypeSingleSelect(ALL)
                .build();

        OtodomRequestBody.Variable.FilterLocations filterLocations = OtodomRequestBody.Variable.FilterLocations.builder()
                .byGeoAttributes(of(WARSZAWA))
                .build();

        OtodomRequestBody.Variable variable = OtodomRequestBody.Variable.builder()
                .filterAttributes(filterAttributes)
                .filterLocations(filterLocations)
                .build();

        return OtodomRequestBody.builder()
                .variables(variable)
                .build();
    }
}
