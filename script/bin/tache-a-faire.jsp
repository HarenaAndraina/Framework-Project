<%@ page import="viewsClasses.Employe" %>
<%
Employe[] emp = (Employe[]) request.getAttribute("tache");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tache a faire</title>
</head>
<body>
    <h1>Taches a faire</h1>
    <ul>
        <%
        if (emp != null) {
            for (Employe e : emp) {
        %>
        <li><%= e.getTache() %></li>
        <%
            }
        } else {
        %>
        <li>Aucune tache a afficher</li>
        <%
        }
        %>
    </ul>
    <a href="acceuil.jsp">retour</a>
</body>
</html>
