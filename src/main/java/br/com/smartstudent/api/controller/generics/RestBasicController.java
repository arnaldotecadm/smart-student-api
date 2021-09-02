package br.com.smartstudent.api.controller.generics;

import br.com.smartstudent.api.enums.EnumException;
import br.com.smartstudent.api.exception.ValidationException;
import br.com.smartstudent.api.model.AbstractModel;
import br.com.smartstudent.api.model.SequenciaChavePrimaria;
import br.com.smartstudent.api.service.RestBasicService;
import br.com.smartstudent.api.service.SequenciaChavePrimariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class RestBasicController<T extends AbstractModel> {

    final RestBasicService<T> basicService;

    @Autowired
    private SequenciaChavePrimariaService chavePrimariaService;

    public RestBasicController(RestBasicService<T> basicService) {
        this.basicService = basicService;
    }

    /**
     * @return Return a list of all DTOs
     */
    @GetMapping(value = {"", "all"})
    public List<T> getAll() throws ExecutionException, InterruptedException {
        return this.basicService.getAll();
    }

    @GetMapping(value = "{id}")
    public Optional<T> getById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return basicService.getById(id);
    }

    /**
     * create a resource
     *
     * @param t -> generic type to be persisted
     * @return Response entity containing the resource location and the resource it self
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<T> save(@RequestBody T t) throws ExecutionException, InterruptedException {

        if(null == t.getId()){
            setIdentifier(t);
        }

        T saved = basicService.save(t);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getDocumentId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Buscar o ultimo idientificador valido para a tabela
     * caos nao exista, cria um novo registro e o inicia com o valor 1
     * @param t
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void setIdentifier(AbstractModel t) throws ExecutionException, InterruptedException {
        String collectionName = this.basicService.getRespository().getCollection();
        setIdentifier(t, collectionName);
    }

    public void setIdentifier(AbstractModel t, String collectionName) throws ExecutionException, InterruptedException {
        List<SequenciaChavePrimaria> lastKey = this.chavePrimariaService.getLastKey(collectionName);

        if(lastKey.isEmpty()){
            t.setId(1);
            SequenciaChavePrimaria chavePrimaria = new SequenciaChavePrimaria(collectionName, 1);
            this.chavePrimariaService.save(chavePrimaria);
        } else{
            SequenciaChavePrimaria sequenciaChavePrimaria = lastKey.get(0);
            int nextIndice = sequenciaChavePrimaria.getUltimoValor() + 1;
            t.setId(nextIndice);
            sequenciaChavePrimaria.setUltimoValor(nextIndice);
            this.chavePrimariaService.save(sequenciaChavePrimaria);
        }
    }

    /**
     * delete a resource searching by its ID
     * @param id of the Object you want to delete
     * @return Response Entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        T t = basicService.getById(id)
                .orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        basicService.deleteById(t.getDocumentId());
        return ResponseEntity.noContent().build();
    }
}
