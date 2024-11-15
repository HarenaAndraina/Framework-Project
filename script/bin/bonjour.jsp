<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Message</title>
</head>
<body>
    <p>65555555</p>
    <h1>pseudo: <%= request.getAttribute("pseudo") %></h1>
    <h1>password: <%=request.getAttribute("password")  %>  </h1>
     <h1>fileName: <%=request.getAttribute("file")%> </h1>
    <h1> fileContent:<%=request.getAttribute("fileContent")  %>  </h1>
</body>
</html>