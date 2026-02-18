package guru.qa.country.domain.graphql;

import guru.qa.country.repository.entity.CountryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GqlCountryDetails {

    private String code;

    private String name;

    public static GqlCountryDetails fromEntity(CountryEntity entity) {
        return GqlCountryDetails.builder()
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }
}
