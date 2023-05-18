package com.kodlamaio.rentalservice.business.concrete;

import com.kodlamaio.commonpackage.events.rentel.RentalCreatedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.rentalservice.api.clients.CarClient;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.UpdateRentalResponse;
import com.kodlamaio.rentalservice.business.kafka.producer.RentalProducer;
import com.kodlamaio.rentalservice.business.rules.RentalBusinessRules;
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
    private final RentalRepository rentalRepository;
    private final ModelMapperService modelMapperService;
    private final RentalBusinessRules rentalBusinessRules;
    private final CarClient carClient;
    private final RentalProducer rentalProducer;
    @Override
    public List<GetAllRentalsResponse> getAll() {
        var rentals = rentalRepository.findAll();
        var response = rentals
                .stream()
                .map(rental -> modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        //rules.checkIfRentalExists(id);
        var rental = rentalRepository.findById(id).orElseThrow();
        var response = modelMapperService.forResponse().map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {
        //  rules.ensureCarIsAvailable(createRentalRequest.getCarId());
        carClient.checkIfCarAvailable(createRentalRequest.getCarId());
        var rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
        rental.setId(null);
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());
        rentalRepository.save(rental);
        sendKafkaRentalCreatedEvent(createRentalRequest.getCarId());

        var response = modelMapperService.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
        // rules.checkIfRentalExists(id);
        var rental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
        rentalRepository.save(rental);
        var response = modelMapperService.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        // rules.checkIfRentalExists(id);
        //  sendKafkaRentalDeletedEvent(id);
        rentalRepository.deleteById(id);
    }
    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }
    private void sendKafkaRentalCreatedEvent(UUID carId){
        rentalProducer.sendMessage(new RentalCreatedEvent(carId), "rental-created");
    }
}
