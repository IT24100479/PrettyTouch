<%@ page import="java.util.List" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.services.AppointmentService" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.janani.prettytouch.model.*" %>
<%@ page import="com.janani.prettytouch.services.PaymentService" %>
<%@ page import="static jdk.internal.org.jline.utils.Colors.s" %>
<%@ page import="com.janani.prettytouch.util.Queue" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-19
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Payment</title>
  <link rel="stylesheet" href="<%=request.getContextPath()+"/css/DataTables/datatables.min.css"%>">
  <script src="<%=request.getContextPath()+"/css/DataTables/datatables.min.js"%>"></script>
  <%
    UserModel logUser = (UserModel)session.getAttribute("user");
    String timeId = TypeConverter.replaceNull(request.getParameter("time"));
    String date = TypeConverter.replaceNull(request.getParameter("date"));
    if(logUser==null || !GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
      response.sendRedirect(request.getContextPath()+"/user/logout");
      return;
    }
    if(TypeConverter.stringIsEmpty(date)){
      date=TypeConverter.localDateToString(LocalDate.now());
    }
    if(TypeConverter.stringIsEmpty(timeId)){
      timeId="0";
    }
    Queue queue = AppointmentService.getInstance().getQueue(date,timeId);
    AppointmentModel appointment=null;
    ServiceModel service = null;
    UserModel client = null;
    UserModel createdBy =null;
    if(queue!=null && !queue.isEmpty()){
      appointment = (AppointmentModel)queue.peekFront();
      service=appointment.getService();
      client=appointment.getUser();
      createdBy=appointment.getCreatedByUser();
    }

  %>
  <style>
    .allpage .container {
      position: relative;
      z-index: 1;
      max-width: none;

    }
    .allpage .container .innerContainer{
      background: #fff;
      padding: 50px;
      border-radius: 10px;
      margin: 0 auto;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    }
    .btn2 {
      display: inline-block;
      background: var(--primary-color) !important;
      color: #fff!important;
      padding: 12px 30px!important;
      border: none!important;
      border-radius: 50px!important;
      cursor: pointer!important;
      font-size: 16px!important;
      font-weight: 600!important;
      transition: all 0.3s ease!important;
      box-shadow: 0 4px 15px rgba(255, 107, 107, 0.3)!important;
    }

    .btn2:hover {
      background: #ff5252!important;
      transform: translateY(-3px)!important;
      box-shadow: 0 6px 20px rgba(255, 107, 107, 0.4)!important;
    }
    .dt-search{
      text-align: end!important;
    }
    .dt-paging .pagination {
      justify-content: end!important;
    }
    .dt-paging .pagination .page-item{
      margin-left: 0px !important;
    }
    .dt-length{
      padding-bottom: 10px !important;
    }

    .firstCapitalize{
      text-transform: capitalize;
    }
    .col-md-4{
      padding-top: 5px;
    }

  </style>
</head>
<body>
<jsp:include page="../root/header.jsp"/>

<section class="allpage">
  <div id="payment" class="container">
    <div class="innerContainer">
      <h2 class="section-title">Payment</h2>
      <form method="get" id="form">
        <div class="form-row justify-content-md-center">
<%--          <div class="col-md-6">--%>
<%--            <input type="date" name="date" id="date" class="form-control" placeholder="Appointment Date" value="<%=date%>">--%>
<%--          </div>--%>
          <div class="col-md-6">
            <input type="hidden" name="date" id="date" value="<%=date%>">
            <select id="time" class="form-control" name="time" id="time" onchange="timeslotChange()">
              <option value="">Select a Time Slot</option>
              <%
                for(int i = 0; i< GlobalConst.TIME_SLOT_LIST.size(); i++){
                  String slot = GlobalConst.TIME_SLOT_LIST.get(i);
                  if(TypeConverter.intToString(i).equals(timeId)){%>
              <option value="<%=i%>" selected><%=slot%></option>
              <%}else{%>
              <option value="<%=i%>"><%=slot%></option>
              <%}
              }
              %>
            </select>
          </div>
