/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.mock.web.portlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.servlet.http.Part;

/**
 * Mock implementation of the {@link javax.portlet.ClientDataRequest} interface.
 *
 * @author Juergen Hoeller
 *
 * @since 3.0
 */
public class MockClientDataRequest extends MockPortletRequest implements ClientDataRequest {

    /** The character encoding. */
    private String characterEncoding;

    /** The content. */
    private byte[] content;

    /** The content type. */
    private String contentType;

    /** The method. */
    private String method;

    /**
     * Create a new MockClientDataRequest with a default {@link MockPortalContext} and a default
     * {@link MockPortletContext}.
     *
     * @see org.springframework.mock.web.portlet.MockPortalContext
     * @see org.springframework.mock.web.portlet.MockPortletContext
     */
    public MockClientDataRequest() {
        super();
    }

    /**
     * Create a new MockClientDataRequest with a default {@link MockPortalContext}.
     *
     * @param portletContext
     *            the PortletContext that the request runs in
     */
    public MockClientDataRequest(PortletContext portletContext) {
        super(portletContext);
    }

    /**
     * Create a new MockClientDataRequest.
     *
     * @param portalContext
     *            the PortalContext that the request runs in
     * @param portletContext
     *            the PortletContext that the request runs in
     */
    public MockClientDataRequest(PortalContext portalContext, PortletContext portletContext) {
        super(portalContext, portletContext);
    }

    /**
     * Sets the content.
     *
     * @param content
     *            the new content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public InputStream getPortletInputStream() throws IOException {
        if (this.content != null) {
            return new ByteArrayInputStream(this.content);
        } else {
            return null;
        }
    }

    @Override
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException {
        if (this.content != null) {
            InputStream sourceStream = new ByteArrayInputStream(this.content);
            Reader sourceReader = (this.characterEncoding != null)
                    ? new InputStreamReader(sourceStream, Charset.forName(this.characterEncoding))
                    : new InputStreamReader(sourceStream, StandardCharsets.UTF_8);
            return new BufferedReader(sourceReader);
        } else {
            return null;
        }
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    /**
     * Sets the content type.
     *
     * @param contentType
     *            the new content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentLength() {
        return (this.content != null ? content.length : -1);
    }

    /**
     * Sets the method.
     *
     * @param method
     *            the new method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public long getContentLengthLong() {
        return content.length;
    }

    @Override
    public Part getPart(String name) throws IOException, PortletException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Part> getParts() throws IOException, PortletException {
        // TODO Auto-generated method stub
        return null;
    }

}
