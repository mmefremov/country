package guru.qa.country.domain.graphql;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GqlCountryInput {

    private String code;

    private String name;

}