<%--          <div class="col-md-4">--%>
<%--            <input type="submit" class="form-control btn2" value="Search" style="margin-top: 10px;">--%>
<%--          </div>--%>
        </div>
      </form>
      <div style="margin-top: 30px;">
        <%if(appointment==null){%>
        <h4 style="padding-top: 50px;text-align: center">No Appointment Available</h4>
        <%}else{%>
        <div class="card text-center">
          <div class="card-header firstCapitalize">
            <h5><%=service.getServiceName()+" (RS."+service.getRealPrice()+")"%></h5>
          </div>
          <div class="card-body firstCapitalize text-left">
            <h5 class="card-title text-center"><%=client.getFirstName()+" "+ client.getLastName()%></h5>

            <div class="row">
              <div class="col-md-4">
                <b>Client Id:&nbsp;</b><%=client.getId()%>
              </div>
              <div class="col-md-4">
                <b>Client Tel:&nbsp;</b><%=client.getPhoneNumber()%>
              </div>
              <div class="col-md-4">
                <b>Client Dob:&nbsp;</b><%=client.getDob()%>
              </div>
              <div class="col-md-4">
                <b>Service Id:&nbsp;</b><%=service.getId()%>
              </div>
              <div class="col-md-4">
                <b>Service Name:&nbsp;</b><%=service.getServiceName()%>
              </div>
              <div class="col-md-4">
                <b>Service Real Price:&nbsp;</b>Rs.<%=service.getRealPrice()%>
              </div>
              <div class="col-md-4">
                <b>Service Print Price:&nbsp;</b>Rs.<%=service.getPrintPrice()%>
              </div>
              <div class="col-md-4">
                <b>Service Is Offer:&nbsp;</b><%=service.getIsOffer()%>
              </div>
              <div class="col-md-4">
                <b>Appointment Id:&nbsp;</b><%=appointment.getId()%>
              </div>
              <div class="col-md-4">
                <b>Appointment date:&nbsp;</b><%=appointment.getDate()%>
              </div>
              <div class="col-md-4">
                <b>Appointment Time:&nbsp;</b><%=GlobalConst.TIME_SLOT_LIST.get(appointment.getTimeSlotId())%>
              </div>
              <div class="col-md-4">
                <b>Appointment Status:&nbsp;</b><%=appointment.getStatusForCsv()%>
              </div>
              <div class="col-md-4">
                <b>Created At:&nbsp;</b><%=TypeConverter.formatLocalDateTime(appointment.getCreatedAt())%>
              </div>
              <div class="col-md-4">
                <b>Created By:&nbsp;</b><%=createdBy.getFirstName()+" "+createdBy.getLastName()%>
              </div>

            </div>
            <p class="card-text text-center" style="padding-top: 5px"><b>Note:&nbsp;</b><%=appointment.getRequestData()%></p>
<%--            <a href="#" class="btn btn-primary">Go somewhere</a>--%>
          </div>
          <div class="card-footer alert-warning">
            <div class="row">
              <div class="col-md-12 alert alert-danger" role="alert" style="display: none;" id="error">

              </div>
            </div>
            <div class="row text-left">
              <div class="col-md-4">
                <label for="cash">Cash</label>
                <input type="text" oninput="calBal()" name="cash" id="cash" class="form-control" autocomplete="off">
              </div>
              <div class="col-md-4">
                <label for="bal">Balance</label>
                <input type="text" name="bal" id="bal" class="form-control" disabled autocomplete="off">
              </div>
              <div class="col-md-2">
                <label for="compBtn">&nbsp;</label><br/>
                <button type="button" class="btn btn-primary" id="compBtn" onclick="completed()">Complete And Next</button>
              </div>
            </div>
          </div>
        </div>
        <%}%>
      </div>
    </div>
  </div>
</section>
<jsp:include page="../root/footer.jsp"/>

<script>
  function timeslotChange(){
    $('#form').submit();
  }

  function calBal(){
    let cash = $('#cash').val();
    let amount = <%=service!=null?service.getRealPrice():0.0%>;
    // Remove all characters except digits and a single dot
    cash = cash.replace(/[^\d.]/g, '');

    // Keep only the first dot (in case multiple were typed)
    const parts = cash.split('.');
    if (parts.length > 2) {
      cash = parts[0] + '.' + parts[1];
    }

    // Trim decimal part to 2 digits
    if (parts.length === 2) {
      cash = parts[0] + '.' + parts[1].substring(0, 2);
    }
    $('#cash').val(cash);
    if(cash==null||cash==0){
      $('#bal').val("");
    }else{
      $('#bal').val((cash-amount).toFixed(2));
    }
  }
  function completed() {
    let cash = $('#cash').val();
    let amount = <%=service!=null?service.getRealPrice():0.0%>;
    if(cash==null||cash==0){
      $("#error").html("Cash Value Required");
      $("#error").show();
    }else if(cash<amount){
      $("#error").html("Cash Must Be Greater Than Or Equal To Amount");
      $("#error").show();
    }else{
      $("#error").hide();
      $.ajax({
        url: '<%=request.getContextPath()+"/payment/pay"%>', // Sample API
        type: 'POST',
        data: {
          cash:cash,
          aid:<%=appointment!=null?appointment.getId():0%>
        },
        success: function(response) {
          let msg = response.msg;
          if(msg==undefined){
            msg=JSON.parse(response).msg;
          }
          if(msg=="success"){
            window.location.reload();
          }else{
            $("#error").html(msg);
            $("#error").show();
          }
        },
        error: function(xhr, status, error) {
          console.log(JSON.stringify(error));
        }
      });
    }

  }
</script>
</body>
</html>
