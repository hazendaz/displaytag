package org.displaytag.decorator;

import org.displaytag.exception.DecoratorInstantiationException;

/**
 * <p>Factory for TableDecorator or ColumnDecorator object</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class DecoratorFactory
{

    /**
     * Constructor for DecoratorFactory
     */
    private DecoratorFactory()
    {
    }

    /**
     * If the user has specified a decorator, then this method takes care of
     * creating the decorator (and checking to make sure it is a subclass of
     * the TableDecorator object).  If there are any problems loading the
     * decorator then this will throw a DecoratorInstantiationException which
     * will get propagated up to the page.
     * @param pDecoratorName String full decorator class name
     * @return instance of TableDecorator
     * @throws DecoratorInstantiationException if unable to load specified TableDecorator
     */
    public static TableDecorator loadTableDecorator(String pDecoratorName) throws DecoratorInstantiationException
    {
        if (pDecoratorName == null || pDecoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class lClass = Class.forName(pDecoratorName);
            return (TableDecorator) lClass.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pDecoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pDecoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pDecoratorName, e);
        }
        catch (ClassCastException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pDecoratorName, e);
        }
    }

    /**
     * If the user has specified a column decorator, then this method takes care of
     * creating the decorator (and checking to make sure it is a subclass of
     * the ColumnDecorator object).  If there are any problems loading the
     * decorator then this will throw a DecoratorInstantiationException which will
     * get propagated up to the page.
     * @param pColumnDecoratorName String full decorator class name
     * @return instance of ColumnDecorator
     * @throws DecoratorInstantiationException if unable to load ColumnDecorator
     */
    public static ColumnDecorator loadColumnDecorator(String pColumnDecoratorName)
        throws DecoratorInstantiationException
    {
        if (pColumnDecoratorName == null || pColumnDecoratorName.length() == 0)
        {
            return null;
        }

        try
        {
            Class lClass = Class.forName(pColumnDecoratorName);
            return (ColumnDecorator) lClass.newInstance();
        }

        catch (ClassNotFoundException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pColumnDecoratorName, e);
        }
        catch (InstantiationException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pColumnDecoratorName, e);
        }
        catch (IllegalAccessException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pColumnDecoratorName, e);
        }
        catch (ClassCastException e)
        {
            throw new DecoratorInstantiationException(DecoratorFactory.class, pColumnDecoratorName, e);
        }
    }

}