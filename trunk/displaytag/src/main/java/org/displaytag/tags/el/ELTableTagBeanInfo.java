package org.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;


/**
 * BeanInfo descriptor for the <code>ELTableTag</code> class. Unevaluated EL expression has to be kept separately from
 * the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the same
 * original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELTableTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        ArrayList proplist = new ArrayList();

        try
        {
            // @todo check attributes in ELTableTag and fix others
            proplist.add(new PropertyDescriptor("cellpadding", ELTableTag.class, null, "setCellpadding"));
            proplist.add(new PropertyDescriptor("cellspacing", ELTableTag.class, null, "setCellspacing"));
            proplist.add(new PropertyDescriptor("class", ELTableTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("decorator", ELTableTag.class, null, "setDecorator"));
            proplist.add(new PropertyDescriptor("defaultorder", ELTableTag.class, null, "setDefaultorder"));
            proplist.add(new PropertyDescriptor("defaultsort", ELTableTag.class, null, "setDefaultsort"));
            proplist.add(new PropertyDescriptor("export", ELTableTag.class, null, "setExport"));
            proplist.add(new PropertyDescriptor("frame", ELTableTag.class, null, "setFrame"));
            proplist.add(new PropertyDescriptor("id", ELTableTag.class, null, "setId"));
            proplist.add(new PropertyDescriptor("length", ELTableTag.class, null, "setLength"));
            proplist.add(new PropertyDescriptor("list", ELTableTag.class, null, "setList"));
            proplist.add(new PropertyDescriptor("name", ELTableTag.class, null, "setName"));
            proplist.add(new PropertyDescriptor("offset", ELTableTag.class, null, "setOffset"));
            proplist.add(new PropertyDescriptor("pagesize", ELTableTag.class, null, "setPagesize"));
            proplist.add(new PropertyDescriptor("requestURI", ELTableTag.class, null, "setRequestURI"));
            proplist.add(new PropertyDescriptor("rules", ELTableTag.class, null, "setRules"));
            proplist.add(new PropertyDescriptor("sort", ELTableTag.class, null, "setSort"));
            proplist.add(new PropertyDescriptor("style", ELTableTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("summary", ELTableTag.class, null, "setSummary"));
        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}