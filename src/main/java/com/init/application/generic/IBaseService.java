package com.init.application.generic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IBaseService<E, ID> {
    E create(E entity);

    E save(E entity);

    void delete(ID id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void deleteInBatch(List<E> entities);

    Map<String, Object> findAllPage(Integer page, Integer size, String sortType, String sortBy);
}
