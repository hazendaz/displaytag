package org.displaytag.properties;

import java.io.IOException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TablePropertiesLoadException;
import org.displaytag.export.MediaTypeEnum;

/**
 * The properties used by the Table tags. The properties are loaded in the following order, in increasing order of
 * priority.
 * <ol>
 * <li>First, from the TableTag.properties included with the DisplayTag distribution.</li>
 * <li>Then, from the file displaytag.properties, if it is present; these properties are intended to be set by the
 * user for sitewide application.</li>
 * <li>Finally, if this class has a userProperties defined, all of the properties from that Properties object are
 * copied in as well. The userProperties Properties can be set by the {@link DisplayPropertiesLoaderServlet}if it is
 * configured.</li>
 * </ol>
 * @author fgiust
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @see DisplayPropertiesLoaderServlet
 */
public class TableProperties
{

    /**
     * name of the default properties file name ("TableTag.properties").
     */
    public static final String DEFAULT_FILENAME = "TableTag.properties";

    /**
     * The name of the local properties file that is searched for on the classpath. Settings in this file will override
     * the defaults loaded from TableTag.properties.
     */
    public static final String LOCAL_PROPERTIES = "displaytag";

    /**
     * property <code>export.banner</code>.
     */
    public static final String PROPERTY_STRING_EXPORTBANNER = "export.banner";

    /**
     * property <code>export.banner.sepchar</code>.
     */
    public static final String PROPERTY_STRING_EXPORTBANNER_SEPARATOR = "export.banner.sepchar";

    /**
     * property <code>export.decorated</code>.
     */
    public static final String PROPERTY_BOOLEAN_EXPORTDECORATED = "export.decorated";

    /**
     * property <code>export.amount</code>.
     */
    public static final String PROPERTY_STRING_EXPORTAMOUNT = "export.amount";

    /**
     * property <code>export.filename</code>.
     */
    public static final String PROPERTY_STRING_EXPORT_FILENAME = "export.filename";

    /**
     * property <code>basic.show.header</code>.
     */
    public static final String PROPERTY_BOOLEAN_SHOWHEADER = "basic.show.header";

    /**
     * property <code>basic.msg.empty_list</code>.
     */
    public static final String PROPERTY_STRING_EMPTYLIST_MESSAGE = "basic.msg.empty_list";

    /**
     * property <code>paging.banner.placement</code>.
     */
    public static final String PROPERTY_STRING_BANNER_PLACEMENT = "paging.banner.placement";

    /**
     * property <code>error.msg.invalid_page</code>.
     */
    public static final String PROPERTY_STRING_PAGING_INVALIDPAGE = "error.msg.invalid_page";

    /**
     * property <code>paging.banner.item_name</code>.
     */
    public static final String PROPERTY_STRING_PAGING_ITEM_NAME = "paging.banner.item_name";

    /**
     * property <code>paging.banner.items_name</code>.
     */
    public static final String PROPERTY_STRING_PAGING_ITEMS_NAME = "paging.banner.items_name";

    /**
     * property <code>paging.banner.no_items_found</code>.
     */
    public static final String PROPERTY_STRING_PAGING_NOITEMS = "paging.banner.no_items_found";

    /**
     * property <code>paging.banner.one_item_found</code>.
     */
    public static final String PROPERTY_STRING_PAGING_FOUND_ONEITEM = "paging.banner.one_item_found";

    /**
     * property <code>paging.banner.all_items_found</code>.
     */
    public static final String PROPERTY_STRING_PAGING_FOUND_ALLITEMS = "paging.banner.all_items_found";

    /**
     * property <code>paging.banner.some_items_found</code>.
     */
    public static final String PROPERTY_STRING_PAGING_FOUND_SOMEITEMS = "paging.banner.some_items_found";

    /**
     * property <code>paging.banner.group_size</code>.
     */
    public static final String PROPERTY_INT_PAGING_GROUPSIZE = "paging.banner.group_size";

    /**
     * property <code>paging.banner.onepage</code>.
     */
    public static final String PROPERTY_STRING_PAGING_BANNER_ONEPAGE = "paging.banner.onepage";

    /**
     * property <code>paging.banner.first</code>.
     */
    public static final String PROPERTY_STRING_PAGING_BANNER_FIRST = "paging.banner.first";

    /**
     * property <code>paging.banner.last</code>.
     */
    public static final String PROPERTY_STRING_PAGING_BANNER_LAST = "paging.banner.last";

    /**
     * property <code>paging.banner.full</code>.
     */
    public static final String PROPERTY_STRING_PAGING_BANNER_FULL = "paging.banner.full";

