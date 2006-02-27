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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.util.Href;
import org.displaytag.util.ShortToStringStyle;


/**
 * Helper class for generation of paging banners.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Pagination
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(Pagination.class);

    /**
     * Base href for urls.
     */
    private Href href;

    /**
     * page parameter name.
     */
    private String pageParam;

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
     * @see org.displaytag.pagination.NumberedPage
     */
    private List pages = new ArrayList();

    /**
     * Constructor for Pagination.
     * @param baseHref Href used for links
     * @param pageParameter name for the page parameter
     */
    public Pagination(Href baseHref, String pageParameter)
    {
        this.href = baseHref;
        this.pageParam = pageParameter;
    }

    /**
     * Adds a page.
     * @param number int page number
     * @param isSelected is the page selected?
     */
    public void addPage(int number, boolean isSelected)
    {
        if (log.isDebugEnabled())
        {
            log.debug("adding page " + number);
        }
        this.pages.add(new NumberedPage(number, isSelected));
    }

    /**
     * first page selected?
     * @return boolean
     */
    public boolean isFirst()
    {
        return this.firstPage == null;
    }

    /**
     * last page selected?
     * @return boolean
     */
    public boolean isLast()
    {
        return this.lastPage == null;
    }

    /**
     * only one page?
     * @return boolean
     */
    public boolean isOnePage()
    {
        return (this.pages == null) || this.pages.size() <= 1;
    }

    /**
     * Gets the number of the first page.
     * @return Integer number of the first page
     */
    public Integer getFirst()
    {
        return this.firstPage;
    }

    /**
     * Sets the number of the first page.
     * @param first Integer number of the first page
     */
    public void setFirst(Integer first)
    {
        this.firstPage = first;
    }

    /**
     * Gets the number of the last page.
     * @return Integer number of the last page
     */
    public Integer getLast()
    {
        return this.lastPage;
    }

    /**
     * Sets the number of the last page.
     * @param last Integer number of the last page
     */
    public void setLast(Integer last)
    {
        this.lastPage = last;
    }

    /**
     * Gets the number of the previous page.
     * @return Integer number of the previous page
     */
    public Integer getPrevious()
    {
        return this.previousPage;
    }

    /**
     * Sets the number of the previous page.
     * @param previous Integer number of the previous page
     */
    public void setPrevious(Integer previous)
    {
        this.previousPage = previous;
    }

    /**
     * Gets the number of the next page.
     * @return Integer number of the next page
     */
    public Integer getNext()
    {
        return this.nextPage;
    }

    /**
     * Sets the number of the next page.
     * @param next Integer number of the next page
     */
    public void setNext(Integer next)
    {
        this.nextPage = next;
    }

    /**
     * Sets the number of the current page.
     * @param current number of the current page
     */
    public void setCurrent(Integer current)
    {
        this.currentPage = current;
    }

    /**
     * Returns the appropriate banner for the pagination.
     * @param numberedPageFormat String to be used for a not selected page
     * @param numberedPageSelectedFormat String to be used for a selected page
     * @param numberedPageSeparator separator beetween pages
     * @param fullBanner String basic banner
     * @return String formatted banner whith pages
     */
    public String getFormattedBanner(String numberedPageFormat, String numberedPageSelectedFormat,
        String numberedPageSeparator, String fullBanner)
    {
        StringBuffer buffer = new StringBuffer(100);

        // numbered page list
        Iterator pageIterator = this.pages.iterator();

        while (pageIterator.hasNext())
        {

            // get NumberedPage from iterator
            NumberedPage page = (NumberedPage) pageIterator.next();

            Integer pageNumber = new Integer(page.getNumber());

            String urlString = ((Href) this.href.clone()).addParameter(this.pageParam, pageNumber).toString();

            // needed for MessageFormat : page number/url
            Object[] pageObjects = {pageNumber, urlString};

            // selected page need a different formatter
            if (page.getSelected())
            {
                buffer.append(MessageFormat.format(numberedPageSelectedFormat, pageObjects));
            }
            else
            {
                buffer.append(MessageFormat.format(numberedPageFormat, pageObjects));
            }

            // next? add page separator
            if (pageIterator.hasNext())
            {
                buffer.append(numberedPageSeparator);
            }
        }

        // String for numbered pages
        String numberedPageString = buffer.toString();

        // Object array
        // {0} full String for numbered pages
        // {1} first page url
        // {2} previous page url
        // {3} next page url
        // {4} last page url
        // {5} current page
        // {6} total pages
        Object[] pageObjects = {
            numberedPageString,
            ((Href) this.href.clone()).addParameter(this.pageParam, getFirst()),
            ((Href) this.href.clone()).addParameter(this.pageParam, getPrevious()),
            ((Href) this.href.clone()).addParameter(this.pageParam, getNext()),
            ((Href) this.href.clone()).addParameter(this.pageParam, getLast()),
            this.currentPage,
            new Integer(pages.size())};

        // return the full banner
        return MessageFormat.format(fullBanner, pageObjects);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
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