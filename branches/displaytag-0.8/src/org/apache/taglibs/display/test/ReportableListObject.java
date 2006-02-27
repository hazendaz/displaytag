/**
 * $Id$
 *
 * Status: Under Development
 *
 * Todo
 *   - impementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 **/

package org.apache.taglibs.display.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


/**
 * A test class that has data that looks more like information that comes back
 * in a report...
 **/

public class ReportableListObject extends Object implements Comparable {
    private static Random rnd = new Random();
    private static List words = null;

    public String city;
    public String project;
    public String task;
    public double amount;

    public ReportableListObject() {
        this.setupRandomData();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return "ReportableListObject(" + city + ":" + project + ":" + amount + ")";
    }


    public void setupRandomData() {
        this.amount = (rnd.nextInt(99999) + 1) / 100;

        city = this.getRandomCity();
        project = this.getRandomProject();
        task = this.getRandomTask();
    }


    public String getRandomCity() {
        String cities[] = {
            "Roma", "Olympia", "Neapolis", "Carthago"
        };

        return cities[rnd.nextInt(cities.length)];
    }

    public String getRandomProject() {
        String projects[] = {
            "Taxes", "Arts", "Army", "Gladiators"
        };

        return projects[rnd.nextInt(projects.length)];
    }

    public String getRandomTask() {
        if (words == null) {
            this.setupWordBase();
        }

        return ((String) words.get(rnd.nextInt(words.size()))).toLowerCase() +
                " " + ((String) words.get(rnd.nextInt(words.size()))).toLowerCase() +
                " " + ((String) words.get(rnd.nextInt(words.size()))).toLowerCase() +
                " " + ((String) words.get(rnd.nextInt(words.size()))).toLowerCase();

    }


    public void setupWordBase() {
        String lorumIpsum =
                "Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy " +
                "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam " +
                "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet " +
                "clita kasd gubergren no sea takimata sanctus est Lorem ipsum dolor sit " +
                "amet Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam " +
                "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat " +
                "sed diam voluptua At vero eos et accusam et justo duo dolores et ea " +
                "rebum Stet clita kasd gubergren no sea takimata sanctus est Lorem " +
                "ipsum dolor sit amet Lorem ipsum dolor sit amet consetetur sadipscing " +
                "elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna " +
                "aliquyam erat sed diam voluptua At vero eos et accusam et justo duo " +
                "dolores et ea rebum Stet clita kasd gubergren no sea takimata sanctus " +
                "est Lorem ipsum dolor sit amet " +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse " +
                "molestie consequat vel illum dolore eu feugiat nulla facilisis at vero " +
                "eros et accumsan et iusto odio dignissim qui blandit praesent luptatum " +
                "zzril delenit augue duis dolore te feugait nulla facilisi Lorem ipsum " +
                "dolor sit amet consectetuer adipiscing elit sed diam nonummy nibh " +
                "euismod tincidunt ut laoreet dolore magna aliquam erat volutpat " +
                "Ut wisi enim ad minim veniam quis nostrud exerci tation ullamcorper " +
                "suscipit lobortis nisl ut aliquip ex ea commodo consequat Duis autem " +
                "vel eum iriure dolor in hendrerit in vulputate velit esse molestie " +
                "consequat vel illum dolore eu feugiat nulla facilisis at vero eros et " +
                "accumsan et iusto odio dignissim qui blandit praesent luptatum zzril " +
                "delenit augue duis dolore te feugait nulla facilisi " +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet " +
                "doming id quod mazim placerat facer possim assum Lorem ipsum dolor sit " +
                "amet consectetuer adipiscing elit sed diam nonummy nibh euismod " +
                "tincidunt ut laoreet dolore magna aliquam erat volutpat Ut wisi enim ad " +
                "minim veniam quis nostrud exerci tation ullamcorper suscipit lobortis " +
                "nisl ut aliquip ex ea commodo consequat " +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse " +
                "molestie consequat vel illum dolore eu feugiat nulla facilisis " +
                "At vero eos et accusam et justo duo dolores et ea rebum Stet clita kasd " +
                "gubergren no sea takimata sanctus est Lorem ipsum dolor sit amet Lorem " +
                "ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy " +
                "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam " +
                "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet " +
                "clita kasd gubergren no sea takimata sanctus est Lorem ipsum dolor sit " +
                "amet Lorem ipsum dolor sit amet consetetur sadipscing elitr At " +
                "accusam aliquyam diam diam dolore dolores duo eirmod eos erat et nonumy " +
                "sed tempor et et invidunt justo labore Stet clita ea et gubergren kasd " +
                "magna no rebum sanctus sea sed takimata ut vero voluptua est Lorem " +
                "ipsum dolor sit amet Lorem ipsum dolor sit amet consetetur sadipscing " +
                "elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna " +
                "aliquyam erat " +
                "Consetetur sadipscing elitr sed diam nonumy eirmod tempor invidunt ut " +
                "labore et dolore magna aliquyam erat sed diam voluptua At vero eos et " +
                "accusam et justo duo dolores et ea rebum Stet clita kasd gubergren no " +
                "sea takimata sanctus est Lorem ipsum dolor sit amet Lorem ipsum dolor " +
                "sit amet consetetur sadipscing elitr sed diam nonumy eirmod tempor " +
                "invidunt ut labore et dolore magna aliquyam erat sed diam voluptua At " +
                "vero eos et accusam et justo duo dolores et ea rebum Stet clita kasd " +
                "gubergren no sea takimata sanctus est Lorem ipsum dolor sit amet Lorem " +
                "ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy " +
                "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam " +
                "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet " +
                "clita kasd gubergren no sea takimata sanctus ";

        words = new ArrayList();
        StringTokenizer st = new StringTokenizer(lorumIpsum);
        while (st.hasMoreTokens()) {
            String word = (String) st.nextToken();
            words.add(word);
        }
    }

    public int compareTo(Object o) {
        ReportableListObject o1 = this;
        ReportableListObject o2 = (ReportableListObject) o;

        if (o1.city.equals(o2.city)) {
            if (o1.project.equals(o2.project)) {
                return (int) (o2.amount - o1.amount);
            }
            else {
                return o1.project.compareTo(o2.project);
            }
        }
        else {
            return o1.city.compareTo(o2.city);
        }
    }
}
