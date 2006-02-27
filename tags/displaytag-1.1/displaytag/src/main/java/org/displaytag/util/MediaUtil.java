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
package org.displaytag.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.MediaTypeEnum;


/**
 * This class provides services for configuring and determining the list of media types an instance of
 * <code>SupportsMedia</code> supports. (Background: ColumnTag, FooterTag and CaptionTag can be configured to support
 * a set of media types. This class factors the logic for setting and determining the media instances these objects
 * support)
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public final class MediaUtil
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(MediaUtil.class);

    /**
     * Don't instantiate MediaUtil.
     */
    private MediaUtil()
    {
    }

    /**
     * Defines a type of object that can support a list of media types.
     * @author Jorge L. Barroso
     * @version $Revision$ ($Author$)
     */
    public static interface SupportsMedia
    {

        /**
         * Configure the list of media types this object will support.
         * @param media The list of media types this object will support.
         */
        void setSupportedMedia(List media);

        /**
         * Obtain the list of media types this object supports.
         * @return The list of media types this object supports.
         */
        List getSupportedMedia();
    }

    /**
     * Configures the media supported by an object that implements <code>SupportsMedia</code>. (Background: factored
     * from ColumnTag)
     * @param mediaSupporter The <code>SupportsMedia</code> instance being configured to support a list of media.
     * @param media The media being configured on the given <code>SupportsMedia</code> instance.
     */
    public static void setMedia(SupportsMedia mediaSupporter, String media)
    {
        if (mediaSupporter == null)
        {
            return;
        }

        if (StringUtils.isBlank(media) || media.toLowerCase().indexOf("all") > -1)
        {
            mediaSupporter.setSupportedMedia(null);
            return;
        }
        List supportedMedia = new ArrayList();
        String[] values = StringUtils.split(media);
        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];
            if (!StringUtils.isBlank(value))
            {
                MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase());
                if (type == null)
                {
                    log.warn("Unrecognized value for attribute \"media\" value=\"" + value + "\"");
                }
                else
                {
                    supportedMedia.add(type);
                }
            }
        }
        mediaSupporter.setSupportedMedia(supportedMedia);
    }

    /**
     * Is this media supporter configured for the media type? (Background: Factored from ColumnTag)
     * @param mediaSupporter An object that supports various media.
     * @param mediaType The currentMedia type
     * @return true if the media supporter should be displayed for this request
     */
    public static boolean availableForMedia(SupportsMedia mediaSupporter, MediaTypeEnum mediaType)
    {
        if (mediaSupporter == null)
        {
            return false;
        }

        List supportedMedia = mediaSupporter.getSupportedMedia();

        if (supportedMedia == null)
        {
            return true;
        }

        return supportedMedia.contains(mediaType);
    }
}
