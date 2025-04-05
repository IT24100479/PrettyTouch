<%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 10:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- Offers Section --%>
<section class="offers-page" id="offersPage">
    <div class="container">
        <h2 class="section-title">Special Offers</h2>

        <div class="offer-grid" id ='offerGrid'>
            <!-- all offers -->
        </div>
    </div>
</section>

<!-- Services Section -->
<section class="services" id="services">
    <div class="container">
        <h2 class="section-title">Our Services</h2>
        <div class="services-grid" id="servicesGrid">
            <!-- all servicess -->
        </div>
    </div>
</section>
<script>
    const grid = document.getElementById("servicesGrid");
    const footerServices = document.getElementById("footerServices");
    const offerGrid = document.getElementById('offerGrid');

    services.forEach(service => {
        if(service.type=="Service"){
            const card = document.createElement("div");
            card.className = "service-card";
            card.id=service.name;

            let imgHTML =  "<div class=\"service-img\">"+
                        "<img src=\""+service.img+"\" alt=\""+service.key+"\">"+
                    "</div>" ;

            let description = service.data && service.data.length > 0 ? service.data[0] : "This is one of our exclusive offers!";

            card.innerHTML = imgHTML+`
                    <div class="service-content">
                        <h3>${service.key}</h3>
                        <p>${description}</p>
                        <span class="price">From LKR. ${service.price.toLocaleString()}</span>
                        <a href="booknow.html?id=${service.id}" class="btn">Book Now</a>
                    </div>
                `;

            grid.appendChild(card);
        }else{
            const card = document.createElement('div');
            card.className = 'offer-card';
            const header = document.createElement('div');
            header.className = 'offer-card-header';
            header.textContent = service.key;
            const body = document.createElement('div');
            body.className = 'offer-card-body';
            const ul = document.createElement('ul');
            service.data.forEach(item => {
                const li = document.createElement('li');
                li.textContent = item;
                ul.appendChild(li);
            });
            const price = document.createElement('div');
            price.className = 'offer-price';
            price.innerHTML = `LKR. ${service.price.toLocaleString()} <span>LKR. ${service.maxPrice.toLocaleString()}</span>`;

            body.appendChild(ul);
            body.appendChild(price);
            const link = document.createElement('div');
            link.className = 'offer-link';
            link.innerHTML = `<a href="booknow.html?id=${service.id}">Book now & Enjoy your Offer</a>`;
            card.appendChild(header);
            card.appendChild(body);
            card.appendChild(link);
            offerGrid.appendChild(card);
        }

    });

    // Booking Link Click Handler
    /*const bookingLink = document.getElementById('booking-link');

    bookingLink.addEventListener('click', (e) => {
        e.preventDefault();
        sessionStorage.setItem('redirectToBooking', 'true');
        loginModal.style.display = 'block';
    });*/

    // Service Book Now Buttons
    /*const serviceBookButtons = document.querySelectorAll('.service-card .btn');

    serviceBookButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            sessionStorage.setItem('redirectToBooking', 'true');
            loginModal.style.display = 'block';
        });
    });*/
</script>
