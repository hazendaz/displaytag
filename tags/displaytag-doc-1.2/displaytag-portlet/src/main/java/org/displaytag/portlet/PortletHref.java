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
package org.displaytag.portlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AnyPredicate;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.collections.functors.NullPredicate;
import org.apache.commons.collections.map.PredicatedMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.displaytag.util.Href;


/**
 * Implementation of the Href interface that generates URLs using the javax.portlet APIs. As the portlet API supports
 * the concept of WindowStates, PorletModes, secure URLs and actions versus render the implementation supports these
 * concepts as well through the standard {@link Href} APIs. <br>
 * <br>
 * The features are manipulated using special parameter names and values: <table>
 * <tr>
 * <th>Feature</th>
 * <th>Parameter Name</th>
 * <th>Parameter Value</th>
 * </tr>
 * <tr>
 * <td>Render vs Action URL</td>
 * <td>{@link #PARAM_TYPE} (portlet:type)</td>
 * <td>"render" for RenderURLs, "action" for ActionURLs</td>
 * </tr>
 * <tr>
 * <td>WindowState</td>
 * <td>{@link #PARAM_STATE} (portlet:state)</td>
 * <td>The value is used directly for the WindowState name</td>
 * </tr>
 * <tr>
 * <td>PorltetMode</td>
 * <td>{@link #PARAM_MODE} (portlet:mode)</td>
 * <td>The value is used directly for the PortletMode name</td>
 * </tr>
 * <tr>
 * <td>Secure URL</td>
 * <td>{@link #PARAM_SECURE} (portlet:secure)</td>
 * <td>"true" requests a secure URL, anything else requests a standard URL</td>
 * </tr>
 * </table>
 * @author Eric Dalquist <a href="mailto:dalquist@gmail.com">dalquist@gmail.com</a>
 * @version $Id$
 */
public class PortletHref implements Href
{

    // Constants for working with the special parameters
    private static final String PARAM_PREFIX = "portlet:";

    public static final String PARAM_MODE = PARAM_PREFIX + "mode";

    public static final String PARAM_STATE = PARAM_PREFIX + "state";

    public static final String PARAM_SECURE = PARAM_PREFIX + "secure";

    public static final String PARAM_TYPE = PARAM_PREFIX + "type";

    public static final String TYPE_RENDER = "render";

    public static final String TYPE_ACTION = "action";

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    // Predicated for type checking the parameter map
    private static final Predicate PRED_TYPE_OF_STRING = new InstanceofPredicate(String.class);

    private static final Predicate PRED_TYPE_OF_STRING_ARRY = new InstanceofPredicate(String[].class);

    private static final Predicate PRED_OR_STR_STRARR = new AnyPredicate(new Predicate[]{
        PRED_TYPE_OF_STRING,
        PRED_TYPE_OF_STRING_ARRY,
        NullPredicate.INSTANCE});

    // Portlet request and response are needed for feature checking and generating the URLs
    private final PortletRequest portletRequest;

    private final RenderResponse renderResponse;

    private Map parameters = this.createParameterMap();

    private boolean isAction;

    private PortletMode requestedMode;

    private WindowState requestedState;

    private boolean requestedSecure;

    private String anchor;

    /**
     * Creates a new PortletHref. The actual PortletURL object is not generated until the toString method is called.
     * @param portletRequest request to to feature checking with, may not be null.
     * @param renderResponse response to generate the URLs from, may not be null.
     */
    public PortletHref(PortletRequest portletRequest, RenderResponse renderResponse)
    {
        if (portletRequest == null)
        {
            throw new IllegalArgumentException("portletRequest may not be null");
        }
        if (renderResponse == null)
        {
            throw new IllegalArgumentException("renderResponse may not be null");
        }

        this.portletRequest = portletRequest;
        this.renderResponse = renderResponse;
    }

    /**
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    public void setFullUrl(String baseUrl)
    {
        // do nothing
    }

    /**
     * @return Returns the isAction.
     */
    public boolean isAction()
    {
        return this.isAction;
    }

    /**
     * @param isAction The isAction to set.
     */
    public void setAction(boolean isAction)
    {
        this.isAction = isAction;
    }

    /**
     * @return Returns the requestedMode.
     */
    public PortletMode getRequestedMode()
    {
        return this.requestedMode;
    }

