package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "titlekey" column attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class AbstractTitleKeyAutoColumnTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "titlekeyautocolumn.jsp";
    }

    /**
     * Returns the suffix expected in the specific resource bundle.
     * @return expected suffix
     */
    protected abstract String getExpectedSuffix();

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

        // find the "camel" column
        int j;
        for (j = 0; j < tables[0].getColumnCount(); j++)
        {
            if (KnownValue.CAMEL.equals(tables[0].getCellAsText(1, j)))
            {
                break;
            }
        }

        // resource should be used also without the property attribute for the "camel" header
        assertEquals("Header from resource is not valid.", "camel title" + getExpectedSuffix(), tables[0]
            .getCellAsText(0, j));

    }
}