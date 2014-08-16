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

<table  class="table table-bordered table-striped table-hover">
  <tr>
    <th >Property</th>
    <th >Default</th>
    <th >Valid Values</th>
    <th >Description</th>
    <th >allowed in properties</th>
    <th >allowed in setProperty</th>
  </tr>
  <tr>
    <td>basic.show.header</td>
    <td>true</td>
    <td>true, false</td>
    <td>Indicates if you want the header to appear at the top of the table, the header contains the
      column names, and any additional action banners that might be required (like paging, export, etc...)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>basic.empty.showtable</td>
    <td>false</td>
    <td>true, false</td>
    <td>Indicates if you want the table to show up also if the list is empty</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>basic.msg.empty_list</td>
    <td>Nothing found to display</td>
    <td>any string</td>
    <td>The message that is displayed if the list that this table is associated with is either null, or
      empty. Used only if basic.empty.showtable is false</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>basic.msg.empty_list_row</td>
    <td>
      &lt;tr class=&quot;empty&quot;&gt; &lt;td colspan=&quot;
      <a name="a0">0</a>&quot;&gt;Nothing
      found to display.&lt;/td&gt; &lt;/tr&gt; &lt;/tr&gt;
    </td>
    <td>Any string</td>
    <td>
      The message that is displayed into the first table row if the list that this table is associated with is
      either null, or empty. {0} is replaced with the total number of columns to generate a correct colspan.
      Used only if
      <tt>basic.empty.showtable</tt>
      is true
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>sort.amount</td>
    <td>page</td>
    <td>page, list</td>
    <td>Indicates if the full list should be sorted before paging or if the sorting only affects items
      in the current page. Default behaviour is to sort only items in the current page (first paging, then
      sorting)</td>
    <td>yes</td>
    <td>no</td>
  </tr>
  <tr>
    <td>export.banner</td>
    <td>&lt;div class=&quot;exportlinks&quot;&gt; Export options: {0} &lt;/div&gt;</td>
    <td>any string</td>
    <td>Contains the string that is displayed in the table footer when the user indicates that they
      want to enable the export function. The placeholder is replaced with links to the various supported export
      formats</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>export.banner.sepchar</td>
    <td>&#xa0;u007C</td>
    <td>any string</td>
    <td>Used to separate the valid export type (typically would be a bar, a comma, or a dash)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.placement</td>
    <td>top</td>
    <td>top, bottom, both</td>
    <td>When the table tag has to show the header for paging through a long list, this option indicates
      where that header should be shown in relation to the table</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.item_name</td>
    <td>item</td>
    <td>any string</td>
    <td>What the various objects in the list being displayed should be referred to as (singular)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.items_name</td>
    <td>items</td>
    <td>any string</td>
    <td>What the various objects in the list being displayed should be referred to as (plural)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.no_items_found</td>
    <td>&lt;span class=&quot;pagebanner&quot;&gt; No {0} found. &lt;/span&gt;</td>
    <td>any string</td>
    <td>What is shown in the pagination header when no objects are available in the list to be
      displayed. The single placeholder is replaced with the name of the items in the list (plural)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.one_item_found</td>
    <td>&lt;span class=&quot;pagebanner&quot;&gt; One {0} found. &lt;/span&gt;</td>
    <td>any string</td>
    <td>What is shown in the pagination header when one object is available in the list to be
      displayed. The single placeholder is replaced with the name of the items in the list (singular)</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.all_items_found</td>
    <td>&lt;span class=&quot;pagebanner&quot;&gt; {0} {1} found, displaying all {2}. &lt;/span&gt;</td>
    <td>any string</td>
    <td>
      What is shown in the pagination header when all the objects in the list are being shown. {0} and {2} are
      replaced with the number of objects in the list, {1} is replaced with the name of the items
      <a name="plural">plural</a>
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.some_items_found</td>
    <td>&lt;span class=&quot;pagebanner&quot;&gt; {0} {1} found, displaying {2} to {3}. &lt;/span&gt;
    </td>
    <td>any string</td>
    <td>What is shown in the pagination header when a partial list of the objects in the list are being
      shown. Parameters: * {0}: total number of objects in the list * {1}: name of the items (plural) * {2}:
      start index of the objects being shown * {3}: end index of the objects being shown * {4}: current page *
      {5}: total number of pages</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.group_size</td>
    <td>8</td>
    <td>any number</td>
    <td>The number of pages to show in the header that this person can instantly jump to</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.full</td>
    <td>&lt;span class=&quot;pagelinks&quot;&gt; [&lt;a href=&quot;{1}&quot;&gt;First&lt;/a&gt; /&lt;a
      href=&quot;{2}&quot;&gt;Prev&lt;/a&gt;] {0} [&lt;a href=&quot;{3}&quot;&gt;Next&lt;/a&gt; /&lt;a
      href=&quot;{4}&quot;&gt;Last&lt;/a&gt;] &lt;/span&gt;</td>
    <td>any string</td>
    <td>What is shown in the pagination bar when there are more pages and the selected page is not the
      first or the last one. Parameters: * {0}: numbered pages list * {1}: link to the first page * {2}: link to
      the previous page * {3}: link to the next page * {4}: link to the last page * {5}: current page * {6}:
      total number of pages</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.first</td>
    <td>&lt;span class=&quot;pagelinks&quot;&gt; [First/Prev] {0} [&lt;a
      href=&quot;{3}&quot;&gt;Next&lt;/a&gt; /&lt;a href=&quot;{4}&quot;&gt;Last&lt;/a&gt;] &lt;/span&gt;</td>
    <td>any string</td>
    <td>
      What is shown in the pagination bar when the first page is being shown. Placeholders are the same as for
      <tt>paging.banner.full</tt>
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.last</td>
    <td>&lt;span class=&quot;pagelinks&quot;&gt; [&lt;a href=&quot;{1}&quot;&gt;First&lt;/a&gt; /&lt;a
      href=&quot;{2}&quot;&gt;Prev&lt;/a&gt;] {0} [Next/Last] &lt;/span&gt;</td>
    <td>any string</td>
    <td>
      What is shown in the pagination bar when the last page is being shown. Placeholders are the same as for
      <tt>paging.banner.full</tt>
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.onepage</td>
    <td>&lt;span class=&quot;pagelinks&quot;&gt; {0}&lt;/span&gt; &lt;/span&gt;</td>
    <td>any string</td>
    <td>
      What is shown in the pagination bar when only one page is being shown. Placeholders are the same as for
      <tt>paging.banner.full</tt>
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.page.selected</td>
    <td>&lt;strong&gt;{0}&lt;/strong&gt;</td>
    <td>any string</td>
    <td>Selected page. {0} is replaced with the page number, {1} with the page url.</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.page.link</td>
    <td>&lt;a href=&quot;{1}&quot; title=&quot;Go to page {0}&quot;&gt;{0}&lt;/a&gt;</td>
    <td>any string</td>
    <td>Link to a page. {0} is replaced with the page number, {1} with the page url.</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>paging.banner.page.separator</td>
    <td>,</td>
    <td>any string</td>
    <td>separator between pages</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td><tt>export.banner.item</td></td>
    <td>&lt;a class="btn btn-default" href=\"{0}\">{1}&lt;/a></td>
    <td>any string</td>
    <td>Export link. {0} is replaced with the url, {1} with the export format name. Since displaytag 2.0</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>decorator.media.html</td>
    <td></td>
    <td>
      Class name of
      <tt>TableDecorator</tt>
      subclass
    </td>
    <td>
      Decorator used to render table as html. Required when also decorating the table in other media. Refer to
      the
      <a href="#tut_decorators.htmlTable_decorators_and_exports">exports decorators tutorial</a>
      for detailed explanation. Ignored if a decorator is configured in table tag's decorator attribute.
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>factory.requestHelper</td>
    <td>
      <tt>org.displaytag.util.DefaultRequestHelperFactory</tt>
    </td>
    <td>
      Class name for a valid
      <tt>RequestHelperFactory</tt>
      implementation
    </td>
    <td>RequestHelperFactory to be used. You can replace the default one if you need to generate links
      with a different format (for example in portal applications)</td>
    <td>yes</td>
    <td>no</td>
  </tr>
  <tr>
    <td>factory.decorator</td>
    <td>
      <tt>org.displaytag.decorator.DefaultDecoratorFactory</tt>
    </td>
    <td>
      DecoratorFactory to be used. Class name for a valid
      <tt>DecoratorFactory</tt>
      implementation
    </td>
    <td>yes You can replace the default one in order to load decorator instances from different sources
      (e.g. you could use Spring managed beans)</td>
    <td>no</td>
    <td></td>
  </tr>
  <tr>
    <td>comparator.default</td>
    <td>
      <tt>org.displaytag.model.DefaultComparator</tt>
    </td>
    <td>
      Default comparator to be used when sorting columns. Class name for a valid
      <tt>Comparator</tt>
      implementation
    </td>
    <td>yes</td>
    <td>yes</td>
    <td></td>
  </tr>
