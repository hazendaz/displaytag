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
        this.table = new TableTag();
        this.table.setList(testData);
        this.tableLifecycle = new JspTagLifecycle(this.pageContext, this.table);

        // column with property
        ColumnTag column = new ColumnTag();
        column.setProperty("xxx");
        this.columnPropertyLifecycle = this.tableLifecycle.addNestedTag(column);

        // column with body
        ColumnTag column3 = new ColumnTag();
        this.columnBodyLifecycle = this.tableLifecycle.addNestedTag(column3);
        this.columnBodyLifecycle.addNestedText("column body");

    }

    public void testEmptyTable() throws Exception
    {
        this.tableLifecycle.invoke();

        // body should be always skipped when using property
        this.columnPropertyLifecycle.expectBodySkipped();

        // body should also be skipped when list is empty
        this.columnBodyLifecycle.expectBodySkipped();
    }

}
