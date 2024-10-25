package vn.edu.likelion.front_ice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BaseService<E, I, U> {
    Optional<E> create(I t) throws IOException;

    Optional<E> updateInfo(String id, U i);

    List<E> saveAll(List<E> ts);

    void delete(String id);

    void deleteAll(List<String> listId);

    E findById(String id);

    List<E> findAll();
}
