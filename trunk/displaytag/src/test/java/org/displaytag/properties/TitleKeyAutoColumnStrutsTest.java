package org.displaytag.properties;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.I18nStrutsAdapter;
import org.displaytag.localization.LocaleResolver;


/**
 * I18n test with Struts adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyAutoColumnStrutsTest extends AbstractTitleKeyAutoColumnTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public TitleKeyAutoColumnStrutsTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getI18nResourceProvider()
     */
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nStrutsAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getResolver()
     */
    protected LocaleResolver getResolver()
    {
        return new I18nStrutsAdapter();
    }

}
