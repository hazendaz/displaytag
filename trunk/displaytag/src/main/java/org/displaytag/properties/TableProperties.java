package org.displaytag.properties;

import java.util.Properties;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TablePropertiesLoadException;
import org.displaytag.export.MediaTypeEnum;

/**
 * The properties used by the Table tags.  The properties are loaded in the following order,
 * in increasing order of priority.
 *<ol>
 * <li> First, from the TableTag.properties included with the DisplayTag distribution.  </li>
 * <li>Then, from the file displaytag.properties, if it is present; these properties are
 * intended to be set by the user for sitewide application.  </li>
 * <li>Finally, if this class has a userProperties defined, all of the properties from that
 * Properties object are copied in as well.  The userProperties Properties can be set by the
 * {@link DisplayPropertiesLoaderServlet} if it is configured.</li>
 * </ol>
 *
 * @author fgiust
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @see DisplayPropertiesLoaderServlet
 */
public class TableProperties
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(TableProperties.class);

    /**
     * Field DEFAULT_FILENAME
     */
    private static final String DEFAULT_FILENAME = "TableTag.properties";

    /**
     * Field mPropertiesFilename
     */
    private static String mPropertiesFilename = DEFAULT_FILENAME;

    /**
     * The name of the local properties file that is searched for on the
     * classpath. Settings in this file will override the defaults loaded
     * from TableTag.properties.
     */
    public static final String LOCAL_PROPERTIES = "displaytag";

    /**
     * The userProperties are  local, non-default properties; these
     * settings override the defaults from displaytag.properties
     * and TableTag.properties.
     */
    private static Properties userProperties = new Properties();

    /**
     * Local, non-default properties; these settings override the defaults
     * from displaytag.properties and TableTag.properties.
     * @return the Properties that was set
     */
    public static Properties getUserProperties()
    {
        return userProperties;
    }

    /**
     * Local, non-default properties; these settings override the defaults
     * from displaytag.properties and TableTag.properties.  Please note that the values are copied in,
     * so that multiple calls with non-overlapping properties will be merged, not overwritten.
     * @param overrideProperties - The local, non-default properties
     */
    public static void setUserProperties(Properties overrideProperties)
    {
        Enumeration enum = overrideProperties.keys();
        while (enum.hasMoreElements())
        {
            String key = (String) enum.nextElement();
            if (key != null && overrideProperties.get(key) != null)
            {
                userProperties.setProperty(key, (String) overrideProperties.get(key));
            }
        }
    }

    /**
     * Field mProp
     */
    private Properties properties = null;

    /**
     * Field PROPERTY_STRING_EXPORT_LABEL
     */
    private static final String PROPERTY_BOOLEAN_EXPORT_PREFIX = "export";

    /**
     * Field PROPERTY_STRING_EXPORT_LABEL
     */
    private static final String PROPERTY_STRING_EXPORT_LABEL = "label";

    /**
     * Field PROPERTY_BOOLEAN_EXPORT_HEADER
     */
    private static final String PROPERTY_BOOLEAN_EXPORT_HEADER = "include_header";

    /**
     * Field PROPERTY_STRING_EXPORTBANNER
     */
    private static final String PROPERTY_STRING_EXPORTBANNER = "export.banner";
    /**
     * Field PROPERTY_STRING_EXPORTBANNER_SEPARATOR
     */
    private static final String PROPERTY_STRING_EXPORTBANNER_SEPARATOR = "export.banner.sepchar";

    /**
     * Field PROPERTY_BOOLEAN_EXPORTDECORATED
     */
    private static final String PROPERTY_BOOLEAN_EXPORTDECORATED = "export.decorated";

    /**
     * Field PROPERTY_STRING_EXPORTAMOUNT
     */
    private static final String PROPERTY_STRING_EXPORTAMOUNT = "export.amount";
    /**
     * Field PROPERTY_STRING_EXPORT_FILENAME
     */
    private static final String PROPERTY_STRING_EXPORT_FILENAME = "export.filename";

    /**
     * Field PROPERTY_BOOLEAN_SHOWHEADER
     */
    private static final String PROPERTY_BOOLEAN_SHOWHEADER = "basic.show.header";
    /**
     * Field PROPERTY_STRING_EMPTYLIST_MESSAGE
     */
    private static final String PROPERTY_STRING_EMPTYLIST_MESSAGE = "basic.msg.empty_list";

    /**
     * Field PROPERTY_STRING_BANNER_PLACEMENT
     */
    private static final String PROPERTY_STRING_BANNER_PLACEMENT = "paging.banner.placement";

    /**
     * Field PROPERTY_STRING_PAGING_INVALIDPAGE
     */
    private static final String PROPERTY_STRING_PAGING_INVALIDPAGE = "error.msg.invalid_page";

    /**
     * Field PROPERTY_STRING_PAGING_ITEM_NAME
     */
    private static final String PROPERTY_STRING_PAGING_ITEM_NAME = "paging.banner.item_name";
    /**
     * Field PROPERTY_STRING_PAGING_ITEMS_NAME
     */
    private static final String PROPERTY_STRING_PAGING_ITEMS_NAME = "paging.banner.items_name";
    /**
     * Field PROPERTY_STRING_PAGING_NOITEMS
     */
    private static final String PROPERTY_STRING_PAGING_NOITEMS = "paging.banner.no_items_found";
    /**
     * Field PROPERTY_STRING_PAGING_FOUND_ONEITEM
     */
    private static final String PROPERTY_STRING_PAGING_FOUND_ONEITEM = "paging.banner.one_item_found";
    /**
     * Field PROPERTY_STRING_PAGING_FOUND_ALLITEMS
     */
    private static final String PROPERTY_STRING_PAGING_FOUND_ALLITEMS = "paging.banner.all_items_found";
    /**
     * Field PROPERTY_STRING_PAGING_FOUND_SOMEITEMS
     */
    private static final String PROPERTY_STRING_PAGING_FOUND_SOMEITEMS = "paging.banner.some_items_found";

    /**
     * Field PROPERTY_INT_PAGING_GROUPSIZE
     */
    private static final String PROPERTY_INT_PAGING_GROUPSIZE = "paging.banner.group_size";
    /**
     * Field PROPERTY_STRING_PAGING_BANNER_ONEPAGE
     */
    private static final String PROPERTY_STRING_PAGING_BANNER_ONEPAGE = "paging.banner.onepage";
    /**
     * Field PROPERTY_STRING_PAGING_BANNER_FIRST
     */
    private static final String PROPERTY_STRING_PAGING_BANNER_FIRST = "paging.banner.first";
    /**
     * Field PROPERTY_STRING_PAGING_BANNER_LAST
     */
    private static final String PROPERTY_STRING_PAGING_BANNER_LAST = "paging.banner.last";
    /**
     * Field PROPERTY_STRING_PAGING_BANNER_FULL
     */
    private static final String PROPERTY_STRING_PAGING_BANNER_FULL = "paging.banner.full";
    /**
     * Field PROPERTY_STRING_PAGING_PAGE_LINK
     */
    private static final String PROPERTY_STRING_PAGING_PAGE_LINK = "paging.banner.page.link";
    /**
     * Field PROPERTY_STRING_PAGING_PAGE_SELECTED
     */
    private static final String PROPERTY_STRING_PAGING_PAGE_SELECTED = "paging.banner.page.selected";
    /**
     * Field PROPERTY_STRING_PAGING_PAGE_SPARATOR
     */
    private static final String PROPERTY_STRING_PAGING_PAGE_SPARATOR = "paging.banner.page.separator";

    /**
     * Field PROPERTY_STRING_SAVE_EXCEL_FILENAME
     */
    private static final String PROPERTY_STRING_SAVE_EXCEL_FILENAME = "save.excel.filename";
    /**
     * Field PROPERTY_STRING_SAVE_EXCEL_BANNER
     */
    private static final String PROPERTY_STRING_SAVE_EXCEL_BANNER = "save.excel.banner";

    /**
     * Method getPagingInvalidPage
     * @return String
     */
    public String getPagingInvalidPage()
    {
        return getProperty(PROPERTY_STRING_PAGING_INVALIDPAGE);
    }

    /**
     * Method getPagingItemName
     * @return String
     */
    public String getPagingItemName()
    {
        return getProperty(PROPERTY_STRING_PAGING_ITEM_NAME);
    }

    /**
     * Method getPagingItemsName
     * @return String
     */
    public String getPagingItemsName()
    {
        return getProperty(PROPERTY_STRING_PAGING_ITEMS_NAME);
    }

    /**
     * Method getPagingFoundNoItems
     * @return String
     */
    public String getPagingFoundNoItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_NOITEMS);
    }

    /**
     * Method getPagingFoundOneItem
     * @return String
     */
    public String getPagingFoundOneItem()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_ONEITEM);
    }

    /**
     * Method getPagingFoundAllItems
     * @return String
     */
    public String getPagingFoundAllItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_ALLITEMS);
    }

    /**
     * Method getPagingFoundSomeItems
     * @return String
     */
    public String getPagingFoundSomeItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_SOMEITEMS);
    }

    /**
     * Method getPagingGroupSize
     * @param pDefault int
     * @return int
     */
    public int getPagingGroupSize(int pDefault)
    {
        return getIntProperty(PROPERTY_INT_PAGING_GROUPSIZE, pDefault);
    }

    /**
     * Method getPagingBannerOnePage
     * @return String
     */
    public String getPagingBannerOnePage()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_ONEPAGE);
    }

    /**
     * Method getPagingBannerFirst
     * @return String
     */
    public String getPagingBannerFirst()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_FIRST);
    }

    /**
     * Method getPagingBannerLast
     * @return String
     */
    public String getPagingBannerLast()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_LAST);
    }

    /**
     * Method getPagingBannerFull
     * @return String
     */
    public String getPagingBannerFull()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_FULL);
    }

    /**
     * Method getPagingPageLink
     * @return String
     */
    public String getPagingPageLink()
    {
        return getProperty(PROPERTY_STRING_PAGING_PAGE_LINK);
    }

    /**
     * Method getPagingPageSelected
     * @return String
     */
    public String getPagingPageSelected()
    {
        return getProperty(PROPERTY_STRING_PAGING_PAGE_SELECTED);
    }

    /**
     * Method getPagingPageSeparator
     * @return String
     */
    public String getPagingPageSeparator()
    {
        return getProperty(PROPERTY_STRING_PAGING_PAGE_SPARATOR);
    }

    /**
     * Is the given export option enabled?
     * @param exportType instance of MediaTypeEnum
     * @return boolean true if export is enabled
     */
    public boolean getAddExport(MediaTypeEnum exportType)
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType.getName());
    }

    /**
     * Should headers be included in given export type?
     * @param exportType instance of MediaTypeEnum
     * @return boolean true if export should include headers
     */
    public boolean getExportHeader(MediaTypeEnum exportType)
    {
        return getBooleanProperty(
            PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + PROPERTY_BOOLEAN_EXPORT_HEADER);
    }

    /**
     * Returns the label for the given export option
     * @param exportType instance of MediaTypeEnum
     * @return String label
     */
    public String getExportLabel(MediaTypeEnum exportType)
    {
        return getProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + PROPERTY_STRING_EXPORT_LABEL);
    }

    /**
     * Method getExportDecorated
     * @return boolean
     */
    public boolean getExportDecorated()
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_EXPORTDECORATED);
    }

    /**
     * Method getExportFileName
     * @return String
     */
    public String getExportFileName()
    {
        return getProperty(PROPERTY_STRING_EXPORT_FILENAME);
    }

    /**
     * Method getExportBanner
     * @return String
     */
    public String getExportBanner()
    {
        return getProperty(PROPERTY_STRING_EXPORTBANNER);
    }

    /**
     * Method getExportBannerSeparator
     * @return String
     */
    public String getExportBannerSeparator()
    {
        return getProperty(PROPERTY_STRING_EXPORTBANNER_SEPARATOR);
    }

    /**
     * Method getShowHeader
     * @return boolean
     */
    public boolean getShowHeader()
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_SHOWHEADER);
    }

    /**
     * Method getEmptyListMessage
     * @return String
     */
    public String getEmptyListMessage()
    {
        return getProperty(PROPERTY_STRING_EMPTYLIST_MESSAGE);
    }

    /**
     * Method getExportFullList
     * @return boolean
     */
    public boolean getExportFullList()
    {
        return "list".equals(getProperty(PROPERTY_STRING_EXPORTAMOUNT));
    }

    /**
     * Method getAddPagingBannerTop
     * @return boolean
     */
    public boolean getAddPagingBannerTop()
    {
        String lPlacement = getProperty(PROPERTY_STRING_BANNER_PLACEMENT);
        return "top".equals(lPlacement) || "both".equals(lPlacement);
    }

    /**
     * Method getAddPagingBannerBottom
     * @return boolean
     */
    public boolean getAddPagingBannerBottom()
    {
        String lPlacement = getProperty(PROPERTY_STRING_BANNER_PLACEMENT);
        return "bottom".equals(lPlacement) || "both".equals(lPlacement);
    }

    /**
     * Method getSaveExcelFilename
     * @return String
     */
    public String getSaveExcelFilename()
    {
        return getProperty(PROPERTY_STRING_SAVE_EXCEL_FILENAME);
    }

    /**
     * Method getSaveExcelBanner
     * @return String
     */
    public String getSaveExcelBanner()
    {
        return getProperty(PROPERTY_STRING_SAVE_EXCEL_BANNER);
    }

    /**
     * Method getNoColumnMessage
     * @return String
     */
    public String getNoColumnMessage()
    {
        return "Please provide column tags";
    }

    /**
     * Initialize a new TableProperties loading the default properties file and the user defined one
     * @throws TablePropertiesLoadException for errors during loading of properties files
     */
    public TableProperties() throws TablePropertiesLoadException
    {

        Properties lDefaultProperties = new Properties();
        try
        {
            lDefaultProperties.load(this.getClass().getResourceAsStream(mPropertiesFilename));
            properties = new Properties(lDefaultProperties);
        }
        catch (Exception ex)
        {
            throw new TablePropertiesLoadException(getClass(), mPropertiesFilename, ex);
        }

        // Try to load the properties from the local properties file,
        // displaytag.properties.
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(LOCAL_PROPERTIES);
            Enumeration keys = bundle.getKeys();
            while (keys.hasMoreElements())
            {
                String key = (String) keys.nextElement();
                properties.setProperty(key, bundle.getString(key));
            }
        }
        catch (MissingResourceException e)
        {
            mLog.info("Was not able to load a displaytag.properties; " + e.getMessage());
        }

        // Now copy in the user properties
        Enumeration keys = userProperties.keys();
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            if (key != null)
            {
                properties.setProperty(key, (String) userProperties.get(key));
            }
        }
    }

    /**
     * Method getProperty
     * @param pPropertyName String
     * @return String
     */
    private String getProperty(String pPropertyName)
    {
        return properties.getProperty(pPropertyName);
    }

    /**
     * Method setProperty
     * @param pPropertyName String
     * @param pPropertyValue String
     */
    public void setProperty(String pPropertyName, String pPropertyValue)
    {
        properties.setProperty(pPropertyName, pPropertyValue);
    }

    /**
     * Method getBooleanProperty
     * @param pPropertyName String
     * @return boolean
     */
    private boolean getBooleanProperty(String pPropertyName)
    {
        return Boolean.TRUE.toString().equals(getProperty(pPropertyName));
    }

    /**
     * Method getIntProperty
     * @param pPropertyName String
     * @param pDefault int
     * @return int
     */
    private int getIntProperty(String pPropertyName, int pDefault)
    {
        int lInt = pDefault;
        try
        {
            lInt = Integer.parseInt(getProperty(pPropertyName));
        }
        catch (NumberFormatException e)
        {
            // Don't care, use default
            mLog.warn(
                "Invalid value for \"pPropertyName\" property: value=\""
                    + getProperty(pPropertyName)
                    + "\"; using default \"pDefault\"");
        }

        return lInt;
    }

}