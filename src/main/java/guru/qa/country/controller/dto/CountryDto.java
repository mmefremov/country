package guru.qa.country.controller.dto;

import guru.qa.country.repository.entity.CountryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryDto {

    private String code;

    private String name;

    public static CountryDto fromEntity(CountryEntity entity) {
        return CountryDto.builder()
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }
}
