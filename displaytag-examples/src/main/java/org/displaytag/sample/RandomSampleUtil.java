/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.sample;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Utility class used to get random word and sentences used in examples.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class RandomSampleUtil {

    /**
     * list of words.
     */
    private static String[] words = new String[] { "Lorem", //$NON-NLS-1$
            "ipsum", //$NON-NLS-1$
            "dolor", //$NON-NLS-1$
            "sit", //$NON-NLS-1$
            "amet", //$NON-NLS-1$
            "consetetur", //$NON-NLS-1$
            "sadipscing", //$NON-NLS-1$
            "elitr", //$NON-NLS-1$
            "sed", //$NON-NLS-1$
            "diam", //$NON-NLS-1$
            "nonumy", //$NON-NLS-1$
            "eirmod", //$NON-NLS-1$
            "tempor", //$NON-NLS-1$
            "invidunt", //$NON-NLS-1$
            "ut", //$NON-NLS-1$
            "labore", //$NON-NLS-1$
            "et", //$NON-NLS-1$
            "dolore", //$NON-NLS-1$
            "magna", //$NON-NLS-1$
            "aliquyam", //$NON-NLS-1$
            "erat", //$NON-NLS-1$
            "sed", //$NON-NLS-1$
            "diam", //$NON-NLS-1$
            "voluptua", //$NON-NLS-1$
            "At", //$NON-NLS-1$
            "vero", //$NON-NLS-1$
            "eos", //$NON-NLS-1$
            "et", //$NON-NLS-1$
            "accusam", //$NON-NLS-1$
            "et", //$NON-NLS-1$
            "justo", //$NON-NLS-1$
            "duo", //$NON-NLS-1$
            "dolores", //$NON-NLS-1$
            "et", //$NON-NLS-1$
            "ea", //$NON-NLS-1$
            "rebum", //$NON-NLS-1$
            "Stet", //$NON-NLS-1$
            "clita", //$NON-NLS-1$
            "kasd", //$NON-NLS-1$
            "gubergren", //$NON-NLS-1$
            "no", //$NON-NLS-1$
            "sea", //$NON-NLS-1$
            "takimata", //$NON-NLS-1$
            "sanctus", //$NON-NLS-1$
            "est" }; //$NON-NLS-1$

    /**
     * random number producer.
     */
    private static Random random = new Random();

    /**
     * utility class, don't instantiate.
     */
    private RandomSampleUtil() {
        super();
    }

    /**
     * returns a random word.
     *
     * @return random word
     */
    public static String getRandomWord() {
        return words[random.nextInt(words.length)];
    }

    /**
     * returns a random sentence.
     *
     * @param wordNumber
     *            number of word in the sentence
     *
     * @return random sentence made of <code>wordNumber</code> words
     */
    public static String getRandomSentence(int wordNumber) {
        StringBuilder buffer = new StringBuilder(wordNumber * 12);

        int j = 0;
        while (j < wordNumber) {
            buffer.append(getRandomWord());
            buffer.append(" "); //$NON-NLS-1$
            j++;
        }
        return buffer.toString();
    }

    /**
     * returns a random email.
     *
     * @return random email
     */
    public static String getRandomEmail() {
        return getRandomWord() + "@" + getRandomWord() + ".com"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * returns a random date.
     *
     * @return random date
     */
    public static Date getRandomDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 365 - random.nextInt(730));
        return calendar.getTime();
    }
}
