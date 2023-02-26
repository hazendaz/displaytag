/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Href;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for generation of paging banners.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class Pagination {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(Pagination.class);

    /**
     * Base href for urls.
     */
    private final Href href;

    /**
     * page parameter name.
     */
    private final String pageParam;

    /**
     * first page.
     */
    private Integer firstPage;

    /**
     * last page.
     */
    private Integer lastPage;

    /**
     * previous page.
     */
    private Integer previousPage;

    /**
     * next page.
     */
    private Integer nextPage;

    /**
     * current page.
     */
    private Integer currentPage;

    /**
     * List containg NumberedPage objects.
     *
     * @see org.displaytag.pagination.NumberedPage
     */
    private final List<NumberedPage> pages = new ArrayList<>();

    /**
     * Table properties, needed fot locale.
     */
    private final TableProperties properties;

    /**
     * Constructor for Pagination.
     *
     * @param baseHref
     *            Href used for links
     * @param pageParameter
     *            name for the page parameter
     * @param properties
     *            the properties
     */
    public Pagination(final Href baseHref, final String pageParameter, final TableProperties properties) {
        this.href = baseHref;
        this.pageParam = pageParameter;
        this.properties = properties;
    }

    /**
     * Adds a page.
     *
     * @param number
     *            int page number
     * @param isSelected
     *            is the page selected?
     */
    public void addPage(final int number, final boolean isSelected) {
        if (Pagination.log.isDebugEnabled()) {
            Pagination.log.debug("adding page {}", number);
        }
        this.pages.add(new NumberedPage(number, isSelected));
    }

    /**
     * first page selected?.
     *
     * @return boolean
     */
    public boolean isFirst() {
        return this.firstPage == null;
    }

    /**
     * last page selected?.
     *
     * @return boolean
     */
    public boolean isLast() {
        return this.lastPage == null;
    }

    /**
     * only one page?.
     *
     * @return boolean
     */
    public boolean isOnePage() {
        return this.pages == null || this.pages.size() <= 1;
    }

    /**
     * Gets the number of the first page.
     *
     * @return Integer number of the first page
     */
    public Integer getFirst() {
        return this.firstPage;
    }

    /**
     * Sets the number of the first page.
     *
     * @param first
     *            Integer number of the first page
     */
    public void setFirst(final Integer first) {
        this.firstPage = first;
    }

    /**
     * Gets the number of the last page.
     *
     * @return Integer number of the last page
     */
    public Integer getLast() {
        return this.lastPage;
    }

    /**
     * Sets the number of the last page.
     *
     * @param last
     *            Integer number of the last page
     */
    public void setLast(final Integer last) {
        this.lastPage = last;
    }

    /**
     * Gets the number of the previous page.
     *
     * @return Integer number of the previous page
     */
    public Integer getPrevious() {
        return this.previousPage;
    }

    /**
     * Sets the number of the previous page.
     *
     * @param previous
     *            Integer number of the previous page
     */
    public void setPrevious(final Integer previous) {
        this.previousPage = previous;
    }

    /**
     * Gets the number of the next page.
     *
     * @return Integer number of the next page
     */
    public Integer getNext() {
        return this.nextPage;
    }

    /**
     * Sets the number of the next page.
     *
     * @param next
     *            Integer number of the next page
     */
    public void setNext(final Integer next) {
        this.nextPage = next;
    }

    /**
     * Sets the number of the current page.
     *
     * @param current
     *            number of the current page
     */
    public void setCurrent(final Integer current) {
        this.currentPage = current;
    }

    /**
     * Returns the appropriate banner for the pagination.
     *
     * @param numberedPageFormat
     *            String to be used for a not selected page
     * @param numberedPageSelectedFormat
     *            String to be used for a selected page
     * @param numberedPageSeparator
     *            separator beetween pages
     * @param fullBanner
     *            String basic banner
     *
     * @return String formatted banner whith pages
     */
    public String getFormattedBanner(final String numberedPageFormat, final String numberedPageSelectedFormat,
            final String numberedPageSeparator, final String fullBanner) {
        final StringBuilder buffer = new StringBuilder(100);

        // numbered page list
        final Iterator<NumberedPage> pageIterator = this.pages.iterator();

        while (pageIterator.hasNext()) {

            // get NumberedPage from iterator
            final NumberedPage page = pageIterator.next();

            final Integer pageNumber = Integer.valueOf(page.getNumber());

            final String urlString = ((Href) this.href.clone()).addParameter(this.pageParam, pageNumber).toString();

            // needed for MessageFormat : page number/url
            final Object[] pageObjects = { pageNumber, urlString };

            // selected page need a different formatter
            if (page.getSelected()) {
                buffer.append(
                        new MessageFormat(numberedPageSelectedFormat, this.properties.getLocale()).format(pageObjects));
            } else {
                buffer.append(new MessageFormat(numberedPageFormat, this.properties.getLocale()).format(pageObjects));
            }

            // next? add page separator
            if (pageIterator.hasNext()) {
                buffer.append(numberedPageSeparator);
            }
        }

        // String for numbered pages
        final String numberedPageString = buffer.toString();
        // Object array
        // {0} full String for numbered pages
        // {1} first page url
        // {2} previous page url
        // {3} next page url
        // {4} last page url
        // {5} current page
        // {6} total pages
        final Object[] pageObjects = { numberedPageString,
                ((Href) this.href.clone()).addParameter(this.pageParam, this.getFirst()),
                ((Href) this.href.clone()).addParameter(this.pageParam, this.getPrevious()),
                ((Href) this.href.clone()).addParameter(this.pageParam, this.getNext()),
                ((Href) this.href.clone()).addParameter(this.pageParam, this.getLast()), this.currentPage,
                this.isLast() ? this.currentPage : this.lastPage }; // this.lastPage is null if the last page is
                                                                    // displayed

        // return the full banner
        return new MessageFormat(fullBanner, this.properties.getLocale()).format(pageObjects);
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
                .append("firstPage", this.firstPage) //$NON-NLS-1$
                .append("lastPage", this.lastPage) //$NON-NLS-1$
                .append("currentPage", this.currentPage) //$NON-NLS-1$
                .append("nextPage", this.nextPage) //$NON-NLS-1$
                .append("previousPage", this.previousPage) //$NON-NLS-1$
                .append("pages", this.pages) //$NON-NLS-1$
                .append("href", this.href) //$NON-NLS-1$
                .append("pageParam", this.pageParam) //$NON-NLS-1$
                .toString();
    }
}
