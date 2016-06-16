<%-- 
    Document   : update
    Created on : Jun 14, 2016, 1:57:45 AM
    Author     : Khoi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get Last Data</title>
    </head>
    <body>
        <h1>Last Data:</h1>
        <div id="update"></div>
        <script>
            var myvar = setInterval(myFunction,2000);
          
            function myFunction() {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById("update").innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "GetLastData", true);
                xhttp.send();
            }
        </script>
    </body>
</html>
