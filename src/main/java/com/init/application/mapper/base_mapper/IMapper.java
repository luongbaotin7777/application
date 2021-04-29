package com.init.application.mapper.base_mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.TargetType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface IMapper {

    @BeforeMapping
    default void storeMappedInstance(Object source, @TargetType Class<?> targetType) {
        if(source == null)
        {
            return;
        }
        MapperUtil.setNullForAllLazyLoadEntities(source);
    }

    @BeforeMapping
    default <T> Set<T> fixLazyLoadingSet(Collection<?> c, @TargetType Class<?> targetType) {
        if (!MapperUtil.wasInitialized(c)) {
            return Collections.emptySet();
        }
        return null;
    }
    @BeforeMapping
    default <T> List<T> fixLazyLoadingList(Collection<?> c, @TargetType Class<?> targetType) {
        if (!MapperUtil.wasInitialized(c)) {
            return Collections.emptyList();
        }
        return null;
    }
}
