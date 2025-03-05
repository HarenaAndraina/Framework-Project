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
        :root {
            --primary: #2563eb;
            --primary-dark: #1d4ed8;
            --secondary: #6b7280;
            --success: #10b981;
            --danger: #ef4444;
            --warning: #f59e0b;
            --light: #f3f4f6;
            --dark: #1f2937;
            --white: #ffffff;
            --shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
        }

        body {
            background-color: #f9fafb;
            color: var(--dark);
            line-height: 1.5;
            padding: 0;
            margin: 0;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            flex-wrap: wrap;
            gap: 20px;
        }

        .header-title {
            flex: 1;
        }

        h1 {
            color: var(--dark);
            font-size: 2rem;
            margin-bottom: 10px;
            font-weight: 700;
        }

        .subtitle {
            color: var(--secondary);
            font-size: 1rem;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 10px 16px;
            background-color: var(--primary);
            color: var(--white);
            border: none;
            border-radius: 6px;
            font-weight: 500;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.2s ease;
            gap: 8px;
        }

        .btn:hover {
            background-color: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-success {
            background-color: var(--success);
        }

        .btn-success:hover {
            background-color: #0ca678;
        }

        /* Card styles */
        .card {
            background-color: var(--white);
            border-radius: 10px;
            overflow: hidden;
            box-shadow: var(--shadow);
            margin-bottom: 30px;
        }

        .card-header {
            background-color: var(--light);
            padding: 15px 20px;
            border-bottom: 1px solid #e5e7eb;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: var(--dark);
        }

        .card-body {
            padding: 20px;
        }

        /* Filter form */
        .filter-form {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 0;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--dark);
            font-size: 14px;
        }

        input, select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #d1d5db;
            border-radius: 6px;
            font-size: 15px;
            background-color: var(--white);
            transition: border-color 0.2s ease;
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
        }

        .form-submit {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }

        /* Table styles */
        .table-responsive {
            overflow-x: auto;
            border-radius: 6px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }

        th {
            background-color: var(--light);
            font-weight: 600;
            padding: 12px 16px;
            border-bottom: 1px solid #e5e7eb;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            color: var(--secondary);
        }

        td {
            padding: 12px 16px;
            border-bottom: 1px solid #e5e7eb;
            vertical-align: middle;
        }

        tr:hover td {
            background-color: rgba(37, 99, 235, 0.05);
        }

        tr:last-child td {
            border-bottom: none;
        }

        .flight-number {
            font-weight: 600;
            color: var(--dark);
        }

        .flight-date {
            color: var(--secondary);
        }

        .flight-city {
            font-weight: 500;
        }

        .action-cell {
            text-align: right;
        }

        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: var(--secondary);
        }

        .empty-state-icon {
            font-size: 40px;
            margin-bottom: 15px;
        }

        .empty-state-message {
            font-size: 16px;
            margin-bottom: 20px;
        }

        /* Badge styles */
        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 50px;
            font-size: 12px;
            font-weight: 500;
        }

        .badge-primary {
            background-color: rgba(37, 99, 235, 0.1);
            color: var(--primary);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .form-submit {
                justify-content: center;
                width: 100%;
            }
            
            .form-submit .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="header-title">
                <h1>Liste des vols disponibles</h1>
                <p class="subtitle">Consultez et reservez votre prochain voyage</p>
            </div>
            <a href="${pageContext.request.contextPath}/res.own" class="btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
                Mes reservations
            </a>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Filtrer les vols</h2>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/res.filter" method="post" class="filter-form">
                    <div class="form-group">
                        <label for="dateHeure">Date et heure de depart</label>
                        <input type="datetime-local" id="dateHeure" name="dateHeureVol">
                    </div>
        
                    <div class="form-group">
                        <label for="depart">Ville de depart</label>
                        <select id="depart" name="depart">
                            <option value="-1">Toutes les villes</option>
                            <% for(int i=0; i<villes.length; i++){ %>
                                <option value="<%= villes[i].getId() %>"><%= villes[i].getNom() %></option>
                            <% } %>
                        </select>
                    </div>
        
                    <div class="form-group">
                        <label for="arrive">Ville d'arrivee</label>
                        <select id="arrive" name="arrive">
                            <option value="-1">Toutes les villes</option>
                            <% for(int i=0; i<villes.length; i++){ %>
                                <option value="<%= villes[i].getId() %>"><%= villes[i].getNom() %></option>
                            <% } %>
                        </select>
                    </div>
        
                    <div class="form-group">
                        <label for="avion">Type d'avion</label>
                        <select id="avion" name="avion">
                            <option value="-1">Tous les avions</option>
                            <% for(int i=0; i<avions.length; i++){ %>
                                <option value="<%= avions[i].getId() %>"><%= avions[i].getNum() %></option>
                            <% } %>
                        </select>
                    </div>
                    
                    <div class="form-submit" style="grid-column: 1 / -1;">
                        <button type="submit" class="btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="11" cy="11" r="8"></circle>
                                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                            </svg>
                            Rechercher
                        </button>
                    </div>
                </form>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Resultats</h2>
            </div>
            <div class="card-body">
                <div class="table-responsive">
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
                                        <td><span class="flight-number"><%= i + 1 %></span></td>
                                        <td>
                                            <span class="badge badge-primary"><%= vols[i].getAvion().getNum() %></span>
                                        </td>
                                        <td><span class="flight-date"><%= vols[i].getDateHeureVol() %></span></td>
                                        <td><span class="flight-city"><%= vols[i].getDepart().getNom() %></span></td>
                                        <td><span class="flight-city"><%= vols[i].getArrive().getNom() %></span></td>
                                        <td class="action-cell">
                                            <a href="${pageContext.request.contextPath}/res.details?id=<%= vols[i].getId() %>" class="btn btn-success">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <circle cx="11" cy="11" r="8"></circle>
                                                    <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                                </svg>
                                                Details
                                            </a>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } else { %>
                                <tr>
                                    <td colspan="6">
                                        <div class="empty-state">
                                            <div class="empty-state-icon">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                    <path d="M17.8 19.2 16 11l3.5-3.5C21 6 21.5 4 21 3c-1-.5-3 0-4.5 1.5L13 8 4.8 6.2c-.5-.1-.9.1-1.1.5l-.3.5c-.2.5-.1 1 .3 1.3L9 12l-2 3H4l-1 1 3 2 2 3 1-1v-3l3-2 3.5 5.3c.3.4.8.5 1.3.3l.5-.2c.4-.3.6-.7.5-1.2z"></path>
                                                </svg>
                                            </div>
                                            <p class="empty-state-message">Aucun vol ne correspond a vos criteres de recherche.</p>
                                        </div>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>