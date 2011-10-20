
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
  <script type="text/javascript">
  function callback() {
      if (window.opener != null)
      {
        window.opener.document.getElementByID("avatar").src="${createLink(controller:"image",action:"downloadFile")}";
      }
      window.close();
  
  }
  
  </script>
  <meta name="layout" content="main" />
		<title>The uploaded image</title>
	</head>
  	<body>
  		<b> caller = ${session.caller} </b><br>
      <b> x-limit = ${session.xlimit} </b><br>
      <b> y-limit = ${session.ylimit} </b><br>
      <b> s-limit = ${session.slimit} </b><br>
      <b> fmt = ${session.format} </b><br>
    	<div style="margin:10px;"><img src="<g:createLink controller='image' action='renderImage' id='${params.id}'/>"/></div>
      <button type="button" value="Use Image" onClick="callback();">Use Image</button>
      <button type="button" value="Upload Image" onClick="window.location.href='http://localhost:8080/ImageUtil/image/upload.gsp'">Upload Image</button>
    </body>
</html>