/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.MissingAttributeException;
import org.displaytag.exception.TagStructureException;


/**
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class SetPropertyTag extends BodyTagSupport
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * property name.
     */
    private String name;

    /**
     * property value.
     */
    private String value;

    /**
     * is this the first iteration?
     */
    private boolean firstIteration;

    /**
     * Sets the name of the property.
     * @param propertyName String
     */
    public void setName(String propertyName)
    {
        this.name = propertyName;
    }

    /**
     * Sets the value of the property.
     * @param propertyValue String
     */
    public void setValue(String propertyValue)
    {
        this.value = propertyValue;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "setProperty", "table"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // read body only once
        if (tableTag.isFirstIteration())
        {
            this.firstIteration = true;
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        this.firstIteration = false;
        return SKIP_BODY;

    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants to
     * change a property value, and we pass the name/value pair that the user gave us, up to the parent
     * </p>
     * @return <code>TagSupport.EVAL_PAGE</code>
     * @throws MissingAttributeException if no value or body content has been set
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws MissingAttributeException
    {

        if (this.firstIteration)
        {
            TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

            // tableTag can't be null, it has been checked in doStartTag
            if (this.value == null)
            {
                if (getBodyContent() == null)
                {
                    throw new MissingAttributeException(getClass(), //
                        new String[]{"value", "body content"}); //$NON-NLS-1$ //$NON-NLS-2$
                }
                this.value = getBodyContent().getString();
            }

            tableTag.setProperty(this.name, this.value);

            this.name = null;
            this.value = null;
        }
        return EVAL_PAGE;
    }

}