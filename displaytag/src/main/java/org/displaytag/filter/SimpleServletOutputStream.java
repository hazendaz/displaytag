package org.displaytag.filter;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;


/**
 * A simple implementation of ServletOutputStream which wraps a ByteArrayOutputStream.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class SimpleServletOutputStream extends ServletOutputStream
{

    /**
     * My outputWriter stream, a buffer.
     */
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * {@inheritDoc}
     */
    public void write(int b)
    {
        this.outputStream.write(b);
    }

    /**
     * Get the contents of the outputStream.
     * @return contents of the outputStream
     */
    public String toString()
    {
        return this.outputStream.toString();
    }

    /**
     * Reset the wrapped ByteArrayOutputStream.
     */
    public void reset()
    {
        outputStream.reset();
    }
}