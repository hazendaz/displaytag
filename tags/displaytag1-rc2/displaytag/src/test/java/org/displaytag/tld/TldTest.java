package org.displaytag.tld;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;


/**
 * Reads tlds and check tag classes for declared attributes. This simple reports missing/invalid setters in tag classes.
 * Basic tests only, other tests are performed by the maven-taglib plugin.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TldTest extends TestCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TldTest.class);

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Check displaytag 1.2 dtd.
     * @throws Exception any Exception generated during test.
     */
    public void test12Tld() throws Exception
    {
        checkTld("/src/tld/displaytag-12.tld");
    }

    /**
     * Check displaytag EL dtd.
     * @throws Exception any Exception generated during test.
     */
    public void testELTld() throws Exception
    {
        checkTld("/src/tld/displaytag-el-12.tld");
    }

    /**
     * Check the given tld. Assure then:
     * <ul>
     * <li>Any tag class is loadable</li>
     * <li>the tag class has a setter for any of the declared attribute</li>
     * <li>the type declared in the dtd for an attribute (if any) matches the type accepted by the getter</li>
     * </ul>
     * @param checkedTld path for the tld to check, relative to basedir.
     * @throws Exception any Exception generated during test.
     */
    public void checkTld(String checkedTld) throws Exception
    {

        List tagsAttributes = getTagAttributeList(checkedTld);

        List errors = new ArrayList();
        Iterator iterator = tagsAttributes.iterator();
        while (iterator.hasNext())
        {
            TagAttribute attribute = (TagAttribute) iterator.next();

            if (log.isDebugEnabled())
            {
                log.debug("testing " + attribute);
            }
            String className = attribute.getTagClass();
            Class tagClass = null;
            try
            {
                tagClass = Class.forName(className);
            }
            catch (ClassNotFoundException e)
            {
                errors.add("unable to find declared tag class [" + className + "]");
                continue;
            }

            if (!TagSupport.class.isAssignableFrom(tagClass))
            {
                errors.add("Declared class [" + className + "] doesn't extend TagSupport");
                continue;
            }

            // load it
            Object tagObject = null;
            try
            {
                tagObject = tagClass.newInstance();
            }
            catch (Throwable e)
            {
                errors.add("unable to instantiate declared tag class [" + className + "]");
                continue;
            }

            if (!PropertyUtils.isWriteable(tagObject, attribute.getAttributeName()))
            {
                errors.add("Setter for attribute [" + attribute.getAttributeName() + "] not found in " + className);
                continue;
            }

            Class propertyType = PropertyUtils.getPropertyType(tagObject, attribute.getAttributeName());

            String tldType = attribute.getAttributeType();
            if (tldType != null)
            {
                Class tldTypeClass = getClassFromName(tldType);

                if (!propertyType.isAssignableFrom(tldTypeClass))
                {
                    errors.add("Tag attribute ["
                        + attribute.getAttributeName()
                        + "] declared in tld as ["
                        + tldType
                        + "], class declare ["
                        + propertyType.getName()
                        + "]");
                    continue;
                }

            }

        }

        if (errors.size() > 0)
        {
            if (log.isInfoEnabled())
            {
                log.info(errors.size() + " errors found in tag classes: " + errors);
            }
            fail(errors.size() + " errors found in tag classes: " + errors);
        }
    }

    /**
     * returns a class from its name, handling primitives.
     * @param className clss name
     * @return Class istantiated using Class.forName or the matching primitive.
     */
    private Class getClassFromName(String className)
    {

        Class tldTypeClass = null;

        if ("int".equals(className))
        {
            tldTypeClass = int.class;
        }
        else if ("long".equals(className))
        {
            tldTypeClass = long.class;
        }
        else if ("double".equals(className))
        {
            tldTypeClass = double.class;
        }
        else if ("boolean".equals(className))
        {
            tldTypeClass = boolean.class;
        }
        else if ("char".equals(className))
        {
            tldTypeClass = char.class;
        }
        else if ("byte".equals(className))
        {
            tldTypeClass = byte.class;
        }

        if (tldTypeClass == null)
        {
            // not a primitive type
            try
            {
                tldTypeClass = Class.forName(className);
            }
            catch (ClassNotFoundException e)
            {
                fail("unable to find class [" + className + "] declared in 'type' attribute");
            }
        }
        return tldTypeClass;
    }

    /**
     * Extract a list of attributes from tld.
     * @param checkedTld path for the checked tld, relative to basedir.
     * @return List of TagAttribute
     * @throws Exception any Exception thrown during test
     */
    private List getTagAttributeList(String checkedTld) throws Exception
    {

        URL classDir = TldTest.class.getResource("TldTest.class");
        String tldPath = classDir.getPath();

        String baseWebappDir = tldPath.substring(0, tldPath.indexOf("target") - 1);
        tldPath = baseWebappDir + checkedTld;
        log.debug("tld found: " + tldPath);

        File tldFile = new File(tldPath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        builder.setEntityResolver(new ClasspathEntityResolver());
        Document webXmlDoc = builder.parse(tldFile);

        NodeList tagList = webXmlDoc.getElementsByTagName("tag");
        List tagsAttributes = new ArrayList();

        for (int i = 0; i < tagList.getLength(); i++)
        {
            Node tag = tagList.item(i);

            // String tagclass = tag.getAttributes().getNamedItem("tag-class").getNodeValue();

            NodeList tagAttributes = tag.getChildNodes();

            String tagclass = null;
            for (int k = 0; k < tagAttributes.getLength(); k++)
            {
                Node tagAttribute = tagAttributes.item(k);

                // only handle 1.0 tlds
                if ("tag-class".equals(tagAttribute.getNodeName()))
                {
                    tagclass = tagAttribute.getChildNodes().item(0).getNodeValue();
                    break;
                }

            }

            tagAttributes = tag.getChildNodes();
            for (int k = 0; k < tagAttributes.getLength(); k++)
            {
                Node tagAttribute = tagAttributes.item(k);
                if ("attribute".equals(tagAttribute.getNodeName()))
                {
                    NodeList initParams = tagAttribute.getChildNodes();
                    String attributeName = null;
                    String attributeType = null;
                    for (int z = 0; z < initParams.getLength(); z++)
                    {
                        Node initParam = initParams.item(z);
                        if (initParam.getNodeType() != Node.TEXT_NODE && initParam.hasChildNodes())
                        {
                            if (initParam.getNodeName().equals("name"))
                            {
                                attributeName = initParam.getFirstChild().getNodeValue();
                            }
                            else if (initParam.getNodeName().equals("type"))
                            {
                                attributeType = initParam.getFirstChild().getNodeValue();
                            }
                        }
                    }
                    TagAttribute attribute = new TagAttribute();
                    attribute.setTagClass(tagclass);
                    attribute.setAttributeName(attributeName);
                    attribute.setAttributeType(attributeType);
                    tagsAttributes.add(attribute);
                    if (log.isDebugEnabled())
                    {
                        log.debug(attribute);
                    }
                }
            }
        }

        return tagsAttributes;
    }

    /**
     * Simple Entity resolver which looks in the classpath for dtds.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    public static class ClasspathEntityResolver implements EntityResolver
    {

        /**
         * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
         */
        public InputSource resolveEntity(String publicID, String systemID)
        {
            if (systemID != null)
            {
                String systemFileName = systemID;

                if (systemFileName.indexOf("/") > 0)
                {
                    systemFileName = systemFileName.substring(systemFileName.lastIndexOf("/") + 1, systemFileName
                        .length());
                }

                ClassLoader classLoader = getClass().getClassLoader();

                URL dtdURL = classLoader.getResource("javax/servlet/jsp/resources/" + systemFileName);

                if (dtdURL == null)
                {
                    return null;
                }

                // Return local copy of the dtd
                try
                {
                    return new InputSource(dtdURL.openStream());
                }
                catch (IOException e)
                {
                    // return null
                }
            }

            // If no match, returning null makes process continue normally
            return null;
        }
    }

    /**
     * Javabean representing a tag attribute.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    public static class TagAttribute
    {

        /**
         * Tag class.
         */
        private String tagClass;

        /**
         * Attribute name.
         */
        private String attributeName;

        /**
         * Atttribute type (can be null).
         */
        private String attributeType;

        /**
         * @return Returns the attribute name.
         */
        public String getAttributeName()
        {
            return this.attributeName;
        }

        /**
         * @param name attribute name.
         */
        public void setAttributeName(String name)
        {
            this.attributeName = name;
        }

        /**
         * @return Returns the attributeType.
         */
        public String getAttributeType()
        {
            return this.attributeType;
        }

        /**
         * @param type The attributeType to set.
         */
        public void setAttributeType(String type)
        {
            this.attributeType = type;
        }

        /**
         * @return Returns the tagClass.
         */
        public String getTagClass()
        {
            return this.tagClass;
        }

        /**
         * @param tagClassName name of the tag class
         */
        public void setTagClass(String tagClassName)
        {
            this.tagClass = tagClassName;
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("tagClass", this.tagClass).append(
                "attributeName",
                this.attributeName).append("attributeType", this.attributeType).toString();
        }
    }
}