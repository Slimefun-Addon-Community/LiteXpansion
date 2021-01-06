package dev.j3fftw.litexpansion.utils;

import java.lang.reflect.Field;

public final class Reflections {

    private Reflections() {}

    public static void setStaticField(Class<?> clazz, String fieldName, Object newValue) {
        try {
            final Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true); // NOSONAR

            f.set(null, newValue); // NOSONAR
        } catch (ReflectiveOperationException e) {
            Log.warn("Failed to change field {} to {} in {}", fieldName, newValue, clazz.getSimpleName());
        }
    }

    public static void setField(Object instance, String fieldName, Object newValue) {
        try {
            final Field f = instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true); // NOSONAR

            f.set(instance, newValue); // NOSONAR
        } catch (ReflectiveOperationException e) {
            Log.warn("Failed to change field {} to {} in {}", fieldName, newValue, instance.getClass().getSimpleName());
        }
    }
}
