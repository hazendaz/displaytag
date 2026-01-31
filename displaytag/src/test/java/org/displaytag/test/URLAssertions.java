/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.test;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.junit.jupiter.api.Assertions;

/**
 * Assert class used to compare URLs.
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
        expectedUrl = URLDecoder.decode(expectedUrl, StandardCharsets.UTF_8);
        generatedUrl = URLDecoder.decode(generatedUrl, StandardCharsets.UTF_8);

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
            final String[] generatedParameters = StringUtils.split(Strings.CS.replace(generatedSplit[1], "&amp;", "&"),
                    '&');
            final String[] expectedParameters = StringUtils.split(Strings.CS.replace(expectedSplit[1], "&amp;", "&"),
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
