package com.leilao.backend.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GenericUpdater {
    public static <T> T atualizaCampos(T target, T source) {
        if (target == null || source == null) {
            throw new IllegalArgumentException("Nenhum dos parâmetros pode ser nulo.");
        }
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // Pula atributos final ou static
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    Object valorSource = field.get(source);
                    if (valorSource != null) {
                        field.set(target, valorSource);
                    }
                } catch (IllegalAccessException e) {
                    // Trate a exceção de acordo com a sua necessidade
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return target;
    }
}