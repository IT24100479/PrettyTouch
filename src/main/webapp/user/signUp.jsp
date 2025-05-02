<%@ page import="com.janani.prettytouch.util.TypeConverter" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-05-02
  Time: 9:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/booking.css"%>">
    <script src="<%=request.getContextPath()+"/js/data/services.js"%>"></script>

</head>
<body>
<jsp:include page="../root/header.jsp"/>
<!-- Booking Page -->
<section class="booking-page">
    <div class="container">
        <h2 class="section-title" id="titel">Sign Up</h2>
        <div class="booking-container">
            <form id="signup-form">
                <div class="form-group">
                    <label for="signup-username">Username</label>
                    <input type="text" id="signup-username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="dob">Date of Birth</label>
                    <input type="Date" id="dob" name="dob" required>
                </div>
                <div class="form-group">
                    <label for="signup-email">Email</label>
                    <input type="email" id="signup-email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="signup-phone">Telephone Number</label>
                    <input type="tel" id="signup-phone" name="phone" required>
                </div>
                <div class="form-group">
                    <label for="signup-password">Password</label>
                    <input type="password" id="signup-password" name="password" required>
                </div>
                <button type="submit" class="btn login-btn">Save</button>
            </form>


        </div>
    </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>

</script>

</body>
</html>
