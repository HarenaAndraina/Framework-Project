<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1> Bonjour <%= (String) session.getAttribute("pseudo") %>!!</h1>

    <a href="${pageContext.request.contextPath}/tacheAFaire">voir mon tache</a>
    <a href="${pageContext.request.contextPath}/deconnection">Deconnecter</a>
</body>
</html>