package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

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
    private static Log mLog = LogFactory.getLog(Pagination.class);

    /**
     * MessageFormat for urls
     */
    private MessageFormat mUrlFormat;

    /**
     * first page
     */
    private Integer mFirst;

    /**
     * last page
     */
    private Integer mLast;

    /**
     * previous page
     */
    private Integer mPrevious;

    /**
     * next page
     */
    private Integer mNext;

    /**
     * ArrayList containg NumberedPage objects
     * @see org.displaytag.pagination.NumberedPage
     */
    private ArrayList mPages = new ArrayList();

    /**
     * Constructor for Pagination
     * @param pUrlFormatString String
     */
    public Pagination(String pUrlFormatString)
    {
        mUrlFormat = new MessageFormat(pUrlFormatString);
    }

    /**
     * add a page
     * @param pNumber int page number
     * @param pSelected is the page selected?
     */
    public void addPage(int pNumber, boolean pSelected)
    {
        mLog.debug("addpage " + pNumber);
        mPages.add(new NumberedPage(pNumber, pSelected));
    }

    /**
     * first page selected?
     * @return boolean
     */
    public boolean isFirst()
    {
        return mFirst == null;
    }

    /**
     * last page selected?
     * @return boolean
     */
    public boolean isLast()
    {
        return mLast == null;
    }

    /**
     * only one page?
     * @return boolean
     */
    public boolean isOnePage()
    {
        return (mPages == null) || mPages.size() <= 1;
    }

    /**
     * Get the number of the first page
     * @return Integer number of the first page
     */
    public Integer getFirst()
    {
        return mFirst;
    }

    /**
     * Set the number of the first page
     * @param pFirst Integer number of the first page
     */
    public void setFirst(Integer pFirst)
    {
        mFirst = pFirst;
    }

    /**
     * Get the number of the last page
     * @return Integer number of the last page
     */
    public Integer getLast()
    {
        return mLast;
    }

    /**
     * Set the number of the last page
     * @param pLast Integer number of the last page
     */
    public void setLast(Integer pLast)
    {
        mLast = pLast;
    }

    /**
     * Get the number of the previous page
     * @return Integer number of the previous page
     */
    public Integer getPrevious()
    {
        return mPrevious;
    }

    /**
     * Set the number of the previous page
     * @param pPrevious Integer number of the previous page
     */
    public void setPrevious(Integer pPrevious)
    {
        mPrevious = pPrevious;
    }

    /**
     * Get the number of the next page
     * @return Integer number of the next page
     */
    public Integer getNext()
    {
        return mNext;
    }

    /**
     * Set the number of the next page
     * @param pNext Integer number of the next page
     */
    public void setNext(Integer pNext)
    {
        mNext = pNext;
    }

    /**
     * returns the appropriate banner for the pagination
     * @param pNumberedPageFormat String to be used for a not selected page
     * @param pNumberedPageSelectedFormat String to be used for a selected page
     * @param pNumberedPageSeparator separator beetween pages
     * @param pFullBanner String basic banner
     * @return String formatted banner whith pages
     */
    public String getFormattedBanner(
        String pNumberedPageFormat,
        String pNumberedPageSelectedFormat,
        String pNumberedPageSeparator,
        String pFullBanner)
    {

        StringBuffer lBuffer = new StringBuffer(100);

        // numbered page list
        Iterator lPageIterator = mPages.iterator();

        while (lPageIterator.hasNext())
        {

            // get NumberedPage from iterator
            NumberedPage lPage = (NumberedPage) lPageIterator.next();

            Integer lPageNumber = new Integer(lPage.getNumber());

            String lUrlString = mUrlFormat.format(new Object[] { lPageNumber });

            // needed for MessageFormat : page number/url
            Object[] lPageObjects = { lPageNumber, lUrlString };

            // selected page need a different formatter
            if (lPage.getSelected())
            {
                lBuffer.append(MessageFormat.format(pNumberedPageSelectedFormat, lPageObjects));
            }
            else
            {
                lBuffer.append(MessageFormat.format(pNumberedPageFormat, lPageObjects));
            }

            // next? add page separator
            if (lPageIterator.hasNext())
            {
                lBuffer.append(pNumberedPageSeparator);
            }
        }

        // String for numbered pages
        String lNumberedPageString = lBuffer.toString();

        //  Object array
        //  {0} full String for numbered pages
        //  {1} first page url
        //  {2} previous page url
        //  {3} next page url
        //  {4} last page url
        Object[] lPageObjects =
            {
                lNumberedPageString,
                mUrlFormat.format(new Object[] { getFirst()}),
                mUrlFormat.format(new Object[] { getPrevious()}),
                mUrlFormat.format(new Object[] { getNext()}),
                mUrlFormat.format(new Object[] { getLast()}),
                };

        // return the full banner
        return MessageFormat.format(pFullBanner, lPageObjects);
    }

}