Installation Guide
------------------

### Manual installation

This package comes with pre-built binaries. Those distribution files
are:

| file                  | description                           |
| --------------------- | ------------------------------------- |
| displaytag-1.1.jar    |  main taglib jar        |
| displaytag-export-poi-1.1.jar   | optional excel export module          |



To quickly view the documentation and examples showing the features and
functionality of the display taglib, just deploy the sample web
application to your application server (the details of how differ from
server to server) or servlet container.

If you would like to make use of the display taglib in your own
application, do the following:

1.  Drop the displaytag-*version*.jar file in your application `WEB-INF/lib` directory
2.  Make sure that following libraries are in your `WEB-INF/lib` directory
    (or made available via the classpath to your application server).
    Refer to the dependencies document for the correct version of these
    libraries. You can download a copy of everything from jakarta or you
    can grab them from the example webapp in the bin distribution. The
    following is the list of dependencies:

    -   commons-logging
    -   commons-lang
    -   commons-collections
    -   commons-beanutils
    -   log4j
    -   itext (optional, for pdf/rtf export)

    You may want to include also the `displaytag-export-poi` jar, which
    adds an excel binary export using jakarta POI. The `poi` jar is
    required by displaytag-export-poi

3.  Optional. Depending on your architecture, you may need to configure
    a filter to make export work. See the [export filter](export_filter.html) page for the details about how to do it
    and when you could need it.

DONE : Define the tag extension in each JSP page that uses the display
taglib. The uri directives must match the URI defined in one of the tlds
in the jar file. With JSP 1.2 containers, the jar file is automatically
scanned and you don't need to define an entry in your web.xml file. The
prefix identifies the tags in the tag library within the JSP page.

```html
      <%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
```

The declaration, if you are using a JSP XML syntax, looks like:

```html
      <jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
         xmlns:display="urn:jsptld:http://displaytag.sf.net">
```

For more help with general taglib use, please see:
<http://jakarta.apache.org/taglibs/tutorial.html>

### Using maven

If you use maven 2 for your projects all you need to do is including a
reference to displaytag in the dependencies section of your POM:

```xml
        <dependency>
          <groupId>org.displaytag</groupId>
          <artifactId>displaytag</artifactId>
          <version>1.1.1</version>
        </dependency>
```

The only additional configuration required in your web.xml is the
[export filter](export_filter.html) entry.

If you want to also use the optional excel export module you will need
to include:

```xml
        <dependency>
          <groupId>org.displaytag</groupId>
          <artifactId>displaytag-export-poi</artifactId>
          <version>1.1.1</version>
        </dependency>
```

When using maven 1 you will also need to add displaytag dependencies to
your POM. An easy way to do this is cut and paste from the example web
application

