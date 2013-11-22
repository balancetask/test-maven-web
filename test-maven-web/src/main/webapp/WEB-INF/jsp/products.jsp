<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head><title>Products List</title></head>
  <body>
    <h2>Products list</h2>
    <c:forEach items="${products}" var="prod">
      <p><c:out value="${prod.description}"/> <i>$<c:out value="${prod.price}"/></i></p>
    </c:forEach>
    
    <p>Copyright &copy; BalanceTask, Inc.</p>
    
  </body>
</html>
