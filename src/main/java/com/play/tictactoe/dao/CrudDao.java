package com.play.tictactoe.dao;

import java.util.List;

public interface CrudDao<E, K> {

    E save(E model);

    void update(E model);

    E findById(K id);

    List<E> findAll();

    void remove(E model);
}