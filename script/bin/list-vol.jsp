<%@ page import="com.model.*" %>
<%
VolModel[] vols = (VolModel[]) request.getAttribute("vols");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Vols</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        h1 {
            color: #333;
            background: #007BFF;
            padding: 15px;
            border-radius: 10px;
            color: white;
            display: inline-block;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007BFF;
            color: white;
            text-transform: uppercase;
        }

        tr:hover {
            background-color: #f1f1f1;
            transition: 0.3s;
        }

        td {
            color: #333;
        }
    </style>
</head>
<body>

    <h1>Pseudo: <%= request.getAttribute("pseudo") %></h1>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Avion</th>
                <th>Date et Heure</th>
                <th>Depart</th>
                <th>Arrivee</th>
            </tr>
        </thead>
        <tbody>
            <% if (vols != null && vols.length > 0) { %>
    <% for (int i = 0; i < vols.length; i++) { %>
        <tr>
            <td><strong><%= i + 1 %></strong></td>
            <td><%= vols[i].getAvion().getNum() %></td>
            <td><%= vols[i].getDateHeureVol() %></td>
            <td><%= vols[i].getDepart().getNom() %></td>
            <td><%= vols[i].getArrive().getNom() %></td>
        </tr>
    <% } %>
<% } else { %>
    <tr>
        <td colspan="5">Aucun vol disponible.</td>
    </tr>
<% } %>

        </tbody>
    </table>

</body>
</html>
