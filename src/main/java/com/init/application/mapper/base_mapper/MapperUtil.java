package com.init.application.mapper.base_mapper;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.MappingException;
import org.modelmapper.internal.Errors;

import java.lang.reflect.Field;

public class MapperUtil {
    public static boolean wasInitialized(Object c) {
        if (!(c instanceof PersistentCollection)) {
            return true;
        }

        PersistentCollection pc = (PersistentCollection) c;
        return pc.wasInitialized();
    }
    public static void setNullForAllLazyLoadEntities(Object source) {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(source);
                if (!(fieldValue instanceof PersistentCollection)) {
                    if(!Hibernate.isInitialized(fieldValue))
                    {
                        fieldValue = null;
                        field.set(source, fieldValue);
                    }
                }
               /* else
                {
                    PersistentCollection pc = (PersistentCollection) fieldValue;
                   *//*boolean a =pc.wasInitialized();
                    boolean b =pc.afterInitialize();*//*
                    if(!pc.afterInitialize())
                    {
                        fieldValue = Collections.emptyList();
                        field.set(source, fieldValue);
                    }
                }*/
            } catch (IllegalAccessException e) {
                Errors errors = new Errors();
                errors.addMessage(e, "Failed to map field %s ", field.getType());
                throw new MappingException(errors.getMessages());
            }

        }
    }
}
