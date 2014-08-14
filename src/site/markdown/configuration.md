Configuration
-------------

### Configuration properties

This table lists all the configurable properties for the tag libraries.
The default properties are defined in the `displaytag.properties` file
included in the library jar.

There are 2 ways to override default property settings:

-   For the whole web application, create a custom properties file named
    `displaytag.properties` and place it in the application classpath
    (tipically into `WEB-INF/classes`). Displaytag will use the locale
    of the request object to determine the locale of the property file
    to use; if the key required does not exist in the specified file,
    the key will be loaded from a more general property file.
-   For a single table instance, using the \<display:setProperty\> tag

Include in your custom properties file only the properties you need to
change. If a property is not defined in the user file, the default from
the TableTag.properties included in the jar is used.

### I18n

Some properties contain messages you may wish to display according to
the user Locale. To do that first add a default displaytag.properties
file where you set all the locale independent entries and default
messages. Then you can add localized properties file (for example
displaytag\_IT.properties ).

### Generic

Property

Default

Valid Values

Description

allowed in properties

allowed in setProperty

basic.show.header

true

true, false

Indicates if you want the header to appear at the top of the table, the
header contains the column names, and any additional action banners that
might be required (like paging, export, etc...)

yes

yes

basic.empty.showtable

false

true, false

Indicates if you want the table to show up also if the list is empty

yes

yes

basic.msg.empty\_list

Nothing found to display

any string

The message that is displayed if the list that this table is associated
with is either null, or empty. Used only if basic.empty.showtable is
false

yes

yes

basic.msg.empty\_list\_row

\<tr class="empty"\> \<td colspan="0"\>Nothing found to display.\</td\>
\</tr\> \</tr\>

Any string

The message that is displayed into the first table row if the list that
this table is associated with is either null, or empty. {0} is replaced
with the total number of columns to generate a correct colspan. Used
only if `basic.empty.showtable` is true

yes

yes

sort.amount

page

page, list

Indicates if the full list should be sorted before paging or if the
sorting only affects items in the current page. Default behaviour is to
sort only items in the current page (first paging, then sorting)

yes

no

export.banner

\<div class="exportlinks"\> Export options: {0} \</div\>

any string

Contains the string that is displayed in the table footer when the user
indicates that they want to enable the export function. The placeholder
is replaced with links to the various supported export formats

yes

yes

export.banner.sepchar

 u007C

any string

Used to separate the valid export type (typically would be a bar, a
comma, or a dash)

yes

yes

paging.banner.placement

top

top, bottom, both

When the table tag has to show the header for paging through a long
list, this option indicates where that header should be shown in
relation to the table

yes

yes

paging.banner.item\_name

item

any string

What the various objects in the list being displayed should be referred
to as (singular)

yes

yes

paging.banner.items\_name

items

any string

What the various objects in the list being displayed should be referred
to as (plural)

yes

yes

paging.banner.no\_items\_found

\<span class="pagebanner"\> No {0} found. \</span\>

any string

What is shown in the pagination header when no objects are available in
the list to be displayed. The single placeholder is replaced with the
name of the items in the list (plural)

yes

yes

paging.banner.one\_item\_found

\<span class="pagebanner"\> One {0} found. \</span\>

any string

What is shown in the pagination header when one object is available in
the list to be displayed. The single placeholder is replaced with the
name of the items in the list (singular)

yes

yes

paging.banner.all\_items\_found

\<span class="pagebanner"\> {0} {1} found, displaying all {2}. \</span\>

any string

What is shown in the pagination header when all the objects in the list
are being shown. {0} and {2} are replaced with the number of objects in
the list, {1} is replaced with the name of the items plural

yes

yes

paging.banner.some\_items\_found

\<span class="pagebanner"\> {0} {1} found, displaying {2} to {3}.
\</span\>

any string

What is shown in the pagination header when a partial list of the
objects in the list are being shown. Parameters: \* {0}: total number of
objects in the list \* {1}: name of the items (plural) \* {2}: start
index of the objects being shown \* {3}: end index of the objects being
shown \* {4}: current page \* {5}: total number of pages

yes

yes

paging.banner.group\_size

8

any number

The number of pages to show in the header that this person can instantly
jump to

yes

yes

paging.banner.full

\<span class="pagelinks"\> [\<a href="{1}"\>First\</a\> /\<a
href="{2}"\>Prev\</a\>] {0} [\<a href="{3}"\>Next\</a\> /\<a
href="{4}"\>Last\</a\>] \</span\>

any string

What is shown in the pagination bar when there are more pages and the
selected page is not the first or the last one. Parameters: \* {0}:
numbered pages list \* {1}: link to the first page \* {2}: link to the
previous page \* {3}: link to the next page \* {4}: link to the last
page \* {5}: current page \* {6}: total number of pages

yes

yes

paging.banner.first

\<span class="pagelinks"\> [First/Prev] {0} [\<a href="{3}"\>Next\</a\>
/\<a href="{4}"\>Last\</a\>] \</span\>

any string

