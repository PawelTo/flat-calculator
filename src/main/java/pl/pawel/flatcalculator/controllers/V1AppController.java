package pl.pawel.flatcalculator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.flatcalculator.service.DataFetchService;

@RestController
@RequiredArgsConstructor
public class V1AppController {

    private final DataFetchService dataFetchService;

    @RequestMapping("/test")
    public String test() {
        dataFetchService.fetchData();
        return "ok";
    }
}
