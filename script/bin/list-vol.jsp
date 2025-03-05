<%@ page import="com.model.*" %>
<%
VolModel[] vols = (VolModel[]) request.getAttribute("vols");

AvionModel[] avions = (AvionModel[]) request.getAttribute("avions");
VilleModel[] villes = (VilleModel[]) request.getAttribute("villes");
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

    <h1>Liste des Vols</h1>

    <!-- Lien pour ajouter un nouveau vol -->
    <a href="${pageContext.request.contextPath}/vol.create" class="btn-add">Ajouter un Vol</a>
    

<form action="${pageContext.request.contextPath}/vol.filter" method="post">

    <label for="date">date et heure de depart:</label>
    <input type="datetime-local" id="dateHeure" name="dateHeureVol"  >

    <label for="depart">depart:</label>
    <select  id="depart" name="depart" >
        <option value="-1" >choissir ville de depart</option>
            <% for(int i=0; i<villes.length; i++){ %>
                <option value="<%= villes[i].getId() %>" > <%= villes[i].getNom() %></option>
            <% }%>
    </select>

        <label for="arrive">arrive:</label>
        <select  id="arrive" name="arrive" >
            <option value="-1" >choissir ville d'arrive</option>
                <% for(int i=0; i<villes.length; i++){ %>
                    <option value="<%= villes[i].getId() %>" ><%= villes[i].getNom() %></option>
                <% }%>
        </select>   

        <label for="avion">avion:</label>
        <select  id="avion" name="avion" >
            <option value="-1" >choissir avion</option>
                <% for(int i=0; i<avions.length; i++){ %>
                    <option value="<%= avions[i].getId() %>" ><%= avions[i].getNum() %></option>
                <% }%>
        </select> 

        <input type="submit" value="Valider">
</form>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Avion</th>
                <th>Date et Heure</th>
                <th>Depart</th>
                <th>Arrivee</th>
                <th>Actions</th>
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
                        <td class="action-buttons">
                           <a href="${pageContext.request.contextPath}/res.add?id=<%= vols[i].getId() %>" class="btn-add">Ajouter gestion de reservation</a>
                            <a href="${pageContext.request.contextPath}/vol.sold?id_vol=<%= vols[i].getId() %>" class="btn btn-add">add sold</a>
                            <a href="${pageContext.request.contextPath}/vol.update?id=<%= vols[i].getId() %>" class="btn btn-edit">Modifier</a>
                            <a href="${pageContext.request.contextPath}/vol.delete?id=<%= vols[i].getId() %>" class="btn btn-delete" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce vol ?');">Supprimer</a>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="6">Aucun vol disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
