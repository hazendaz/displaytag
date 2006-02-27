/*
 * $Id$
 */

package org.apache.taglibs.display;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This is a little utility class that the SmartListTag uses to chop up a
 * List of objects into small bite size pieces that are more suitable for
 * display.
 *
 * <p>This class is a stripped down version of the WebListHelper that we got
 * from Tim Dawson (tdawson@is.com)</p>
 *
 * @version $Revision$
 */
class SmartListHelper extends Object {
    private List masterList;
    private int pageSize;
    private int pageCount;
    private int currentPage;

    private Properties prop = null;

    /**
     * Creates a SmarListHelper instance that will help you chop up a list
     * into bite size pieces that are suitable for display.
     */
    protected SmartListHelper(List list, int pageSize, Properties prop) {
        super();

        if (list == null || pageSize < 1) {
            throw new IllegalArgumentException("Bad arguments passed into " +
                                               "SmartListHelper() constructor");
        }

        this.prop = prop;
        this.pageSize = pageSize;
        this.masterList = list;
        this.pageCount = this.computedPageCount();
        this.currentPage = 1;
    }

    /**
     * Returns the computed number of pages it would take to show all the
     * elements in the list given the pageSize we are working with.
     */
    protected int computedPageCount() {
        int result = 0;

        if ((this.masterList != null) && (this.pageSize > 0)) {
            int size = this.masterList.size();
            int div = size / this.pageSize;
            int mod = size % this.pageSize;
            result = (mod == 0) ? div : div + 1;
        }

        return result;
    }

    /**
     * Returns the index into the master list of the first object that
     * should appear on the current page that the user is viewing.
     */
    protected int getFirstIndexForCurrentPage() {
        return this.getFirstIndexForPage(this.currentPage);
    }

    /**
     * Returns the index into the master list of the last object that should
     * appear on the current page that the user is viewing.
     */
    protected int getLastIndexForCurrentPage() {
        return this.getLastIndexForPage(this.currentPage);
    }

    /**
     * Returns the index into the master list of the first object that
     * should appear on the given page.
     */
    protected int getFirstIndexForPage(int page) {
        return ((page - 1) * this.pageSize);
    }

    /**
     * Returns the index into the master list of the last object that should
     * appear on the given page.
     */
    protected int getLastIndexForPage(int page) {
        int firstIndex = this.getFirstIndexForPage(page);
        int pageIndex = this.pageSize - 1;
        int lastIndex = this.masterList.size() - 1;

        return Math.min(firstIndex + pageIndex, lastIndex);
    }

    /**
     * Returns a subsection of the list that contains just the elements that
     * are supposed to be shown on the current page the user is viewing.
     */
    protected List getListForCurrentPage() {
        return this.getListForPage(this.currentPage);
    }

    /**
     * Returns a subsection of the list that contains just the elements that
     * are supposed to be shown on the given page.
     */
    protected List getListForPage(int page) {
        List list = new ArrayList(this.pageSize + 1);

        int firstIndex = this.getFirstIndexForPage(page);
        int lastIndex = this.getLastIndexForPage(page);

        for (int i = firstIndex; i <= lastIndex; i++) {
            list.add(this.masterList.get(i));
        }

        return list;
    }

    /**
     * Set's the page number that the user is viewing.
     *
     * @throws IllegalArgumentException if the page provided is invalid.
     */
    protected void setCurrentPage(int page) {
        if (page < 1 || page > this.pageCount) {
            Object[] objs = {new Integer(page), new Integer(pageCount)};
            throw new IllegalArgumentException(
                    MessageFormat.format(prop.getProperty("error.msg.invalid_page"), objs));
        }

        this.currentPage = page;
    }

