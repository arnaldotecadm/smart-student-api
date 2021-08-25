package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.Feature;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class FeatureService implements RestBasicService<Feature>{

    private final FeatureRepository repository;

    @Autowired
    public FeatureService(FeatureRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Feature> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Feature> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Feature save(Feature t) {
        return this.repository.save(t);
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public AbstractFirebaseRepository getRespository() {
        return repository;
    }

}
