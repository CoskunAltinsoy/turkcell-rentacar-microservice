package com.kodlamaio.inventoryservice.business.concrete;

import com.kodlamaio.commonpackage.events.inventory.BrandDeletedEvent;
import com.kodlamaio.commonpackage.events.inventory.BrandUpdatedEvent;
import com.kodlamaio.commonpackage.events.inventory.ModelDeletedEvent;
import com.kodlamaio.commonpackage.events.inventory.ModelUpdatedEvent;
import com.kodlamaio.commonpackage.kafka.KafkaProducer;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.BrandService;
import com.kodlamaio.inventoryservice.business.abstracts.ModelService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryservice.entities.Brand;
import com.kodlamaio.inventoryservice.entities.Model;
import com.kodlamaio.inventoryservice.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelManager implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapperService modelMapperService;
    private final KafkaProducer kafkaProducer;
    @Override
    public List<GetAllModelsResponse> getAll() {
        var models = modelRepository.findAll();
        var response = models
                .stream()
                .map(model -> modelMapperService.forResponse().map(model, GetAllModelsResponse.class))
                .collect(Collectors.toList());

        return response;
    }
    @Override
    public GetModelResponse getById(UUID id) {
        var model = modelRepository.findById(id).orElseThrow();
        var response = modelMapperService.forResponse().map(model, GetModelResponse.class);

        return response;
    }
    @Override
    public CreateModelResponse add(CreateModelRequest createModelRequest) {
        var model = modelMapperService.forRequest().map(createModelRequest, Model.class);
        model.setId(null);
        modelRepository.save(model);
        var response = modelMapperService.forResponse().map(model, CreateModelResponse.class);

        return response;
    }
    @Override
    public UpdateModelResponse update(UpdateModelRequest updateModelRequest) {
        var model = modelMapperService.forRequest().map(updateModelRequest, Model.class);
        var updatedModel = modelRepository.save(model);

        sendKafkaModelUpdatedEvent(updatedModel);

        var response = modelMapperService.forResponse().map(model, UpdateModelResponse.class);

        return response;
    }
    @Override
    public void delete(UUID id) {
        modelRepository.deleteById(id);
        sendKafkaModelDeletedEvent(id);
    }
    private void sendKafkaModelDeletedEvent(UUID id) {
        kafkaProducer.sendMessage(new ModelDeletedEvent(id), "model-deleted");
    }
    private void sendKafkaModelUpdatedEvent(Model updatedModel) {
        var event = modelMapperService.forResponse().map(updatedModel, ModelUpdatedEvent.class);

        kafkaProducer.sendMessage(event,"model-updated");
    }
}
