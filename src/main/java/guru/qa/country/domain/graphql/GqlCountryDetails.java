package guru.qa.country.domain.graphql;

import guru.qa.country.domain.rest.CountryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GqlCountryDetails {

    private String code;

    private String name;

    public static GqlCountryDetails fromDto(CountryDto dto) {
        return GqlCountryDetails.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }
}