    /**
     * @param requestedMode The requestedMode to set.
     */
    public void setRequestedMode(PortletMode requestedMode)
    {
        this.requestedMode = requestedMode;
    }

    /**
     * @return Returns the requestedSecure.
     */
    public boolean isRequestedSecure()
    {
        return this.requestedSecure;
    }

    /**
     * @param requestedSecure The requestedSecure to set.
     */
    public void setRequestedSecure(boolean requestedSecure)
    {
        this.requestedSecure = requestedSecure;
    }

    /**
     * @return Returns the requestedState.
     */
    public WindowState getRequestedState()
    {
        return this.requestedState;
    }

    /**
     * @param requestedState The requestedState to set.
     */
    public void setRequestedState(WindowState requestedState)
    {
        this.requestedState = requestedState;
    }

    /**
     * @see org.displaytag.util.Href#addParameter(java.lang.String, int)
     */
    public Href addParameter(String name, int value)
    {
        return this.addParameter(name, Integer.toString(value));
    }

    /**
     * @see org.displaytag.util.Href#addParameter(String, Object)
     */
    public Href addParameter(String name, Object objValue)
    {
        String value = ObjectUtils.toString(objValue, null);

        if (name != null && name.startsWith(PARAM_PREFIX))
        {
            if (PARAM_TYPE.equals(name))
            {
                if (TYPE_RENDER.equals(value))
                {
                    this.setAction(false);
                }
                else if (TYPE_ACTION.equals(value))
                {
                    this.setAction(true);
                }
                else
                {
                    throw new IllegalArgumentException("Value of parameter '"
                        + name
                        + "' must be equal to '"
                        + TYPE_RENDER
                        + "' or '"
                        + TYPE_ACTION
                        + "'. '"
                        + value
                        + "' is not allowed.");
                }
            }
            else if (PARAM_SECURE.equals(name))
            {
                if (new Boolean(value).booleanValue())
                {
                    this.setRequestedSecure(true);
                }
                else
                {
                    this.setRequestedSecure(false);
                }
            }
            else if (PARAM_MODE.equals(name))
            {
                if (value == null)
                {
                    this.setRequestedMode(null);
                }
                else
                {
                    final PortletMode mode = new PortletMode(value);

                    if (!this.portletRequest.isPortletModeAllowed(mode))
                    {
                        throw new IllegalArgumentException("PortletMode '"
                            + mode
                            + "' is not allowed for this request.");
                    }

                    this.setRequestedMode(mode);
                }
            }
            else if (PARAM_STATE.equals(name))
            {
                if (value == null)
                {
                    this.setRequestedState(null);
                }
                else
                {
                    final WindowState state = new WindowState(value);

                    if (!this.portletRequest.isWindowStateAllowed(state))
                    {
                        throw new IllegalArgumentException("WindowState '"
                            + state
                            + "' is not allowed for this request.");
                    }

                    this.setRequestedState(state);
                }
            }
            else
            {
                throw new IllegalArgumentException("'"
                    + name
                    + "' is not a valid '"
                    + PARAM_PREFIX
                    + "' prefixed parameter.");
            }
        }
        else
        {
            this.parameters.put(name, value);
        }

        return this;
    }

    /**
     * @see org.displaytag.util.Href#addParameterMap(java.util.Map)
     */
    public void addParameterMap(Map parametersMap)
    {
        for (final Iterator paramItr = parametersMap.entrySet().iterator(); paramItr.hasNext();)
        {
            final Map.Entry entry = (Map.Entry) paramItr.next();

            final String name = (String) entry.getKey();
            final Object value = entry.getValue();

            // Allow multivalued parameters since code elsewhere calls this method to copy
            // parameters from the request to the response. Ensures that developer specified
            // multivalued parameters are retained correctly.
            if (value instanceof String[])
            {
                this.parameters.put(name, value);
            }
            else if (value == null || value instanceof String)
            {
                this.addParameter(name, value);
            }
            else
            {
                this.addParameter(name, value.toString());
            }
        }
    }

    /**
     * @see org.displaytag.util.Href#setParameterMap(java.util.Map)
     */
    public void setParameterMap(Map parametersMap)
    {
        this.parameters.clear();
        this.addParameterMap(parametersMap);
    }

    /**
     * Warning, parameters added to the Map directly will not be parsed by the PortletUrl feature support portions of
     * this class.
     * @see org.displaytag.util.Href#getParameterMap()
     */
    public Map getParameterMap()
    {
        return this.parameters;
    }

