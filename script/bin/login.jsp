<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login.do" method="post" enctype="multipart/form-data">
        <label for="pseudo">pseudo:</label>
        <input type="text" name="empka.pseud">

        <label for="password">password:</label>
        <input type="password" name="empka.passwrd">
        <input type="file" name="file" />
        <input type="submit" value="Valider">
    </form>
</body>
</html>