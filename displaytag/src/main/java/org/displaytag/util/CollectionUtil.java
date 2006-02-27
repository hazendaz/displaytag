package org.displaytag.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;

/**
 * <p>Utility methods for collection handling</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class CollectionUtil
{

    /**
     * Don't instantiate a CollectionUtil
     */
    private CollectionUtil()
    {
    }

    /**
     * Create a list of objects taken from the given iterator and crop the resulting list according to the pStartNumber
     * and pNumberOfItems parameters
     * @param pIterator Iterator
     * @param pStartNumber int starting index
     * @param pNumberOfItems int number of items to keep in the list
     * @return List with values taken from the given object, cropped according the pStartNumber and pNumberOfItems
     * parameters
     */
    private static List getSubList(Iterator pIterator, int pStartNumber, int pNumberOfItems)
    {

        ArrayList lCroppedList = new ArrayList(pNumberOfItems);

        int lSkippedRecordCount = 0;
        int lCopiedRecordCount = 0;
        while (pIterator.hasNext())
        {

            Object lObject = pIterator.next();

            if (++lSkippedRecordCount <= pStartNumber)
            {
                continue;
            }

            lCroppedList.add(lObject);

            if ((pNumberOfItems != 0) && (++lCopiedRecordCount >= pNumberOfItems))
            {
                break;
            }

        }

        return lCroppedList;

    }

    /**
     * create an iterator on a given object (Collection, Enumeration, array, single Object) and crop the resulting
     * list according to the pStartNumber and pNumberOfItems parameters
     * @param pFullList Collection, Enumeration or array to crop
     * @param pStartNumber int starting index
     * @param pNumberOfItems int number of items to keep in the list
     * @return List with values taken from the given object, cropped according the pStartNumber and pNumberOfItems
     * parameters
     */
    public static List getListFromObject(Object pFullList, int pStartNumber, int pNumberOfItems)
    {
        Iterator lIterator = IteratorUtils.getIterator(pFullList);
        return getSubList(lIterator, pStartNumber, pNumberOfItems);
    }

}