package org.displaytag.properties;

import org.apache.struts.action.ActionServlet;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nStrutsAdapter;
import org.displaytag.localization.LocaleResolver;
import org.junit.Test;


/**
 * I18n test with Struts adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyStrutsTest extends AbstractTitleKeyTest
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName()
    {
        return super.getJspName() + ".struts";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix()
    {
        return " struts";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nStrutsAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver()
    {
        return new I18nStrutsAdapter();
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        this.runner.registerServlet("*.struts", ActionServlet.class.getName());
        super.doTest();
    }

}
