<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.model.FeedbackModel" %>
<%@ page import="com.janani.prettytouch.services.FeedbackService" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/7/2025
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>FeedBack</title>
  <link rel="stylesheet" href="<%=request.getContextPath()+"/css/booking.css"%>">
  <%
     UserModel logUser = (UserModel)session.getAttribute("user");
     if(logUser==null || !GlobalConst.USER_TYPE_USER.equalsIgnoreCase(logUser.getRole())){
      response.sendRedirect(request.getContextPath()+"/user/logout");
      return;
    }
    String fid=TypeConverter.replaceNull(request.getParameter("fid"));
    String rating= TypeConverter.replaceNull(request.getParameter("rating"));
    String comment=TypeConverter.replaceNull(request.getParameter("comment"));
    boolean edit = false;
    if(logUser.getId()!=0){
      FeedbackModel sModel = (FeedbackModel) FeedbackService.getInstance().getRatingByUserId(logUser.getId());
      if(sModel!=null){
        fid=TypeConverter.intToString(sModel.getId());
        rating=TypeConverter.intToString(sModel.getRating());
        comment=sModel.getComment();
        edit=true;
      }
    }
    if(TypeConverter.stringIsEmpty(rating)){
      rating="0";
    }
    String error = TypeConverter.replaceNull(request.getParameter("error"));
    boolean isError =TypeConverter.stringIsNotEmpty(error);
    String actionUrl = request.getContextPath()+"/feedback/"+(edit?"update":"create");
    String msg =TypeConverter.replaceNull(request.getParameter("msg"));
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
<body onload="setStarRating()">
<jsp:include page="../root/header.jsp"/>
<!-- Booking Page -->
<section class="booking-page">
  <div class="container">
    <h2 class="section-title" id="titel">FeedBack</h2>
    <div class="booking-container">

      <% if(isError){%>
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Error!</strong> <%=error%>
      </div>
      <%}%>
      <% if(TypeConverter.stringIsNotEmpty(msg)){%>
      <div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Success!</strong> <%=msg%>
      </div>
      <%}%>
      <form class="booking-form" id="appointment-form" method="POST" action="<%=actionUrl%>">

        <input type="hidden" name="fid" value="<%=fid%>"/>
        <div class="form-group">
          <label for="rating">Rating</label>
          <input name="rating" value="<%=rating%>" type="range" min="0" max="5" class="form-control" id="rating" required onchange="setStarRating()">
          <small><span id="star">0</span> Out Of 5</small>
        </div>
        <div class="form-group">
          <label for="comment">Comment</label>
          <textarea id="comment" name="comment" required><%=comment%></textarea>
        </div>

        <div style="display: flex; justify-content: center;width: 100%;">
          <button type="submit" class="btn" id="book-now-btn"><%=edit?"Update":"Add"%> Rating</button>
        </div>

      </form>


    </div>
  </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>
  function setStarRating(){
    let rating=$("#rating").val();
    $('#star').html(rating)
  }

</script>

</body>
</html>

