package guru.qa.country.controller.graphql;

import guru.qa.country.domain.graphql.GqlCountryDetails;
import guru.qa.country.domain.graphql.GqlCountryInput;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryGraphQlController {

    private final CountryService countryService;

    @QueryMapping
    public List<GqlCountryDetails> getAllCountries() {
        return countryService.getCountries();
    }

    @MutationMapping
    public GqlCountryDetails addCountry(@Argument GqlCountryInput input) {
        return countryService.addCountry(input);
    }

    @MutationMapping
    public GqlCountryDetails updateCountry(@Argument String code,
                                           @Argument GqlCountryInput input) {
        return countryService.updateCountry(code, input);
    }
}
