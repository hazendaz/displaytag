package org.displaytag.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * A test class that has data that looks more like information that comes back
 * in a report...
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ReportableListObject extends Object implements Comparable
{
    /**
     * Field rnd
     */
    private static Random mRandom = new Random();
    /**
     * Field words
     */
    private static List mWords = null;

    /**
     * Field city
     */
    private String mCity;

    /**
     * Field project
     */
    private String mProject;

    /**
     * Field task
     */
    private String mTask;

    /**
     * Field amount
     */
    private double mAmount;

    /**
     * cities
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
        setupRandomData();
    }

    /**
     * Method getCity
     * @return String
     */
    public String getCity()
    {
        return mCity;
    }

    /**
     * Method setCity
     * @param pCity String
     */
    public void setCity(String pCity)
    {
        mCity = pCity;
    }

    /**
     * Method getProject
     * @return String
     */
    public String getProject()
    {
        return mProject;
    }

    /**
     * Method setProject
     * @param pProject String
     */
    public void setProject(String pProject)
    {
        mProject = pProject;
    }

    /**
     * Method getTask
     * @return String
     */
    public String getTask()
    {
        return mTask;
    }

    /**
     * Method setTask
     * @param pTask String
     */
    public void setTask(String pTask)
    {
        mTask = pTask;
    }

    /**
     * Method getAmount
     * @return double
     */
    public double getAmount()
    {
        return mAmount;
    }

    /**
     * Method setAmount
     * @param pAmount double
     */
    public void setAmount(double pAmount)
    {
        mAmount = pAmount;
    }

    /**
     * Method toString
     * @return String
     */
    public String toString()
    {
        return "ReportableListObject(" + mCity + ":" + mProject + ":" + mAmount + ")";
    }

    /**
     * Method setupRandomData
     */
    public void setupRandomData()
    {
        mAmount = (mRandom.nextInt(99999) + 1) / 100;

        mCity = getRandomCity();
        mProject = getRandomProject();
        mTask = getRandomTask();
    }

    /**
     * Method getRandomCity
     * @return String
     */
    public String getRandomCity()
    {

        return mCities[mRandom.nextInt(mCities.length)];
    }

    /**
     * Method getRandomProject
     * @return String
     */
    public String getRandomProject()
    {

        return mProjects[mRandom.nextInt(mProjects.length)];
    }

    /**
     * Method getRandomTask
     * @return String
     */
    public String getRandomTask()
    {
        if (mWords == null)
        {
            this.setupWordBase();
        }

        return ((String) mWords.get(mRandom.nextInt(mWords.size()))).toLowerCase()
            + " "
            + ((String) mWords.get(mRandom.nextInt(mWords.size()))).toLowerCase()
            + " "
            + ((String) mWords.get(mRandom.nextInt(mWords.size()))).toLowerCase()
            + " "
            + ((String) mWords.get(mRandom.nextInt(mWords.size()))).toLowerCase();

    }

    /**
     * Method setupWordBase
     */
    public void setupWordBase()
    {
        String lLoremIpsum =
            "Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy "
                + "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam "
                + "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet "
                + "clita kasd gubergren no sea takimata sanctus est Lorem ipsum dolor sit "
                + "amet Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam "
                + "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat "
                + "sed diam voluptua At vero eos et accusam et justo duo dolores et ea "
                + "rebum Stet clita kasd gubergren no sea takimata sanctus est Lorem "
                + "ipsum dolor sit amet Lorem ipsum dolor sit amet consetetur sadipscing "
                + "elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna "
                + "aliquyam erat sed diam voluptua At vero eos et accusam et justo duo "
                + "dolores et ea rebum Stet clita kasd gubergren no sea takimata sanctus "
                + "est Lorem ipsum dolor sit amet "
                + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse "
                + "molestie consequat vel illum dolore eu feugiat nulla facilisis at vero "
                + "eros et accumsan et iusto odio dignissim qui blandit praesent luptatum "
                + "zzril delenit augue duis dolore te feugait nulla facilisi Lorem ipsum "
                + "dolor sit amet consectetuer adipiscing elit sed diam nonummy nibh "
                + "euismod tincidunt ut laoreet dolore magna aliquam erat volutpat "
                + "Ut wisi enim ad minim veniam quis nostrud exerci tation ullamcorper "
                + "suscipit lobortis nisl ut aliquip ex ea commodo consequat Duis autem "
                + "vel eum iriure dolor in hendrerit in vulputate velit esse molestie "
                + "consequat vel illum dolore eu feugiat nulla facilisis at vero eros et "
                + "accumsan et iusto odio dignissim qui blandit praesent luptatum zzril "
                + "delenit augue duis dolore te feugait nulla facilisi "
                + "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet "
                + "doming id quod mazim placerat facer possim assum Lorem ipsum dolor sit "
                + "amet consectetuer adipiscing elit sed diam nonummy nibh euismod "
                + "tincidunt ut laoreet dolore magna aliquam erat volutpat Ut wisi enim ad "
                + "minim veniam quis nostrud exerci tation ullamcorper suscipit lobortis "
                + "nisl ut aliquip ex ea commodo consequat "
                + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse "
                + "molestie consequat vel illum dolore eu feugiat nulla facilisis "
                + "At vero eos et accusam et justo duo dolores et ea rebum Stet clita kasd "
                + "gubergren no sea takimata sanctus est Lorem ipsum dolor sit amet Lorem "
                + "ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy "
                + "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam "
                + "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet "
                + "clita kasd gubergren no sea takimata sanctus est Lorem ipsum dolor sit "
                + "amet Lorem ipsum dolor sit amet consetetur sadipscing elitr At "
                + "accusam aliquyam diam diam dolore dolores duo eirmod eos erat et nonumy "
                + "sed tempor et et invidunt justo labore Stet clita ea et gubergren kasd "
                + "magna no rebum sanctus sea sed takimata ut vero voluptua est Lorem "
                + "ipsum dolor sit amet Lorem ipsum dolor sit amet consetetur sadipscing "
                + "elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna "
                + "aliquyam erat "
                + "Consetetur sadipscing elitr sed diam nonumy eirmod tempor invidunt ut "
                + "labore et dolore magna aliquyam erat sed diam voluptua At vero eos et "
                + "accusam et justo duo dolores et ea rebum Stet clita kasd gubergren no "
                + "sea takimata sanctus est Lorem ipsum dolor sit amet Lorem ipsum dolor "
                + "sit amet consetetur sadipscing elitr sed diam nonumy eirmod tempor "
                + "invidunt ut labore et dolore magna aliquyam erat sed diam voluptua At "
                + "vero eos et accusam et justo duo dolores et ea rebum Stet clita kasd "
                + "gubergren no sea takimata sanctus est Lorem ipsum dolor sit amet Lorem "
                + "ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy "
                + "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam "
                + "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet "
                + "clita kasd gubergren no sea takimata sanctus ";

        mWords = new ArrayList();
        StringTokenizer lTokenizer = new StringTokenizer(lLoremIpsum);
        while (lTokenizer.hasMoreTokens())
        {
            String lWord = lTokenizer.nextToken();
            mWords.add(lWord);
        }
    }
    /**
     * Method compareTo
     * @param pObject Object
     * @return int
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object pObject)
    {
        ReportableListObject lObject1 = this;
        ReportableListObject lObject2 = (ReportableListObject) pObject;

        if (lObject1.mCity.equals(lObject2.mCity))
        {
            if (lObject1.mProject.equals(lObject2.mProject))
            {
                return (int) (lObject2.mAmount - lObject1.mAmount);
            }
            else
            {
                return lObject1.mProject.compareTo(lObject2.mProject);
            }
        }
        else
        {
            return lObject1.mCity.compareTo(lObject2.mCity);
        }
    }
}
