package org.displaytag.tags;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;


/**
 * Beaninfo class for tableTag. Needed to make the "class" tag attribute working and to handle the swith between
 * setName() and setNameString() setters for the name attribute.
 * @author Fabrizio Giustina
 * @see org.displaytag.util.BeanInfoUtil
 * @version $Revision$ ($Author$)
 */
public class TableTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List proplist = new ArrayList();

        try
        {
            proplist.add(new PropertyDescriptor("cellpadding", TableTag.class, null, "setCellpadding"));
            proplist.add(new PropertyDescriptor("cellspacing", TableTag.class, null, "setCellspacing"));
            proplist.add(new PropertyDescriptor("class", TableTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("decorator", TableTag.class, null, "setDecorator"));
            proplist.add(new PropertyDescriptor("defaultorder", TableTag.class, null, "setDefaultorder"));
            proplist.add(new PropertyDescriptor("defaultsort", TableTag.class, null, "setDefaultsort"));
            proplist.add(new PropertyDescriptor("export", TableTag.class, null, "setExport"));
            proplist.add(new PropertyDescriptor("frame", TableTag.class, null, "setFrame"));
            proplist.add(new PropertyDescriptor("length", TableTag.class, null, "setLength"));
            proplist.add(new PropertyDescriptor("offset", TableTag.class, null, "setOffset"));
            proplist.add(new PropertyDescriptor("pagesize", TableTag.class, null, "setPagesize"));
            proplist.add(new PropertyDescriptor("requestURI", TableTag.class, null, "setRequestURI"));
            proplist.add(new PropertyDescriptor("rules", TableTag.class, null, "setRules"));
            proplist.add(new PropertyDescriptor("sort", TableTag.class, null, "setSort"));
            proplist.add(new PropertyDescriptor("style", TableTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("summary", TableTag.class, null, "setSummary"));

            // can't be evaluated
            proplist.add(new PropertyDescriptor("id", TableTag.class, null, "setId"));

            // deprecated attributes
            proplist.add(new PropertyDescriptor("list", TableTag.class, null, "setList"));
            proplist.add(new PropertyDescriptor("property", TableTag.class, null, "setProperty"));
            proplist.add(new PropertyDescriptor("scope", TableTag.class, null, "setScope"));

            proplist.add(new PropertyDescriptor("width", TableTag.class, null, "setWidth"));
            proplist.add(new PropertyDescriptor("styleClass", TableTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("border", TableTag.class, null, "setBorder"));
            proplist.add(new PropertyDescriptor("align", TableTag.class, null, "setAlign"));
            proplist.add(new PropertyDescriptor("background", TableTag.class, null, "setBackground"));
            proplist.add(new PropertyDescriptor("bgcolor", TableTag.class, null, "setBgcolor"));
            proplist.add(new PropertyDescriptor("height", TableTag.class, null, "setHeight"));
            proplist.add(new PropertyDescriptor("hspace", TableTag.class, null, "setHspace"));
            proplist.add(new PropertyDescriptor("vspace", TableTag.class, null, "setVspace"));

            try
            {
                Class.forName("javax.servlet.jsp.tagext.IterationTag");
                // jsp >= 1.2
                proplist.add(new PropertyDescriptor("name", TableTag.class, null, "setName"));
            }
            catch (Throwable e)
            {
                // jsp 1.1, can't use a setter with an Object parameter
                proplist.add(new PropertyDescriptor("name", TableTag.class, null, "setNameString"));
            }

        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}