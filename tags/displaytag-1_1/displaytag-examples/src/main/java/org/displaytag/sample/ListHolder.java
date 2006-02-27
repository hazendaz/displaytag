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
package org.displaytag.sample;

import java.util.List;


/**
 * Simple objects which holds a list.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ListHolder extends Object
{

    /**
     * contained list.
     */
    private List list;

    /**
     * Instantiate a new ListHolder and initialize a TestList with 5 elements.
     */
    public ListHolder()
    {
        this.list = new TestList(15, false);
    }

    /**
     * Returns the contained list.
     * @return a TestList with 15 elements
     */
    public final List getList()
    {
        return this.list;
    }
}
