package vn.edu.likelion.front_ice.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<E,I, U> {
    Optional<E> create(I t);

    Optional<E> updateInfo(String id, U i);

    List<E> saveAll(List<E> ts);

    void delete(String id);

    void deleteAll(List<String> listId);

    Optional<E> findById(String id);

    List<E> findAll();
}
