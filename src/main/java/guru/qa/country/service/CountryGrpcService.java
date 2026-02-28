package guru.qa.country.service;

import com.google.protobuf.Empty;
import guru.qa.country.grpc.Count;
import guru.qa.country.grpc.CountryDetails;
import guru.qa.country.grpc.CountryDetailsList;
import guru.qa.country.grpc.CountryServiceGrpc;
import guru.qa.country.grpc.UpdateCountryRequest;
import guru.qa.country.repository.CountryRepository;
import guru.qa.country.repository.entity.CountryEntity;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryGrpcService extends CountryServiceGrpc.CountryServiceImplBase {

    private final CountryRepository countryRepository;

    @Override
    public void listCountries(Empty request, StreamObserver<CountryDetailsList> responseObserver) {
        responseObserver.onNext(
                CountryDetailsList.newBuilder()
                        .addAllDetails(
                                countryRepository.findAll().stream()
                                        .map(CountryGrpcService::mapCountryEntityToProto)
                                        .toList())
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void createCountry(CountryDetails request, StreamObserver<CountryDetails> responseObserver) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(request.getCode());

        if (foundEntity.isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Country with code %s already exists".formatted(request.getCode()))
                            .asException());
        }
        responseObserver.onNext(
                mapCountryEntityToProto(
                        countryRepository.save(
                                CountryEntity.builder()
                                        .code(request.getCode())
                                        .name(request.getName())
                                        .build())));
        responseObserver.onCompleted();
    }

    @Override
    public void updateCountry(UpdateCountryRequest request, StreamObserver<CountryDetails> responseObserver) {
        Optional<CountryEntity> foundEntity = countryRepository.findByCode(request.getCode());

        if (foundEntity.isEmpty()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Country with code %s not found".formatted(request.getCode()))
                            .asException());
        }
        CountryEntity updatedEntity = foundEntity.get();
        updatedEntity.setCode(request.getDetails().getCode());
        updatedEntity.setName(request.getDetails().getName());
        responseObserver.onNext(
                mapCountryEntityToProto(
                        countryRepository.save(updatedEntity))
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryDetails> createCountryStream(StreamObserver<Count> responseObserver) {
        return new StreamObserver<>() {

            private int count;

            @Override
            public void onNext(CountryDetails country) {
                Optional<CountryEntity> foundEntity = countryRepository.findByCode(country.getCode());

                if (foundEntity.isPresent()) {
                    responseObserver.onError(
                            Status.ALREADY_EXISTS
                                    .withDescription("Country with code %s already exists".formatted(country.getCode()))
                                    .asException());
                }
                count++;
                countryRepository.save(
                        CountryEntity.builder()
                                .code(country.getCode())
                                .name(country.getName())
                                .build());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        Count.newBuilder()
                                .setCount(count)
                                .build());
                responseObserver.onCompleted();
            }
        };
    }

    private static CountryDetails mapCountryEntityToProto(CountryEntity entity) {
        return CountryDetails.newBuilder()
                .setCode(entity.getCode())
                .setName(entity.getName())
                .build();
    }
}
