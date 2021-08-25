package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.enums.EnumException;
import br.com.smartstudent.api.enums.FeatureStatusEnum;
import br.com.smartstudent.api.exception.ValidationException;
import br.com.smartstudent.api.model.Feature;
import br.com.smartstudent.api.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("feature")
@CrossOrigin("*")
public class FeatureController extends RestBasicController<Feature> {

    @Autowired
    public FeatureController(FeatureService basicService) {
        super(basicService);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Feature> delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        return changeStatus(id, FeatureStatusEnum.DELETED);
    }

    @PostMapping("mark-in-progress/{id}")
    public ResponseEntity<Feature> markInProgress(@PathVariable String id) throws ExecutionException, InterruptedException {
        return changeStatus(id, FeatureStatusEnum.IN_PROGRESS);
    }

    @PostMapping("mark-as-done/{id}")
    public ResponseEntity<Feature> markAsDone(@PathVariable String id) throws ExecutionException, InterruptedException {
        return changeStatus(id, FeatureStatusEnum.DONE);
    }

    private ResponseEntity<Feature> changeStatus(String id, FeatureStatusEnum featureStatusEnum) throws ExecutionException, InterruptedException {
        Feature feature = super.getById(id)
                .orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        feature.setFeatureStatusEnum(featureStatusEnum);
        return super.save(feature);
    }
}
