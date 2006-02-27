package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "titlekey" column attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class AbstractTitleKeyTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "titlekey.jsp";
    }

    /**
     * Returns the LocaleResolver instance to be used in this test.
     * @return LocaleResolver
     */
    protected abstract LocaleResolver getResolver();

    /**
     * Returns the I18nResourceProvider instance to be used in this test.
     * @return I18nResourceProvider
     */
    protected abstract I18nResourceProvider getI18nResourceProvider();

    /**
     * Returns the suffix expected in the specific resource bundle.
     * @return expected suffix
     */
    protected abstract String getExpectedSuffix();

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);

        TableProperties.setLocaleResolver(getResolver());
        TableProperties.setResourceProvider(getI18nResourceProvider());

        WebResponse response;
        try
        {
            response = runner.getResponse(request);
        }
        finally
        {
            // reset
            TableProperties.setLocaleResolver(null);
            TableProperties.setResourceProvider(null);
        }

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table", 1, tables.length);

        assertEquals("Header from resource is not valid.", //
            "foo title" + getExpectedSuffix(), tables[0].getCellAsText(0, 0));

        assertEquals("Header from resource is not valid.", //
            "baz title" + getExpectedSuffix(), tables[0].getCellAsText(0, 1));

        assertEquals("Header from resource is not valid.", //
            "camel title" + getExpectedSuffix(), tables[0].getCellAsText(0, 2));

        assertEquals("Missing resource should generate the ???missing??? header.", "???missing???", tables[0]
            .getCellAsText(0, 3));

    }
}