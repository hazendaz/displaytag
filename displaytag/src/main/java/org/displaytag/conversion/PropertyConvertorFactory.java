package org.displaytag.conversion;

import org.displaytag.properties.TableProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.HashMap;


/**
 * Creates a PropertyConvertor. Previously created instances are cached.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public final class PropertyConvertorFactory
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(PropertyConvertorFactory.class);

    /**
     * Known convertors.
     */
    private static Map instantiatedConvertors = new HashMap();

    /**
     * Private constructor.
     */
    private PropertyConvertorFactory()
    {
    }

    /**
     * Get a PropertyConvertor instance; use the indicated properties to choose the implementation.
     * @param properties the properties to use for configuration
     * @return a PropertyConvertor
     */
    public static PropertyConvertor create(TableProperties properties)
    {
        String convClassName = properties.getPropertyConvertorClass();
        PropertyConvertor propConv = (PropertyConvertor) instantiatedConvertors.get(convClassName);
        if (propConv != null)
        {
            return propConv;
        }
        Class convClass = DefaultPropertyConvertor.class;
        try
        {
            convClass = Class.forName(convClassName);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Error: Cannot find convertor class " + convClassName);
        }
        try
        {
            propConv = (PropertyConvertor) convClass.newInstance();
        }
        catch (InstantiationException e)
        {
            log.error("Error: " + e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            log.error("Error: " + e.getMessage(), e);
        }
        if (propConv == null)
        {
            propConv = new DefaultPropertyConvertor();
        }
        instantiatedConvertors.put(convClassName, propConv);
        return propConv;
    }
}
