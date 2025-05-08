<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/7/2025
  Time: 10:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Feedback</title>
</head>
<body>
 <h2> Customer Feedback</h2>
 <prep>
     <%
         String path =application.getRealPath("/")+"feedback.txt";
         File file = new File(path);
         if(file.exists()){
             BufferedReader reader= new BufferedReader(new FileReader(file));
             String line;
             while ((line = reader.readLine())!= null){
                 out.print(line);
             }
             reader.close();

         }else {
             out.println("NO FEEDBACK YET.");
         }
     %>
 </prep>

</body>
</html>
