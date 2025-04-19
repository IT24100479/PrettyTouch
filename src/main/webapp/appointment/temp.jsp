<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.model.Model" %>
<%@ page import="java.util.List" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.services.AppointmentService" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.model.AppointmentModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.janani.prettytouch.model.ServiceModel" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-04-19
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Appointments</title>
  <link rel="stylesheet" href="<%=request.getContextPath()+"/css/DataTables/datatables.min.css"%>">
  <script src="<%=request.getContextPath()+"/css/DataTables/datatables.min.js"%>"></script>
  <%
    UserModel logUser = (UserModel)session.getAttribute("user");
    String timeId = TypeConverter.replaceNull(request.getParameter("timeId"));
    String date = TypeConverter.replaceNull(request.getParameter("date"));
    if(logUser==null){
      response.sendRedirect(request.getContextPath()+"/user/logout");
    }
    List<Model> ap;
    assert logUser != null;
    String userRole = logUser.getRole();
    if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
      ap = AppointmentService.getInstance().getAll();
    }else{
      ap = AppointmentService.getInstance().getAllAppointmentsByUserId(logUser.getId());
    }
    List<AppointmentModel> appointments = new ArrayList<>();
    if(TypeConverter.stringIsNotEmpty(date)){
      for(int i=(ap.size()-1);i>=0;i--){
        LocalDate d = TypeConverter.stringToLocalDate(date);
        AppointmentModel a =(AppointmentModel) ap.get(i);
        if(d!=null && d.equals(a.getDate())){
          appointments.add(a);
        }
      }
    }else{
      for(int i=ap.size()-1;i>=0;i--){
        appointments.add((AppointmentModel) ap.get(i));
      }
    }
    if(TypeConverter.stringIsNotEmpty(timeId)){
      List<AppointmentModel> temp = new ArrayList<>();
      for(int i=0;i<appointments.size();i++){
        if(TypeConverter.stringToInt(timeId)==appointments.get(i).getTimeSlotId()){
          temp.add(appointments.get(i));
        }
      }
      appointments = temp;
    }
    List<AppointmentModel> temp2 = new ArrayList<>();
    for(int i=0;i<appointments.size();i++){
      if(!GlobalConst.APPOINTMENT_STATUS_TYPE.get(3).equalsIgnoreCase(appointments.get(i).getStatusForCsv())){
        temp2.add(appointments.get(i));
      }
    }
    appointments = temp2;
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

  </style>
</head>
<body>
<jsp:include page="../root/header.jsp"/>

<section class="allpage">
  <div id="appointments" class="container">
    <div class="innerContainer">
      <h2 class="section-title">Appointments</h2>
      <form method="get">
        <div class="form-row justify-content-md-center">
          <div class="col-md-6">
            <input type="date" name="date" class="form-control" placeholder="Appointment Date" value="<%=date%>">
          </div>
          <div class="col-md-6">
            <select id="timeId" class="form-control" name="timeId">
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
          <div class="col-md-4">
            <input type="submit" class="form-control btn2" value="Search" style="margin-top: 10px;">
          </div>
        </div>
      </form>
      <div style="margin-top: 10px;">
        <table class="table table-striped table-hover" id="dataTable">
          <thead>
          <tr class="bg-info">
            <th scope="col">#</th>
            <%if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(userRole)){%>
            <th scope="col">Client</th>
            <th scope="col">Tel</th>
            <%}%>
            <th scope="col">Service</th>
            <th scope="col">Price</th>
            <th scope="col">Date</th>
            <th scope="col">Time</th>
            <th scope="col">Note</th>
            <th scope="col">Status</th>
            <th scope="col">Edit Note</th>
            <th scope="col">Cancel</th>
            <%if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(userRole)){%>
            <th scope="col">Remove</th>
            <%}%>
          </tr>
          </thead>
          <tbody>
          <%if(appointments.isEmpty()){%>
          <tr>
            <th colspan="<%=GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(userRole)?"12":"9"%>" style="text-align: center">
              No Appointments
            </th>
          </tr>
          <%}else{
            for(int i=0;i<appointments.size();i++){
              AppointmentModel a = appointments.get(i);
              boolean isActive = GlobalConst.APPOINTMENT_STATUS_TYPE.get(1).equalsIgnoreCase(a.getStatusForCsv());
              UserModel c = a.getUser();
              ServiceModel s = a.getService();
          %>
          <tr>
            <th><%=a.getId()%></th>
            <%if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(userRole)){%>
            <td><%=c.getFirstName()+" "+c.getLastName()%></td>
            <td ><%=c.getPhoneNumber()%></td>
            <%}%>
            <td><%=s.getServiceName()%></td>
            <td><%=s.getRealPrice()%></td>
            <td><%=a.getDate()%></td>
            <td><%=GlobalConst.TIME_SLOT_LIST.get(a.getTimeSlotId())%></td>
            <td><%=a.getRequestData()%></td>
            <td><%=a.getStatusForCsv()%></td>
            <td>
              <%if(isActive){%>
              <a href="<%=request.getContextPath()+"/appointment/createAppointment.jsp?edit=true&aid="+a.getId()%>" class="btn btn-warning">Edit</a>
              <%}else{%>
              <a href="" class="btn btn-warning disabled">Edit</a>
              <%}%>
            </td>
            <td>
              <%if(isActive){%>
              <a href="<%=request.getContextPath()+"/appointment/cancel?aid="+a.getId()%>" class="btn btn-secondary">Cancel</a>
              <%}else{%>
              <a href="" class="btn btn-secondary disabled">Cancel</a>
              <%}%>
            </td>
            <%if(GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(userRole)){%>
            <td>
              <%if(isActive){%>
              <a href="<%=request.getContextPath()+"/appointment/Remove?aid="+a.getId()%>" class="btn btn-danger">Remove</a>
              <%}else{%>
              <a href="" class="btn btn-danger disabled">Remove</a>
              <%}%>
            </td>
            <%}%>
          </tr>
          <%}
          }%>

          </tbody>
        </table>
      </div>
    </div>
  </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>
  new DataTable('#dataTable',{
    dom: 'RBflrtip',
    order:[],
    buttons: [
      {
        extend: 'colvis',
        collectionLayout: 'fixed two-column'
      },
      'copyHtml5',
      'excelHtml5',
      'csvHtml5',
      'pdfHtml5',


    ],
    autoFill: true,
    "paging": true,
    "scrollX": true,
    "sScrollX": "100%",
    "sScrollXInner": "100%",
    select: true
  });
</script>

</body>
</html>
