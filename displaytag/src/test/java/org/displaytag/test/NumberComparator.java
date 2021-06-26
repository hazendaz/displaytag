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
package org.displaytag.test;

import java.util.Comparator;

import org.apache.commons.beanutils.ConvertUtils;


/**
 * Sorts 2 numbers, converted from objects using beanutils Converters.
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class NumberComparator implements Comparator<Object>
{

    /**
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(Object obj1, Object obj2)
    {
        double dbl1 = 0;
        if (obj1 instanceof Number)
        {
            dbl1 = ((Number) obj1).doubleValue();
        }
        else if (obj1 != null)
        {
            dbl1 = ((Number) ConvertUtils.convert(obj1.toString(), Number.class)).doubleValue();
        }

        double dbl2 = 0;
        if (obj2 instanceof Number)
        {
            dbl2 = ((Number) obj2).doubleValue();
        }
        else if (obj1 != null)
        {
            dbl2 = ((Number) ConvertUtils.convert(obj2.toString(), Number.class)).doubleValue();
        }

        return Double.valueOf(dbl1).compareTo(Double.valueOf(dbl2));
    }
}