</table>
        

### Exporting

Displaytag supports exporting to excel, csv, pdf, rtf, and xml formats.
Some configurable properties are specific for one of these format.

Replace *export name* in the property name with "excel", "csv", "pdf",
"rtf", or "xml". Some of the properties won't work in any export format.

<table  class="table table-bordered table-striped table-hover">
  <tr>
    <th >Property</th>
    <th >Default</th>
    <th >Valid Values</th>
    <th >Description</th>
    <th >allowed in properties</th>
    <th >allowed in setProperty</th>
  </tr>
  <tr>
    <td>export.types</td>
    <td>csv excel xml pdf</td>
    <td>any string</td>
    <td>Whitespace separated list of configured export types</td>
    <td>yes</td>
    <td>no</td>
  </tr>
  <tr>
    <td>
      export.
      <i>export name</i>
    </td>
    <td>true</td>
    <td>true, false</td>
    <td>Should the tag present the option to export data in this specific format</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>
      export.
      <i>export name</i>
      .class
    </td>
    <td></td>
    <td>
      Any valid class that implements the
      <tt>org.displaytag.export.ExportView interface</tt>
    </td>
    <td>Fully qualified class name for the class which will be used for exporting</td>
    <td>yes</td>
    <td>no</td>
  </tr>
  <tr>
    <td>
      export.
      <i>export name</i>
      .label
    </td>
    <td>
      &lt;span class=&quot;export
      <i>export name</i>&quot;&gt;
      <i>export name</i>
      &lt;/span&gt;
    </td>
    <td>Any string</td>
    <td>The label on the link that the user clicks on to export the data in a specific format</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>
      export.
      <i>export name</i>
      .include_header
    </td>
    <td>false</td>
    <td>true, false</td>
    <td>If set to true, the first line of the export will contain column titles as displayed on the
      HTML page.</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>
      export.
      <i>export name</i>
      .filename
    </td>
    <td>none</td>
    <td>any valid file name</td>
    <td>When saving exported files the user will be prompted to use this file name</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>export.amount</td>
    <td>list</td>
    <td>page, list</td>
    <td>Indicates how much data should be sent down to the user when they ask for a data export. By
      default, it sends the entire list. You can instruct the table tag to only send down the data that is
      currently being shown on the page</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>export.decorated</td>
    <td>true</td>
    <td>true, false</td>
    <td>Should
      the data be &quot;decorated&quot; as it is exported. Note that
      <tt>org.displaytag.export.excel.DefaultHssfExportView</tt>
      ,
      <tt>org.displaytag.export.DefaultPdfExportView</tt>
      , and
      <tt>org.displaytag.export.DefaultRtfExportView</tt>
      do not observe this value.
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>
      decorator.media.
      <i>export name</i>
    </td>
    <td></td>
    <td>
      Class name of
      <tt>TableDecorator</tt>
      subclass
    </td>
    <td>
      Decorator used to render table in given media. Refer to the
      <a href="#tut_decorators.htmlTable_decorators_and_exports">exports and decorators tutorial</a>
      for detailed explanation. Ignored if a decorator is configured in table tag's decorator attribute.
    </td>
    <td>yes</td>
    <td>yes</td>
  </tr>
