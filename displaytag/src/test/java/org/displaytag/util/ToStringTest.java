package org.displaytag.util;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.commons.lang3.ClassUtils;
import org.displaytag.model.Cell;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.NumberedPage;
import org.displaytag.pagination.Pagination;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.ColumnTag;
import org.junit.Assert;
import org.junit.Test;


/**
 * Check that toString() methods are constructed appropriately, uses the correct style and that there aren't stupid NPE
 * bugs in them.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ToStringTest
{

    /**
     * ToString methods should be costructed using toStringBuilder and the <code>ShortToStringStyle.SHORT_STYLE</code>
     * style.
     * @param object test instance
     */
    private void checkToString(Object object)
    {
        String toString = object.toString();
        Assert.assertTrue(toString.startsWith(ClassUtils.getShortClassName(object, null)));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testSmartListHelper()
    {
        checkToString(new SmartListHelper(new ArrayList<Object>(), 100, 10, TableProperties.getInstance(null), false));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testNumberedPage()
    {
        checkToString(new NumberedPage(1, false));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testPagination()
    {
        checkToString(new Pagination(null, null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testCell()
    {
        checkToString(new Cell(null));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testHeaderCell()
    {
        checkToString(new HeaderCell());
    }

    /**
     * ToString() test.
     */
    @Test
    public void testColumn()
    {
        checkToString(new Column(new HeaderCell(), null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testRow()
    {
        checkToString(new Row(null, 0));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testTableModel()
    {
        checkToString(new TableModel(null, null, null));
    }

    /**
     * ToString() test.
     */
    @Test
    public void testColumnTag()
    {
        checkToString(new ColumnTag());
    }

}
