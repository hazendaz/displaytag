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
    private String[] attributes;

    /**
     * Constructor for MissingAttributeException.
     * @param source Class where the exception is generated
     * @param attributeNames String attribute name
     */
    public MissingAttributeException(Class source, String[] attributeNames)
    {
        super(source, "You must specify one of the following: " + ArrayUtils.toString(attributeNames));
        this.attributes = attributeNames;
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
        return this.attributes;
    }

}
