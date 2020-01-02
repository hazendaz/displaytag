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
package org.displaytag.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;


/**
 * Assert class used to compare URLs.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class URLAssert
{

    /**
     * Don't instantiate.
     */
    private URLAssert()
    {
        // unused
    }

    /**
     * utility method for comparing two URLs.
     * @param expectedUrl expected URL
     * @param generatedUrl generated URL
     */
    public static void assertEquals(String expectedUrl, String generatedUrl)
    {
        try
        {
            expectedUrl = URLDecoder.decode(expectedUrl, "UTF-8");
            generatedUrl = URLDecoder.decode(generatedUrl, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }

        // hack for missing base url
        if (expectedUrl.startsWith("?"))
        {
            expectedUrl = "[empty]" + expectedUrl;
        }
        if (generatedUrl.startsWith("?"))
        {
            generatedUrl = "[empty]" + generatedUrl;
        }

        // if urls contains parameters they could be written in different order
        String[] generatedSplit = StringUtils.split(generatedUrl, "?#");
        String[] expectedSplit = StringUtils.split(expectedUrl, "?#");

        Assert.assertEquals(
            "Different number of tokens (base, parameters, anchor) in link.",
            generatedSplit.length,
            expectedSplit.length);

        // same base url
        Assert.assertEquals("Wrong base url", expectedSplit[0], generatedSplit[0]);

        // same anchor #
        if (generatedSplit.length > 2)
        {
            Assert.assertEquals("Anchor is different", generatedSplit[2], expectedSplit[2]);
        }
        else if (generatedSplit.length > 1 && generatedUrl.indexOf("?") == -1)
        {
            // url without parameters
            Assert.assertEquals("Anchor is different", generatedSplit[1], expectedSplit[1]);
            return;
        }

        // same parameters
        if (generatedSplit.length > 1)
        {
            // compare parameters
            String[] generatedParameters = StringUtils.split(StringUtils.replace(generatedSplit[1], "&amp;", "&"), '&');
            String[] expectedParameters = StringUtils.split(StringUtils.replace(expectedSplit[1], "&amp;", "&"), '&');

            Assert.assertEquals("Compared urls have different number of parameters. Expected "
                + expectedUrl
                + " - generated "
                + generatedUrl, expectedParameters.length, generatedParameters.length);

            for (String expectedParameter : expectedParameters) {
                // assuming url?param == url?param=
                String singleParam = expectedParameter;
                if (singleParam.indexOf("=") == -1)
                {
                    singleParam += "=";
                }
                Assert.assertTrue("Expected parameter "
                    + singleParam
                    + " could not be found in generated URL. Expected url "
                    + expectedUrl
                    + " - generated "
                    + generatedUrl, ArrayUtils.contains(generatedParameters, singleParam));
            }
        }
    }
}
