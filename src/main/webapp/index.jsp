<%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 8:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>PrettyTouch</title>
</head>
<body>
<jsp:include page="root/header.jsp"/>

<!-- Slideshow Hero Section -->
<section class="slideshow" id="home"></section>

<!-- About Section -->
<section class="about" id="about">
  <div class="container">
    <h2 class="section-title">About Us</h2>
    <div class="about-content">
      <div class="about-text">
        <h3>Welcome to Pretty Touch</h3>
        <p>At Pretty Touch, we are dedicated to providing exceptional beauty services tailored to your needs. Our skilled team is passionate about enhancing your natural beauty in a welcoming and relaxing environment. Experience the perfect blend of professionalism and care with us.</p>
        <a href="#services" class="btn">Our Services</a>
      </div>
      <div class="about-image">
        <img src="https://images.unsplash.com/photo-1595476108010-b4d1f102b1b1?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80" alt="Saloon Interior">
      </div>
    </div>
  </div>
</section>

<jsp:include page="services/indexOffersServices.jsp"/>

<!-- Contact Information Section -->
<section class="contact-info">
  <div class="container">
    <h2 class="section-title">Contact Information</h2>
    <div class="info-grid">
      <div class="info-card">
        <div class="info-icon">
          <i class="fas fa-map-marker-alt"></i>
        </div>
        <h3>Address</h3>
        <p>123 Beauty Street, Hansi Town, HS 12345</p>
      </div>
      <div class="info-card">
        <div class="info-icon">
          <i class="fas fa-phone"></i>
        </div>
        <h3>Phone</h3>
        <p>(123) 456-7890</p>
      </div>
      <div class="info-card">
        <div class="info-icon">
          <i class="fas fa-envelope"></i>
        </div>
        <h3>Email</h3>
        <p>info@prettytouch.com</p>
      </div>
      <div class="info-card">
        <div class="info-icon">
          <i class="fas fa-clock"></i>
        </div>
        <h3>Hours</h3>
        <p>Monday - Sunday: 9am - 7pm</p>
      </div>
    </div>
  </div>
</section>
<jsp:include page="root/footer.jsp"/>

<script>
  const homeSection = document.getElementById('home');

  heroSlides.forEach(slide => {
    const slideDiv = document.createElement('div');
    slideDiv.className='slide';
    slideDiv.style="background-image: url('"+slide.image+"');";
    slideDiv.innerHTML="<div class='slide-content'>"+
            "<h1>"+slide.title+"</h1>"+
            "<p>"+slide.description+"</p>"+
            "<a href='<%=request.getContextPath()+"/appointment/createAppointment.jsp"%>' class='btn'>"+slide.buttonText+"</a>"+
            "</div>";
    homeSection.appendChild(slideDiv);
    startSlideshow();
  });

  // Slideshow
  function startSlideshow(){
    const slides = document.querySelectorAll('.slide');
    let currentSlide = 0;

    function showSlide(index) {
      slides.forEach(slide => slide.classList.remove('active'));
      slides[index].classList.add('active');
    }

    function nextSlide() {
      currentSlide = (currentSlide + 1) % slides.length;
      showSlide(currentSlide);
    }
    showSlide(currentSlide);
    setInterval(nextSlide, 5000);
  }

  // Smooth scrolling for anchor links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();

      const targetId = this.getAttribute('href');
      if (targetId === '#') return;

      const targetElement = document.querySelector(targetId);
      if (targetElement) {
        window.scrollTo({
          top: targetElement.offsetTop - 80,
          behavior: 'smooth'
        });
      }
    });
  });
</script>
</body>
</html>
