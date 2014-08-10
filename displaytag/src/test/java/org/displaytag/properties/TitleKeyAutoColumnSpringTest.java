package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nSpringAdapter;
import org.displaytag.localization.LocaleResolver;
import org.junit.Test;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * I18n test with Spring adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyAutoColumnSpringTest extends AbstractTitleKeyAutoColumnTest
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName()
    {
        return super.getJspName() + ".spring";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix()
    {
        return " spring";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nSpringAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver()
    {
        return new I18nSpringAdapter();
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        this.runner.registerServlet("*.spring", DispatcherServlet.class.getName());
        super.doTest();
    }

}
