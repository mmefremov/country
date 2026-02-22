package guru.qa.country.domain.graphql;

import guru.qa.country.domain.rest.CountryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GqlCountryInput {

    private String code;

    private String name;

    public CountryDto toDto() {
        return CountryDto.builder()
                .name(getName())
                .code(getCode())
                .build();
    }
}
