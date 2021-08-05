package br.com.smartstudent.api.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/***
 * T stands for TYPE to be returned
 * I stands for Interface to be returned
 * @param <T>
 */
public interface RestBasicService<T> {

    List<T> getAll() throws ExecutionException, InterruptedException;

    Optional<T> getById(String id) throws ExecutionException, InterruptedException;

    T save(T t);

    void deleteById(String id);
}