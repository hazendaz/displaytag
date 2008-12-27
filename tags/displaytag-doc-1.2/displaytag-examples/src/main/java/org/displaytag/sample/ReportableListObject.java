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
package org.displaytag.sample;

import java.io.Serializable;
import java.util.Random;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * A test class that has data that looks more like information that comes back in a report.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ReportableListObject extends Object implements Comparable, Serializable
{

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
    {"Roma", "Olympia", "Neapolis", "Carthago"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    /**
     * project names.
     */
    private static String[] projects = //
    {"Taxes", "Arts", "Army", "Gladiators"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

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
    public ReportableListObject()
    {
        this.amount = (random.nextInt(99999) + 1) / 100;
        this.city = cities[random.nextInt(cities.length)];
        this.project = projects[random.nextInt(projects.length)];
        this.task = RandomSampleUtil.getRandomSentence(4);
        this.count = random.nextInt(10);
    }

    /**
     * getter for city.
     * @return String city
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * Getter for <code>count</code>.
     * @return Returns the count.
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * getter for project.
     * @return String project
     */
    public String getProject()
    {
        return this.project;
    }

    /**
     * getter for task.
     * @return String task
     */
    public String getTask()
    {
        return this.task;
    }

    /**
     * getter for amount.
     * @return double amount
     */
    public double getAmount()
    {
        return this.amount;
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object)
    {
        ReportableListObject myClass = (ReportableListObject) object;
        return new CompareToBuilder().append(this.project, myClass.project).append(this.amount, myClass.amount).append(
            this.city,
            myClass.city).append(this.task, myClass.task).toComparison();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) //
            .append("project", this.project) //$NON-NLS-1$
            .append("amount", this.amount) //$NON-NLS-1$
            .append("city", this.city) //$NON-NLS-1$
            .append("task", this.task) //$NON-NLS-1$
            .toString();
    }

}
