package com.init.application.generic;

import com.init.application.common.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BaseService<E, ID> implements IBaseService<E, ID> {
    private final IBaseRepository<E, ID> repository;

    public BaseService(IBaseRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    public E create(E entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<E> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public Map<String, Object> findAllPage(Integer page, Integer size, String sortType, String sortBy) {
        Sort sort = null;

        if (sortType.equals("ASC")) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortType.equals("DESC")) {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, Objects.requireNonNull(sort));
        Page<E> roleEntityPages = repository.findAll(pageable);

        int totalPages = roleEntityPages.getTotalPages();
        long totalElements = roleEntityPages.getTotalElements();
        int numberOfElements = roleEntityPages.getNumberOfElements();

        Map<String, Object> result = new HashMap<>();
        result.put(CommonUtils.totalPage, totalPages);
        result.put(CommonUtils.totalElements, totalElements);
        result.put(CommonUtils.numberOfElements, numberOfElements);
        result.put(CommonUtils.resultContent, roleEntityPages.getContent());

        return result;
    }
}
