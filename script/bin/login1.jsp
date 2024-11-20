<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login.get" method="post">
        <label for="pseudo">pseudo:</label>
        <input type="text" name="empka.pseud" value="<%= request.getAttribute("old_empka.pseud") != null ? request.getAttribute("old_empka.pseud") : "" %>">
        <label for="password">password:</label>
        <input type="password" name="empka.passwrd">
        <%= request.getAttribute("error_empka.passwrd") != null ? request.getAttribute("error_empka.passwrd") : "" %>
        
        <input type="submit" value="Valider">
    </form>
</body>
</html>