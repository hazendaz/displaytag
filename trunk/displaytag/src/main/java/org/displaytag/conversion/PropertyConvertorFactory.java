package org.displaytag.conversion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.TableProperties;


/**
 * Creates a Convertor. Previously created instances are cached.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public final class PropertyConvertorFactory
{

    /**
     * The convert utils bean, with standard converters registered.
     */
    private static ConvertUtilsBean defaultConvertorSource = new ConvertUtilsBean();

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
     * Get a Converter instance; use the indicated properties to choose the implementation.
     * @param properties the properties to use for configuration
     * @return a Converter
     */
    public static Converter createNumberConverter(TableProperties properties)
    {
        String specializedConvClassName = properties.getPropertyConvertorClass();
        if (StringUtils.isBlank(specializedConvClassName))
        {
            return defaultConvertorSource.lookup(Number.class);
        }
        Converter propConv = (Converter) instantiatedConvertors.get(specializedConvClassName);

        if (propConv != null)
        {
            return propConv;
        }
        Class convClass = DefaultPropertyConvertor.class;
        try
        {
            convClass = Class.forName(specializedConvClassName);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Error: Cannot find convertor class " + specializedConvClassName);
        }
        try
        {
            propConv = (Converter) convClass.newInstance();
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
        instantiatedConvertors.put(specializedConvClassName, propConv);
        return propConv;
    }
}
