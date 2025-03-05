<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste de vos réservations</title>
    <style>
        :root {
            --success-color: #28a745;
            --success-bg: #d4edda;
            --error-color: #dc3545;
            --error-bg: #f8d7da;
            --primary-color: #3498db;
            --text-color: #333;
            --light-gray: #f8f9fa;
            --border-color: #dee2e6;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: #f5f5f5;
            color: var(--text-color);
            line-height: 1.6;
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .message {
            padding: 15px 20px;
            margin: 20px 0;
            border-radius: 8px;
            text-align: center;
            font-size: 16px;
            font-weight: 500;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            animation: fadeIn 0.5s ease-in-out;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        .success {
            background-color: var(--success-bg);
            color: var(--success-color);
            border-left: 4px solid var(--success-color);
        }
        
        .failed {
            background-color: var(--error-bg);
            color: var(--error-color);
            border-left: 4px solid var(--error-color);
        }
        
        .container {
            background-color: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        
        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: var(--primary-color);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 500;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: all 0.2s ease;
        }
        
        .back-link:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }
        
        .back-link:active {
            transform: translateY(0);
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            body {
                padding: 15px;
            }
            
            .message {
                padding: 12px 15px;
                font-size: 14px;
            }
            
            .container {
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        
        <%-- Affichage des messages de succès ou d'échec --%>
        <% if (request.getAttribute("success") != null) { %>
            <div class="message success"><%= request.getAttribute("success") %></div>
        <% } %>
        
        <% if (request.getAttribute("failed") != null) { %>
            <div class="message failed"><%= request.getAttribute("failed") %></div>
        <% } %>
        
        <a href="${pageContext.request.contextPath}/res.get" class="back-link">Retour à la liste</a>
    </div>
</body>
</html>