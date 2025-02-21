<%@ page import="com.model.*" %>
<%
AvionModel[] avions = (AvionModel[]) request.getAttribute("avions");
VilleModel[] villes = (VilleModel[]) request.getAttribute("villes");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>insert vol Page</title>
    <style>
        /* Style général du corps de la page */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Style du formulaire */
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        /* Style des étiquettes */
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }

        /* Style des champs de saisie */
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }

        /* Style du bouton de soumission */
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        /* Effet au survol du bouton */
        input[type="submit"]:hover {
            background-color: #218838;
        }

        /* Style pour les messages d'erreur ou de succès (optionnel) */
        .message {
            margin-top: 15px;
            padding: 10px;
            border-radius: 4px;
            text-align: center;
            font-size: 14px;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
        }
    </style>
</head>
<body>
    <form action="${pageContext.request.contextPath}/vol.save" method="post">
        
        <label for="date">date et heure:</label>
        <input type="datetime-local" id="dateHeure" name="vol.dateHeureVol" required>
        <%= request.getAttribute("error_vol.dateHeureVol") != null ? request.getAttribute("error_vol.dateHeureVol") : "" %>


        <label for="depart">depart:</label>
        <select  id="depart" name="vol.depart" >
            <option selected>choissir ville de depart</option>
                <% for(int i=0; i<villes.length; i++){ %>
                    <option value="<%= villes[i].getId() %>" ><%= villes[i].getNom() %></option>
                <% }%>
        </select>

        <label for="arrive">arrive:</label>
        <select  id="arrive" name="vol.arrive" >
            <option selected>choissir ville d'arrive</option>
                <% for(int i=0; i<villes.length; i++){ %>
                    <option value="<%= villes[i].getId() %>" ><%= villes[i].getNom() %></option>
                <% }%>
        </select>   

        <label for="avion">avion:</label>
        <select  id="avion" name="vol.avion" >
            <option selected>choissir avion</option>
                <% for(int i=0; i<avions.length; i++){ %>
                    <option value="<%= avions[i].getId() %>" ><%= avions[i].getNum() %></option>
                <% }%>
        </select> 

        <input type="submit" value="Valider">
    </form>
</body>
</html>