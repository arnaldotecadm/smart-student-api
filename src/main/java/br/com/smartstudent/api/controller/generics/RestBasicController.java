package br.com.smartstudent.api.controller.generics;

import br.com.smartstudent.api.enums.EnumException;
import br.com.smartstudent.api.exception.ValidationException;
import br.com.smartstudent.api.model.AbstractModel;
import br.com.smartstudent.api.service.RestBasicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class RestBasicController<T extends AbstractModel> {

    final RestBasicService<T> basicService;

    public RestBasicController(RestBasicService<T> basicService) {
        this.basicService = basicService;
    }

    /**
     * @return Return a list of all DTOs
     */
    @GetMapping(value = {"", "all"})
    public List<T> getAll() {
        return this.basicService.getAll();
    }

    @GetMapping(value = "{id}")
    public Optional<T> getById(@PathVariable("id") String id) {
        return basicService.getById(id);
    }

    /**
     * create a resource
     *
     * @param t -> generic type to be persisted
     * @return Response entity containing the resource location and the resource it self
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<T> save(@RequestBody T t) {
        T saved = basicService.save(t);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getDocumentId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * delete a resource searching by its ID
     * @param id of the Object you want to delete
     * @return Response Entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable String id) {
        T t = basicService.getById(id)
                .orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        basicService.deleteById(t.getDocumentId());
        return ResponseEntity.noContent().build();
    }
}
