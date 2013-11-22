<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Price Increase</title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h2>Price Increase</h2>
<form:form method="post">
  <p>Increase (%):</p>
  <p><input name="percentage" /></p>
  <p><input type="submit" value="Execute"></p>
</form:form>
</body>
</html>
