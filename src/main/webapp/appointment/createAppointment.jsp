<%@ page import="com.janani.prettytouch.model.UserModel" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 11:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BookNow</title>
    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/booking.css"%>">
    <script src="<%=request.getContextPath()+"/js/data/services.js"%>"></script>
    <%
        UserModel logUser = (UserModel)session.getAttribute("user");

    %>
</head>
<body>
<jsp:include page="../root/header.jsp"/>

<!-- Booking Page -->
<section class="booking-page">
    <div class="container">
        <h2 class="section-title">Book An Appointment</h2>
        <div class="booking-container">
            <% if(logUser==null){%>
            <h3 style="display: flex;justify-content: center;">Please Log In To Book An Appointment</h3>
            <%}else{%>
                <form class="booking-form" id="appointment-form">
                    <% if (logUser!=null && "admin".equalsIgnoreCase(logUser.getRole())) { %>
                        <div class="form-group">
                            <label for="client">Client Name</label>
                            <select id="client" name="client" required>
                                <option value="">Select a user</option>
                            </select>
                        </div>
                    <%}else{%>
                    <input type="hidden" name="client" value="<%=logUser.getUsername()%>"/>
                    <%}%>
                    <div class="form-group">
                        <label for="service">Service</label>
                        <select id="service" name="service" required>
                            <option value="">Select a Service</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="date">Date</label>
                        <input type="date" id="date" name="date" required>
                    </div>
                    <div class="form-group">
                        <label for="time">Time</label>
                        <select id="time" name="time" required>
                            <option value="">Select a Time Slot</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="notes">Special Requests</label>
                        <textarea id="notes" name="notes"></textarea>
                    </div>
                    <button type="submit" class="btn" id="book-now-btn">Confirm Booking</button>
                </form>
            <%}%>

        </div>
    </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>
    const serviceSelect = document.getElementById('service');
    const params = new URLSearchParams(window.location.search);
    const idFromUrl = parseInt(params.get('id'));
    services.forEach(service => {
        const option = document.createElement('option');
        option.selected = service.id == idFromUrl;
        option.value = service.name;
        option.textContent = service.key+" (LKR. "+service.price.toLocaleString()+")";
        serviceSelect.appendChild(option);
    });
    const select = document.getElementById('time');

    // Start and end hours (24-hour format)
    const startHour = 9; // 9 AM
    const endHour = 19;  // 8 PM

    for (let hour = startHour; hour < endHour; hour++) {
        const nextHour = hour + 1;

        // Format the time to 12-hour with AM/PM
        const formatHour = (h) => {
            const period = h >= 12 ? 'PM' : 'AM';
            const hour12 = h % 12 === 0 ? 12 : h % 12;
            return hour12+period;
        };

        const timeSlot = formatHour(hour)+" - "+formatHour(nextHour);
        const option = document.createElement('option');
        option.value = timeSlot;
        option.textContent = timeSlot;
        select.appendChild(option);
    }

    // Form submission
    document.getElementById('appointment-form').addEventListener('submit', function(e) {
        e.preventDefault();

        // Get form values
        const name = document.getElementById('name').value;
        const service = document.getElementById('service').value;
        const date = document.getElementById('date').value;
        const time = document.getElementById('time').value;

        // Simple validation
        if (name && service && date && time) {
            <%--alert(`Booking confirmed!\n\nName: ${name}\nService: ${service}\nDate: ${date}\nTime: ${time}`);--%>
            this.reset();
        } else {
            alert('Please fill in all required fields');
        }
    });

    // Set minimum date to today
    document.getElementById('date').min = new Date().toISOString().split('T')[0];
</script>

</body>
</html>
