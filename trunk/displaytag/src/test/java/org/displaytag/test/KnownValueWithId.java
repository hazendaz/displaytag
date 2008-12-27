package org.displaytag.test;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class KnownValueWithId extends KnownValue
{

    private String id;

    public KnownValueWithId(String id)
    {
        this.id = id;
    }

    /**
     * Getter for <code>objectId</code>.
     * @return Returns the objectId.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Setter for <code>objectId</code>.
     * @param objectId The objectId to set.
     */
    public void setId(String objectId)
    {
        this.id = objectId;
    }

}
