package pl.pawel.flatcalculator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.flatcalculator.service.DataScrapperService;

@RestController
@RequiredArgsConstructor
public class V1AppController {

    private final DataScrapperService dataScrapperService;

    @RequestMapping("/test")
    public String test() {
        dataScrapperService.fetchData();
        return "ok";
    }
}
