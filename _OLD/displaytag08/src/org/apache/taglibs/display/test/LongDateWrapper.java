/**
 * $Id$
 *
 * Status: Under Development
 **/

package org.apache.taglibs.display.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.taglibs.display.ColumnDecorator;


public class LongDateWrapper extends ColumnDecorator {
    private DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public String decorate(Object columnValue) {
        Date t = (Date) columnValue;
        return sdf.format(t);
    }
}