    /**
     * property <code>paging.banner.page.link</code>.
     */
    public static final String PROPERTY_STRING_PAGING_PAGE_LINK = "paging.banner.page.link";

    /**
     * property <code>paging.banner.page.selected</code>.
     */
    public static final String PROPERTY_STRING_PAGING_PAGE_SELECTED = "paging.banner.page.selected";

    /**
     * property <code>paging.banner.page.separator</code>.
     */
    public static final String PROPERTY_STRING_PAGING_PAGE_SPARATOR = "paging.banner.page.separator";

    /**
     * property <code>css.tr.even</code>: holds the name of the css class added to even rows.
     * Defaults to <code>even</code>.
     */
    public static final String PROPERTY_CSS_TR_EVEN = "css.tr.even";

    /**
     * property <code>css.tr.odd</code>: holds the name of the css class added to odd rows.
     * Defaults to <code>odd</code>.
     */
    public static final String PROPERTY_CSS_TR_ODD = "css.tr.odd";

    /**
     * property <code>css.table</code>: holds the name of the css class added to the main table tag. By default no
     * css class is added.
     */
    public static final String PROPERTY_CSS_TABLE = "css.table";
    
    /**
     * property <code>css.th.sortable</code>: holds the name of the css class added to the the header of a sortable
     * column. By default no css class is added.
     */
    public static final String PROPERTY_CSS_TH_SORTABLE = "css.th.sortable";

    /**
     * property <code>css.th.sorted</code>: holds the name of the css class added to the the header of a sorted
     * column. Defaults to <code>sorted</code>.
     */
    public static final String PROPERTY_CSS_TH_SORTED = "css.th.sorted";

    /**
     * property <code>css.th.ascending</code>: holds the name of the css class added to the the header of a column
     * sorted in ascending order. Defaults to <code>order1</code>.
     */
    public static final String PROPERTY_CSS_TH_SORTED_ASCENDING = "css.th.ascending";
    
    /**
     * property <code>css.th.descending</code>: holds the name of the css class added to the the header of a column
     * sorted in descending order. Defaults to <code>order2</code>.
     */
    public static final String PROPERTY_CSS_TH_SORTED_DESCENDING = "css.th.descending";

    /**
     * prefix used for all the properties related to export ("export"). The full property name is <code>export.</code>
     * <em>[export type]</em><code>.</code><em>[property name]</em>
     */
    public static final String PROPERTY_BOOLEAN_EXPORT_PREFIX = "export";

    /**
     * export property <code>label</code>.
     */
    public static final String EXPORTPROPERTY_STRING_LABEL = "label";

    /**
     * export property <code>include_header</code>.
     */
    public static final String EXPORTPROPERTY_BOOLEAN_EXPORTHEADER = "include_header";

    /**
     * export property <code>filename</code>. @todo unused at the moment
     */
    public static final String EXPORTPROPERTY_STRING_FILENAME = "filename";

    /**
     * export property <code>banner</code>. @todo unused at the moment
     */
    public static final String EXPORTPROPERTY_STRING_BANNER = "banner";

