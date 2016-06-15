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
        <title>Action</title>
    </head>
    <body>
        
        <div id="data"></div>
        <button onClick="myFunction2(document.getElementById('data').innerHTML);">Click</button>
        <script>
            var myvar = setInterval(myFunction,500);
          
            function myFunction() {
                var xhttp = new XMLHttpRequest();
                var currentStatus = document.getElementById("data").innerHTML;
                currentStatus = currentStatus.trim();
                
                
                xhttp.onreadystatechange = function() {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        var resp = xhttp.responseText;
                        resp = resp.trim();
                        var res = resp.split(" ");
                        document.getElementById("data").innerHTML = res[1];
                    }
                };
                xhttp.open("GET", "Binh", true);
                xhttp.send();
                
            }
            
            function myFunction2(status) {
                var xhttp = new XMLHttpRequest();
                var currentStatus = document.getElementById("data").innerHTML;
                currentStatus = currentStatus.trim();
                
                
                xhttp.onreadystatechange = function() {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        var resp = xhttp.responseText;
                        resp = resp.trim();
                        var res = resp.split(" ");
                        document.getElementById("data").innerHTML = res[1];
                    }
                };
                if (status == "on") {
                    xhttp.open("GET", "Binh?action=off", true);
                    xhttp.send();
                }
                else {
                    xhttp.open("GET", "Binh?action=on", true);
                    xhttp.send();
                }
            }
            
            myFunction();
        </script>
    </body>
</html>
