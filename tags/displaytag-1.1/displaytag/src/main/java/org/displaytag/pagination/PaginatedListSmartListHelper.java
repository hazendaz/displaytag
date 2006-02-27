/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Href;


/**
 * An implementation of SmartListHelper used for externally sorted and paginated lists. It duplicates nearly all of its
 * superclass, so these two classes should be refactored
 * @author JBN
 */
public class PaginatedListSmartListHelper extends SmartListHelper
{

    private PaginatedList paginatedList;

    private TableProperties properties;

    private int pageCount;

    public PaginatedListSmartListHelper(PaginatedList paginatedList, TableProperties tableProperties)
    {
        super();
        this.paginatedList = paginatedList;
        this.properties = tableProperties;
        this.pageCount = computePageCount();
    }

    private int computePageCount()
    {
        int pageCount = paginatedList.getFullListSize() / Math.max(1, paginatedList.getObjectsPerPage());
        if ((paginatedList.getFullListSize() % paginatedList.getObjectsPerPage()) > 0)
        {
            pageCount++;
        }
        return pageCount;
    }

    public int getFirstIndexForCurrentPage()
    {
        return getFirstIndexForPage(paginatedList.getPageNumber());
    }

    protected int getFirstIndexForPage(int pageNumber)
    {
        if (pageNumber > pageCount)
        {
            pageNumber = pageCount;
        }

        return ((pageNumber - 1) * paginatedList.getObjectsPerPage());
    }

    protected int getLastIndexForCurrentPage()
    {
        return getLastIndexForPage(paginatedList.getPageNumber());
    }

    protected int getLastIndexForPage(int pageNumber)
    {
        if (pageNumber > pageCount)
        {
            pageNumber = pageCount;
        }

        int result = getFirstIndexForPage(pageNumber) + paginatedList.getObjectsPerPage() - 1;
        if (result >= paginatedList.getFullListSize())
        {
            result = paginatedList.getFullListSize() - 1;
        }
        return result;
    }

    public List getListForCurrentPage()
    {
        return paginatedList.getList();
    }

    protected List getListForPage(int pageNumber)
    {
        if ((pageNumber) == paginatedList.getPageNumber())
        {
            return getListForCurrentPage();
        }
        else
        {
            return null;
        }
    }

    public String getPageNavigationBar(Href baseHref, String pageParameter)
    {

        int groupSize = this.properties.getPagingGroupSize();
        int startPage;
        int endPage;

        Pagination pagination = new Pagination(baseHref, pageParameter);
        pagination.setCurrent(new Integer(paginatedList.getPageNumber()));

        // if no items are found still add pagination?
        if (pageCount == 0)
        {
            pagination.addPage(1, true);
        }

        // center the selected page, but only if there are {groupSize} pages
        // available after it, and check that the
        // result is not < 1
        startPage = Math.max(Math.min(paginatedList.getPageNumber() - groupSize / 2, pageCount - (groupSize - 1)), 1);
        endPage = Math.min(startPage + groupSize - 1, pageCount);

        if (paginatedList.getPageNumber() != 1)
        {
            pagination.setFirst(new Integer(1));
            pagination.setPrevious(new Integer(paginatedList.getPageNumber() - 1));
        }

        for (int j = startPage; j <= endPage; j++)
        {
            pagination.addPage(j, (j == paginatedList.getPageNumber()));
        }

        if (paginatedList.getPageNumber() != pageCount)
        {
            pagination.setNext(new Integer(paginatedList.getPageNumber() + 1));
            pagination.setLast(new Integer(pageCount));
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

    public String getSearchResultsSummary()
    {

        Object[] objs;
        String message;

        if (this.paginatedList.getFullListSize() == 0)
        {
            objs = new Object[]{this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundNoItems();
        }
        else if (this.paginatedList.getFullListSize() == 1)
        {
            objs = new Object[]{this.properties.getPagingItemName()};
            message = this.properties.getPagingFoundOneItem();
        }
        else if (pageCount == 1)
        {
            objs = new Object[]{
                new Integer(this.paginatedList.getFullListSize()),
                this.properties.getPagingItemsName(),
                this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundAllItems();
        }
        else
        {
            objs = new Object[]{
                new Integer(this.paginatedList.getFullListSize()),
                this.properties.getPagingItemsName(),
                new Integer(getFirstIndexForCurrentPage() + 1),
                new Integer(getLastIndexForCurrentPage() + 1),
                new Integer(this.paginatedList.getPageNumber()),
                new Integer(pageCount)};
            message = this.properties.getPagingFoundSomeItems();
        }

        return MessageFormat.format(message, objs);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("paginatedList", this.paginatedList) //$NON-NLS-1$
            .append("properties", this.properties) //$NON-NLS-1$
            .toString();
    }
}