package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Href;


/**
 * <p>
 * Utility class that chops up a List of objects into small bite size pieces that are more suitable for display.
 * </p>
 * <p>
 * This class is a stripped down version of the WebListHelper from Tim Dawson (tdawson@is.com)
 * </p>
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class SmartListHelper
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(SmartListHelper.class);

    /**
     * full list.
     */
    private List fullList;

    /**
     * sixe of the full list.
     */
    private int fullListSize;

    /**
     * number of items in a page.
     */
    private int pageSize;

    /**
     * number of pages.
     */
    private int pageCount;

    /**
     * index of current page.
     */
    private int currentPage;

    /**
     * TableProperties.
     */
    private TableProperties properties;

    /**
     * Creates a SmarListHelper instance that will help you chop up a list into bite size pieces that are suitable for
     * display.
     * @param list List
     * @param fullSize size of the full list
     * @param itemsInPage number of items in a page
     * @param tableProperties TableProperties
     */
    public SmartListHelper(List list, int fullSize, int itemsInPage, TableProperties tableProperties)
    {
        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("SmartListHelper.debug.instantiated", //$NON-NLS-1$
                new Object[]{new Integer(list.size()), new Integer(itemsInPage), new Integer(fullSize)}));
        }

        this.properties = tableProperties;
        this.pageSize = itemsInPage;
        this.fullList = list;
        this.fullListSize = fullSize;
        this.pageCount = computedPageCount();
        this.currentPage = 1;
    }

    /**
     * Returns the computed number of pages it would take to show all the elements in the list given the pageSize we are
     * working with.
     * @return int computed number of pages
     */
    protected int computedPageCount()
    {

        int result = 0;

        if ((this.fullList != null) && (this.pageSize > 0))
        {
            int size = this.fullListSize;
            int div = size / this.pageSize;
            int mod = size % this.pageSize;
            result = (mod == 0) ? div : div + 1;
        }

        return result;

    }

    /**
     * Returns the index into the master list of the first object that should appear on the current page that the user
     * is viewing.
     * @return int index of the first object that should appear on the current page
     */
    public int getFirstIndexForCurrentPage()
    {
        return getFirstIndexForPage(this.currentPage);
    }

    /**
     * Returns the index into the master list of the last object that should appear on the current page that the user is
     * viewing.
     * @return int
     */
    protected int getLastIndexForCurrentPage()
    {

        return getLastIndexForPage(this.currentPage);
    }

    /**
     * Returns the index into the master list of the first object that should appear on the given page.
     * @param pageNumber page number
     * @return int index of the first object that should appear on the given page
     */
    protected int getFirstIndexForPage(int pageNumber)
    {
        return (pageNumber - 1) * this.pageSize;
    }

    /**
     * Returns the index into the master list of the last object that should appear on the given page.
     * @param pageNumber page number
     * @return int index of the last object that should appear on the given page
     */
    protected int getLastIndexForPage(int pageNumber)
    {

        int firstIndex = getFirstIndexForPage(pageNumber);
        int pageIndex = this.pageSize - 1;
        int lastIndex = this.fullListSize - 1;

        return Math.min(firstIndex + pageIndex, lastIndex);
    }

    /**
     * Returns a subsection of the list that contains just the elements that are supposed to be shown on the current
     * page the user is viewing.
     * @return List subsection of the list that contains the elements that are supposed to be shown on the current page
     */
    public List getListForCurrentPage()
    {

        return getListForPage(this.currentPage);
    }

    /**
     * Returns a subsection of the list that contains just the elements that are supposed to be shown on the given page.
     * @param pageNumber page number
     * @return List subsection of the list that contains just the elements that are supposed to be shown on the given
     * page
     */
    protected List getListForPage(int pageNumber)
    {
        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("SmartListHelper.debug.sublist", //$NON-NLS-1$
                new Object[]{new Integer(pageNumber)}));
        }

        int firstIndex = getFirstIndexForPage(pageNumber);
        int lastIndex = getLastIndexForPage(pageNumber);
        return this.fullList.subList(firstIndex, lastIndex + 1);
    }

    /**
     * Set's the page number that the user is viewing.
     * @param pageNumber page number
     */
    public void setCurrentPage(int pageNumber)
    {
        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("SmartListHelper.debug.currentpage", //$NON-NLS-1$
                new Object[]{new Integer(pageNumber), new Integer(this.pageCount)}));
        }

        if (pageNumber < 1 || ((pageNumber != 1) && (pageNumber > this.pageCount)))
        {
            // invalid page: better don't throw an exception, since this could easily happen
            // (list changed, user bookmarked the page)
            this.currentPage = 1;
        }
        else
        {
            this.currentPage = pageNumber;
        }
    }

    /**
     * Return the little summary message that lets the user know how many objects are in the list they are viewing, and
     * where in the list they are currently positioned. The message looks like: nnn [item(s)] found, displaying nnn to
     * nnn. [item(s)] is replaced by either itemName or itemNames depending on if it should be signular or plural.
     * @return String
     */
    public String getSearchResultsSummary()
    {

        Object[] objs;
        String message;

        if (this.fullListSize == 0)
        {
            objs = new Object[]{this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundNoItems();

        }
        else if (this.fullListSize == 1)
        {
            objs = new Object[]{this.properties.getPagingItemName()};
            message = this.properties.getPagingFoundOneItem();
        }
        else if (computedPageCount() == 1)
        {
            objs = new Object[]{
                new Integer(this.fullListSize),
                this.properties.getPagingItemsName(),
                this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundAllItems();
        }
        else
        {
            objs = new Object[]{
                new Integer(this.fullListSize),
                this.properties.getPagingItemsName(),
                new Integer(getFirstIndexForCurrentPage() + 1),
                new Integer(getLastIndexForCurrentPage() + 1)};
            message = this.properties.getPagingFoundSomeItems();
        }

        return MessageFormat.format(message, objs);
    }

    /**
     * Returns a string containing the nagivation bar that allows the user to move between pages within the list. The
     * urlFormatString should be a URL that looks like the following: somepage.page?page={0}
     * @param baseHref Href used for links
     * @param pageParameter name for the page parameter
     * @return String
     */
    public String getPageNavigationBar(Href baseHref, String pageParameter)
    {
        int maxPages = 8;

        maxPages = this.properties.getPagingGroupSize(maxPages);

        int currentIndex = this.currentPage;
        int count = this.pageCount;
        int startPage;
        int endPage;

        Pagination pagination = new Pagination(baseHref, pageParameter);

        // if no items are found still add pagination?
        if (count == 0)
        {
            pagination.addPage(1, true);
        }

        if (currentIndex < maxPages)
        {
            startPage = 1;
            endPage = maxPages;
            if (count < endPage)
            {
                endPage = count;
            }
        }
        else
        {
            startPage = currentIndex;
            while (startPage + maxPages > (count + 1))
            {
                startPage--;
            }

            endPage = startPage + (maxPages - 1);
        }

        if (currentIndex != 1)
        {
            pagination.setFirst(new Integer(1));
            pagination.setPrevious(new Integer(currentIndex - 1));
        }

        for (int j = startPage; j <= endPage; j++)
        {
            if (log.isDebugEnabled())
            {
                log.debug("adding page " + j); //$NON-NLS-1$
            }
            pagination.addPage(j, (j == currentIndex));
        }

        if (currentIndex != count)
        {
            pagination.setNext(new Integer(currentIndex + 1));
            pagination.setLast(new Integer(count));
        }

        // format for previous/next banner
        String bannerFormat;

        if (pagination.isOnePage())
        {
            bannerFormat = this.properties.getPagingBannerOnePage();
        }
        else if (pagination.isFirst())
        {
            bannerFormat = this.properties.getPagingBannerFirst();
        }
        else if (pagination.isLast())
        {
            bannerFormat = this.properties.getPagingBannerLast();
        }
        else
        {
            bannerFormat = this.properties.getPagingBannerFull();
        }

        return pagination.getFormattedBanner(this.properties.getPagingPageLink(), this.properties
            .getPagingPageSelected(), this.properties.getPagingPageSeparator(), bannerFormat);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) //
            .append("fullList", this.fullList) //$NON-NLS-1$
            .append("fullListSize", this.fullListSize) //$NON-NLS-1$
            .append("pageSize", this.pageSize) //$NON-NLS-1$
            .append("pageCount", this.pageCount) //$NON-NLS-1$
            .append("properties", this.properties) //$NON-NLS-1$
            .append("currentPage", this.currentPage) //$NON-NLS-1$
            .toString();
    }
}