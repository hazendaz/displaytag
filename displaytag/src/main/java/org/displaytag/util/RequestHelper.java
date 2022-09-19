/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.util;

import java.util.Map;

/**
 * A RequestHelper object is used to read parameters from the request. Main features are handling of numeric parameters
 * and the ability to create Href objects from the current request.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public interface RequestHelper {

    /**
     * return the current Href for the request (base url and parameters).
     *
     * @return Href
     */
    Href getHref();

    /**
     * Reads a String parameter from the request.
     *
     * @param key
     *            String parameter name
     *
     * @return String parameter value
     */
    String getParameter(String key);

    /**
     * Reads an Integer parameter from the request.
     *
     * @param key
     *            String parameter name
     *
     * @return Integer parameter value or null if the parameter is not found or it can't be transformed to an Integer
     */
    Integer getIntParameter(String key);

    /**
     * Returns a Map containing all the parameters in the request.
     *
     * @return Map
     */
    Map<String, String[]> getParameterMap();

}
