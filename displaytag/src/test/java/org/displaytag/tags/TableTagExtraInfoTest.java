package org.displaytag.tags;

import junit.framework.TestCase;

import org.junit.Assert;


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
    @Override
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdValid()
    {
        Assert.assertTrue(TableTagExtraInfo.isJavaId("table"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdEnum()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("enum"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdSpace()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("invalid x"));
    }

    /**
     * Test for isJavaId().
     */
    public final void testIsJavaIdEsclamationMark()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("invalid!"));
    }

}