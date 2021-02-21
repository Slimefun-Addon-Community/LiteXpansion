package dev.j3fftw.litexpansion.utils;

import java.lang.reflect.Field;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Reflections {

    private Reflections() {}

    public static void setStaticField(@Nonnull Class<?> clazz, @Nonnull String fieldName, @Nonnull Object newValue) {
        try {
            final Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true); // NOSONAR

            f.set(null, newValue); // NOSONAR
        } catch (ReflectiveOperationException e) {
            Log.warn("Failed to change field {} to {} in {}", fieldName, newValue, clazz.getSimpleName());
        }
    }

    public static void setField(@Nullable Object instance, @Nonnull String fieldName, @Nonnull Object newValue) {
        if (instance == null) return;
        try {
            final Field f = instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true); // NOSONAR

            f.set(instance, newValue); // NOSONAR
        } catch (ReflectiveOperationException e) {
            Log.warn("Failed to change field {} to {} in {}", fieldName, newValue, instance.getClass().getSimpleName());
        }
    }
}
