<%@ page import="com.janani.prettytouch.model.ServiceModel" %>
<%@ page import="java.util.List" %>
<%@ page import="com.janani.prettytouch.services.ServiceService" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-05
  Time: 10:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- Offers Section --%>
<%
    List<ServiceModel> offers = ServiceService.getInstance().getServiceWithFilter(false,true);
    List<ServiceModel> services = ServiceService.getInstance().getServiceWithFilter(true,false);

%>


<section class="offers-page" id="offersPage">
    <div class="container">
        <h2 class="section-title">Special Offers</h2>

        <div class="offer-grid" id ='offerGrid'>
            <%
                if(offers.isEmpty()){
            %>
            <div class="no-offers text-danger text-center h3">
                No Offers Found
            </div>
            <% }else{
                for (int i = 0; i < offers.size(); i++) {
                    ServiceModel model = offers.get(i);

            %>

            <div class="offer-card">
                <div class="offer-card-header">
                    <%=TypeConverter.replaceNull(model.getServiceName())%>
                </div>
                <div class="offer-card-body">
                    <ul>
                        <%
                            String [] pArray= TypeConverter.replaceNull(model.getDescription()).split("\n");
                            for (int j = 0; j < pArray.length; j++) {

                        %>
                        <li><%=pArray[j]%></li>
                        <%}%>
                    </ul>
                    <div class="offer-price">
                        LKR. <%=String.format("%,.2f", model.getRealPrice())%> <span>LKR. <%=String.format("%,.2f", model.getPrintPrice())%></span>
                    </div>
                </div>
                <div class="offer-link">
                    <a href="<%=request.getContextPath()+"/appointment/createAppointment.jsp?id="+model.getId()%>">
                        Book now &amp; Enjoy your Offer
                    </a>
                </div>
            </div>

            <%  }
            }%>

        </div>
    </div>
</section>

<!-- Services Section -->
<section class="services" id="services">
    <div class="container">
        <h2 class="section-title">Our Services</h2>
        <div class="services-grid" id="servicesGrid">
            <%
                if(services.isEmpty()){
            %>
            <div class="no-offers text-danger text-center h3">
                No Services Found
            </div>
            <% }else{
                for (int i = 0; i < services.size(); i++) {
                    ServiceModel model = services.get(i);

            %>

            <div class="service-card" id="<%=TypeConverter.replaceNull(model.getServiceName()).replace(" ","-")%>">
                <div class="service-img">
                    <img src="<%=model.getImageUrl()%>" alt="<%=model.getServiceName()%>">
                </div>
                <div class="service-content">
                    <h3><%=TypeConverter.replaceNull(model.getServiceName())%></h3>
                    <p><%=TypeConverter.replaceNull(model.getDescription())%></p>
                    <span class="price">From LKR. <%=String.format("%,.2f", model.getRealPrice())%></span>
                    <a href="<%=request.getContextPath()+"/appointment/createAppointment.jsp?id="+model.getId()%>" class="btn">Book Now</a>
                </div>
            </div>

            <%  }
            }%>
        </div>
    </div>
</section>
