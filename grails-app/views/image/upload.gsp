
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta name="layout" content="main" />
		<title>Upload an image</title>
	</head>
	<body>
      <b> caller = ${params.caller} </b><br>
      <b> x-limit = ${params['x-limit']} </b><br>
      <b> y-limit = ${params['y-limit']} </b><br>
      <b> s-limit = ${params['s-limit']} </b><br>
      <b> fmt = ${params.fmt} </b><br>
      <g:uploadForm name="imageUploadForm" controller="image" action="uploadImage">
		  	<g:hasErrors>
		  		<g:eachError><p style="color:red"><g:message error="${it}"/></p></g:eachError>
		  	</g:hasErrors>
		  	<div>
		  		<label for="photo">Upload a photo:</label>&nbsp;
		  		<input name="photo" type="file" accept="image/jpeg">
		  	</div>
		  	<g:submitButton name="submit" value="Upload photo"/>	
		    <g:hiddenField name="caller" value='${params.caller}'/>
        <g:hiddenField name="xlimit" value="${params['x-limit']}"/>
        <g:hiddenField name="ylimit" value="${params['y-limit']}" />
        <g:hiddenField name="slimit" value="${params['s-limit']}" />
        <g:hiddenField name="format" value="${params.fmt}" />
      </g:uploadForm>	
	</body>
</html>