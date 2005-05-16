package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nWebworkAdapter;
import org.displaytag.localization.LocaleResolver;

import com.opensymphony.webwork.dispatcher.ServletDispatcher;


/**
 * I18n test with WebWork adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyWebWorkTest extends AbstractTitleKeyTest
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return super.getJspName() + ".webwork";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    protected String getExpectedSuffix()
    {
        return " webwork";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nWebworkAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    protected LocaleResolver getResolver()
    {
        return new I18nWebworkAdapter();
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    public void doTest(String jspName) throws Exception
    {
        this.runner.registerServlet("*.webwork", ServletDispatcher.class.getName());
        super.doTest(jspName);
    }

}
