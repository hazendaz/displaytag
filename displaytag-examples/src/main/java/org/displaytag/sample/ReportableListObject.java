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
import java.util.Random;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A test class that has data that looks more like information that comes back in a report.
 *
 * @author epesh
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class ReportableListObject extends Object implements Comparable<Object>, Serializable {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * random number producer.
     */
    private static Random random = new Random();

    /**
     * city names.
     */
    private static String[] cities = //
            { "Roma", "Olympia", "Neapolis", "Carthago" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    /**
     * project names.
     */
    private static String[] projects = //
            { "Taxes", "Arts", "Army", "Gladiators" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    /**
     * city.
     */
    private String city;

    /**
     * project.
     */
    private String project;

    /**
     * task.
     */
    private String task;

    /**
     * amount.
     */
    private double amount;

    /**
     * count.
     */
    private int count;

    /**
     * Constructor for ReportableListObject.
     */
    public ReportableListObject() {
        this.amount = (random.nextInt(99999) + 1) / 100;
        this.city = cities[random.nextInt(cities.length)];
        this.project = projects[random.nextInt(projects.length)];
        this.task = RandomSampleUtil.getRandomSentence(4);
        this.count = random.nextInt(10);
    }

    /**
     * getter for city.
     *
     * @return String city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Getter for <code>count</code>.
     *
     * @return Returns the count.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * getter for project.
     *
     * @return String project
     */
    public String getProject() {
        return this.project;
    }

    /**
     * getter for task.
     *
     * @return String task
     */
    public String getTask() {
        return this.task;
    }

    /**
     * getter for amount.
     *
     * @return double amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object object) {
        ReportableListObject myClass = (ReportableListObject) object;
        return new CompareToBuilder().append(this.project, myClass.project).append(this.amount, myClass.amount)
                .append(this.city, myClass.city).append(this.task, myClass.task).toComparison();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) //
                .append("project", this.project) //$NON-NLS-1$
                .append("amount", this.amount) //$NON-NLS-1$
                .append("city", this.city) //$NON-NLS-1$
                .append("task", this.task) //$NON-NLS-1$
                .toString();
    }

}
