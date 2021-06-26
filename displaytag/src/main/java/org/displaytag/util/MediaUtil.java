/*
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
package org.displaytag.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.properties.MediaTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private static Logger log = LoggerFactory.getLogger(MediaUtil.class);

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
        void setSupportedMedia(List<MediaTypeEnum> media);

        /**
         * Obtain the list of media types this object supports.
         * @return The list of media types this object supports.
         */
        List<MediaTypeEnum> getSupportedMedia();
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
        List<MediaTypeEnum> supportedMedia = new ArrayList<>();
        String[] values = StringUtils.split(media);
        for (String value : values) {
            if (!StringUtils.isBlank(value))
            {
                MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase());
                if (type == null)
                {
                    log.warn("Unrecognized value for attribute \"media\" value=\"{}\"", value);
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
     * Is this media supporter configured for the media type? (Background: Factored from ColumnTag).
     *
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

        List<MediaTypeEnum> supportedMedia = mediaSupporter.getSupportedMedia();

        if (supportedMedia == null)
        {
            return true;
        }

        return supportedMedia.contains(mediaType);
    }
}
