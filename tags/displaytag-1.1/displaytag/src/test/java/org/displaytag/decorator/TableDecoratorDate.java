package org.displaytag.decorator;

import java.util.Date;


/**
 * Test decorator used in tests.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableDecoratorDate extends TableDecorator
{

    /**
     * getter property for "decoratorDate".
     * @return a fixed date
     */
    public Date getDecoratorDate()
    {
        return new Date(60121180800000L);
    }
}