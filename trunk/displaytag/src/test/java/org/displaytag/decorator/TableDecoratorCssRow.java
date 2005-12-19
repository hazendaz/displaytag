package org.displaytag.decorator;

/**
 * Test decorator used in tests.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableDecoratorCssRow extends TableDecorator
{

    /**
     * @see org.displaytag.decorator.TableDecorator#addRowId()
     */
    public String addRowId()
    {
        return "rowid" + getViewIndex();
    }

    /**
     * @see org.displaytag.decorator.TableDecorator#addRowClass()
     */
    public String addRowClass()
    {
        if (getViewIndex() == 2)
        {
            return "highlighted";
        }
        return null;
    }

}