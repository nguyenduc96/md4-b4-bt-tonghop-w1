package com.nguyenduc.repository;

import java.util.List;

public interface IGeneralRepository<T> {
    List<T> findAll();

    List<T> findByName(String name);

    void save(T t);

    void delete(Long id);

    T findById(Long id);
}
