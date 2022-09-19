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
// Borrowed from tomcat 8 so we can get to tomcat 7 more easily here. //

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.displaytag.util;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

import org.apache.tomcat.InstanceManager;

/**
 * SimpleInstanceManager Implement the org.apache.tomcat.InstanceManager interface.
 */
// NOTE: This is necessary until we can upgrade to tomcat 8+
public class SimpleInstanceManager implements InstanceManager {

    /**
     * Instantiates a new simple instance manager.
     */
    public SimpleInstanceManager() {
    }

    /**
     * New instance.
     *
     * @param clazz
     *            the clazz
     *
     * @return the object
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NamingException
     *             the naming exception
     * @throws InstantiationException
     *             the instantiation exception
     */
    @Override
    public Object newInstance(final Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException, NamingException, InstantiationException {
        return this.prepareInstance(clazz.newInstance());
    }

    /**
     * New instance.
     *
     * @param className
     *            the class name
     *
     * @return the object
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NamingException
     *             the naming exception
     * @throws InstantiationException
     *             the instantiation exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    @Override
    public Object newInstance(final String className) throws IllegalAccessException, InvocationTargetException,
            NamingException, InstantiationException, ClassNotFoundException {
        final Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        return this.prepareInstance(clazz.newInstance());
    }

    /**
     * New instance.
     *
     * @param fqcn
     *            the fqcn
     * @param classLoader
     *            the class loader
     *
     * @return the object
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NamingException
     *             the naming exception
     * @throws InstantiationException
     *             the instantiation exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    @Override
    public Object newInstance(final String fqcn, final ClassLoader classLoader) throws IllegalAccessException,
            InvocationTargetException, NamingException, InstantiationException, ClassNotFoundException {
        final Class<?> clazz = classLoader.loadClass(fqcn);
        return this.prepareInstance(clazz.newInstance());
    }

    /**
     * New instance.
     *
     * @param o
     *            the o
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NamingException
     *             the naming exception
     */
    @Override
    public void newInstance(final Object o) throws IllegalAccessException, InvocationTargetException, NamingException {
        this.prepareInstance(o);
    }

    /**
     * Destroy instance.
     *
     * @param o
     *            the o
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Override
    public void destroyInstance(final Object o) throws IllegalAccessException, InvocationTargetException {
    }

    /**
     * Prepare instance.
     *
     * @param o
     *            the o
     *
     * @return the object
     */
    private Object prepareInstance(final Object o) {
        return o;
    }
}
