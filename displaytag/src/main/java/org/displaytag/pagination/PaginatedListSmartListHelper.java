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
        int pageCount = this.paginatedList.getFullListSize() / Math.max(1, this.paginatedList.getObjectsPerPage());
        if ((this.paginatedList.getFullListSize() % this.paginatedList.getObjectsPerPage()) > 0)
        {
            pageCount++;
        }
        return pageCount;
    }

    @Override
    public int getFirstIndexForCurrentPage()
    {
        return getFirstIndexForPage(this.paginatedList.getPageNumber());
    }

    @Override
    protected int getFirstIndexForPage(int pageNumber)
    {
        if (pageNumber > this.pageCount)
        {
            pageNumber = this.pageCount;
        }

        return ((pageNumber - 1) * this.paginatedList.getObjectsPerPage());
    }

    @Override
    protected int getLastIndexForCurrentPage()
    {
        return getLastIndexForPage(this.paginatedList.getPageNumber());
    }

    @Override
    protected int getLastIndexForPage(int pageNumber)
    {
        if (pageNumber > this.pageCount)
        {
            pageNumber = this.pageCount;
        }

        int result = getFirstIndexForPage(pageNumber) + this.paginatedList.getObjectsPerPage() - 1;
        if (result >= this.paginatedList.getFullListSize())
        {
            result = this.paginatedList.getFullListSize() - 1;
        }
        return result;
    }

    @Override
    public List<Object> getListForCurrentPage()
    {
        return this.paginatedList.getList();
    }

    @Override
    protected List<Object> getListForPage(int pageNumber)
    {
        if ((pageNumber) == this.paginatedList.getPageNumber())
        {
            return getListForCurrentPage();
        }
        else
        {
            return null;
        }
    }

    @Override
    public String getPageNavigationBar(Href baseHref, String pageParameter)
    {

        int groupSize = this.properties.getPagingGroupSize();
        int startPage;
        int endPage;

        Pagination pagination = new Pagination(baseHref, pageParameter, this.properties);
        pagination.setCurrent(new Integer(this.paginatedList.getPageNumber()));

        // if no items are found still add pagination?
        if (this.pageCount == 0)
        {
            pagination.addPage(1, true);
        }

        // center the selected page, but only if there are {groupSize} pages
        // available after it, and check that the
        // result is not < 1
        startPage = Math.max(Math.min(this.paginatedList.getPageNumber() - groupSize / 2, this.pageCount - (groupSize - 1)), 1);
        endPage = Math.min(startPage + groupSize - 1, this.pageCount);

        if (this.paginatedList.getPageNumber() != 1)
        {
            pagination.setFirst(new Integer(1));
            pagination.setPrevious(new Integer(this.paginatedList.getPageNumber() - 1));
        }

        for (int j = startPage; j <= endPage; j++)
        {
            pagination.addPage(j, (j == this.paginatedList.getPageNumber()));
        }

        if (this.paginatedList.getPageNumber() != this.pageCount)
        {
            pagination.setNext(new Integer(this.paginatedList.getPageNumber() + 1));
            pagination.setLast(new Integer(this.pageCount));
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

    @Override
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
        else if (this.pageCount == 1)
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
                new Integer(this.pageCount)};
            message = this.properties.getPagingFoundSomeItems();
        }

        return new MessageFormat(message, this.properties.getLocale()).format(objs);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("paginatedList", this.paginatedList) //$NON-NLS-1$
            .append("properties", this.properties) //$NON-NLS-1$
            .toString();
    }
}