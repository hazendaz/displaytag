package org.displaytag.decorator;

import org.displaytag.exception.DecoratorInstantiationException;

/**
 * Factory for TableDecorator or ColumnDecorator object.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class DecoratorFactory
{

    /**
     * Constructor for DecoratorFactory.
     */
    private DecoratorFactory()
    {
    }

    /**
     * If the user has specified a decorator, then this method takes care of creating the decorator (and checking to
     * make sure it is a subclass of the TableDecorator object). If there are any problems loading the decorator then
     * this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * @param decoratorName String full decorator class name
     * @return instance of TableDecorator
     * @throws DecoratorInstantiationException if unable to load specified TableDecorator
     */
    public static TableDecorator loadTableDecorator(String decoratorName) throws DecoratorInstantiationException
    {
        if (decoratorName == null || decoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class decoratorClass = Class.forName(decoratorName);
            return (TableDecorator) decoratorClass.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
        catch (ClassCastException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, decoratorName, e);
        }
    }

    /**
     * If the user has specified a column decorator, then this method takes care of creating the decorator (and
     * checking to make sure it is a subclass of the ColumnDecorator object). If there are any problems loading the
     * decorator then this will throw a DecoratorInstantiationException which will get propagated up to the page.
     * @param columnDecoratorName String full decorator class name
     * @return instance of ColumnDecorator
     * @throws DecoratorInstantiationException if unable to load ColumnDecorator
     */
    public static ColumnDecorator loadColumnDecorator(String columnDecoratorName)
        throws DecoratorInstantiationException
    {
        if (columnDecoratorName == null || columnDecoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class decoratorClass = Class.forName(columnDecoratorName);
            return (ColumnDecorator) decoratorClass.newInstance();
        }

        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
        catch (ClassCastException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, columnDecoratorName, e);
        }
    }

}