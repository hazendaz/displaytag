package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Pagination
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(Pagination.class);

    /**
     * MessageFormat for urls
     */
    private MessageFormat urlFormat;

    /**
     * first page
     */
    private Integer firstPage;

    /**
     * last page
     */
    private Integer lastPage;

    /**
     * previous page
     */
    private Integer previousPage;

    /**
     * next page
     */
    private Integer nextPage;

    /**
     * List containg NumberedPage objects
     * @see org.displaytag.pagination.NumberedPage
     */
    private List pages = new ArrayList();

    /**
     * Constructor for Pagination
     * @param urlFormatString String
     */
    public Pagination(String urlFormatString)
    {
        this.urlFormat = new MessageFormat(urlFormatString);
    }

    /**
     * add a page
     * @param number int page number
     * @param isSelected is the page selected?
     */
    public void addPage(int number, boolean isSelected)
    {
        log.debug("addpage " + number);
        pages.add(new NumberedPage(number, isSelected));
    }

    /**
     * first page selected?
     * @return boolean
     */
    public boolean isFirst()
    {
        return firstPage == null;
    }

    /**
     * last page selected?
     * @return boolean
     */
    public boolean isLast()
    {
        return lastPage == null;
    }

    /**
     * only one page?
     * @return boolean
     */
    public boolean isOnePage()
    {
        return (pages == null) || pages.size() <= 1;
    }

    /**
     * Get the number of the first page
     * @return Integer number of the first page
     */
    public Integer getFirst()
    {
        return firstPage;
    }

    /**
     * Set the number of the first page
     * @param first Integer number of the first page
     */
    public void setFirst(Integer first)
    {
        firstPage = first;
    }

    /**
     * Get the number of the last page
     * @return Integer number of the last page
     */
    public Integer getLast()
    {
        return lastPage;
    }

    /**
     * Set the number of the last page
     * @param last Integer number of the last page
     */
    public void setLast(Integer last)
    {
        lastPage = last;
    }

    /**
     * Get the number of the previous page
     * @return Integer number of the previous page
     */
    public Integer getPrevious()
    {
        return previousPage;
    }

    /**
     * Set the number of the previous page
     * @param previous Integer number of the previous page
     */
    public void setPrevious(Integer previous)
    {
        previousPage = previous;
    }

    /**
     * Get the number of the next page
     * @return Integer number of the next page
     */
    public Integer getNext()
    {
        return nextPage;
    }

    /**
     * Set the number of the next page
     * @param next Integer number of the next page
     */
    public void setNext(Integer next)
    {
        nextPage = next;
    }

    /**
     * returns the appropriate banner for the pagination
     * @param numberedPageFormat String to be used for a not selected page
     * @param numberedPageSelectedFormat String to be used for a selected page
     * @param numberedPageSeparator separator beetween pages
     * @param fullBanner String basic banner
     * @return String formatted banner whith pages
     */
    public String getFormattedBanner(
        String numberedPageFormat,
        String numberedPageSelectedFormat,
        String numberedPageSeparator,
        String fullBanner)
    {

        StringBuffer buffer = new StringBuffer(100);

        // numbered page list
        Iterator pageIterator = pages.iterator();

        while (pageIterator.hasNext())
        {

            // get NumberedPage from iterator
            NumberedPage page = (NumberedPage) pageIterator.next();

            Integer pageNumber = new Integer(page.getNumber());

            String urlString = this.urlFormat.format(new Object[] { pageNumber });

            // needed for MessageFormat : page number/url
            Object[] pageObjects = { pageNumber, urlString };

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

        //  Object array
        //  {0} full String for numbered pages
        //  {1} first page url
        //  {2} previous page url
        //  {3} next page url
        //  {4} last page url
        Object[] pageObjects =
            {
                numberedPageString,
                this.urlFormat.format(new Object[] { getFirst()}),
                this.urlFormat.format(new Object[] { getPrevious()}),
                this.urlFormat.format(new Object[] { getNext()}),
                this.urlFormat.format(new Object[] { getLast()}),
                };

        // return the full banner
        return MessageFormat.format(fullBanner, pageObjects);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append("firstPage", this.firstPage)
            .append("lastPage", this.lastPage)
            .append("nextPage", this.nextPage)
            .append("previousPage", this.previousPage)
            .append("pages", this.pages)
            .append("urlFormat", this.urlFormat)
            .toString();
    }
}