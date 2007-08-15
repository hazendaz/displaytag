package org.displaytag.decorator;

/**
 * @author Jorge Barroso
 * @version $Id$
 */
public class ModelDecorator extends TableDecorator
{

    public static final String DECORATED_VALUE = "decoratedValue";

    public String getDecoratedValue()
    {
        return DECORATED_VALUE;
    }

}