package org.displaytag.test;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;


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
        Assert.assertEquals("Wrong base url", generatedSplit[0], expectedSplit[0]);

        // same anchor #
        if (generatedSplit.length > 2)
        {
            Assert.assertEquals("Anchor is different", generatedSplit[2], expectedSplit[2]);
        }
        else if (generatedSplit.length > 1 && (generatedUrl.indexOf("?") == -1))
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

            Assert.assertEquals(
                "Compared urls have different number of parameters",
                expectedParameters.length,
                generatedParameters.length);

            for (int j = 0; j < expectedParameters.length; j++)
            {
                // assuming url?param == url?param=
                String singleParam = expectedParameters[j];
                if (singleParam.indexOf("=") == -1)
                {
                    singleParam += "=";
                }
                Assert.assertTrue(
                    "Expected parameter " + singleParam + " could not be found in generated URL",
                    ArrayUtils.contains(generatedParameters, singleParam));
            }
        }
    }
}
