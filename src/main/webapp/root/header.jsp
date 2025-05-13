<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 8:30 PM
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/bootstrap/css/bootstrap.min.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/master.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/fontawesome/css/all.min.css"%>">

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<script src="<%=request.getContextPath()+"/js/data/slide.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jQuery/jquery-3.7.1.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/css/bootstrap/js/bootstrap.min.js"%>"></script>

<%
    UserModel logUser = (UserModel)session.getAttribute("user");
    String pathUrl = request.getRequestURI().contains("index.jsp")||
            request.getRequestURI().equalsIgnoreCase(request.getContextPath()+"/")?"":
            (request.getContextPath()+"/index.jsp");

%>
<header>
    <div class="container header-container">
        <a href="<%=pathUrl+"#home"%>" class="logo"><i class="fas fa-spa"></i>Pretty <span>Touch</span></a>
        <nav>
            <ul id="nav-menu">
                <li><a href="<%=pathUrl+"#home"%>">Home</a></li>
                <li><a href="<%=pathUrl+"#about"%>">About</a></li>
                <li><a href="<%=pathUrl+"#offersPage"%>">Offers</a></li>
                <li><a href="<%=pathUrl+"#services"%>">Services</a></li>

                <li><a href="<%=request.getContextPath()+"/appointment/createAppointment.jsp"%>" id="booking-link">Book Now</a></li>
                <% if (logUser!=null && GlobalConst.USER_TYPE_USER.equalsIgnoreCase(logUser.getRole())) { %>
                <li><a href="<%=request.getContextPath()+"/Feedback/createFeedback.jsp"%>">Feedback</a></li>
                <%}%>
                <% if (logUser!=null && GlobalConst.USER_TYPE_USER.equalsIgnoreCase(logUser.getRole())) { %>
                <li><a href="<%=request.getContextPath()+"/user/signUp.jsp"%>">My Profile</a></li>
                <%}%>
                <% if (logUser!=null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())) { %>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin Panel<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=request.getContextPath()+"/appointment/createAppointment.jsp"%>">Appointment Create</a></li>
                        <li><a href="<%=request.getContextPath()+"/appointment/appointments.jsp"%>">Appointment Manage</a></li>
                        <li><a href="<%=request.getContextPath()+"/payment/payment.jsp"%>">Payment</a></li>
                        <li><a href="<%=request.getContextPath()+"/payment/report.jsp"%>">Payment Report</a></li>
                        <li><a href="<%=request.getContextPath()+"/user/userManage.jsp"%>">User Manage</a></li>
                        <li><a href="<%=request.getContextPath()+"/user/signUp.jsp"%>">Create User</a></li>
                        <li><a href="<%=request.getContextPath()+"/services/serviceManage.jsp"%>">Service Manage</a></li>
                        <li><a href="<%=request.getContextPath()+"/services/createService.jsp"%>">Create Service</a></li>
                        <li><a href="<%=request.getContextPath()+"/Feedback/manageFeedback.jsp"%>">Rating Manage</a></li>
                    </ul>
                </li>
                <%}%>
                <% if (logUser!=null && GlobalConst.USER_TYPE_USER.equalsIgnoreCase(logUser.getRole())) { %>
                <li><a href="<%=request.getContextPath()+"/appointment/appointments.jsp"%>">My Appointment</a></li>
                <%}%>
                <% if (logUser==null) { %>
                <li><a href="#" id="login-btn">Login</a></li>
                <%}else{%>
                <li><a href="<%=request.getContextPath()+"/user/logout"%>" >Logout</a></li>
                <%}%>
            </ul>
        </nav>
        <div class="mobile-menu" id="mobile-menu">
            <i class="fas fa-bars"></i>
        </div>
    </div>
</header>
<!-- Login Modal -->
<div id="login-modal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <div class="login-form">
            <h2>Login to Your Account</h2>
            <h5 id="loginerror" style="display: flex;justify-content: center;color: red;"></h5>
            <% if ("true".equalsIgnoreCase(request.getParameter("login"))) { %>
            <h5 id="" style="display: flex;justify-content: center;color: green;"> User Created Please Login</h5>
            <%}%>
            <form id="login-form">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn login-btn">Login</button>
                <div class="signup-link">
                    Don't have an account? <a href="<%=request.getContextPath()+"/user/signUp.jsp"%>" id="signup-link">Sign Up</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Mobile Menu Toggle
    const mobileMenuBtn = document.getElementById('mobile-menu');
    const navMenu = document.getElementById('nav-menu');

    mobileMenuBtn.addEventListener('click', () => {
        navMenu.classList.toggle('active');
        mobileMenuBtn.innerHTML = navMenu.classList.contains('active') ?
            '<i class="fas fa-times"></i>' : '<i class="fas fa-bars"></i>';
    });

    // Close mobile menu when clicking on a link
    const navLinks = document.querySelectorAll('#nav-menu a');
    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            navMenu.classList.remove('active');
            mobileMenuBtn.innerHTML = '<i class="fas fa-bars"></i>';
        });
    });
    // Login Modal
    const loginBtn = document.getElementById('login-btn');
    const loginModal = document.getElementById('login-modal');
    const closeLogin = document.querySelector('.close');

    loginBtn.addEventListener('click', (e) => {
        e.preventDefault();
        loginModal.style.display = 'block';
    });

    <% if (logUser==null && (request.getRequestURI().contains("/appointment/createAppointment.jsp")||"true".equalsIgnoreCase(request.getParameter("login")))) { %>
    loginModal.style.display = 'block';
    <%}%>

    closeLogin.addEventListener('click', () => {
        loginModal.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === loginModal) {
            loginModal.style.display = 'none';
        }
    });

    // Login Form Submission
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        $.ajax({
            url: '<%=request.getContextPath()+"/user/login"%>', // Sample API
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            success: function(response) {
                if(response.msg=="login success"){
                    window.location.reload();
                }else{
                    $("#loginerror").html(response.msg);
                }
            },
            error: function(xhr, status, error) {
                console.log(JSON.stringify(error));
            }
        });
    });
</script>
