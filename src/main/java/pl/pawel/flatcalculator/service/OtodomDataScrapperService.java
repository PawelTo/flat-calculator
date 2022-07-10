package pl.pawel.flatcalculator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OtodomDataScrapperService implements DataScrapperService{

    @Override
    public void fetchData() {
        log.info("fetch data from otodom");
    }
}
