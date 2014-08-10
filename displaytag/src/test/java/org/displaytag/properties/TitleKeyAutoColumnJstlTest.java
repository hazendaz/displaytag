package org.displaytag.properties;

import org.displaytag.localization.I18nJstlAdapter;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;


/**
 * I18n test with JSTL adapter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyAutoColumnJstlTest extends AbstractTitleKeyAutoColumnTest
{

    /**
     * @see org.displaytag.properties.AbstractTitleKeyTest#getExpectedSuffix()
     */
    @Override
    protected String getExpectedSuffix()
    {
        return "";
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyAutoColumnTest#getI18nResourceProvider()
     */
    @Override
    protected I18nResourceProvider getI18nResourceProvider()
    {
        return new I18nJstlAdapter();
    }

    /**
     * @see org.displaytag.properties.AbstractTitleKeyAutoColumnTest#getResolver()
     */
    @Override
    protected LocaleResolver getResolver()
    {
        return new I18nJstlAdapter();
    }

}
