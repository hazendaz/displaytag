/**
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
package org.displaytag.properties;

import org.displaytag.localization.I18nJstlAdapter;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;


/**
 * I18n test with JSTL adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyJstlTest extends AbstractTitleKeyTest
{

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix()
    {
        return "";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nJstlAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver()
    {
        return new I18nJstlAdapter();
    }

}
