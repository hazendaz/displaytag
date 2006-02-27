package org.displaytag.exception;

/**
 * runtime exception thrown during sorting when a checked exception can't be used.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class RuntimeLookupException extends RuntimeException
{

    /**
     * @param sourceClass class where the exception is thrown
     * @param property object property who caused the exception
     * @param cause previous (checked) exception
     */
    public RuntimeLookupException(Class sourceClass, String property, BaseNestableJspTagException cause)
    {
        super("LookupException while trying to fetch property \"" + property + "\"\nCause:     " + cause.getMessage());
    }

}