    /**
     * The userProperties are local, non-default properties; these settings override the defaults from
     * displaytag.properties and TableTag.properties.
     */
    private static Properties userProperties = new Properties();

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableProperties.class);

    /**
     * Loaded properties.
     */
    private Properties properties;

    /**
     * Initialize a new TableProperties loading the default properties file and the user defined one.
     * @throws TablePropertiesLoadException for errors during loading of properties files
     */
    public TableProperties() throws TablePropertiesLoadException
    {

        Properties defaultProperties = new Properties();
        try
        {
            defaultProperties.load(this.getClass().getResourceAsStream(DEFAULT_FILENAME));
        }
        catch (IOException e)
        {
            throw new TablePropertiesLoadException(getClass(), DEFAULT_FILENAME, e);
        }

        this.properties = new Properties(defaultProperties);

        // Try to load the properties from the local properties file, displaytag.properties.
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(LOCAL_PROPERTIES);
            Enumeration keys = bundle.getKeys();
            while (keys.hasMoreElements())
            {
                String key = (String) keys.nextElement();
                this.properties.setProperty(key, bundle.getString(key));
            }
        }
        catch (MissingResourceException e)
        {
            log.debug("Was not able to load a displaytag.properties; " + e.getMessage());
        }

        // Now copy in the user properties
        Enumeration keys = userProperties.keys();
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            if (key != null)
            {
                this.properties.setProperty(key, (String) userProperties.get(key));
            }
        }
    }

    /**
     * Local, non-default properties; these settings override the defaults from displaytag.properties and
     * TableTag.properties.
     * @return the Properties that was set
     */
    public static Properties getUserProperties()
    {
        return userProperties;
    }

    /**
     * Local, non-default properties; these settings override the defaults from displaytag.properties and
     * TableTag.properties. Please note that the values are copied in, so that multiple calls with non-overlapping
     * properties will be merged, not overwritten.
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
     * Getter for the <code>PROPERTY_STRING_PAGING_INVALIDPAGE</code> property.
     * @return String
     */
    public String getPagingInvalidPage()
    {
        return getProperty(PROPERTY_STRING_PAGING_INVALIDPAGE);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_ITEM_NAME</code> property.
     * @return String
     */
    public String getPagingItemName()
    {
        return getProperty(PROPERTY_STRING_PAGING_ITEM_NAME);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_ITEMS_NAME</code> property.
     * @return String
     */
    public String getPagingItemsName()
    {
        return getProperty(PROPERTY_STRING_PAGING_ITEMS_NAME);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_NOITEMS</code> property.
     * @return String
     */
    public String getPagingFoundNoItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_NOITEMS);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_FOUND_ONEITEM</code> property.
     * @return String
     */
    public String getPagingFoundOneItem()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_ONEITEM);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_FOUND_ALLITEMS</code> property.
     * @return String
     */
    public String getPagingFoundAllItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_ALLITEMS);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_FOUND_SOMEITEMS</code> property.
     * @return String
     */
    public String getPagingFoundSomeItems()
    {
        return getProperty(PROPERTY_STRING_PAGING_FOUND_SOMEITEMS);
    }

    /**
     * Getter for the <code>PROPERTY_INT_PAGING_GROUPSIZE</code> property.
     * @param defaultValue int
     * @return int
     */
    public int getPagingGroupSize(int defaultValue)
    {
        return getIntProperty(PROPERTY_INT_PAGING_GROUPSIZE, defaultValue);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_BANNER_ONEPAGE</code> property.
     * @return String
     */
    public String getPagingBannerOnePage()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_ONEPAGE);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_BANNER_FIRST</code> property.
     * @return String
     */
    public String getPagingBannerFirst()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_FIRST);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_BANNER_LAST</code> property.
     * @return String
     */
    public String getPagingBannerLast()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_LAST);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_BANNER_FULL</code> property.
     * @return String
     */
    public String getPagingBannerFull()
    {
        return getProperty(PROPERTY_STRING_PAGING_BANNER_FULL);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_PAGE_LINK</code> property.
     * @return String
     */
    public String getPagingPageLink()
    {
        return getProperty(PROPERTY_STRING_PAGING_PAGE_LINK);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_PAGE_SELECTED</code> property.
     * @return String
     */
    public String getPagingPageSelected()
    {
        return getProperty(PROPERTY_STRING_PAGING_PAGE_SELECTED);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_PAGING_PAGE_SPARATOR</code> property.
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
            PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + EXPORTPROPERTY_BOOLEAN_EXPORTHEADER);
    }

    /**
     * Returns the label for the given export option.
     * @param exportType instance of MediaTypeEnum
     * @return String label
     */
    public String getExportLabel(MediaTypeEnum exportType)
    {
        return getProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + EXPORTPROPERTY_STRING_LABEL);
    }

    /**
     * Returns the filename for the given export option.
     * @param exportType instance of MediaTypeEnum
     * @return file name @todo unused
     */
    public String getExportFilename(MediaTypeEnum exportType)
    {
        return getProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + EXPORTPROPERTY_STRING_FILENAME);
    }

    /**
     * Returns the banner for the given export option.
     * @param exportType instance of MediaTypeEnum
     * @return String @todo unused
     */
    public String getExportBanner(MediaTypeEnum exportType)
    {
        return getProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + EXPORTPROPERTY_STRING_BANNER);
    }

    /**
     * Getter for the <code>PROPERTY_BOOLEAN_EXPORTDECORATED</code> property.
     * @return boolean <code>true</code> if decorators should be used in exporting
     */
    public boolean getExportDecorated()
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_EXPORTDECORATED);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_EXPORT_FILENAME</code> property.
     * @return String filename for export
     */
    public String getExportFileName()
    {
        return getProperty(PROPERTY_STRING_EXPORT_FILENAME);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_EXPORTBANNER</code> property.
     * @return String
     */
    public String getExportBanner()
    {
        return getProperty(PROPERTY_STRING_EXPORTBANNER);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_EXPORTBANNER_SEPARATOR</code> property.
     * @return String
     */
    public String getExportBannerSeparator()
    {
        return getProperty(PROPERTY_STRING_EXPORTBANNER_SEPARATOR);
    }

    /**
     * Getter for the <code>PROPERTY_BOOLEAN_SHOWHEADER</code> property.
     * @return boolean
     */
    public boolean getShowHeader()
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_SHOWHEADER);
    }

    /**
     * Getter for the <code>PROPERTY_BOOLEAN_SHOWHEADER</code> property.
     * @return String
     */
    public String getEmptyListMessage()
    {
        return getProperty(PROPERTY_STRING_EMPTYLIST_MESSAGE);
    }

    /**
     * Getter for the <code>PROPERTY_STRING_EXPORTAMOUNT</code> property.
     * @return boolean
     */
    public boolean getExportFullList()
    {
        return "list".equals(getProperty(PROPERTY_STRING_EXPORTAMOUNT));
    }

    /**
     * Should paging banner be added before the table?
     * @return boolean
     */
    public boolean getAddPagingBannerTop()
    {
        String placement = getProperty(PROPERTY_STRING_BANNER_PLACEMENT);
        return "top".equals(placement) || "both".equals(placement);
    }

    /**
     * Should paging banner be added after the table?
     * @return boolean
     */
    public boolean getAddPagingBannerBottom()
    {
        String placement = getProperty(PROPERTY_STRING_BANNER_PLACEMENT);
        return "bottom".equals(placement) || "both".equals(placement);
    }

    /**
     * Returns the appropriate css class for a table row.
     * @param rowNumber row number
     * @return the value of <code>PROPERTY_CSS_TR_EVEN</code> if rowNumber is even or <code>PROPERTY_CSS_TR_ODD</code>
     * if rowNumber is odd.
     */
    public String getCssRow(int rowNumber)
    {
        if (rowNumber % 2 == 0)
        {    
            return getProperty(PROPERTY_CSS_TR_ODD);
        }
        else
        {
            return getProperty(PROPERTY_CSS_TR_EVEN);            
        }
    }

    /**
     * Returns the appropriate css class for a sorted column header.
     * @param ascending <code>true</code> if column is sorded in ascending order.
     * @return the value of <code>PROPERTY_CSS_TH_SORTED_ASCENDING</code> if column is sorded in ascending order or
     * <code>PROPERTY_CSS_TH_SORTED_DESCENDING</code> if column is sorded in descending order.
     */
    public String getCssOrder(boolean ascending)
    {
        if (ascending)
        {    
            return getProperty(PROPERTY_CSS_TH_SORTED_ASCENDING);
        }
        else
        {
            return getProperty(PROPERTY_CSS_TH_SORTED_DESCENDING);            
        }
    }
    
    /**
     * Returns the configured css class for a sorted column header.
     * @return the value of <code>PROPERTY_CSS_TH_SORTED</code>
     */
    public String getCssSorted()
    {
        return getProperty(PROPERTY_CSS_TH_SORTED);
    }
    
    /**
     * Returns the configured css class for the main table tag.
     * @return the value of <code>PROPERTY_CSS_TABLE</code>
     */
    public String getCssTable()
    {
        return getProperty(PROPERTY_CSS_TABLE);
    }
    
    /**
     * Returns the configured css class for a sortable column header.
     * @return the value of <code>PROPERTY_CSS_TH_SORTABLE</code>
     */
    public String getCssSortable()
    {
        return getProperty(PROPERTY_CSS_TH_SORTABLE);
    }
    
    /**
     * Reads a String property.
     * @param key property name
     * @return property value or <code>null</code> if property is not found
     */
    private String getProperty(String key)
    {
        return this.properties.getProperty(key);
    }

    /**
     * Sets a property.
     * @param key property name
     * @param value property value
     */
    public void setProperty(String key, String value)
    {
        this.properties.setProperty(key, value);
    }

    /**
     * Reads a boolean property.
     * @param key property name
     * @return boolean <code>true</code> if the property value is "true", <code>false</code> for any other value.
     */
    private boolean getBooleanProperty(String key)
    {
        return Boolean.TRUE.toString().equals(getProperty(key));
    }

    /**
     * Reads an int property.
     * @param key property name
     * @param defaultValue default value returned if property is not found or not a valid int value
     * @return property value
     */
    private int getIntProperty(String key, int defaultValue)
    {
        int intValue = defaultValue;

        try
        {
            intValue = Integer.parseInt(getProperty(key));
        }
        catch (NumberFormatException e)
        {
            // Don't care, use default
            log.warn(
                "Invalid value for \""
                    + key
                    + "\" property: value=\""
                    + getProperty(key)
                    + "\"; using default \""
                    + defaultValue
                    + "\"");
        }

        return intValue;
    }

}