<%@ page import="weatherstyle.gestionesuggerimentiia.applicationlogic.logic.beans.Suggerimento" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: rafau
  Date: 28/01/2023
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% List<Suggerimento> suggerimentoList = (List<Suggerimento>) request.getAttribute("suggerimentoList"); %>
<%for (Suggerimento suggerimento: suggerimentoList) { %>
    <p>
        <%=suggerimento.toString()%>
    </p>
<% } %>

</body>
</html>
