package com.harleyoconnor.potionsexpansion.util.json;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

/**
 * Holds {@link JsonObjectGetter} objects, which can be used to obtain objects from
 * {@link JsonElement} objects.
 *
 * @author Harley O'Connor
 */
public final class JsonObjectGetters {

    private static final Set<JsonObjectGetterHolder<?>> OBJECT_GETTERS = Sets.newHashSet();

    /** Returned by {@link #getObjectGetter(Class)} if an object getter wasn't found. */
    public static final class NullObjectGetter<T> implements JsonObjectGetter<T> {
        @Override
        public boolean isValidGetter() {
            return false;
        }

        @Override
        public ObjectFetchResult<T> get(final JsonElement jsonElement) {
            return ObjectFetchResult.failure("Could not get Json object getter for json element: " + jsonElement.toString() + ".");
        }
    }

    /**
     * Gets the {@link JsonObjectGetter} for the given class type.
     *
     * @param objectClass The {@link Class} of the object to get.
     * @param <T> The type of the object.
     * @return The {@link JsonObjectGetter} for the class, or {@link NullObjectGetter} if it wasn't found.
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonObjectGetter<T> getObjectGetter (final Class<T> objectClass) {
        return OBJECT_GETTERS.stream().filter(jsonObjectGetterHolder -> jsonObjectGetterHolder.objectClass.equals(objectClass))
                .findFirst().map(jsonObjectGetterHolder -> (JsonObjectGetter<T>) jsonObjectGetterHolder.objectGetter).orElse(new NullObjectGetter<>());
    }

    /**
     * Registers an {@link JsonObjectGetter} to the registry.
     *
     * @param objectClass The {@link Class} of the object that will be obtained.
     * @param objectGetter The {@link JsonObjectGetter} to register.
     * @param <T> The type of the object getter.
     * @return The {@link JsonObjectGetter} given.
     */
    public static <T> JsonObjectGetter<T> register(final Class<T> objectClass, final JsonObjectGetter<T> objectGetter) {
        OBJECT_GETTERS.add(new JsonObjectGetterHolder<>(objectClass, objectGetter));
        return objectGetter;
    }

    /**
     * Holds an {@link JsonObjectGetter} for the relevant class.
     *
     * @param <T> The type of the object getter.
     */
    private static final class JsonObjectGetterHolder<T> {
        private final Class<T> objectClass;
        private final JsonObjectGetter<T> objectGetter;

        public JsonObjectGetterHolder(final Class<T> objectClass, final JsonObjectGetter<T> objectGetter) {
            this.objectClass = objectClass;
            this.objectGetter = objectGetter;
        }
    }

    public static final JsonObjectGetter<String> STRING_GETTER = register(String.class, jsonElement -> {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isString())
            return ObjectFetchResult.failure("Json element was not a string.");

        return ObjectFetchResult.success(jsonElement.getAsString());
    });

    public static final JsonObjectGetter<Boolean> BOOLEAN_GETTER = register(Boolean.class, jsonElement -> {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isBoolean())
            return ObjectFetchResult.failure("Json element was not a boolean.");

        return ObjectFetchResult.success(jsonElement.getAsBoolean());
    });

    public static final JsonObjectGetter<Number> NUMBER_GETTER = register(Number.class, jsonElement -> {
        if (!jsonElement.isJsonPrimitive() || !jsonElement.getAsJsonPrimitive().isNumber())
            return ObjectFetchResult.failure("Json element was not a number.");

        return ObjectFetchResult.success(jsonElement.getAsNumber());
    });

    public static final JsonObjectGetter<Byte> BYTE_GETTER = register(Byte.class, jsonElement -> {
        final ObjectFetchResult<Number> numberFetch = NUMBER_GETTER.get(jsonElement);

        if (!numberFetch.wasSuccessful())
            return ObjectFetchResult.failureFromOther(numberFetch);

        return ObjectFetchResult.success(numberFetch.getValue().byteValue());
    });

    public static final JsonObjectGetter<Integer> INTEGER_GETTER = register(Integer.class, jsonElement -> {
        final ObjectFetchResult<Number> numberFetch = NUMBER_GETTER.get(jsonElement);

        if (!numberFetch.wasSuccessful())
            return ObjectFetchResult.failureFromOther(numberFetch);

        return ObjectFetchResult.success(numberFetch.getValue().intValue());
    });

    public static final JsonObjectGetter<Double> DOUBLE_GETTER = register(Double.class, jsonElement -> {
        final ObjectFetchResult<Number> numberFetch = NUMBER_GETTER.get(jsonElement);

        if (!numberFetch.wasSuccessful())
            return ObjectFetchResult.failureFromOther(numberFetch);

        return ObjectFetchResult.success(numberFetch.getValue().doubleValue());
    });

    public static final JsonObjectGetter<Float> FLOAT_GETTER = register(Float.class, jsonElement -> {
        final ObjectFetchResult<Number> numberFetch = NUMBER_GETTER.get(jsonElement);

        if (!numberFetch.wasSuccessful())
            return ObjectFetchResult.failureFromOther(numberFetch);

        return ObjectFetchResult.success(numberFetch.getValue().floatValue());
    });

    public static final JsonObjectGetter<JsonObject> JSON_OBJECT_GETTER = register(JsonObject.class, jsonElement -> {
        if (!jsonElement.isJsonObject())
            return ObjectFetchResult.failure("Json element was not a json object.");

        return ObjectFetchResult.success(jsonElement.getAsJsonObject());
    });

    public static final JsonObjectGetter<JsonArray> JSON_ARRAY_GETTER = register(JsonArray.class, jsonElement -> {
        if (!jsonElement.isJsonArray())
            return ObjectFetchResult.failure("Json element was not a json array.");

        return ObjectFetchResult.success(jsonElement.getAsJsonArray());
    });

    public static final JsonObjectGetter<ResourceLocation> RESOURCE_LOCATION_GETTER = register(ResourceLocation.class, jsonElement -> {
        final ObjectFetchResult<String> stringFetchResult = STRING_GETTER.get(jsonElement);

        if (!stringFetchResult.wasSuccessful())
            return ObjectFetchResult.failureFromOther(stringFetchResult);

        try {
            return ObjectFetchResult.success(new ResourceLocation(stringFetchResult.getValue()));
        } catch (ResourceLocationException e) {
            return ObjectFetchResult.failure("Json element was not a valid resource location: " + e.getMessage());
        }
    });

    public static final JsonObjectGetter<Block> BLOCK_GETTER = register(Block.class, new ForgeRegistryEntryGetter<>(ForgeRegistries.BLOCKS, "block"));
    public static final JsonObjectGetter<Item> ITEM_GETTER = register(Item.class, new ForgeRegistryEntryGetter<>(ForgeRegistries.ITEMS, "item"));
    public static final JsonObjectGetter<Effect> EFFECT_GETTER = register(Effect.class, new ForgeRegistryEntryGetter<>(ForgeRegistries.POTIONS, "effect"));

}