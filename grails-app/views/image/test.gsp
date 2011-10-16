
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
  <meta name="layout" content="main" />
		<title>The uploaded image</title>
	</head>
  	<body>
  		<b> caller = ${session.caller} </b><br>
      <b> x-limit = ${session.xlimit} </b><br>
      <b> y-limit = ${session.ylimit} </b><br>
      <b> s-limit = ${session.slimit} </b><br>
      <b> fmt = ${session.format} </b><br>
    	<div style="margin:10px;"><img src="http://localhost:8080/ImageUtil/image/downloadFile/3"></div>
  	  <button type="button" value="Use Image">Use Image</button>
      <button type="button" value="Upload Image" onClick="window.location.href='http://localhost:8080/ImageUtil/image/upload.gsp'">Upload Image</button>
    </body>
</html>