What is shown in the pagination bar when the first page is being shown.
Placeholders are the same as for `paging.banner.full`

yes

yes

paging.banner.last

\<span class="pagelinks"\> [\<a href="{1}"\>First\</a\> /\<a
href="{2}"\>Prev\</a\>] {0} [Next/Last] \</span\>

any string

What is shown in the pagination bar when the last page is being shown.
Placeholders are the same as for `paging.banner.full`

yes

yes

paging.banner.onepage

\<span class="pagelinks"\> {0}\</span\> \</span\>

any string

What is shown in the pagination bar when only one page is being shown.
Placeholders are the same as for `paging.banner.full`

yes

yes

paging.banner.page.selected

\<strong\>{0}\</strong\>

any string

Selected page. {0} is replaced with the page number, {1} with the page
url.

yes

yes

paging.banner.page.link

\<a href="{1}" title="Go to page {0}"\>{0}\</a\>

any string

Link to a page. {0} is replaced with the page number, {1} with the page
url.

yes

yes

paging.banner.page.separator

,

any string

separator between pages

yes

yes

decorator.media.html

Class name of `TableDecorator` subclass

Decorator used to render table as html. Required when also decorating
the table in other media. Refer to the [exports decorators
tutorial](#tut_decorators.htmlTable_decorators_and_exports) for detailed
explanation. Ignored if a decorator is configured in table tag's
decorator attribute.

yes

yes

factory.requestHelper

`org.displaytag.util.DefaultRequestHelperFactory`

Class name for a valid `RequestHelperFactory` implementation

RequestHelperFactory to be used. You can replace the default one if you
need to generate links with a different format (for example in portal
applications)

yes

no

factory.decorator

`org.displaytag.decorator.DefaultDecoratorFactory`

DecoratorFactory to be used. Class name for a valid `DecoratorFactory`
implementation

yes You can replace the default one in order to load decorator instances
from different sources (e.g. you could use Spring managed beans)

no

comparator.default

`org.displaytag.model.DefaultComparator`

Default comparator to be used when sorting columns. Class name for a
valid `Comparator` implementation

yes

yes

### Exporting

Displaytag supports exporting to excel, csv, pdf, rtf, and xml formats.
Some configurable properties are specific for one of these format.

Replace *export name* in the property name with "excel", "csv", "pdf",
"rtf", or "xml". Some of the properties won't work in any export format.

Property

Default

Valid Values

Description

allowed in properties

allowed in setProperty

export.types

csv excel xml pdf

any string

Whitespace separated list of configured export types

yes

no

export.*export name*

true

true, false

Should the tag present the option to export data in this specific format

yes

yes

export.*export name*.class

Any valid class that implements the
`org.displaytag.export.ExportView interface`

Fully qualified class name for the class which will be used for
exporting

yes

no

export.*export name*.label

\<span class="export *export name*"\> *export name* \</span\>

Any string

The label on the link that the user clicks on to export the data in a
specific format

yes

yes

export.*export name*.include\_header

false

true, false

If set to true, the first line of the export will contain column titles
as displayed on the HTML page.

yes

yes

export.*export name*.filename

none

any valid file name

When saving exported files the user will be prompted to use this file
name

yes

yes

export.amount

list

page, list

Indicates how much data should be sent down to the user when they ask
for a data export. By default, it sends the entire list. You can
instruct the table tag to only send down the data that is currently
being shown on the page

yes

yes

export.decorated

true

true, false

Should the data be "decorated" as it is exported. Note that
`org.displaytag.export.excel.DefaultHssfExportView`,
`org.displaytag.export.DefaultPdfExportView`, and
`org.displaytag.export.DefaultRtfExportView` do not observe this value.

yes

yes

decorator.media.*export name*

Class name of `TableDecorator` subclass

Decorator used to render table in given media. Refer to the [exports and
decorators tutorial](#tut_decorators.htmlTable_decorators_and_exports)
for detailed explanation. Ignored if a decorator is configured in table
tag's decorator attribute.

yes

yes

### CSS

  -----------------------------------------------------------------------------
  Property
  Default
  Valid Values
  Description
  allowed in
  properties
  allowed in
  setProperty
  ------------ ------------ ------------ ------------ ------------ ------------
  css.tr.even  css.tr.odd   css.th.sorte css.th.ascen css.th.desce css.table
  even         odd          d            ding         nding        none
  any valid    any valid    sorted       order1       order2       any valid
  css class    css class    any valid    any valid    any valid    css class
  name         name         css class    css class    css class    name
  css class    css class    name         name         name         css class
  automaticall automaticall css class    css class    css class    automaticall
  y            y            automaticall automaticall automaticall y
  added to     added to odd y            y            y            added to the
  even rows    rows         added to the added to the added to the main table
  yes          yes          header of    header of a  header of a  tag
  yes          yes          sorted       column       column       yes
                            columns      sorted in    sorted in    yes
                            yes          ascending    descending   
                            yes          order        order        
                                         yes          yes          
                                         yes          yes          
  -----------------------------------------------------------------------------


