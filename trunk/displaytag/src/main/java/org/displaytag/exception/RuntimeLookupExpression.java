package org.displaytag.exception;

/**
 * runtime exception thrown during sorting when a checked exception can't be used
 * @author fgiust
 * @version $Revision: $ ($Author: $)
 */
public class RuntimeLookupExpression extends RuntimeException
{

    /**
     * @param sourceClass class where the exception is thrown
     * @param property object property who caused the exception
     * @param cause previous (checked) exception
     */
    public RuntimeLookupExpression(Class sourceClass, String property, BaseNestableJspTagException cause)
    {
        super("LookupExpression while trying to fetch property \"" + property + "\"\nCause:     " + cause.getMessage());
    }

}
