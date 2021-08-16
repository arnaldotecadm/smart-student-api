package br.com.smartstudent.api.service;

import java.util.List;
import java.util.Optional;

/***
 * T stands for TYPE to be returned
 * I stands for Interface to be returned
 * @param <T>
 */
public interface RestBasicService<T> {

    List<T> getAll();

    Optional<T> getById(String id);

    T save(T t);

    void deleteById(String id);
}