package guru.qa.country.service;

import guru.qa.country.domain.rest.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> getAllCountries();

    CountryDto addCountry(CountryDto country);

    CountryDto updateCountry(String code, CountryDto country);
}
