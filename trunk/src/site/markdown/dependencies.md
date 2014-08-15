Dependencies
------------

See [displaytag/dependencies.html](displaytagdependencies.html) for the
list of dependencies needed by displaytag.

If you use maven in your project all you need to do is adding the
following to the dependencies section in your POM. Maven will take care
of including all the needed libraries for you.

```xml
        <dependency>
          <groupId>displaytag</groupId>
          <artifactId>displaytag</artifactId>
          <version>2.0</version>
        </dependency>
```

### Optional modules

Starting from the 1.1 release optional modules are distributed as
additional jars. Any module could require additional dependencies:

-   [displaytag portlet](displaytag-portletdependencies.html)
-   [displaytag excel export
    module](#displaytag-export-poidependencies.html)

