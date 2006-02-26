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

import java.util.Date;
import java.util.Random;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

import org.apache.taglibs.display.StringUtil;

/**
 * Just a test class that returns columns of data that are useful for testing
 * out the ListTag class and ListColumn class.
 *
 * More detailed class description, including examples of usage if applicable.
 **/

public class ListObject extends Object {
    private static Random rnd = new Random();
    private static List words = null;

    public int id = -1;
    public String name;
    public String email;
    public Date date;
    public double money;
    public String description;
    public String longDescription;
    public String status;
    public String url;

    public ListObject() {
        this.setupRandomData();
    }

    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public double getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public String getNullValue() {
        return null;
    }

    public String toString() {
        return "ListObject(" + id + ")";
    }

    public String toDetailedString() {
        return
                "ID:          " + this.id + "\n" +
                "Name:        " + this.name + "\n" +
                "Email:       " + this.email + "\n" +
                "Date:        " + this.date + "\n" +
                "Money:       " + this.money + "\n" +
                "Description: " + this.description + "\n" +
                "Status:      " + this.status + "\n" +
                "URL:         " + this.url + "\n";
    }

    public void setupRandomData() {
        this.id = rnd.nextInt(99998) + 1;
        this.money = (rnd.nextInt(999998) + 1) / 100;

        String first = this.getRandomWord();
        String last = this.getRandomWord();

        this.name = StringUtil.toUpperCaseAt(first, 0) + " " +
                StringUtil.toUpperCaseAt(last, 0);

        this.email = first + "-" + last + "@" +
                this.getRandomWord() + ".com";

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 365 - rnd.nextInt(730));
        this.date = cal.getTime();


        this.description = this.getRandomWord() + " " +
                this.getRandomWord() + "...";

        this.longDescription = this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord() + " " +
                this.getRandomWord();


        this.status = this.getRandomWord().toUpperCase();

        this.url = "http://www." + last + ".org/";
    }


    public String getRandomWord() {
        if (words == null) {
            this.setupWordBase();
        }

        return ((String) words.get(rnd.nextInt(words.size()))).toLowerCase();
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


}
