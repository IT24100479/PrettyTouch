<%@ page import="com.janani.prettytouch.model.UserModel" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 8:30 PM
  To change this template use File | Settings | File Templates.
--%>

<link rel="stylesheet" href="<%=request.getContextPath()+"/css/master.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/fontawesome/css/all.min.css"%>">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<script src="<%=request.getContextPath()+"/js/data/services.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/data/slide.js"%>"></script>
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

                <li><a href="<%=request.getContextPath()+"/booknow.jsp"%>" id="booking-link">Book Now</a></li>
                <li><a href="#" id="login-btn">Login</a></li>
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
                    Don't have an account? <a href="#" id="signup-link">Sign Up</a>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Signup Modal -->
<div id="signup-modal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <div class="login-form">
            <h2>Create Your Account</h2>
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

    closeLogin.addEventListener('click', () => {
        loginModal.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === loginModal) {
            loginModal.style.display = 'none';
        }
    });
    // Signup Modal
    const signupModal = document.getElementById('signup-modal');
    const signupLink = document.getElementById('signup-link');
    const closeSignup = signupModal.querySelector('.close');

    signupLink.addEventListener('click', (e) => {
        e.preventDefault();
        loginModal.style.display = 'none';
        signupModal.style.display = 'block';
    });

    closeSignup.addEventListener('click', () => {
        signupModal.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === signupModal) {
            signupModal.style.display = 'none';
        }
    })
    // Signup Form Submission
    const signupForm = document.getElementById('signup-form');

    signupForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const username = document.getElementById('signup-username').value;
        const age = document.getElementById('signup-age').value;
        const email = document.getElementById('signup-email').value;
        const phone = document.getElementById('signup-phone').value;
        const password = document.getElementById('signup-password').value;

        // Simple validation
        if (username && age && email && phone && password) {
            alert('Account created successfully! Please login with your credentials.');
            signupModal.style.display = 'none';
            signupForm.reset();
            loginModal.style.display = 'block';
        } else {
            alert('Please fill in all fields');
        }
    });
    // Login Form Submission
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const userType = document.querySelector('.user-type-btn.active').dataset.type;

        // Simple validation
        if (username && password) {
            alert(`Logged in as ${userType}: ${username}`);
            loginModal.style.display = 'none';

            // Redirect to booking page if booking link was clicked before login
            if (sessionStorage.getItem('redirectToBooking')) {
                sessionStorage.removeItem('redirectToBooking');
                window.open('booknow.html', '_blank');
            }
        } else {
            alert('Please enter both username and password');
        }
    });
</script>
