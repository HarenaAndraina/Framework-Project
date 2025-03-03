<%@ page import="com.model.*" %>
<%
String vol = (String) request.getAttribute("id_vol");
AvionSiegeModel[] avionSieges = (AvionSiegeModel[]) request.getAttribute("avionSieges");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Vol et Réservation</title>
    <style>
        :root {
            --primary-color: #3a86ff;
            --primary-dark: #2667cc;
            --secondary-color: #ff006e;
            --success-color: #38b000;
            --warning-color: #ffbe0b;
            --danger-color: #ff5a5f;
            --text-color: #333;
            --text-light: #767676;
            --gray-light: #f5f7fa;
            --gray-medium: #e9ecef;
            --border-color: #dee2e6;
            --white: #ffffff;
            --shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            color: var(--text-color);
            line-height: 1.6;
            padding: 0;
            margin: 0;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }
        
        header {
            text-align: center;
            margin-bottom: 40px;
        }
        
        h1 {
            color: var(--text-color);
            font-size: 2.2rem;
            margin-bottom: 8px;
            font-weight: 700;
        }
        
        h2 {
            color: var(--text-color);
            font-size: 1.8rem;
            margin: 30px 0 20px;
            font-weight: 600;
            text-align: center;
        }
        
        .subtitle {
            color: var(--text-light);
            font-size: 1.1rem;
        }
        
        /* Card styles */
        .card {
            background-color: var(--white);
            border-radius: 10px;
            box-shadow: var(--shadow);
            overflow: hidden;
            margin-bottom: 30px;
        }
        
        .card-header {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 15px 20px;
        }
        
        .card-header h3 {
            margin: 0;
            font-size: 1.4rem;
        }
        
        .card-body {
            padding: 20px;
        }
        
        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th, td {
            padding: 16px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }
        
        th {
            background-color: var(--gray-light);
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
        }
        
        tr:last-child td {
            border-bottom: none;
        }
        
        tr:hover td {
            background-color: rgba(58, 134, 255, 0.05);
        }
        
        .price {
            font-weight: 600;
            color: var(--primary-color);
        }
        
        .promotion {
            color: var(--secondary-color);
            font-weight: 600;
        }
        
        .no-promotion {
            color: var(--text-light);
            font-style: italic;
        }
        
        /* Form styles */
        .form-reservation {
            max-width: 700px;
            margin: 0 auto;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-color);
        }
        
        input[type="date"],
        select {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-size: 16px;
            transition: all 0.3s ease;
            background-color: var(--white);
        }
        
        input[type="date"]:focus,
        select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(58, 134, 255, 0.15);
            outline: none;
        }
        
        .error-message {
            color: var(--danger-color);
            font-size: 0.9rem;
            margin-top: 5px;
            display: block;
        }
        
        .btn-submit {
            display: block;
            width: 100%;
            padding: 14px;
            background-color: var(--primary-color);
            color: var(--white);
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-align: center;
            margin-top: 20px;
        }
        
        .btn-submit:hover {
            background-color: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(58, 134, 255, 0.2);
        }
        
        .btn-submit:active {
            transform: translateY(0);
        }
        
        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 50px;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
        }
        
        .badge-success {
            background-color: rgba(56, 176, 0, 0.1);
            color: var(--success-color);
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                padding: 20px 15px;
            }
            
            h1 {
                font-size: 1.8rem;
            }
            
            h2 {
                font-size: 1.5rem;
            }
            
            .card-header h3 {
                font-size: 1.2rem;
            }
            
            th, td {
                padding: 12px 8px;
                font-size: 0.9rem;
            }
            
            th {
                font-size: 0.8rem;
            }
        }
        
        @media (max-width: 576px) {
            th:first-child, td:first-child {
                display: none;
            }
            
            th, td {
                padding: 10px 8px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Details du Vol</h1>
            <p class="subtitle">Consultez les informations et reservez votre siege</p>
        </header>
        
        <div class="card">
            <div class="card-header">
                <h3>Options de sieges disponibles</h3>
            </div>
            <div class="card-body">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Type de siege</th>
                            <th>Prix</th>
                            <th>Promotion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (avionSieges != null && avionSieges.length > 0) { %>
                            <% for (int i = 0; i < avionSieges.length; i++) { %>
                                <tr>
                                    <td><strong><%= i + 1 %></strong></td>
                                    <td><%= avionSieges[i].getSiege().getNom() %></td>
                                    <td class="price"><%= avionSieges[i].getPrix() %> ar</td>
                                    <td>
                                        <% if (avionSieges[i].getPromotion() != null) { %>
                                            <span class="promotion"><%= avionSieges[i].getPromotion() %></span>
                                        <% } else { %>
                                            <span class="no-promotion">Non disponible</span>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        <% } else { %>
                            <tr>
                                <td colspan="4">Aucun détail disponible pour ce vol.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        
        <h2>Reservation</h2>
        
        <div class="card">
            <div class="card-header">
                <h3>Formulaire de reservation</h3>
            </div>
            <div class="card-body">
                <form class="form-reservation" action="${pageContext.request.contextPath}/res.save" method="post">
                    <div class="form-group">
                        <label for="dateHeurre">Date de voyage:</label>
                        <input type="datetime-local" id="dateHeurre" name="res.dateHeure" required>
                        <% if (request.getAttribute("error_res.dateHeure") != null) { %>
                            <span class="error-message"><%= request.getAttribute("error_res.dateHeure") %></span>
                        <% } %>
                    </div>
                    
                    <div class="form-group">
                        <label for="siege">Type de siege:</label>
                        <select id="siege" name="res.siege" required>
                            <option value="" disabled selected>Choisissez votre type de siege</option>
                            <% if (avionSieges != null) { %>
                                <% for (int i = 0; i < avionSieges.length; i++) { %>
                                    <option value="<%= avionSieges[i].getId() %>">
                                        <%= avionSieges[i].getSiege().getNom() %>
                                    </option>
                                <% } %>
                            <% } %>
                        </select>
                    </div>
        
                    <input type="hidden" name="res.vol" value="<%= vol %>">
                    <input type="hidden" name="res.utilisateur" value="<%= session.getAttribute("user") %>">
        
                    <button type="submit" class="btn-submit">Confirmer la reservation</button>
                </form>
            </div>
        </div>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/res.get" style="color: var(--primary-color); text-decoration: none; font-weight: 500;">
                &larr; Retour a la liste des vols
            </a>
        </div>
    </div>
</body>
</html>