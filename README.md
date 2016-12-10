Displaytag
==========

[![Build Status](https://travis-ci.org/hazendaz/displaytag.svg?branch=master)](https://travis-ci.org/hazendaz/displaytag)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.hazendaz/displaytag/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hazendaz/displaytag)

See site page [here](http://hazendaz.github.io/displaytag/)

The display tag library is an open source suite of custom tags that provide high-level web presentation
patterns which will work in an MVC model. The library provides a significant amount of functionality while still
being easy to use.

Originally forked from [here](https://sourceforge.net/projects/displaytag/)

This fork intends to be a bug fix, vulnerability free, and dependency up-to-date legacy support of displaytag.

Original site documentation can be found [here](http://www.displaytag.org/1.2/)

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
