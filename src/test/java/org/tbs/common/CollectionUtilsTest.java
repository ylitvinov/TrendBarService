package org.tbs.common;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Yury Litvinov
 */
public class CollectionUtilsTest {

    @Test(expected = NullPointerException.class)
    public void testNullInAsSet() throws Exception {
        CollectionUtils.asSet(null);
    }

    @Test()
    public void testEmptyInAsSet() throws Exception {
        CollectionUtils.asSet().isEmpty();
    }

    @Test()
    public void testDifferentTypesAsSet() throws Exception {
        assertThat(CollectionUtils.asSet(1, "a")).containsOnly(1, "a");
    }

    @Test()
    public void testDuplicationsRemoveTypesAsSet() throws Exception {
        assertThat(CollectionUtils.asSet(1, "a", 1)).containsOnly(1, "a");
    }
}
