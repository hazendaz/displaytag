<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project">
  <jsp:directive.attribute name="name" type="java.lang.String" required="true" rtexprvalue="true"/>
  <c:choose>
    <c:when test="${name eq 'menu'}">
    <![CDATA[
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- * samples menu -->
<ins class="adsbygoogle"
     style="display:inline-block;width:200px;height:200px"
     data-ad-client="ca-pub-0253669611586475"
     data-ad-slot="5744247421"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
]]>
    </c:when>
    <c:when test="${name eq 'header'}">
    <!-- 
<![CDATA[
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-0253669611586475"
     data-ad-slot="0807744234"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
]]> -->
    </c:when>
    <c:when test="${name eq 'footer'}">
<![CDATA[
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- * samples footer -->
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-0253669611586475"
     data-ad-slot="7068462123"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
]]>
    </c:when>
    <c:when test="${name eq 'middle'}">
<![CDATA[
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- * samples middle -->
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-0253669611586475"
     data-ad-slot="5800761039"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
]]>
    </c:when>
    

  </c:choose>
</jsp:root>