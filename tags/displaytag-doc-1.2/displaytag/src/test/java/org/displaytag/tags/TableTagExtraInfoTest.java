package org.displaytag.tags;

import junit.framework.TestCase;


/**
 * Test case for org.displaytag.tags.TableTagExtraInfo.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableTagExtraInfoTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdValid()
    {
        assertTrue(TableTagExtraInfo.isJavaId("table"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdEnum()
    {
        assertFalse(TableTagExtraInfo.isJavaId("enum"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdSpace()
    {
        assertFalse(TableTagExtraInfo.isJavaId("invalid x"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdEsclamationMark()
    {
        assertFalse(TableTagExtraInfo.isJavaId("invalid!"));
    }

}