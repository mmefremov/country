package guru.qa.country.service;

import guru.qa.country.domain.graphql.GqlCountryDetails;
import guru.qa.country.domain.graphql.GqlCountryInput;
import guru.qa.country.domain.rest.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> getAllCountries();

    CountryDto addCountry(CountryDto country);

    CountryDto updateCountry(String code, CountryDto country);

    List<GqlCountryDetails> getCountries();

    GqlCountryDetails addCountry(GqlCountryInput input);

    GqlCountryDetails updateCountry(String code, GqlCountryInput input);
}
