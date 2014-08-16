/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.portlet;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.displaytag.util.Href;


/**
 * Implementation of the Href interface that generates URLs using the javax.portlet APIs. As the portlet API supports
 * the concept of WindowStates, PorletModes, secure URLs and actions versus render the implementation supports these
 * concepts as well through the standard {@link Href} APIs. <br>
 * <br>
 * The features are manipulated using special parameter names and values:
 * <table>
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

    // Portlet request and response are needed for feature checking and generating the URLs
    private final PortletRequest portletRequest;

    private final RenderResponse renderResponse;

    private Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();

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
    @Override
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
    @Override
    public Href addParameter(String name, int value)
    {
        return this.addParameter(name, Integer.toString(value));
    }

    /**
     * @see org.displaytag.util.Href#addParameter(String, Object)
     */
    @Override
    public Href addParameter(String name, Object objValue)
    {
        @SuppressWarnings("deprecation")
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
            this.parameters.put(name, new String[]{value});
        }

        return this;
    }

    /**
     * @see org.displaytag.util.Href#addParameterMap(java.util.Map)
     */
    @Override
    public void addParameterMap(Map<String, String[]> parametersMap)
    {
        for (final Iterator<Entry<String, String[]>> paramItr = parametersMap.entrySet().iterator(); paramItr.hasNext();)
        {
            final Entry<String, String[]> entry = paramItr.next();

            final String name = entry.getKey();

            // Allow multivalued parameters since code elsewhere calls this method to copy
            // parameters from the request to the response. Ensures that developer specified
            // multivalued parameters are retained correctly.

            if (entry.getValue() == null)
            {
                this.addParameter(name, entry.getValue());
            }
            else if (entry.getValue().length == 1)
            {
                // addParameter does some special processing of portlet paramters
                this.addParameter(name, entry.getValue()[0]);
            }
            else if (entry.getValue().getClass().isArray())
            {
                this.parameters.put(name, entry.getValue());
            }
        }
    }

    /**
     * @see org.displaytag.util.Href#setParameterMap(java.util.Map)
     */
    @Override
    public void setParameterMap(Map<String, String[]> parametersMap)
    {
        this.parameters.clear();
        this.addParameterMap(parametersMap);
    }

    /**
     * Warning, parameters added to the Map directly will not be parsed by the PortletUrl feature support portions of
     * this class.
     * @see org.displaytag.util.Href#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap()
    {
        return this.parameters;
    }

    /**
     * @see org.displaytag.util.Href#removeParameter(java.lang.String)
     */
    @Override
    public void removeParameter(String name)
    {
        this.parameters.remove(name);
    }

    /**
     * @see org.displaytag.util.Href#setAnchor(java.lang.String)
     */
    @Override
    public void setAnchor(String name)
    {
        this.anchor = name;
    }

    /**
     * @see org.displaytag.util.Href#getAnchor()
     */
    @Override
    public String getAnchor()
    {
        return this.anchor;
    }

    /**
     * Generates a render or action URL depending on the use of the PortletUrl specific features of this class.
     * @see org.displaytag.util.Href#getBaseUrl()
     */
    @Override
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
    @Override
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
        href.parameters = new LinkedHashMap<String, String[]>();
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
    @Override
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
        return new EqualsBuilder()
            .append(this.isAction, rhs.isAction)
            .append(this.parameters, rhs.parameters)
            .append(this.requestedMode, rhs.requestedMode)
            .append(this.requestedState, rhs.requestedState)
            .append(this.requestedSecure, rhs.requestedSecure)
            .append(this.anchor, rhs.anchor)
            .isEquals();
    }

    /**
     * @see org.displaytag.util.Href#hashCode()
     */
    @Override
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
    @Override
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

        for (final Iterator<Entry<String, String[]>> paramItr = this.parameters.entrySet().iterator(); paramItr
            .hasNext();)
        {
            final Entry<String, String[]> entry = paramItr.next();

            final String name = entry.getKey();
            final String[] value = entry.getValue();

            url.setParameter(name, value);
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

}
