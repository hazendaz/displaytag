package org.displaytag.util;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.commons.lang.ClassUtils;
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


/**
 * Check that toString() methods are constructed appropriately, uses the correct style and that there aren't stupid NPE
 * bugs in them.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ToStringTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * ToString methods should be costructed using toStringBuilder and the <code>ShortToStringStyle.SHORT_STYLE</code>
     * style.
     * @param object test instance
     */
    private void checkToString(Object object)
    {
        String toString = object.toString();
        assertTrue(toString.startsWith(ClassUtils.getShortClassName(object, null)));
    }

    /**
     * ToString() test.
     */
    public void testSmartListHelper()
    {
        checkToString(new SmartListHelper(new ArrayList(), 100, 10, TableProperties.getInstance(null)));
    }

    /**
     * ToString() test.
     */
    public void testNumberedPage()
    {
        checkToString(new NumberedPage(1, false));
    }

    /**
     * ToString() test.
     */
    public void testPagination()
    {
        checkToString(new Pagination(null, null));
    }

    /**
     * ToString() test.
     */
    public void testCell()
    {
        checkToString(new Cell(null));
    }

    /**
     * ToString() test.
     */
    public void testHeaderCell()
    {
        checkToString(new HeaderCell());
    }

    /**
     * ToString() test.
     */
    public void testColumn()
    {
        checkToString(new Column(new HeaderCell(), null, null));
    }

    /**
     * ToString() test.
     */
    public void testRow()
    {
        checkToString(new Row(null, 0));
    }

    /**
     * ToString() test.
     */
    public void testTableModel()
    {
        checkToString(new TableModel(null, null));
    }

    /**
     * ToString() test.
     */
    public void testColumnTag()
    {
        checkToString(new ColumnTag());
    }

}
