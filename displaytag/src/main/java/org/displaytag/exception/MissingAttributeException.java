package org.displaytag.exception;

import org.apache.commons.lang.ArrayUtils;

/**
 * Exception thrown when a required attribute is not set. 
 * This is thrown when the user is required to set at least one of multiple attributes and the check 
 * can't be enforced by the tld.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class MissingAttributeException extends BaseNestableJspTagException
{

    /**
     * list of tag attributes
     */
    private String[] attributeNames;

    /**
     * Constructor for MissingAttributeException.
     * @param pSourceClass Class where the exception is generated
     * @param pAttributeNames String attribute name
     */
    public MissingAttributeException(Class pSourceClass, String[] pAttributeNames)
    {

        super(pSourceClass, "You must specify one of the following: " + ArrayUtils.toString(pAttributeNames));
        attributeNames = pAttributeNames;
    }

    /**
     * @return SeverityEnum.ERROR
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.ERROR;
    }

    /**
     * returns an array containing the names of missing attributes
     * @return String[] array of missing attributes
     */
    public String[] getAttributeNames()
    {
        return attributeNames;
    }

}
