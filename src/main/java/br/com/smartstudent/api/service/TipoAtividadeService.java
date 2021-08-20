package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.TipoAtividade;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.TipoAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class TipoAtividadeService implements RestBasicService<TipoAtividade>{

    private final TipoAtividadeRepository repository;

    @Autowired
    public TipoAtividadeService(TipoAtividadeRepository repository){
        this.repository = repository;
    }

    @Override
    public List<TipoAtividade> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<TipoAtividade> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public TipoAtividade save(TipoAtividade t) {
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
