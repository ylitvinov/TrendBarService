package org.tbs.common;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yury Litvinov
 */
public class CollectionUtils {

    public static <T> Set<T> asSet(T... values) {
        HashSet<T> result = new HashSet<T>();
        for (T value : values) {
            result.add(value);
        }
        return result;
    }
}
