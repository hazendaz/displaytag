/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.Messages;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Href;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private static Logger log = LoggerFactory.getLogger(SmartListHelper.class);

    /**
     * full list.
     */
    private List<Object> fullList;

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

    /** the list we hold is only part of the full dataset. */
    private boolean partialList;

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
     *
     * @param list List
     * @param fullSize size of the full list
     * @param itemsInPage number of items in a page (int &gt; 0)
     * @param tableProperties TableProperties
     * @param partialList the partial list
     */
    public SmartListHelper(
        List<Object> list,
        int fullSize,
        int itemsInPage,
        TableProperties tableProperties,
        boolean partialList)
    {
        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("SmartListHelper.debug.instantiated", //$NON-NLS-1$
                new Object[]{Integer.valueOf(list.size()), Integer.valueOf(itemsInPage), Integer.valueOf(fullSize)}));
        }

        this.properties = tableProperties;
        this.pageSize = itemsInPage;
        this.fullList = list;
        this.fullListSize = fullSize;
        this.pageCount = computedPageCount();
        this.currentPage = 1;
        this.partialList = partialList;
    }

    /**
     * Constructor that can be used by subclasses. Subclasses that use this constructor must also override all the
     * public methods, since this constructor does nothing.
     */
    protected SmartListHelper()
    {
    }

    /**
     * Returns the computed number of pages it would take to show all the elements in the list given the pageSize we are
     * working with.
     * @return int computed number of pages
     */
    protected int computedPageCount()
    {
        int size = this.fullListSize;
        int div = size / this.pageSize;
        int result = (size % this.pageSize == 0) ? div : div + 1;

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
        int retval = (pageNumber - 1) * this.pageSize;
        return retval >= 0 ? retval : 0;
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
    public List<Object> getListForCurrentPage()
    {

        return getListForPage(this.currentPage);
    }

    /**
     * Returns a subsection of the list that contains just the elements that are supposed to be shown on the given page.
     * @param pageNumber page number
     * @return List subsection of the list that contains just the elements that are supposed to be shown on the given
     * page
     */
    protected List<Object> getListForPage(int pageNumber)
    {
        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("SmartListHelper.debug.sublist", //$NON-NLS-1$
                new Object[]{Integer.valueOf(pageNumber)}));
        }

        int firstIndex = getFirstIndexForPage(pageNumber);
        int lastIndex = getLastIndexForPage(pageNumber);

        if (this.partialList)
        {
            firstIndex = 0;
            // use the min of pageSize or list size on the off chance they gave us more data than pageSize allows
            lastIndex = Math.min(this.pageSize - 1, this.fullList.size() - 1);
        }

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
                new Object[]{Integer.valueOf(pageNumber), Integer.valueOf(this.pageCount)}));
        }

        if (pageNumber < 1)
        {
            // invalid page: better don't throw an exception, since this could easily happen
            // (list changed, user bookmarked the page)
            this.currentPage = 1;
        }
        else if (pageNumber != 1 && pageNumber > this.pageCount)
        {
            // invalid page: set to last page
            this.currentPage = this.pageCount;
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
                Integer.valueOf(this.fullListSize),
                this.properties.getPagingItemsName(),
                this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundAllItems();
        }
        else
        {
            objs = new Object[]{
                Integer.valueOf(this.fullListSize),
                this.properties.getPagingItemsName(),
                Integer.valueOf(getFirstIndexForCurrentPage() + 1),
                Integer.valueOf(getLastIndexForCurrentPage() + 1),
                Integer.valueOf(this.currentPage),
                Integer.valueOf(this.pageCount)};
            message = this.properties.getPagingFoundSomeItems();
        }

        return new MessageFormat(message, this.properties.getLocale()).format(objs);
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

        int groupSize = this.properties.getPagingGroupSize();
        int startPage;
        int endPage;

        Pagination pagination = new Pagination(baseHref, pageParameter, this.properties);
        pagination.setCurrent(Integer.valueOf(this.currentPage));

        // if no items are found still add pagination?
        if (this.pageCount == 0)
        {
            pagination.addPage(1, true);
        }

        // center the selected page, but only if there are {groupSize} pages available after it, and check that the
        // result is not < 1
        startPage = Math.max(Math.min(this.currentPage - groupSize / 2, this.pageCount - (groupSize - 1)), 1);
        endPage = Math.min(startPage + groupSize - 1, this.pageCount);

        if (log.isDebugEnabled())
        {
            log.debug("Displaying pages from " + startPage + " to " + endPage);
        }

        if (this.currentPage != 1)
        {
            pagination.setFirst(Integer.valueOf(1));
            pagination.setPrevious(Integer.valueOf(this.currentPage - 1));
        }

        for (int j = startPage; j <= endPage; j++)
        {
            if (log.isDebugEnabled())
            {
                log.debug("adding page " + j); //$NON-NLS-1$
            }
            pagination.addPage(j, (j == this.currentPage));
        }

        if (this.currentPage != this.pageCount)
        {
            pagination.setNext(Integer.valueOf(this.currentPage + 1));
            pagination.setLast(Integer.valueOf(this.pageCount));
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

        return pagination.getFormattedBanner(
            this.properties.getPagingPageLink(),
            this.properties.getPagingPageSelected(),
            this.properties.getPagingPageSeparator(),
            bannerFormat);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("fullList", this.fullList) //$NON-NLS-1$
            .append("fullListSize", this.fullListSize) //$NON-NLS-1$
            .append("pageSize", this.pageSize) //$NON-NLS-1$
            .append("pageCount", this.pageCount) //$NON-NLS-1$
            .append("properties", this.properties) //$NON-NLS-1$
            .append("currentPage", this.currentPage) //$NON-NLS-1$
            .append("partialList", this.partialList) //$NON-NLS-1$
            .toString();
    }
}