package org.apache.taglibs.display;

import java.util.Collection;
import java.util.List;

/**
 * <p>Placeholder class to preserve compatibility with decorator created with older version of the display taglib</p>
 * <p>Never extend this class, you should always subclass decorators in the <code>org.displaytag.decorator</code>
 * package<p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 * @deprecated use <code>org.displaytag.decorator.TableDecorator</code>
 */
public abstract class TableDecorator extends org.displaytag.decorator.TableDecorator
{
    /**
     * returns the Object where the iteration is performed as a List
     * @return List
     * @deprecated use getDecoratedObject()
     */
    public List getList()
    {
        return (List) getDecoratedObject();
    }

    /**
     * returns the Object where the iteration is performed as a Collection
     * @return Collection
     * @deprecated use getDecoratedObject()
     */
    public Collection getCollection()
    {
        return (Collection) getDecoratedObject();
    }

    /**
     * returns the Object for the current row
     * @return Object
     * @deprecated use getCurrentRowObject()
     */
    public Object getObject()
    {
        return getCurrentRowObject();
    }
}
