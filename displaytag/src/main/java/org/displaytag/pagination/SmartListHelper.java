package org.displaytag.pagination;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.TableProperties;

/**
 * <p>
 * Utility class that chops up a List of objects into small bite size pieces
 * that are more suitable for display.
 * </p>
 * <p>
 * This class is a stripped down version of the WebListHelper from Tim Dawson (tdawson@is.com)
 * </p>
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class SmartListHelper
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(SmartListHelper.class);

    /**
     * full list
     */
    private List mFullList;

    /**
     * sixe of the full list
     */
    private int mFullListSize;

    /**
     * number of items in a page
     */
    private int mPageSize;

    /**
     * number of pages
     */
    private int mPageCount;

    /**
     * index of current page
     */
    private int mCurrentPage;

    /**
     * TableProperties
     */
    private TableProperties mProp = null;

    /**
     * Creates a SmarListHelper instance that will help you chop up a list
     * into bite size pieces that are suitable for display.
     * @param pList List
     * @param pPageSize number of items in a page
     * @param pProperties TableProperties
     */
    public SmartListHelper(List pList, int pPageSize, TableProperties pProperties)
    {

        if (mLog.isDebugEnabled())
        {
            mLog.debug("new SmartListHelper: list.size= " + pList.size() + " page size=" + pPageSize);
        }

        if (pList == null || pPageSize < 1)
        {
            throw new IllegalArgumentException(
                "Bad arguments passed into "
                    + "SmartListHelper() constructor. List="
                    + pList
                    + ", pagesize="
                    + pPageSize);
        }

        mProp = pProperties;
        mPageSize = pPageSize;
        mFullList = pList;

        if (mFullList != null)
        {
            mFullListSize = pList.size();
        }
        else
        {
            mFullListSize = 0;
        }

        mPageCount = computedPageCount();
        mCurrentPage = 1;
    }

    /**
     * Returns the computed number of pages it would take to show all the
     * elements in the list given the pageSize we are working with.
     * @return int computed number of pages
     */
    protected int computedPageCount()
    {

        int lResult = 0;

        if ((mFullList != null) && (mPageSize > 0))
        {
            int lSize = mFullListSize;
            int lDiv = lSize / mPageSize;
            int lMod = lSize % mPageSize;
            lResult = (lMod == 0) ? lDiv : lDiv + 1;
        }

        return lResult;

    }

    /**
     * Returns the index into the master list of the first object that
     * should appear on the current page that the user is viewing.
     * @return int index of the first object that should appear on the current page
     */
    protected int getFirstIndexForCurrentPage()
    {
        return getFirstIndexForPage(mCurrentPage);
    }

    /**
     * Returns the index into the master list of the last object that should
     * appear on the current page that the user is viewing.
     * @return int
     */
    protected int getLastIndexForCurrentPage()
    {

        return getLastIndexForPage(mCurrentPage);
    }

    /**
     * Returns the index into the master list of the first object that
     * should appear on the given page.
     * @param pPage page number
     * @return int index of the first object that should appear on the given page
     */
    protected int getFirstIndexForPage(int pPage)
    {

        return (pPage - 1) * mPageSize;
    }

    /**
     * Returns the index into the master list of the last object that should
     * appear on the given page.
     * @param pPage page number
     * @return int index of the last object that should appear on the given page
     */
    protected int getLastIndexForPage(int pPage)
    {

        int lFirstIndex = getFirstIndexForPage(pPage);
        int lPageIndex = mPageSize - 1;
        int lLastIndex = mFullListSize - 1;

        return Math.min(lFirstIndex + lPageIndex, lLastIndex);
    }

    /**
     * Returns a subsection of the list that contains just the elements that
     * are supposed to be shown on the current page the user is viewing.
     * @return List subsection of the list that contains the elements that are supposed to be shown on the current page
     */

    public List getListForCurrentPage()
    {

        return getListForPage(mCurrentPage);
    }

    /**
     * Returns a subsection of the list that contains just the elements that
     * are supposed to be shown on the given page.
     * @param pPage page number
     * @return List subsection of the list that contains just the elements that
     * are supposed to be shown on the given page
     */
    protected List getListForPage(int pPage)
    {

        mLog.debug("getListForPage page=" + pPage);

        List lList = new ArrayList(mPageSize + 1);

        int lFirstIndex = getFirstIndexForPage(pPage);
        int lLastIndex = getLastIndexForPage(pPage);

        Iterator lIterator = mFullList.iterator();
        int lCount = 0;

        while (lIterator.hasNext())
        {

            Object lObject = lIterator.next();

            if (lCount > lLastIndex)
            {
                break;
            }
            else if (lCount >= lFirstIndex)
            {
                lList.add(lObject);
            }
            lCount++;

        }

        return lList;
    }

    /**
     * Set's the page number that the user is viewing.
     * @param pPage page number
     */
    public void setCurrentPage(int pPage)
    {

        mLog.debug("setCurrentPage: page=" + pPage + " of " + mPageCount);

        if (pPage < 1 || ((pPage != 1) && (pPage > mPageCount)))
        {
            // invalid page: better don't throw an exception, since this could easily happen
            // (list changed, user bookmarked the page)
            mCurrentPage = 1;
        }
        else
        {
            mCurrentPage = pPage;
        }
    }

    /**
     * Return the little summary message that lets the user know how many
     * objects are in the list they are viewing, and where in the list they
     * are currently positioned.  The message looks like:
     *
     * nnn <item(s)> found, displaying nnn to nnn.
     *
     * <item(s)> is replaced by either itemName or itemNames depending on if
     * it should be signular or plural.
     * @return String
     */
    public String getSearchResultsSummary()
    {

        if (mFullListSize == 0)
        {
            mLog.debug("returning paging.banner.no_items_found");

            Object[] lObjs = { mProp.getPagingItemsName()};

            return MessageFormat.format(mProp.getPagingFoundNoItems(), lObjs);

        }
        else if (mFullListSize == 1)
        {

            mLog.debug("returning paging.banner.one_item_found");

            Object[] lObjs = { mProp.getPagingItemName()};

            return MessageFormat.format(mProp.getPagingFoundOneItem(), lObjs);
        }
        else if (computedPageCount() == 1)
        {
            Object[] lObjs = { new Integer(mFullListSize), mProp.getPagingItemsName(), mProp.getPagingItemsName()};

            mLog.debug("returning paging.banner.all_items_found");

            return MessageFormat.format(mProp.getPagingFoundAllItems(), lObjs);
        }
        else
        {
            Object[] lObjs =
                {
                    new Integer(mFullListSize),
                    mProp.getPagingItemsName(),
                    new Integer(getFirstIndexForCurrentPage() + 1),
                    new Integer(getLastIndexForCurrentPage() + 1)};

            mLog.debug("returning paging.banner.some_items_found");
            return MessageFormat.format(mProp.getPagingFoundSomeItems(), lObjs);
        }
    }

    /**
     * Returns a string containing the nagivation bar that allows the user
     * to move between pages within the list.
     *
     * The urlFormatString should be a URL that looks like the following:
     *
     * somepage.page?page={0}
     * @param pUrlFormatString String
     * @return String
     */
    public String getPageNavigationBar(String pUrlFormatString)
    {

        mLog.debug("getPageNavigationBar");
        int lMaxPages = 8;

        lMaxPages = mProp.getPagingGroupSize(lMaxPages);

        int lCurrentPage = mCurrentPage;
        int lPageCount = mPageCount;
        int lStartPage = 1;
        int lEndPage = lMaxPages;

        Pagination lPagination = new Pagination(pUrlFormatString);

        mLog.debug("mPageCount=" + mPageCount);

        // if no items are found still add pagination?
        if (lPageCount == 0)
        {
            lPagination.addPage(1, true);
        }

        if (lCurrentPage < lMaxPages)
        {
            lStartPage = 1;
            lEndPage = lMaxPages;
            if (lPageCount < lEndPage)
            {
                lEndPage = lPageCount;
            }
        }
        else
        {
            lStartPage = lCurrentPage;
            while (lStartPage + lMaxPages > (lPageCount + 1))
            {
                lStartPage--;
            }

            lEndPage = lStartPage + (lMaxPages - 1);
        }

        if (lCurrentPage != 1)
        {
            lPagination.setFirst(new Integer(1));
            lPagination.setPrevious(new Integer(lCurrentPage - 1));
        }

        for (int lCounter = lStartPage; lCounter <= lEndPage; lCounter++)
        {
            mLog.debug("adding page " + lCounter);
            lPagination.addPage(lCounter, (lCounter == lCurrentPage));
        }

        if (lCurrentPage != lPageCount)
        {
            lPagination.setNext(new Integer(lCurrentPage + 1));
            lPagination.setLast(new Integer(lPageCount));
        }

        // format for previous/next banner
        String lBannerFormat;

        mLog.debug("lPagination.isOnePage()=" + lPagination.isOnePage());

        if (lPagination.isOnePage())
        {
            lBannerFormat = mProp.getPagingBannerOnePage();
        }
        else if (lPagination.isFirst())
        {
            lBannerFormat = mProp.getPagingBannerFirst();
        }
        else if (lPagination.isLast())
        {
            lBannerFormat = mProp.getPagingBannerLast();
        }
        else
        {
            lBannerFormat = mProp.getPagingBannerFull();
        }

        mLog.debug("getPageNavigationBar end");

        return lPagination.getFormattedBanner(
            mProp.getPagingPageLink(),
            mProp.getPagingPageSelected(),
            mProp.getPagingPageSeparator(),
            lBannerFormat);
    }
}
