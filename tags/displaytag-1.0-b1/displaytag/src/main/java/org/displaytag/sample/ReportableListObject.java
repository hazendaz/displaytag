package org.displaytag.sample;

import java.util.Random;

/**
 * A test class that has data that looks more like information that comes back
 * in a report...
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ReportableListObject extends Object implements Comparable
{
    /**
     * random number producer
     */
    private static Random mRandom = new Random();

    /**
     * city
     */
    private String city;

    /**
     * project
     */
    private String project;

    /**
     * task
     */
    private String task;

    /**
     * amount
     */
    private double amount;

    /**
     * city names
     */
    private static String[] mCities = { "Roma", "Olympia", "Neapolis", "Carthago" };

    /**
     * project names
     */
    private static String[] mProjects = { "Taxes", "Arts", "Army", "Gladiators" };

    /**
     * Constructor for ReportableListObject
     */
    public ReportableListObject()
    {
        this.amount = (mRandom.nextInt(99999) + 1) / 100;
        this.city = mCities[mRandom.nextInt(mCities.length)];
        this.project = mProjects[mRandom.nextInt(mProjects.length)];
        this.task = RandomSampleUtil.getRandomSentence(4);
    }

    /**
     * getter for city
     * @return String city
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * getter for project
     * @return String project
     */
    public String getProject()
    {
        return this.project;
    }

    /**
     * getter for task
     * @return String task
     */
    public String getTask()
    {
        return this.task;
    }

    /**
     * getter for amount
     * @return double amount
     */
    public double getAmount()
    {
        return this.amount;
    }

    /**
     * Method toString
     * @return String
     */
    public String toString()
    {
        return "ReportableListObject(" + city + ":" + project + ":" + amount + ")";
    }

    /**
     * Method compareTo
     * @param anotherObject Object
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object anotherObject)
    {
        ReportableListObject object1 = this;
        ReportableListObject object2 = (ReportableListObject) anotherObject;

        if (object1.city.equals(object2.city))
        {
            if (object1.project.equals(object2.project))
            {
                return (int) (object2.amount - object1.amount);
            }
            else
            {
                return object1.project.compareTo(object2.project);
            }
        }
        else
        {
            return object1.city.compareTo(object2.city);
        }
    }
}
