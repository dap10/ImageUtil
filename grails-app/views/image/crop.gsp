
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
  <meta name="layout" content="main" />
  <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.Jcrop.css')}" />
  <g:javascript plugin="jquery" />
  <g:javascript src="jcrop/jquery.min.js" />
  <g:javascript src="jcrop/jquery.Jcrop.js" />
  <g:javascript src="jcrop/jquery.Jcrop.min.js" />	
    <title>The uploaded image</title>
    <script type="text/javascript">
 function updateCoords(c) {
     $('#x1').val(c.x);
        $('#y1').val(c.y);
        $('#x2').val(c.x2);
        $('#y2').val(c.y2);
 }
</script>

    
	<script language="Javascript">
    $(function() {
        $('#picture').Jcrop({
            aspectRatio: 0,
            setSelect: [0, 0, 100, 100],
            minSize: [0, 0],
            onSelect: updateCoords,
            onChange: updateCoords
        });
    });
</script>

  </head>
  	<body>
  	  <b> caller = ${session.caller} </b><br>
      <b> x-limit = ${session.xlimit} </b><br>
      <b> y-limit = ${session.ylimit} </b><br>
      <b> s-limit = ${session.slimit} </b><br>
      <b> fmt = ${session.format} </b><br>
    	<div style="margin:10px;"><img id ="picture" src="<g:createLink controller='image' action='renderImage' id='${params.id}'/>"/></div>
  	
    <g:form action="savePicture">
    <g:hiddenField name="x1" value="0" />
    <g:hiddenField name="y1" value="0" />
    <g:hiddenField name="x2" value="100" />
    <g:hiddenField name="y2" value="100" />
    <g:hiddenField name="id" value='${params.id}'/>
    <g:submitButton name="Crop and Finish" />

    
</g:form>

    </body>
</html>