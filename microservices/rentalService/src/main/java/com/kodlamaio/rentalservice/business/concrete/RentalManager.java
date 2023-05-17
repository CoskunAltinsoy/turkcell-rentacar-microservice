package com.kodlamaio.rentalservice.business.concrete;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.UpdateRentalResponse;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllRentalsResponse> getAll() {
        var rentals = repository.findAll();
        var response = rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        //rules.checkIfRentalExists(id);
        var rental = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        //  rules.ensureCarIsAvailable(request.getCarId());
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(null);
        //  rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());
        repository.save(rental);

        var response = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UpdateRentalRequest request) {
        // rules.checkIfRentalExists(id);
        var rental = mapper.forRequest().map(request, Rental.class);
        repository.save(rental);
        var response = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        // rules.checkIfRentalExists(id);
        //  sendKafkaRentalDeletedEvent(id);
        repository.deleteById(id);
    }
}
