JSR-168 Portlet Support
-----------------------

### Configuration

As specified in the documentation PDF the `factory.requestHelper` property
must be specified in the `displaytag.properties` file in your applications
classpath. To configure portlet support for the displaytag library
ensure the displaytag-portlet.jar is in the classpath and the following
line is in your displaytag.properties file.

```
     factory.requestHelper=org.displaytag.portlet.PortletRequestHelperFactory
```

### Usage

The displaytag-portlet library needs access to the
`javax.portlet.PortletRequest` and `javax.portlet.RenderResponse` objects
for the request the JSP is rendering in. The library uses the
`jakarta.servlet.jsp.PageContext\#findAttribute` method to locate the
request and response objects using the names `javax.portlet.request` and
`javax.portlet.response`.

To provide these objects it is recommended they be bound as request
attributes using these names. If your portal uses the Apache Pluto
portlet container the objects will already be bound to the request using
the appropriate names.

