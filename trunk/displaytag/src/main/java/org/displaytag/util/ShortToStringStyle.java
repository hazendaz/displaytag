package org.displaytag.util;

import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Simple ToStringStyle used in commons-lang toString builders.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class ShortToStringStyle extends ToStringStyle
{

    /**
     * Default stype. Don't add the hashCode and print only the short class name..
     */
    public static final ToStringStyle SHORT_STYLE = new ShortToStringStyle();

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Don't instantiate.
     */
    private ShortToStringStyle()
    {
        super();
        setUseShortClassName(true);
        setUseIdentityHashCode(false);
    }

    /**
     * New istances should not be created during deserialization.
     * @return DEFAULT_STYLE
     */
    protected Object readResolve()
    {
        return DEFAULT_STYLE;
    }

}
