<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net/el"
    xmlns:c="urn:jsptld:../WEB-INF/tld/c.tld">
    <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]> </jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
    <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
            <title>Displaytag unit test</title>
        </head>
        <body>
						<jsp:declaration>
						  public class ValueBean {
						    public ValueBean(String value) {
						      this.value=value;
						    }
						    private String value;
						    public String getValue() {
						      return this.value;
						   }
						  }
						</jsp:declaration>
            <jsp:scriptlet> <![CDATA[
                java.util.List theList = new java.util.ArrayList();
							  theList.add(new ValueBean("1"));
							  theList.add(new ValueBean("2"));
							  theList.add(new ValueBean("3"));

						    request.setAttribute("row0",new java.util.ArrayList(theList));
						    request.setAttribute("row1",new java.util.ArrayList(theList));
						    request.setAttribute("row2",new java.util.ArrayList(theList));
            ]]> </jsp:scriptlet>
					<c:forEach var="item" items="0,1,2">
						<display:table uid="row${item}" name="row${item}" sort="list" >
						  <display:column property="value" sortable="true"/>
						</display:table>
          </c:forEach>
        </body>
    </html>
</jsp:root>