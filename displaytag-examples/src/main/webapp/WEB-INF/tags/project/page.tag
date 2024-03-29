<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project">
  <jsp:text>
    <![CDATA[<!DOCTYPE html>]]>
  </jsp:text>
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <title> The &lt;display:*> tag library 
      </title>
      <meta http-equiv="Expires" content="-1"/>
      <meta http-equiv="Pragma" content="no-cache"/>
      <meta http-equiv="Cache-Control" content="no-cache"/>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
      <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
      <meta name="viewport" content="width=device-width, initial-scale=1"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/docroot/css/bootstrap.min.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/docroot/css/bootstrap-theme.min.css"/>
      <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.1/styles/default.min.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/docroot/css/s-main.css"/>
    </head>
    <body>
    <![CDATA[
<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-5J63C8"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-5J63C8');</script>
<!-- End Google Tag Manager -->
]]>
      <div class="navbar navbar-default" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">
              <span class="svg-displaytag-logo-standard">displaytag</span>
            </a>
          </div>
          <tags:ad name="header"/>
          <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
              <li>
                <a href="http://www.displaytag.org/20/download.html">Download</a>
              </li>
              <li>
                <a href="http://displaytag.sf.net">Documentation</a>
              </li>
              <li class="active">
                <a href="index.jsp">Examples</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-9" role="main">
            <jsp:doBody/>
            <tags:ad name="footer"/>
          </div>
          <div class="col-md-3">
            <div class="samples-sidebar hidden-print affix-top" role="complementary">
              <tags:ad name="menu"/>
              <tags:menu/>
              <tags:ad name="menu"/>
            </div>
          </div>
        </div>
      </div>
      <footer>
        <div class="xright">&#169; 2002-2014 the Displaytag team</div>
      </footer>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">/**/</script>
      <script src="${pageContext.request.contextPath}/docroot/js/bootstrap.min.js">/**/</script>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.1/highlight.min.js">/**/</script>
      <script>hljs.initHighlightingOnLoad();</script>
    </body>
  </html>
</jsp:root>
