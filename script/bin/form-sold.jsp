<%@ page import="com.model.*" %>
<%

String id_vol=(String) request.getAttribute("id_vol");
AvionSiegeModel[] avionSieges=(AvionSiegeModel[]) request.getAttribute("avionSieges");

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
    <form action="${pageContext.request.contextPath}/sold.save" method="post">
        
        <label for="date">pourcentage:</label>
        <input type="number" id="pourcentage" name="sold.pourcentage" required>
        <%= request.getAttribute("error_.sold.pourcentage") != null ? request.getAttribute("error_sold.pourcentage") : "" %>

        <label for="siege">siege:</label>
        <select  id="siege" name="sold.siege" >
            <option selected>choissir type de siege</option>
                <% for(int i=0; i<avionSieges.length; i++){ %>
                    <option value="<%= avionSieges[i].getId() %>" > <%= avionSieges[i].getSiege().getNom() %> - <%= avionSieges[i].getPrix() %></option>
                <% }%>
        </select>

            <input type="hidden" name="sold.vol" value="<%=id_vol %>">

        <input type="submit" value="Valider">
    </form>
    
</body>
</html>