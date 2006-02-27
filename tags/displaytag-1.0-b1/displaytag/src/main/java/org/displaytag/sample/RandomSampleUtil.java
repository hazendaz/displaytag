package org.displaytag.sample;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Utility class used to get random word and sentences used in examples
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class RandomSampleUtil
{

    /**
     * utility class, don't instantiate
     */
    private RandomSampleUtil()
    {
        super();
    }

    /**
     * list of words
     */
    private static String[] words =
        new String[] {
            "Lorem",
            "ipsum",
            "dolor",
            "sit",
            "amet",
            "consetetur",
            "sadipscing",
            "elitr",
            "sed",
            "diam",
            "nonumy",
            "eirmod",
            "tempor",
            "invidunt",
            "ut",
            "labore",
            "et",
            "dolore",
            "magna",
            "aliquyam",
            "erat",
            "sed",
            "diam",
            "voluptua",
            "At",
            "vero",
            "eos",
            "et",
            "accusam",
            "et",
            "justo",
            "duo",
            "dolores",
            "et",
            "ea",
            "rebum",
            "Stet",
            "clita",
            "kasd",
            "gubergren",
            "no",
            "sea",
            "takimata",
            "sanctus",
            "est" };

    /**
     * random number producer
     */
    private static Random random = new Random();

    /**
     * returns a random word
     * @return random word
     */
    public static String getRandomWord()
    {
        return words[random.nextInt(words.length)];
    }

    /**
     * returns a random sentence
     * @param wordNumber number of word in the sentence
     * @return random sentence made of <code>wordNumber</code> words
     */
    public static String getRandomSentence(int wordNumber)
    {
        StringBuffer buffer = new StringBuffer(wordNumber * 12);

        int j = 0;
        while (j < wordNumber)
        {
            buffer.append(getRandomWord());
            buffer.append(" ");
            j++;
        }
        return buffer.toString();
    }

    /**
     * returns a random email
     * @return random email
     */
    public static String getRandomEmail()
    {
        return getRandomWord() + "@" + getRandomWord() + ".com";
    }

    /**
     * returns a random date
     * @return random date
     */
    public static Date getRandomDate()
    {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 365 - random.nextInt(730));
        return calendar.getTime();
    }
}
