Tlds
----

The Displaytag taglibrary requires a Jsp 1.2 compatible container
(minimum requirement: tomcat 4 or similar). In order to gain EL support
in tag attributes you will require a JSP 2.0 compatible container, such
as Tomcat 5.

Distributions of displaytag prior to version 1.2 also contained a
different tld which added EL support in servlet engines who don't
support jsp 2.0. The custom EL support has however been dropped in
displaytag 1.3.

  ------------------------ ------------------------ ------------------------
  tld                      displaytag.tld
  URI                      http://displaytag.sf.net
  description              Jsp 1.2 version of the
                           tld: requires at least a
                           jsp 2.0 (j2ee 1.3)
                           compatible container
                           (Tomcat 4, WebSphere 5,
                           WebLogic 7...).
  ------------------------ ------------------------ ------------------------


