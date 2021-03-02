package com.harleyoconnor.potionsexpansion.util;

/**
 * An implementation of {@link PropertyApplier} that assumes the application was
 * successful, calling {@link VoidPropertyApplier#applySuccessful(Object, Object)}
 * and returning {@link PropertyApplierResult#SUCCESS}.
 *
 * @author Harley O'Connor
 */
public interface VoidPropertyApplier<T, V> extends PropertyApplier<T, V> {

    @Override
    default PropertyApplierResult apply(final T object, final V value) {
        this.applySuccessful(object, value);
        return PropertyApplierResult.SUCCESS;
    }

    /**
     * Applies the given property value to the given object, assuming the application was successful.
     *
     * @param object The object to apply the value to.
     * @param value The value to apply.
     */
    void applySuccessful (final T object, final V value);

}