</table>

### CSS

<table  class="table table-bordered table-striped table-hover">
  <tr>
    <th >Property</th>
    <th >Default</th>
    <th >Valid Values</th>
    <th >Description</th>
    <th >allowed in properties</th>
    <th >allowed in setProperty</th>
  </tr>
  <tr>
    <td>css.tr.even</td>
    <td>even</td>
    <td>any valid css class name</td>
    <td>css class automatically added to even rows</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.tr.odd</td>
    <td>odd</td>
    <td>any valid css class name</td>
    <td>css class automatically added to odd rows</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.th.sorted</td>
    <td>sorted</td>
    <td>any valid css class name</td>
    <td>css class automatically added to the header of sorted columns</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.th.ascending</td>
    <td>order1</td>
    <td>any valid css class name</td>
    <td>css class automatically added to the header of a column sorted in ascending order</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.th.descending</td>
    <td>order2</td>
    <td>any valid css class name</td>
    <td>css class automatically added to the header of a column sorted in descending order</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.table</td>
    <td>none</td>
    <td>any valid css class name</td>
    <td>css class automatically added to the main table tag</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
  <tr>
    <td>css.th.sortable</td>
    <td>none</td>
    <td>any valid css class name</td>
    <td>css class automatically added to any sortable column</td>
    <td>yes</td>
    <td>yes</td>
  </tr>
</table>


