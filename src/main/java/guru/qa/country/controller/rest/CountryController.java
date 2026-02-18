package guru.qa.country.controller.rest;

import guru.qa.country.domain.rest.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping(path = "all")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PostMapping(path = "add")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto country) {
        CountryDto newCountry = countryService.addCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCountry);
    }

    @PatchMapping("update/{code}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable String code,
                                                    @RequestBody CountryDto country) {
        CountryDto updateCountry = countryService.updateCountry(code, country);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateCountry);
    }
}
