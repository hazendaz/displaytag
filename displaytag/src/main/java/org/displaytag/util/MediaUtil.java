/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.properties.MediaTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides services for configuring and determining the list of media types an instance of
 * <code>SupportsMedia</code> supports. (Background: ColumnTag, FooterTag and CaptionTag can be configured to support a
 * set of media types. This class factors the logic for setting and determining the media instances these objects
 * support)
 */
public final class MediaUtil {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(MediaUtil.class);

    /**
     * Don't instantiate MediaUtil.
     */
    private MediaUtil() {
    }

    /**
     * Defines a type of object that can support a list of media types.
     */
    public interface SupportsMedia {

        /**
         * Configure the list of media types this object will support.
         *
         * @param media
         *            The list of media types this object will support.
         */
        void setSupportedMedia(List<MediaTypeEnum> media);

        /**
         * Obtain the list of media types this object supports.
         *
         * @return The list of media types this object supports.
         */
        List<MediaTypeEnum> getSupportedMedia();
    }

    /**
     * Configures the media supported by an object that implements <code>SupportsMedia</code>. (Background: factored
     * from ColumnTag)
     *
     * @param mediaSupporter
     *            The <code>SupportsMedia</code> instance being configured to support a list of media.
     * @param media
     *            The media being configured on the given <code>SupportsMedia</code> instance.
     */
    public static void setMedia(final SupportsMedia mediaSupporter, final String media) {
        if (mediaSupporter == null) {
            return;
        }

        if (StringUtils.isBlank(media) || media.toLowerCase(Locale.ENGLISH).indexOf("all") > -1) {
            mediaSupporter.setSupportedMedia(null);
            return;
        }
        final List<MediaTypeEnum> supportedMedia = new ArrayList<>();
        final String[] values = StringUtils.split(media);
        for (final String value : values) {
            if (!StringUtils.isBlank(value)) {
                final MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase(Locale.ENGLISH));
                if (type == null) {
                    MediaUtil.log.warn("Unrecognized value for attribute \"media\" value=\"{}\"", value);
                } else {
                    supportedMedia.add(type);
                }
            }
        }
        mediaSupporter.setSupportedMedia(supportedMedia);
    }

    /**
     * Is this media supporter configured for the media type? (Background: Factored from ColumnTag).
     *
     * @param mediaSupporter
     *            An object that supports various media.
     * @param mediaType
     *            The currentMedia type
     *
     * @return true if the media supporter should be displayed for this request
     */
    public static boolean availableForMedia(final SupportsMedia mediaSupporter, final MediaTypeEnum mediaType) {
        if (mediaSupporter == null) {
            return false;
        }

        final List<MediaTypeEnum> supportedMedia = mediaSupporter.getSupportedMedia();

        if (supportedMedia == null) {
            return true;
        }

        return supportedMedia.contains(mediaType);
    }
}
