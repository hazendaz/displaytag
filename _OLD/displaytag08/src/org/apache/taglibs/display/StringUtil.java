/**
 * $Id$
 *
 * Status: Under Development
 *
 * Todo
 *   - implementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 **/

package org.apache.taglibs.display;

/**
 * Document me!
 *
 * @version $Revision$
 */
public class StringUtil extends Object {
    /**
     * Replace character at given index with the same character to upper case.
     *
     * @param       oldString old string
     * @param       index of replacement
     * @return      String new string
     * @exception   StringIndexOutOfBoundsException &nbsp;
     */
    public static String toUpperCaseAt(String oldString, int index) {
        int length = oldString.length();
        String newString = "";

        if (index >= length || index < 0) {
            throw new StringIndexOutOfBoundsException(
                    "Index " + index + " is out of bounds for string length " + length);
        }

        // get upper case replacement
        String upper = String.valueOf(oldString.charAt(index)).toUpperCase();

        // avoid index out of bounds
        String paddedString = oldString + " ";

        // get reusable parts
        String beforeIndex = paddedString.substring(0, index);
        String afterIndex = paddedString.substring(index + 1);

        //generate new String - remove padding spaces
        newString = (beforeIndex + upper + afterIndex).substring(0, length);

        return newString;
    }
}