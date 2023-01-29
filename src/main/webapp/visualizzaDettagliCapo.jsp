<%@ page import="weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Maglia" %>
<%@ page import="weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Pantaloni" %>
<%@ page import="weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Scarpe" %>
<%@ page import="weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.CapoAbbigliamento" %><%--
  Created by IntelliJ IDEA.
  User: migli
  Date: 28/01/2023
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dettagli capo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<%
    CapoAbbigliamento c = (CapoAbbigliamento) request.getAttribute("capo");
%>

<%
if (c instanceof Maglia){
    Maglia m = (Maglia) c;
%>

<div class="card" style="width: 18rem;">
    <img src="<%=m.getDirImmagine()%>" class="card-img-top">
    <div class="card-body">
        <h5 class="card-title"><%=m.getNome()%>></h5>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Categoria: Maglia</li>
        <li class="list-group-item">Materiale: <%=m.getMateriale()%></li>
        <li class="list-group-item">Lunghezza manica: <%=m.getLunghezzaManica()%></li>
        <li class="list-group-item">Colore: <%=m.getColore()%></li>
        <li class="list-group-item">Stagione: <%=m.getStagione()%></li>
    </ul>
    <div class="card-body">
        <form action="elimina-capo" method="post">
            <input type="hidden" value="<%=m.getId()%>" name="idMaglia" id="idMaglia">
            <input type="submit" value="Elimina capo">
        </form>
    </div>
</div>

<%
    }
%>

<%
    if (c instanceof Pantaloni){
        Pantaloni p = (Pantaloni) c;
%>

<div class="card" style="width: 18rem;">
    <img src="<%=p.getDirImmagine()%>" class="card-img-top">
    <div class="card-body">
        <h5 class="card-title"><%=p.getNome()%>></h5>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Categoria: Pantaloni</li>
        <li class="list-group-item">Materiale: <%=p.getMateriale()%></li>
        <li class="list-group-item">Lunghezza: <%=p.getLunghezza()%></li>
        <li class="list-group-item">Colore: <%=p.getColore()%></li>
        <li class="list-group-item">Stagione: <%=p.getStagione()%></li>
    </ul>
    <div class="card-body">
        <form action="elimina-capo" method="post">
            <input type="hidden" value="<%=p.getId()%>" name="idPantaloni" id="idPantaloni">
            <input type="submit" value="Elimina capo">
        </form>
    </div>
</div>

<%
    }
%>

<%
    if (c instanceof Scarpe){
        Scarpe s = (Scarpe) c;
%>

<div class="card" style="width: 18rem;">
    <img src="<%=s.getDirImmagine()%>" class="card-img-top">
    <div class="card-body">
        <h5 class="card-title"><%=s.getNome()%>></h5>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Categoria: Scarpe</li>
        <li class="list-group-item">Tipo: <%=s.getTipo()%></li>
        <li class="list-group-item">Colore: <%=s.getColore()%></li>
        <li class="list-group-item">Stagione: <%=s.getStagione()%></li>
        <li class="list-group-item">Antiscivolo: <%=s.isAntiscivolo()%></li>
        <li class="list-group-item">Impermeabile: <%=s.isImpermeabile()%></li>
    </ul>
    <div class="card-body">
        <form action="elimina-capo" method="post">
            <input type="hidden" value="<%=s.getId()%>" name="idScarpe" id="idScarpe">
            <input type="submit" value="Elimina capo">
        </form>
    </div>
</div>

<%
    }
%>
</body>
</html>