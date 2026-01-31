Displaytag
==========

[![Java CI](https://github.com/hazendaz/displaytag/workflows/ci.yaml/badge.svg)](https://github.com/hazendaz/displaytag/actions/workflows/ci.yaml)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.hazendaz/displaytag.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.hazendaz/displaytag)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](https://opensource.org/licenses/MIT)

See site page [here](https://hazendaz.github.io/displaytag/)

The display tag library is an open source suite of custom tags that provide high-level web presentation
patterns which will work in an MVC model. The library provides a significant amount of functionality while still
being easy to use.

Originally forked from [here](https://sourceforge.net/projects/displaytag/)

This fork intends to be a bug fix, vulnerability free, and dependency up-to-date legacy support of displaytag.

### Use with AjaxTags

It is common to use displaytag in combination with AjaxTags to provide ajax behaviour to the tables. 
The last version of ajaxTags released in maven central ([1.3-beta-rc7](https://mvnrepository.com/artifact/org.ajaxtags/ajaxtags/1.3-beta-rc7)) does not work correctly with the latest version of displaytag. 
The sorting of tables is broken. AjaxTags expects the table element of displaytag to only have the "displaytag" class and the current implementation of displaytag also adds the "table" class. 
This prevents AjaxTags to identify the table as a candidate for link rewriting.

It is possible to configure displaytag to not to add this class to the table by setting the property `css.table` to empty.
In displaytag.properties the following must be set:
```
css.table=
```
