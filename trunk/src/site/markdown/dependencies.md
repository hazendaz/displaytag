# Dependencies

See [displaytag/dependencies.html](displaytagdependencies.html) for the
list of dependencies needed by displaytag.

If you use maven in your project all you need to do is adding the
following to the dependencies section in your POM. Maven will take care
of including all the needed libraries for you.

```xml
<dependency>
  <groupId>org.displaytag</groupId>
  <artifactId>displaytag</artifactId>
  <version>2.0</version>
</dependency>
```

Since displaytag uses [http://www.slf4j.org/](slf4j) for logging you will
also need to choose a slf4j logging implementation (by default displaytag dependencies
will only include slf4j-api, in order to let you choose your preferred implementation).
You usually may want to include the log4j adapter, using:

```xml
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
  <version>1.7.7</version>
</dependency>
```

## Optional dependencies

By default Displaytag render excel files using a text-based format. You may need to enable a different exporter
using excel binary format: the exporter is included by default in the distribution but you will also need to include
the `poi` dependency in order to use it:

```xml
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi</artifactId>
  <version>3.10-FINAL</version>
</dependency>
```

(the export-poi module was distributed in a separate jar before version 2.0. Since displaytag 2.0 the module is not
required since it has been included in the main jar)
