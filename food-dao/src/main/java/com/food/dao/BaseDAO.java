/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author TWINS
 */
public interface BaseDAO<T, KeyType extends Serializable> {

    public T save(T entity);

    public T update(T entity);

    public void delete(T entity);

    public void saveAll(Collection<T> entities);

    public void updateAll(Collection<T> entities);

    public T getById(KeyType id);

    public List<T> getAll();

    public List<T> getAll(int maxResult, int firstResult);

    public void deleteById(KeyType id);

    public void deletelAllById(Collection<KeyType> ids);
}
