/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.decorator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for TableDecorator.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
class TableDecoratorTest {

    /**
     * test decorator one.
     */
    private TableDecorator one;

    /**
     * test decorator 2.
     */
    private TableDecorator two;

    /**
     * test decorator 3.
     */
    private TableDecorator three;

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        this.one = new TableDecoratorOne();
        this.two = new TableDecoratorTwo();
        this.three = new TableDecoratorThree();
    }

    /**
     * test that property list is not shared between decorators. (testcase for [840011])
     */
    @Test
    void testDecoratorPropertyCache() {
        Assertions.assertTrue(this.one.hasGetterFor("one"), "decorator one - property one, expected true");
        Assertions.assertTrue(this.one.hasGetterFor("one.something"), "decorator one - property one, expected true");
        Assertions.assertTrue(this.two.hasGetterFor("two"), "decorator two - property two, expected true");

        Assertions.assertFalse(this.one.hasGetterFor("two"), "decorator one - property two, expected false");
        Assertions.assertFalse(this.two.hasGetterFor("one"), "decorator two - property one, expected false");
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    void testMappedProperty() {
        Assertions.assertTrue(this.one.hasGetterFor("mapped(one)"), "mapped property not recognized");
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    void testNotExistingMappedProperty() {
        Assertions.assertFalse(this.one.hasGetterFor("something(one)"), "Invalid mapped property recognized");
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    void testIndexedProperty() {
        Assertions.assertTrue(this.one.hasGetterFor("indexed[0]"), "indexed property not recognized");
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    void testNotExistingIndexedProperty() {
        Assertions.assertFalse(this.one.hasGetterFor("something[0]"), "Invalid indexed property recognized");
    }

    /**
     * test for read only properties.
     */
    @Test
    void testWriteOnlyProperty() {
        Assertions.assertFalse(this.three.hasGetterFor("simple"), "Invalid simple property recognized");
    }

    /**
     * test for read only properties.
     */
    @Test
    void testReadOnlyProperty() {
        Assertions.assertTrue(this.two.hasGetterFor("two"), "Simple property not recognized");
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    void testReadOnlyIndexedProperty() {
        Assertions.assertFalse(this.three.hasGetterFor("something[0]"), "Invalid indexed property recognized");
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    void testReadOnlyMappedProperty() {
        Assertions.assertFalse(this.three.hasGetterFor("something(one)"), "Invalid mapped property recognized");
    }

}
