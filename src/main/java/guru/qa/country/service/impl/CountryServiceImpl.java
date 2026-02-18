package guru.qa.country.service.impl;

import guru.qa.country.domain.graphql.GqlCountryDetails;
import guru.qa.country.domain.graphql.GqlCountryInput;
import guru.qa.country.domain.rest.CountryDto;
import guru.qa.country.repository.CountryRepository;
import guru.qa.country.repository.entity.CountryEntity;
import guru.qa.country.service.CountryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(CountryDto::fromEntity)
                .toList();
    }

    @Override
    public CountryDto addCountry(CountryDto country) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(country.getCode());

        if (foundEntity.isPresent()) {
            throw new EntityExistsException("Country with code %s already exists".formatted(country.getCode()));
        }
        var newEntity = CountryEntity.builder()
                .code(country.getCode())
                .name(country.getName())
                .build();
        countryRepository.save(newEntity);
        return CountryDto.fromEntity(newEntity);
    }

    @Override
    public CountryDto updateCountry(String code, CountryDto country) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(code);

        if (foundEntity.isEmpty()) {
            throw new EntityNotFoundException("Country with code %s not found".formatted(country.getCode()));
        }
        CountryEntity updatedEntity = foundEntity.get();
        updatedEntity.setCode(country.getCode());
        updatedEntity.setName(country.getName());
        return CountryDto.fromEntity(countryRepository.save(updatedEntity));
    }

    @Override
    public List<GqlCountryDetails> getCountries() {
        return countryRepository.findAll().stream()
                .map(GqlCountryDetails::fromEntity)
                .toList();
    }

    @Override
    public GqlCountryDetails addCountry(GqlCountryInput input) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(input.getCode());

        if (foundEntity.isPresent()) {
            throw new EntityExistsException("Country with code %s already exists".formatted(input.getCode()));
        }
        var newEntity = CountryEntity.builder()
                .code(input.getCode())
                .name(input.getName())
                .build();
        countryRepository.save(newEntity);
        return GqlCountryDetails.fromEntity(newEntity);
    }

    @Override
    public GqlCountryDetails updateCountry(String code, GqlCountryInput input) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(code);

        if (foundEntity.isEmpty()) {
            throw new EntityNotFoundException("Country with code %s not found".formatted(input.getCode()));
        }
        CountryEntity updatedEntity = foundEntity.get();
        updatedEntity.setCode(input.getCode());
        updatedEntity.setName(input.getName());
        return GqlCountryDetails.fromEntity(countryRepository.save(updatedEntity));
    }
}
