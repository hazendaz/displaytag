package org.displaytag.tags;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test case for org.displaytag.tags.TableTagExtraInfo.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableTagExtraInfoTest
{

    /**
     * Test for isJavaId().
     */
    @Test
    public final void testIsJavaIdValid()
    {
        Assert.assertTrue(TableTagExtraInfo.isJavaId("table"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    public final void testIsJavaIdEnum()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("enum"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    public final void testIsJavaIdSpace()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("invalid x"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    public final void testIsJavaIdEsclamationMark()
    {
        Assert.assertFalse(TableTagExtraInfo.isJavaId("invalid!"));
    }

}