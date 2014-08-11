package org.displaytag.decorator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.TableProperties;
import org.junit.Test;


/**
 * Test for TableDecorator with pagination. If you set up pagination and iterate through the entire page, you should
 * always be on the last row at the end. If you have grouped totals, the first group on a page other than the first
 * should start at the page offset, not at 0.
 * @author Robert West
 * @version $Revision: 1 $ ($Author: rwest $)
 */
public class TableDecoratorPaginationTest
{

    @Test
    public void testSinglePage()
    {
        List<Integer> rawData = new ArrayList<Integer>(10);
        List<Object> data = new ArrayList<Object>(10);
        for (int i = 1; i <= 10; i++)
        {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        TableProperties props = TableProperties.getInstance(null);
        SmartListHelper helper = new SmartListHelper(data, data.size(), 10, props, false);
        helper.setCurrentPage(1);
        List fullList = helper.getListForCurrentPage();

        TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext())
        {
            iterator.next();
        }

        assertEquals(decorator.isLastRow(), true);
    }

    @Test
    public void testFirstPage()
    {
        List<Integer> rawData = new ArrayList<Integer>(10);
        List<Object> data = new ArrayList<Object>(10);
        for (int i = 1; i <= 10; i++)
        {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        TableProperties props = TableProperties.getInstance(null);
        SmartListHelper helper = new SmartListHelper(data, data.size(), 5, props, false);
        helper.setCurrentPage(1);
        List fullList = helper.getListForCurrentPage();

        TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext())
        {
            iterator.next();
        }

        assertEquals(decorator.isLastRow(), true);
    }

    @Test
    public void testSecondPage()
    {
        List<Integer> rawData = new ArrayList<Integer>(10);
        List<Object> data = new ArrayList<Object>(10);
        for (int i = 1; i <= 10; i++)
        {
            rawData.add(i);
            data.add(new Row(i, i));
        }

        TableProperties props = TableProperties.getInstance(null);
        SmartListHelper helper = new SmartListHelper(data, data.size(), 5, props, false);
        helper.setCurrentPage(2);
        List fullList = helper.getListForCurrentPage();

        TableModel model = new TableModel(props, "", null);
        model.setRowListPage(fullList);
        model.setPageOffset(helper.getFirstIndexForCurrentPage());

        MultilevelTotalTableDecorator decorator = new MultilevelTotalTableDecorator();
        decorator.init(null, rawData, model);
        model.setTableDecorator(decorator);

        RowIterator iterator = model.getRowIterator(false);
        while (iterator.hasNext())
        {
            iterator.next();
        }

        assertEquals(decorator.isLastRow(), true);
    }
}
