package com.harleyoconnor.potionsexpansion.util.json;

import com.google.gson.JsonElement;

import javax.annotation.Nullable;

/**
 * Manages applying a property (of type <tt>V</tt>) to an object (of type <tt>T</tt>).
 *
 * @author Harley O'Connor
 */
public class JsonPropertyApplier<T, V> {

    protected final String key;
    protected final Class<T> objectClass;
    protected final Class<V> valueClass;
    protected final PropertyApplier<T, V> propertyApplier;

    public JsonPropertyApplier (final String key, final Class<T> objectClass, final Class<V> valueClass, final VoidPropertyApplier<T, V> propertyApplier) {
        this(key, objectClass, valueClass, (PropertyApplier<T, V>) propertyApplier);
    }

    public JsonPropertyApplier (final String key, final Class<T> objectClass, final Class<V> valueClass, final PropertyApplier<T, V> propertyApplier) {
        this.key = key;
        this.objectClass = objectClass;
        this.valueClass = valueClass;
        this.propertyApplier = propertyApplier;
    }

    /**
     * Calls {@link PropertyApplier#apply(Object, Object)} if it should be called - or in other
     * words if the given key equaled {@link #key} and the object given is an instance of the
     * {@link #objectClass} value, and the {@link JsonElement} given contained a value that can
     * be converted to the {@link #valueClass}.
     *
     * @param keyIn The keyIn for the current {@link JsonElement}.
     * @param object The {@link Object} being applied to.
     * @param jsonElement The {@link JsonElement} for the key given.
     * @return The {@link PropertyApplierResult}, or null if application was not necessary.
     */
    @Nullable
    public PropertyApplierResult applyIfShould(final String keyIn, final Object object, final JsonElement jsonElement) {
        if (!this.key.equalsIgnoreCase(keyIn) || !this.objectClass.isInstance(object))
            return null;

        return this.applyIfShould(object, jsonElement, this.valueClass, this.propertyApplier);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <S, R> PropertyApplierResult applyIfShould(final Object object, final JsonElement jsonElement, final Class<R> valueClass, final PropertyApplier<S, R> applier) {
        final JsonObjectGetter<R> valueGetter = JsonObjectGetters.getObjectGetter(valueClass);

        if (!valueGetter.isValidGetter())
            return null;

        final ObjectFetchResult<R> fetchResult = valueGetter.get(jsonElement);

        return fetchResult.wasSuccessful() ? applier.apply((S) object, fetchResult.getValue()) :
                new PropertyApplierResult(fetchResult.getErrorMessage());
    }

    public Class<T> getObjectClass() {
        return objectClass;
    }

    public Class<V> getValueClass() {
        return valueClass;
    }

}