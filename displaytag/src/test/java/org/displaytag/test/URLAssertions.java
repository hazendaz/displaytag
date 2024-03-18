/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;

/**
 * Assert class used to compare URLs.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class URLAssertions {

    /**
     * Don't instantiate.
     */
    private URLAssertions() {
        // unused
    }

    /**
     * utility method for comparing two URLs.
     *
     * @param expectedUrl
     *            expected URL
     * @param generatedUrl
     *            generated URL
     */
    public static void assertEquals(String expectedUrl, String generatedUrl) {
        try {
            expectedUrl = URLDecoder.decode(expectedUrl, StandardCharsets.UTF_8.name());
            generatedUrl = URLDecoder.decode(generatedUrl, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // hack for missing base url
        if (expectedUrl.startsWith("?")) {
            expectedUrl = "[empty]" + expectedUrl;
        }
        if (generatedUrl.startsWith("?")) {
            generatedUrl = "[empty]" + generatedUrl;
        }

        // if urls contains parameters they could be written in different order
        final String[] generatedSplit = StringUtils.split(generatedUrl, "?#");
        final String[] expectedSplit = StringUtils.split(expectedUrl, "?#");

        Assertions.assertEquals(generatedSplit.length, expectedSplit.length,
                "Different number of tokens (base, parameters, anchor) in link.");

        // same base url
        Assertions.assertEquals(expectedSplit[0], generatedSplit[0], "Wrong base url");

        // same anchor #
        if (generatedSplit.length > 2) {
            Assertions.assertEquals(generatedSplit[2], expectedSplit[2], "Anchor is different");
        } else if (generatedSplit.length > 1 && generatedUrl.indexOf("?") == -1) {
            // url without parameters
            Assertions.assertEquals(generatedSplit[1], expectedSplit[1], "Anchor is different");
            return;
        }

        // same parameters
        if (generatedSplit.length > 1) {
            // compare parameters
            final String[] generatedParameters = StringUtils.split(StringUtils.replace(generatedSplit[1], "&amp;", "&"),
                    '&');
            final String[] expectedParameters = StringUtils.split(StringUtils.replace(expectedSplit[1], "&amp;", "&"),
                    '&');

            Assertions.assertEquals(expectedParameters.length, generatedParameters.length,
                    "Compared urls have different number of parameters. Expected " + expectedUrl + " - generated "
                            + generatedUrl);

            for (final String expectedParameter : expectedParameters) {
                // assuming url?param == url?param=
                String singleParam = expectedParameter;
                if (singleParam.indexOf("=") == -1) {
                    singleParam += "=";
                }
                Assertions.assertTrue(ArrayUtils.contains(generatedParameters, singleParam),
                        "Expected parameter " + singleParam + " could not be found in generated URL. Expected url "
                                + expectedUrl + " - generated " + generatedUrl);
            }
        }
    }
}
