<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/7/2025
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Feedback</title>
</head>
<body>
<h2>Leave Your Feedback</h2>
<form action ="FeedbackServelet" method="post">
  Appointment ID:<input type="text" name="AppointmentId"/><br><br>
  Rating (1-5): <input type="number" name="rating" min="1" max="5" /><br/><br/>
  Comment:<br/>
  <textarea name="comment" rows="5" cols="30"></textarea></br></br>
  <input type="submit" value="Submit Feedback"/>
</form>

</body>
</html>
