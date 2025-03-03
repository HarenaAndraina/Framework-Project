<%@ page import="com.model.*" %>
<%

ReservationModel[] reservations = (ReservationModel[]) request.getAttribute("reservations");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des reservations</title>
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

        .action-buttons {
            display: flex;
            gap: 10px;
            justify-content: center;
        }

        .btn {
            padding: 8px 12px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-edit {
            background-color: #28a745;
            color: white;
        }

        .btn-delete {
            background-color: #dc3545;
            color: white;
        }

        .btn-add {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }

        .btn-add:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <h1>Liste de vos reservations</h1>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Avion</th>
                <th>Date et Heure</th>
                <th>Depart</th>
                <th>Arrivee</th>
                <th>siege</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% if (reservations != null && reservations.length > 0) { %>
                <% for (int i = 0; i < reservations.length; i++) { %>
                    <tr>
                        <td><strong><%= i + 1 %></strong></td>
                        <td><%= reservations[i].getVol().getAvion().getNum() %></td>
                        <td><%= reservations[i].getVol().getDateHeureVol() %></td>
                        <td><%= reservations[i].getVol().getDepart().getNom() %></td>
                        <td><%= reservations[i].getVol().getArrive().getNom() %></td>
                        <td><%= reservations[i].getAvionSiege().getSiege().getNom() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/res.annulation?id=<%= reservations[i].getId() %>" class="btn btn-delete">annuler</a>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="6">Aucun reservation disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
