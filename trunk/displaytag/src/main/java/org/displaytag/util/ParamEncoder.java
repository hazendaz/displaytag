package org.displaytag.util;


/**
 * Simple utility class for encoding parameter names.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ParamEncoder
{

    /**
     * Unique identifier for a tag with the given id/name.
     */
    private String parameterIdentifier;

    /**
     * @param idAttribute value of "id" attribute
     * @param nameAttribute value of "name" attribute
     */
    public ParamEncoder(String idAttribute, String nameAttribute)
    {
        // use name and id to get the unique identifier
        String stringIdentifier = "x-" + idAttribute + nameAttribute;

        // get the array
        char[] charArray = stringIdentifier.toCharArray();

        // calculate a simple checksum-like value
        int checkSum = 0;

        for (int j = 0; j < charArray.length; j++)
        {
            checkSum += charArray[j] * j;
        }

        // this is the full identifier used for all the parameters
        this.parameterIdentifier = "d-" + checkSum + "-";
    }

    /**
     * encode a parameter name prepending calculated <code>parameterIdentifier</code>.
     * @param paramName parameter name
     * @return encoded parameter name in the form <code>d-<em>XXXX</em>-<em>name</em></code>
     */
    public String encodeParameterName(String paramName)
    {
        return this.parameterIdentifier + paramName;
    }

}
