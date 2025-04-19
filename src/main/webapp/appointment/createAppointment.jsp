<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="java.util.List" %>
<%@ page import="com.janani.prettytouch.services.UserService" %>
<%@ page import="com.janani.prettytouch.model.Model" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.services.ServiceService" %>
<%@ page import="com.janani.prettytouch.model.ServiceModel" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %><%--
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
        String userId = TypeConverter.replaceNull(request.getParameter("uid"));
        String serviceId = TypeConverter.replaceNull(request.getParameter("id"));
        String date = TypeConverter.replaceNull(request.getParameter("date"));
        String timeId = TypeConverter.replaceNull(request.getParameter("time"));
        String req = TypeConverter.replaceNull(request.getParameter("req"));
        String appointmentId = TypeConverter.replaceNull(request.getParameter("aid"));
        String error = TypeConverter.replaceNull(request.getParameter("error"));
        boolean isError =TypeConverter.stringIsNotEmpty(error);
        String actionUrl = request.getContextPath()+"/appointment/create";
        boolean edit = request.getParameter("edit")!=null;
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
                <% if(isError){%>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error!</strong> <%=error%>
                    </div>
                <%}%>
                <form class="booking-form" id="appointment-form" method="POST" action="<%=actionUrl%>">
                    <% if (logUser!=null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())) { %>
                        <div class="form-group">
                            <label for="client">Client Name</label>
                            <select id="client" name="client" required <%=edit?"disabled":""%>>
                                <option value="">Select a user</option>
                                    <%
                                    List<Model> users = UserService.getInstance().getAll();

                                    for(int i=0;i<users.size();i++){
                                        UserModel user = (UserModel) users.get(i);
                                        if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())){
                                            continue;
                                        }
                                        if(user.getId()== TypeConverter.stringToInt(userId)){%>
                                            <option value="<%=user.getId()%>" selected><%=user.getFirstName()+" "+user.getLastName()%></option>
                                        <%}else{%>
                                            <option value="<%=user.getId()%>"><%=user.getFirstName()+" "+user.getLastName()%></option>
                                        <%}
                                    }
                                    %>
                            </select>
                        </div>
                    <%}else{%>
                    <input type="hidden" name="client" value="<%=logUser.getId()%>"/>
                    <%}%>
                    <input type="hidden" name="appointmentId" value="<%=appointmentId%>"/>
                    <div class="form-group">
                        <label for="service">Service</label>
                        <select id="service" name="service" required  <%=edit?"disabled":""%>>
                            <option value="">Select a Service</option>
                            <%
                                List<Model> services = ServiceService.getInstance().getAll();
                                for(int i=0;i<services.size();i++){
                                    ServiceModel service = (ServiceModel) services.get(i);
                                    if(service.getId()== TypeConverter.stringToInt(serviceId)){%>
                                        <option value="<%=service.getId()%>" selected><%=service.getServiceName()+" ("+service.getRealPrice()+")"%></option>
                                    <%}else{%>
                                        <option value="<%=service.getId()%>"><%=service.getServiceName()+" ("+service.getRealPrice()+")"%></option>
                                    <%}
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="date">Date</label>
                        <input type="date" id="date" name="date" required value="<%=date%>"  <%=edit?"disabled":""%>>
                    </div>
                    <div class="form-group">
                        <label for="time">Time</label>
                        <select id="time" name="time" required  <%=edit?"disabled":""%>>
                            <option value="">Select a Time Slot</option>
                            <%
                                for(int i = 0; i< GlobalConst.TIME_SLOT_LIST.size(); i++){
                                    String slot = GlobalConst.TIME_SLOT_LIST.get(i);
                                    if(i == TypeConverter.stringToInt(timeId)){%>
                                        <option value="<%=i%>" selected><%=slot%></option>
                                    <%}else{%>
                                        <option value="<%=i%>"><%=slot%></option>
                                    <%}
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="notes">Special Requests</label>
                        <textarea id="notes" name="notes" ><%=req%></textarea>
                    </div>
                    <div style="display: flex; justify-content: center;width: 100%;">
                        <button type="submit" class="btn" id="book-now-btn">Confirm Booking</button>
                    </div>

                </form>
            <%}%>

        </div>
    </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>


    // Form submission
    <%--document.getElementById('appointment-form').addEventListener('submit', function(e) {--%>
    <%--    e.preventDefault();--%>

    <%--    // Get form values--%>
    <%--    const name = document.getElementById('name').value;--%>
    <%--    const service = document.getElementById('service').value;--%>
    <%--    const date = document.getElementById('date').value;--%>
    <%--    const time = document.getElementById('time').value;--%>

    <%--    // Simple validation--%>
    <%--    if (name && service && date && time) {--%>
    <%--        &lt;%&ndash;alert(`Booking confirmed!\n\nName: ${name}\nService: ${service}\nDate: ${date}\nTime: ${time}`);&ndash;%&gt;--%>
    <%--        this.reset();--%>
    <%--    } else {--%>
    <%--        alert('Please fill in all required fields');--%>
    <%--    }--%>
    <%--});--%>

    // Set minimum date to today
    document.getElementById('date').min = new Date().toISOString().split('T')[0];
</script>

</body>
</html>
