package com.harleyoconnor.potionsexpansion.util.json;

import com.google.gson.JsonElement;

/**
 * @author Harley O'Connor
 */
public final class JsonHelper {

    /**
     * Determines if a {@link JsonElement} is a comment (comments start with an underscore).
     *
     * @param jsonElement The {@link JsonElement} object.
     * @return True if {@link JsonElement} is a comment.
     */
    public static boolean isComment(final JsonElement jsonElement) {
        final ObjectFetchResult<String> fetchResult = JsonObjectGetters.STRING_GETTER.get(jsonElement);
        return fetchResult.wasSuccessful() && isComment(fetchResult.getValue());
    }

    /**
     * Determines if the key of a {@link JsonElement} is a comment (comments start with
     * an underscore).
     *
     * @param key The key of the {@link JsonElement}.
     * @return True if key is a comment.
     */
    public static boolean isComment(final String key) {
        return key.startsWith("_");
    }

}