    /**
     * @see org.displaytag.util.Href#removeParameter(java.lang.String)
     */
    public void removeParameter(String name)
    {
        this.parameters.remove(name);
    }

    /**
     * @see org.displaytag.util.Href#setAnchor(java.lang.String)
     */
    public void setAnchor(String name)
    {
        this.anchor = name;
    }

    /**
     * @see org.displaytag.util.Href#getAnchor()
     */
    public String getAnchor()
    {
        return this.anchor;
    }

    /**
     * Generates a render or action URL depending on the use of the PortletUrl specific features of this class.
     * @see org.displaytag.util.Href#getBaseUrl()
     */
    public String getBaseUrl()
    {
        if (this.isAction())
        {
            return this.renderResponse.createActionURL().toString();
        }
        else
        {
            return this.renderResponse.createRenderURL().toString();
        }
    }

    /**
     * @see org.displaytag.util.Href#clone()
     */
    public Object clone()
    {
        PortletHref href;

        try
        {
            href = (PortletHref) super.clone();
        }
        catch (CloneNotSupportedException cnse)
        {
            throw new RuntimeException("Parent through a CloneNotSupportedException, this should never happen", cnse);
        }

        href.isAction = this.isAction;
        href.parameters = this.createParameterMap();
        href.parameters.putAll(this.parameters);
        href.requestedMode = this.requestedMode;
        href.requestedState = this.requestedState;
        href.requestedSecure = this.requestedSecure;
        href.anchor = this.anchor;

        return href;
    }

    /**
     * @see org.displaytag.util.Href#equals(java.lang.Object)
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof PortletHref))
        {
            return false;
        }
        PortletHref rhs = (PortletHref) object;
        return new EqualsBuilder().append(this.isAction, rhs.isAction).append(this.parameters, rhs.parameters).append(
            this.requestedMode,
            rhs.requestedMode).append(this.requestedState, rhs.requestedState).append(
            this.requestedSecure,
            rhs.requestedSecure).append(this.anchor, rhs.anchor).isEquals();
    }

    /**
     * @see org.displaytag.util.Href#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(1313733113, -431360889)
            .append(this.isAction)
            .append(this.parameters)
            .append(this.requestedMode)
            .append(this.requestedState)
            .append(this.requestedSecure)
            .append(this.anchor)
            .toHashCode();
    }

    /**
     * @see org.displaytag.util.Href#toString()
     */
    public String toString()
    {
        final PortletURL url;
        if (this.isAction())
        {
            url = this.renderResponse.createActionURL();
        }
        else
        {
            url = this.renderResponse.createRenderURL();
        }

        if (this.isRequestedSecure())
        {
            try
            {
                url.setSecure(true);
            }
            catch (PortletSecurityException pse)
            {
                throw new RuntimeException("Creating secure PortletURL Failed.", pse);
            }
        }

        if (this.getRequestedMode() != null)
        {
            try
            {
                url.setPortletMode(this.getRequestedMode());
            }
            catch (PortletModeException pme)
            {
                final IllegalStateException ise = new IllegalStateException("Requested PortletMode='"
                    + this.getRequestedMode()
                    + "' could not be set.");
                ise.initCause(pme);
                throw ise;
            }
        }

        if (this.getRequestedState() != null)
        {
            try
            {
                url.setWindowState(this.getRequestedState());
            }
            catch (WindowStateException wse)
            {
                final IllegalStateException ise = new IllegalStateException("Requested WindowState='"
                    + this.getRequestedState()
                    + "' could not be set.");
                ise.initCause(wse);
                throw ise;
            }
        }

        for (final Iterator paramItr = this.parameters.entrySet().iterator(); paramItr.hasNext();)
        {
            final Map.Entry entry = (Map.Entry) paramItr.next();

            final String name = (String) entry.getKey();
            final Object value = entry.getValue();

            if (value instanceof String)
            {
                url.setParameter(name, (String) value);
            }
            else if (value instanceof String[])
            {
                url.setParameter(name, (String[]) value);
            }
        }

        if (this.getAnchor() == null)
        {
            return url.toString();
        }
        else
        {
            return url.toString() + "#" + this.getAnchor();
        }
    }

    private Map createParameterMap()
    {
        return PredicatedMap.decorate(new HashMap(), PRED_TYPE_OF_STRING, PRED_OR_STR_STRARR);
    }
}
