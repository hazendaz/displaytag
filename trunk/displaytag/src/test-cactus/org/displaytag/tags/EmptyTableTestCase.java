package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.extension.jsp.JspTagLifecycle;

/**
 * Test for behaviour when using empty tables
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class EmptyTableTestCase extends JspTestCase
{
    TableTag table;
    JspTagLifecycle tableLifecycle;
    JspTagLifecycle columnPropertyLifecycle;
    JspTagLifecycle columnBodyLifecycle;

    protected void setUp() throws Exception
    {
        // set up table
        List testData = new ArrayList();
        table = new TableTag();
        table.setList(testData);
        tableLifecycle = new JspTagLifecycle(pageContext, table);

        // column with property
        ColumnTag column = new ColumnTag();
        column.setProperty("xxx");
        columnPropertyLifecycle = tableLifecycle.addNestedTag(column);

        // column with body
        ColumnTag column3 = new ColumnTag();
        columnBodyLifecycle = tableLifecycle.addNestedTag(column3);
        columnBodyLifecycle.addNestedText("column body");

    }

    public void testEmptyTable() throws Exception
    {
        tableLifecycle.invoke();

        // body should be always skipped when using property
        columnPropertyLifecycle.expectBodySkipped();

        // body should also be skipped when list is empty
        columnBodyLifecycle.expectBodySkipped();
    }

}
