/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.model.Row;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Href;

/**
 * An implementation of SmartListHelper used for externally sorted and paginated lists. It duplicates nearly all of its
 * superclass, so these two classes should be refactored.
 */
public class PaginatedListSmartListHelper extends SmartListHelper {

    /** The paginated list. */
    private final PaginatedList<Row> paginatedList;

    /** The properties. */
    private final TableProperties properties;

    /** The page count. */
    private final int pageCount;

    /**
     * Instantiates a new paginated list smart list helper.
     *
     * @param paginatedList
     *            the paginated list
     * @param tableProperties
     *            the table properties
     */
    public PaginatedListSmartListHelper(final PaginatedList<Row> paginatedList, final TableProperties tableProperties) {
        this.paginatedList = paginatedList;
        this.properties = tableProperties;
        this.pageCount = this.computePageCount();
    }

    /**
     * Compute page count.
     *
     * @return the int
     */
    private int computePageCount() {
        int pageCount = this.paginatedList.getFullListSize() / Math.max(1, this.paginatedList.getObjectsPerPage());
        if (this.paginatedList.getFullListSize() % this.paginatedList.getObjectsPerPage() > 0) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * Gets the first index for current page.
     *
     * @return the first index for current page
     */
    @Override
    public int getFirstIndexForCurrentPage() {
        return this.getFirstIndexForPage(this.paginatedList.getPageNumber());
    }

    /**
     * Gets the first index for page.
     *
     * @param pageNumber
     *            the page number
     *
     * @return the first index for page
     */
    @Override
    protected int getFirstIndexForPage(int pageNumber) {
        if (pageNumber > this.pageCount) {
            pageNumber = this.pageCount;
        }

        return (pageNumber - 1) * this.paginatedList.getObjectsPerPage();
    }

    /**
     * Gets the last index for current page.
     *
     * @return the last index for current page
     */
    @Override
    protected int getLastIndexForCurrentPage() {
        return this.getLastIndexForPage(this.paginatedList.getPageNumber());
    }

    /**
     * Gets the last index for page.
     *
     * @param pageNumber
     *            the page number
     *
     * @return the last index for page
     */
    @Override
    protected int getLastIndexForPage(int pageNumber) {
        if (pageNumber > this.pageCount) {
            pageNumber = this.pageCount;
        }

        int result = this.getFirstIndexForPage(pageNumber) + this.paginatedList.getObjectsPerPage() - 1;
        if (result >= this.paginatedList.getFullListSize()) {
            result = this.paginatedList.getFullListSize() - 1;
        }
        return result;
    }

    /**
     * Gets the list for current page.
     *
     * @return the list for current page
     */
    @Override
    public List<Row> getListForCurrentPage() {
        return this.paginatedList.getList();
    }

    /**
     * Gets the list for page.
     *
     * @param pageNumber
     *            the page number
     *
     * @return the list for page
     */
    @Override
    protected List<Row> getListForPage(final int pageNumber) {
        if (pageNumber == this.paginatedList.getPageNumber()) {
            return this.getListForCurrentPage();
        }
        return null;
    }

    /**
     * Gets the page navigation bar.
     *
     * @param baseHref
     *            the base href
     * @param pageParameter
     *            the page parameter
     *
     * @return the page navigation bar
     */
    @Override
    public String getPageNavigationBar(final Href baseHref, final String pageParameter) {

        final int groupSize = this.properties.getPagingGroupSize();
        int startPage;
        int endPage;

        final Pagination pagination = new Pagination(baseHref, pageParameter, this.properties);
        pagination.setCurrent(Integer.valueOf(this.paginatedList.getPageNumber()));

        // if no items are found still add pagination?
        if (this.pageCount == 0) {
            pagination.addPage(1, true);
        }

        // center the selected page, but only if there are {groupSize} pages
        // available after it, and check that the
        // result is not < 1
        startPage = Math
                .max(Math.min(this.paginatedList.getPageNumber() - groupSize / 2, this.pageCount - (groupSize - 1)), 1);
        endPage = Math.min(startPage + groupSize - 1, this.pageCount);

        if (this.paginatedList.getPageNumber() != 1) {
            pagination.setFirst(Integer.valueOf(1));
            pagination.setPrevious(Integer.valueOf(this.paginatedList.getPageNumber() - 1));
        }

        for (int j = startPage; j <= endPage; j++) {
            pagination.addPage(j, j == this.paginatedList.getPageNumber());
        }

        if (this.paginatedList.getPageNumber() != this.pageCount) {
            pagination.setNext(Integer.valueOf(this.paginatedList.getPageNumber() + 1));
            pagination.setLast(Integer.valueOf(this.pageCount));
        }

        // format for previous/next banner
        String bannerFormat;

        if (pagination.isOnePage()) {
            bannerFormat = this.properties.getPagingBannerOnePage();
        } else if (pagination.isFirst()) {
            bannerFormat = this.properties.getPagingBannerFirst();
        } else if (pagination.isLast()) {
            bannerFormat = this.properties.getPagingBannerLast();
        } else {
            bannerFormat = this.properties.getPagingBannerFull();
        }

        return pagination.getFormattedBanner(this.properties.getPagingPageLink(),
                this.properties.getPagingPageSelected(), this.properties.getPagingPageSeparator(), bannerFormat);
    }

    /**
     * Gets the search results summary.
     *
     * @return the search results summary
     */
    @Override
    public String getSearchResultsSummary() {

        Object[] objs;
        String message;

        if (this.paginatedList.getFullListSize() == 0) {
            objs = new Object[] { this.properties.getPagingItemsName() };
            message = this.properties.getPagingFoundNoItems();
        } else if (this.paginatedList.getFullListSize() == 1) {
            objs = new Object[] { this.properties.getPagingItemName() };
            message = this.properties.getPagingFoundOneItem();
        } else if (this.pageCount == 1) {
            objs = new Object[] { Integer.valueOf(this.paginatedList.getFullListSize()),
                    this.properties.getPagingItemsName(), this.properties.getPagingItemsName() };
            message = this.properties.getPagingFoundAllItems();
        } else {
            objs = new Object[] { Integer.valueOf(this.paginatedList.getFullListSize()),
                    this.properties.getPagingItemsName(), Integer.valueOf(this.getFirstIndexForCurrentPage() + 1),
                    Integer.valueOf(this.getLastIndexForCurrentPage() + 1),
                    Integer.valueOf(this.paginatedList.getPageNumber()), Integer.valueOf(this.pageCount) };
            message = this.properties.getPagingFoundSomeItems();
        }

        return new MessageFormat(message, this.properties.getLocale()).format(objs);
    }

    /**
     * To string.
     *
     * @return the string
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
                .append("paginatedList", this.paginatedList) //$NON-NLS-1$
                .append("properties", this.properties) //$NON-NLS-1$
                .toString();
    }
}
