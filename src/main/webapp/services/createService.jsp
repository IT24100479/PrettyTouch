<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.model.ServiceModel" %>
<%@ page import="com.janani.prettytouch.services.ServiceService" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 5/6/2025
  Time: 11:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>
    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/booking.css"%>">
    <script src="<%=request.getContextPath()+"/js/data/services.js"%>"></script>

    <%
        UserModel logUser = (UserModel)session.getAttribute("user");
        if(logUser==null || !GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
            response.sendRedirect(request.getContextPath()+"/user/logout");
            return;
        }
        String sid=TypeConverter.replaceNull(request.getParameter("sid"));
        String sName=TypeConverter.replaceNull(request.getParameter("sName"));
        String isOffer=TypeConverter.replaceNull(request.getParameter("isOffer"));
        String pPrice=TypeConverter.replaceNull(request.getParameter("pPrice"));
        String rPrice=TypeConverter.replaceNull(request.getParameter("rPrice"));
        String imgUrl=TypeConverter.replaceNull(request.getParameter("imgUrl"));
        String description=TypeConverter.replaceNull(request.getParameter("description"));

        if(TypeConverter.stringIsNotEmpty(sid)){
            ServiceModel sModel = (ServiceModel) ServiceService.getInstance().getById(TypeConverter.stringToInt(sid));
            if(sModel!=null){
                sName=sModel.getServiceName();
                isOffer=sModel.getIsOfferCsv();
                pPrice=TypeConverter.doubaleToString(sModel.getPrintPrice());
                rPrice=TypeConverter.doubaleToString(sModel.getRealPrice());
                imgUrl=sModel.getImageUrl();
                description=sModel.getDescription();
            }
        }
        String error = TypeConverter.replaceNull(request.getParameter("error"));
        boolean isError =TypeConverter.stringIsNotEmpty(error);

        boolean edit = request.getParameter("edit")!=null;
        String actionUrl = request.getContextPath()+"/services/"+(edit?"update":"create");
    %>
    <style>
        input[type=radio]{
            transform:scale(1.5);
            margin-right: 10px !important;
        }
        .form-check-label{
            margin-bottom: 0px !important;
        }
    </style>
</head>
<body>
<jsp:include page="../root/header.jsp"/>
<!-- Booking Page -->
<section class="booking-page">
    <div class="container">
        <h2 class="section-title" id="titel">Services</h2>
        <div class="booking-container">

            <% if(isError){%>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> <%=error%>
            </div>
            <%}%>
            <form class="booking-form" id="appointment-form" method="POST" action="<%=actionUrl%>">

                <input type="hidden" name="sid" value="<%=sid%>"/>
                <div class="form-group">
                    <label for="sName">Service Name</label>
                    <input <%=edit?"disabled":""%> name="sName" value="<%=sName%>" type="text" class="form-control" id="sName" placeholder="Enter Service Name" required>
                </div>
                <div class="form-group">
                    <label >Select Type</label>
                    <div >
                        <div class="form-check form-check-inline">
                            <input <%=edit?"disabled":""%> class="form-check-input" type="radio" name="isOffer" id="isOfferTrue" value="1" <%=isOffer.equals("1")?"checked":""%> onchange="typeChange(this.value)">
                            <label class="form-check-label" for="isOfferTrue">Offer</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input <%=edit?"disabled":""%> class="form-check-input" type="radio" name="isOffer" id="isOfferFalse" value="0" <%=isOffer.equals("1")?"":"checked"%> onchange="typeChange(this.value)">
                            <label class="form-check-label" for="isOfferFalse">Service</label>
                        </div>
                    </div>
                </div>
                <div class="form-group" id="pPricel">
                    <label for="pPrice" >Print Price</label>
                    <input class="form-control" value="<%=pPrice%>" type="text" name="pPrice" id="pPrice" required oninput="checkPrintPrice()">
                </div>
                <div class="form-group">
                    <label  for="rPrice">Real Price</label>
                    <input class="form-control" value="<%=rPrice%>" type="text" name="rPrice" id="rPrice" required oninput="checkRealPrice()">
                </div>
                <div class="form-group" id="imgUrll">
                    <label  for="imgUrl">Image Url</label>
                    <input class="form-control" value="<%=imgUrl%>" type="url" name="imgUrl" id="imgUrl" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <small id="dHelp" class="form-text text-muted">
                        Add list element separate by new line
                    </small>
                    <textarea id="description" name="description" aria-describedby="dHelp" required><%=description%></textarea>

                </div>
                <div style="display: flex; justify-content: center;width: 100%;">
                    <button type="submit" class="btn" id="book-now-btn"><%=edit?"Update":"Create"%></button>
                </div>

            </form>


        </div>
    </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>
    $(document).ready(function(){
        typeChange($("#isOfferTrue").is(':checked'));
    })
    function typeChange(value) {
        if (value == "1") {
            $('#dHelp').show();
            $('#pPricel').show();
            $('#pPrice').attr("required","true")
            $('#imgUrll').hide();
            $('#imgUrl').val("");
            $('#imgUrl').removeAttr("required")
            $('#titel').text('Offers');
        }else{
            $('#dHelp').hide();
            $('#pPricel').hide();
            $('#pPrice').val("");
            $('#pPrice').removeAttr("required")
            $('#imgUrll').show();
            $('#imgUrl').attr("required","true");
            $('#titel').text('Services');
        }
    }
    function checkRealPrice() {
        $('#rPrice').val(checkCurrency($('#rPrice').val()))
    }
    function checkPrintPrice() {
        $('#pPrice').val(checkCurrency($('#pPrice').val()))
    }
    function checkCurrency(value){
        value = value.replace(/[^\d.]/g, '');

        const parts = value.split('.');
        if (parts.length > 2) {
            value = parts[0] + '.' + parts[1];
        }

        if (parts.length === 2) {
            value = parts[0] + '.' + parts[1].substring(0, 2);
        }
        return value;
    }
</script>

</body>
</html>
