/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Just a test class that returns columns of data that are useful for testing out the ListTag class and ListColumn
 * class.
 *
 * @author epesh
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class ListObject implements Serializable {

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

    /**
     * random number generator.
     */
    private static Random random = new Random();

    /**
     * id.
     */
    private int id = -1;

    /**
     * name.
     */
    private String name;

    /**
     * email.
     */
    private String email;

    /**
     * date.
     */
    private Date date;

    /**
     * money.
     */
    private double money;

    /**
     * description.
     */
    private String description;

    /**
     * long description.
     */
    private String longDescription;

    /**
     * status.
     */
    private String status;

    /**
     * url.
     */
    private String url;

    /**
     * sub list used to test nested tables.
     */
    private List<Object> subList;

    /**
     * Constructor for ListObject.
     */
    public ListObject() {
        this.id = random.nextInt(99998) + 1;
        this.money = (random.nextInt(999998) + 1) / 100;

        String firstName = RandomSampleUtil.getRandomWord();
        String lastName = RandomSampleUtil.getRandomWord();

        this.name = StringUtils.capitalize(firstName) + ' ' + StringUtils.capitalize(lastName);

        this.email = firstName + '-' + lastName + '@' + RandomSampleUtil.getRandomWord() + ".com"; //$NON-NLS-1$

        this.date = RandomSampleUtil.getRandomDate();

        this.description = RandomSampleUtil.getRandomWord() + ' ' //
                + RandomSampleUtil.getRandomWord() + "..."; //$NON-NLS-1$

        this.longDescription = RandomSampleUtil.getRandomSentence(10);

        this.status = RandomSampleUtil.getRandomWord().toUpperCase();

        // added sublist for testing of nested tables
        this.subList = new ArrayList<Object>();
        this.subList.add(new SubListItem());
        this.subList.add(new SubListItem());
        this.subList.add(new SubListItem());

        this.url = "http://www." + lastName + ".org/"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * getter for id.
     *
     * @return int id
     */
    public int getId() {
        return this.id;
    }

    /**
     * setter for id.
     *
     * @param value
     *            int id
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * getter for name.
     *
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for email.
     *
     * @return String email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * setter for email.
     *
     * @param value
     *            String email
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * getter for date.
     *
     * @return Date
     */
    public Date getDate() {
        return (Date) this.date.clone();
    }

    /**
     * getter for money.
     *
     * @return double money
     */
    public double getMoney() {
        return this.money;
    }

    /**
     * getter for description.
     *
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * getter for long description.
     *
     * @return String long description
     */
    public String getLongDescription() {
        return this.longDescription;
    }

    /**
     * getter for status.
     *
     * @return String status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * getter for url.
     *
     * @return String url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * test for null values.
     *
     * @return null
     */
    public String getNullValue() {
        return null;
    }

    /**
     * Returns a simple string representation of the object.
     *
     * @return String simple representation of the object
     */
    @Override
    public String toString() {
        return "ListObject(" + this.id + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Returns a detailed string representation of the object.
     *
     * @return String detailed representation of the object
     */
    public String toDetailedString() {
        return "ID:          " //$NON-NLS-1$
                + this.id + "\n" //$NON-NLS-1$
                + "Name:        " //$NON-NLS-1$
                + this.name + "\n" //$NON-NLS-1$
                + "Email:       " //$NON-NLS-1$
                + this.email + "\n" //$NON-NLS-1$
                + "Date:        " //$NON-NLS-1$
                + this.date + "\n" //$NON-NLS-1$
                + "Money:       " //$NON-NLS-1$
                + this.money + "\n" //$NON-NLS-1$
                + "Description: " //$NON-NLS-1$
                + this.description + "\n" //$NON-NLS-1$
                + "Status:      " //$NON-NLS-1$
                + this.status + "\n" //$NON-NLS-1$
                + "URL:         " //$NON-NLS-1$
                + this.url + "\n"; //$NON-NLS-1$
    }

    /**
     * Returns the subList.
     *
     * @return List
     */
    public List<Object> getSubList() {
        return this.subList;
    }

    /**
     * Inner class used in testing nested tables.
     *
     * @author Fabrizio Giustina
     */
    public static class SubListItem implements Serializable {

        /**
         * Stable serialVersionUID.
         */
        private static final long serialVersionUID = 42L;

        /**
         * name.
         */
        private String itemName;

        /**
         * email.
         */
        private String itemEmail;

        /**
         * Constructor for SubListItem.
         */
        public SubListItem() {
            this.itemName = RandomSampleUtil.getRandomWord();
            this.itemEmail = RandomSampleUtil.getRandomEmail();
        }

        /**
         * getter for name.
         *
         * @return String name
         */
        public String getName() {
            return this.itemName;
        }

        /**
         * getter for email.
         *
         * @return String
         */
        public String getEmail() {
            return this.itemEmail;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) //
                    .append("name", this.itemName) //$NON-NLS-1$
                    .append("email", this.itemEmail) //$NON-NLS-1$
                    .toString();
        }
    }

}