    /**
     * Return the little summary message that lets the user know how many
     * objects are in the list they are viewing, and where in the list they
     * are currently positioned.  The message looks like:
     *
     * <p>nnn item(s) found, displaying nnn to nnn.</p>
     *
     * <p><code>item(s)</code> is replaced by either itemName or itemNames depending on if
     * it should be signular or plurel.</p>
     */
    protected String getSearchResultsSummary() {
        if (this.masterList.size() == 0) {
            Object[] objs = {prop.getProperty("paging.banner.items_name")};
            return MessageFormat.format(
                    prop.getProperty("paging.banner.no_items_found"), objs);
        }
        else if (this.masterList.size() == 1) {
            Object[] objs = {prop.getProperty("paging.banner.item_name")};
            return MessageFormat.format(
                    prop.getProperty("paging.banner.one_item_found"), objs);
        }
        else if (this.getFirstIndexForCurrentPage() == this.getLastIndexForCurrentPage()) {
            Object[] objs = {new Integer(this.masterList.size()),
                             prop.getProperty("paging.banner.items_name"),
                             prop.getProperty("paging.banner.items_name")};

            return MessageFormat.format(
                    prop.getProperty("paging.banner.all_items_found"), objs);
        }
        else {
            Object[] objs = {new Integer(this.masterList.size()),
                             prop.getProperty("paging.banner.items_name"),
                             new Integer(this.getFirstIndexForCurrentPage() + 1),
                             new Integer(this.getLastIndexForCurrentPage() + 1)};

            return MessageFormat.format(
                    prop.getProperty("paging.banner.some_items_found"), objs);
        }
    }

    /**
     * Returns a string containing the nagivation bar that allows the user
     * to move between pages within the list.
     *
     * <p>The urlFormatString should be a URL that looks like the following:</p>
     *
     * <p>http://.../somepage.page?page={0}</p>
     */
    protected String getPageNavigationBar(String urlFormatString) {
        MessageFormat form = new MessageFormat(urlFormatString);

        int maxPages = 8;

        try {
            maxPages = Integer.parseInt(prop.getProperty("paging.banner.group_size"));
        }
        catch (NumberFormatException e) {
            // Don't care, we will just default to 8.
        }

        int currentPage = this.currentPage;
        int pageCount = this.pageCount;
        int startPage = 1;
        int endPage = maxPages;

        if (pageCount == 1 || pageCount == 0) {
            return "<b>1</b>";
        }

        if (currentPage < maxPages) {
            startPage = 1;
            endPage = maxPages;
            if (pageCount < endPage) {
                endPage = pageCount;
            }
        }
        else {
            startPage = currentPage;
            while (startPage + maxPages > (pageCount + 1)) {
                startPage--;
            }

            endPage = startPage + (maxPages - 1);
        }

        boolean includeFirstLast = prop.getProperty("paging.banner.include_first_last").equals("true");

        String msg = "";
        if (currentPage == 1) {
            if (includeFirstLast) {
                msg += "[" + prop.getProperty("paging.banner.first_label") +
                        "/" + prop.getProperty("paging.banner.prev_label") + "] ";
            }
            else {
                msg += "[" + prop.getProperty("paging.banner.prev_label") + "] ";
            }
        }
        else {
            Object[] objs = {new Integer(currentPage - 1)};
            Object[] v1 = {new Integer(1)};
            if (includeFirstLast) {
                msg += "[<a href=\"" + form.format(v1) + "\">" +
                        prop.getProperty("paging.banner.first_label") + "</a>/<a href=\"" +
                        form.format(objs) + "\">" +
                        prop.getProperty("paging.banner.prev_label") + "</a>] ";
            }
            else {
                msg += "[<a href=\"" + form.format(objs) + "\">" +
                        prop.getProperty("paging.banner.prev_label") + "</a>] ";
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            if (i == currentPage) {
                msg += "<b>" + i + "</b>";
            }
            else {
                Object[] v = {new Integer(i)};

                msg += "<a href=\"" + form.format(v) + "\">" + i + "</a>";
            }

            if (i != endPage) {
                msg += ", ";
            }
            else {
                msg += " ";
            }
        }

        if (currentPage == pageCount) {
            if (includeFirstLast) {
                msg += "[" + prop.getProperty("paging.banner.next_label") +
                        "/" + prop.getProperty("paging.banner.last_label") + "] ";
            }
            else {
                msg += "[" + prop.getProperty("paging.banner.next_label") + "] ";
            }
        }
        else {
            Object[] objs = {new Integer(currentPage + 1)};
            Object[] v1 = {new Integer(pageCount)};
            if (includeFirstLast) {
                msg += "[<a href=\"" + form.format(objs) + "\">" +
                        prop.getProperty("paging.banner.next_label") + "</a>/<a href=\"" +
                        form.format(v1) + "\">" +
                        prop.getProperty("paging.banner.last_label") + "</a>] ";
            }
            else {
                msg += "[<a href=\"" + form.format(objs) + "\">" +
                        prop.getProperty("paging.banner.next_label") + "</a>] ";
            }
        }

        return msg;
    }
}