<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        :root {
            --primary-color: #4361ee;
            --primary-hover: #3a56d4;
            --error-color: #e63946;
            --success-color: #2a9d8f;
            --text-color: #333;
            --bg-color: #f8f9fa;
            --card-bg: #ffffff;
            --input-border: #dfe0e1;
            --input-focus: #4361ee;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: var(--bg-color);
            background-image: linear-gradient(135deg, #f5f7fa 0%, #e4e8f1 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            max-width: 400px;
            width: 90%;
        }

        .login-header {
            text-align: center;
            margin-bottom: 25px;
        }

        .login-header h1 {
            color: var(--primary-color);
            font-size: 28px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .login-header p {
            color: #6c757d;
            font-size: 16px;
        }

        form {
            background-color: var(--card-bg);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            width: 100%;
        }

        .form-group {
            margin-bottom: 20px;
            position: relative;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-color);
            font-size: 15px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--input-border);
            border-radius: 6px;
            font-size: 16px;
            transition: all 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: var(--input-focus);
            box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
            outline: none;
        }

        .error-message {
            color: var(--error-color);
            font-size: 13px;
            margin-top: 5px;
            display: block;
            font-weight: 500;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: var(--primary-hover);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(67, 97, 238, 0.2);
        }

        input[type="submit"]:active {
            transform: translateY(0);
        }

        .message {
            margin-top: 15px;
            padding: 12px;
            border-radius: 6px;
            text-align: center;
            font-size: 14px;
            font-weight: 500;
        }

        .message.error {
            background-color: rgba(230, 57, 70, 0.1);
            color: var(--error-color);
            border-left: 3px solid var(--error-color);
        }

        .message.success {
            background-color: rgba(42, 157, 143, 0.1);
            color: var(--success-color);
            border-left: 3px solid var(--success-color);
        }

        /* Animation */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        form {
            animation: fadeIn 0.5s ease forwards;
        }

        /* Responsive */
        @media (max-width: 480px) {
            form {
                padding: 20px;
            }
            
            .login-header h1 {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>Connexion</h1>
            <p>Veuillez vous identifier pour continuer</p>
        </div>
        
        <form action="${pageContext.request.contextPath}/login.do" method="post">
            <div class="form-group">
                <label for="pseudo">Pseudo:</label>
                <input type="text" id="pseudo" name="empka.pseud" required>
                <% if(request.getAttribute("error_empka.pseud") != null) { %>
                    <span class="error-message"><%= request.getAttribute("error_empka.pseud") %></span>
                <% } %>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe:</label>
                <input type="password" id="password" name="empka.passwrd">
                <% if(request.getAttribute("error_empka.passwrd") != null) { %>
                    <span class="error-message"><%= request.getAttribute("error_empka.passwrd") %></span>
                <% } %>
            </div>

            <input type="submit" value="Se connecter">
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="message error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <% if(request.getAttribute("success") != null) { %>
                <div class="message success"><%= request.getAttribute("success") %></div>
            <% } %>
        </form>
    </div>
</body>
</html>