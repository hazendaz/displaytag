package org.displaytag.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TablePropertiesLoadException;
import org.displaytag.export.ExportTypeEnum;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
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
     * Field mUserFilename
     */
    private static String mUserFilename = null;

    /**
     *
     * @return the String value of mPropertiesFilename.
     */
    public static String getPropertiesFilename()
    {
        return mUserFilename;
    }

    /**
     *
     * @param pPropertiesFilename - the new value for mPropertiesFilename
     */
    public static void setPropertiesFilename(String pPropertiesFilename)
    {
        mUserFilename = pPropertiesFilename;
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
     * @param exportType instance of ExportTypeEnum
     * @return boolean true if export is enabled
     */
    public boolean getAddExport(ExportTypeEnum exportType)
    {
        return getBooleanProperty(PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType.getName());
    }

    /**
     * Should headers be included in given export type?
     * @param exportType instance of ExportTypeEnum
     * @return boolean true if export should include headers
     */
    public boolean getExportHeader(ExportTypeEnum exportType)
    {
        return getBooleanProperty(
            PROPERTY_BOOLEAN_EXPORT_PREFIX + "." + exportType + "." + PROPERTY_BOOLEAN_EXPORT_HEADER);
    }

    /**
     * Returns the label for the given export option
     * @param exportType instance of ExportTypeEnum
     * @return String label
     */
    public String getExportLabel(ExportTypeEnum exportType)
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

        String lUserPropertiesFileName = getPropertiesFilename();

        if (lUserPropertiesFileName != null)
        {

            try
            {
                FileInputStream lFileInput = new FileInputStream(lUserPropertiesFileName);
                properties.load(lFileInput);
                lFileInput.close();
            }
            catch (FileNotFoundException ex)
            {
                throw new TablePropertiesLoadException(getClass(), lUserPropertiesFileName, ex);
            }

            catch (IOException ex)
            {
                throw new TablePropertiesLoadException(getClass(), lUserPropertiesFileName, ex);
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