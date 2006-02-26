<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<jsp:include page="header.jsp" flush="true" />

<h1><a href="./index.jsp">Documentation</a> > Change History</h1>

<p>This file documents the changes that have been made to the display taglib.  The
most recently changes are towards the top of this file.  Like the log4j project
changes are noted using the following key:</p>

<blockquote><pre>
  [!] New functionality
  [*] Changes that are 100% backward compatible
 [**] Changes that requiring little or no modification to existing code
[***] Changes requiring important modifications to existing code
</pre></blockquote>


<p><b>Version 0.8.5, March 23, 2003</b></p>

<blockquote><pre>

- Allow column elements to be nested inside other tags.  Luiz-Otavio Zorzella [!]

- In decorators, added method getCollection() to allow for Collections, Iterators and Maps to be
   used as well as Lists.  Luiz-Otavio Zorzella [!]
   
- Original table tag only works with Lists. Now works with Collections, Iterators and Maps
   as well (though some functions, like group totals are not available to these, due to
   limitations on the structures themselves).  Luiz-Otavio Zorzella [!]

 - Added startRow() to the decorator API to allow people to put headers on
   groupings as well as footers.  Ed Hill [!]

 - Fixed a caching bug in with tables that had multiple pages, the pageNumber
   is now reset to 1 in the tag object.  Ed Hill [*]

 - Fixed various with URL rewriting (? vs &).  Ed Hill [*]

 - Correct error message on bean property lookups.  Ed Hill [*]

 - Build/Deploy process updates to be more dynamic.  Matt Raible [*]
</pre></blockquote>

<p><b>Version 0.8, May 25, 2002</b></p>

<blockquote><pre>
- added various properties that can be override that effect the default strings
  and behaviors of table, here are some brief examples of message and behaviors
  that you can now set, see examples-config.jsp for a complete list [!!]

  basic.show.header         - include the header at the top of the table
  basic.msg.empty_list      - string displayed when there are no items to show

  sort.behavior             - sort just viewable items, or entire list

  export.banner             - message format shown in footer, you can localize
  export.csv                - include CSV export option
  export.csv.label          - name of link (localize)
  export.csv.include_header - include the column titles in the export?
  export.amount             - export just the page of data or the complete list
  export.decorated          - decorate the data as it is exported?

- introduced two new column attributes "maxLength", "maxWords" that will
  restrict the size of strings being shown in a column - Matt Raible. [!]

- introduced new column attribute "headerStyleClass" that allows you to override
  the class name used in drawing the table's header cell for that column. [!]

- introduced ColumnDecorators - you can now decorate individual columns, this
  makes it easier for you to define reuable DateFmt, MoneyFmt, etc... decorators
  rather then developing a customer decorator for each table.  Note, the
  decorator inteface is still not to my liking, expect changes before this
  tag gets a 1.0 release - Jay Paulsen [!]

- fixed a bug, where nulls were *not* being surpressed like they should be in
  the display, now nulls will only be shown if you specify the nulls attribute
  of the column tag - Matt Raible. [**]

- fixed my web.xml files in the various wars - they were not compliant and
  Tomcat would barf while deploying them - I'm now testing under JRun 3.1/4.0
  and Tomcat 4.0.4b3 - Joachim Martin [*]

- fixed bug in example-styles.jsp, was using a variable "page" which is a
  rarely used standard JSP variable (JRun didn't care, Tomcat did), just
  renamed the variable so that example runs under Tomcat - Joachim Martin [*]

- fixed bug that prevented the first column from being sorted in both ascending
  and descending order [*]

- fixed a problem where a decorated table would interfere with fetching the
  source list on subsequent fetches [*]

- bug was introduced in 0.7 that prevented a decorated object from being
  exported correctly, that has been fixed [*]

- bug was introduced in 0.7 that prevented a decorated object from being
  sorted correctly, that has been fixed [*]

- included a copy of the Artistic license that this program is distributed
  under [*]

</pre></blockquote>

<p><b>Version 0.7, March 10, 2002</b></p>

<blockquote><pre>
- added the requestURI attribute, so that you could tell the table tag what
  URI was used to call it. [!]

- fixed some exporting bugs, added the ability to export in XML, although at
  this stage it is very crude. [!]

- fixed sorting so that it sorts in both ascending and decending order based
  on multiple title clicks. [!]

- updated all of the example jsp pages with a link that people can click on
  to view that pages source. Also re-orged the pages so that the example
  table was always at the top of the page as the first thing you saw, and
  the commentary behind the example followed. [!]

- The table tag now properly expects any decorators used to be children of
  the org.apache.taglibs.display.TableDecorator class (it was in the wrong
  package before).  Reported by everyone... [**]

- Underscore is not a legal character in CSS, so the various style names and
  attributes where changes to be in upcase format.
    - table_cell         -> tableCell
    - table_row_odd      -> tableRowOdd
    - table_row_even     -> tableRowEven
    - table_row_action   -> tableRowAction
    - table_cell_action  -> tableCellAction
    - table_row_header   -> tableRowHeader
    - table_cell_header  -> tableCellHeader

  Reported, fixed by Matt Raible. [**]

- Cleaned up the other examples that were incomplete.  Fixed broken links,
  etc... [*]

- Cloned the ColumnTags as I added them to the TableTag so that the table
  tag works correctly with web containers that reuse tags as an optimization.
  (Resin and Weblogic, probably others).  This bug would manifest itself with
  multiple duplicated columns.  Reported, fixed by Jim Canter [*]

- flushed out the example-export example that shows how to automatically
  export data to CSV, excel and XML formats. [*]

- flushed out the example-callback example that shows how to use a decorator
  to implement summing and totalling... The functionality was included in the
  original version, but there wasn't an example that showed how to use it. [*]

- fixed a NPE if you passed the table tag a null list. [*]

- updated the example-grouping.jsp page to show a more report oriented
  example. [*]

- Included the pointer to the 2.2 DTD in the web.xml files.  Thanks to Rob
  Evans. [*]

- added 'flush="true"' to all of my jsp:includes in my examples to be more
  in sync with the JSP 1.1 spec.  Thanks to Dirk Storck [*]
</pre></blockquote>

<p><b>Jan 7th, 2002 - First public alpha feedback release...</b></p>

<pre><blockquote>
- No changes recorded prior to this release.
</blockquote></pre>

<jsp:include page="footer.jsp" flush="true" />