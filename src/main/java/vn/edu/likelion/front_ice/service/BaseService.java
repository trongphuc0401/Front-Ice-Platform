package vn.edu.likelion.front_ice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BaseService<E, I, U> {
    Optional<E> create(I t) ;

    Optional<E> updateInfo(Long id, U i);

    List<E> saveAll(List<E> ts);

    void delete(Long id);

    void deleteAll(List<Long> listId);

    E findById(Long id);

    List<E> findAll();
}
