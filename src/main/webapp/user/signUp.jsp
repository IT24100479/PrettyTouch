<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.services.UserService" %><%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-05-02
  Time: 9:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/booking.css"%>">
    <script src="<%=request.getContextPath()+"/js/data/services.js"%>"></script>
    <%
        UserModel logUser = (UserModel)session.getAttribute("user");
        boolean edit = request.getParameter("edit")!=null;
        String title="Sing Up";
        String btnTxt=title;

        String uid=TypeConverter.replaceNull(request.getParameter("uid"));
        String uName=TypeConverter.replaceNull(request.getParameter("uName"));
        String fName=TypeConverter.replaceNull(request.getParameter("fName"));
        String lName=TypeConverter.replaceNull(request.getParameter("lName"));
        String role=TypeConverter.replaceNull(request.getParameter("role"));
        String pNumber=TypeConverter.replaceNull(request.getParameter("pNumber"));
        String email=TypeConverter.replaceNull(request.getParameter("email"));
        String dob=TypeConverter.replaceNull(request.getParameter("dob"));

        if(logUser==null){
            if(edit){
                response.sendRedirect(request.getContextPath()+"/user/logout");
                return;
            }
            title="Sing Up";
            btnTxt=title;
        }else if( GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
            title=edit?"Edit User":"Create User";
            btnTxt=title;
        } else if (GlobalConst.USER_TYPE_USER.equalsIgnoreCase(logUser.getRole())) {
            uid=logUser.getId()+"";
            edit=true;
            title="Profile";
            btnTxt="Update "+title;

        }

        if(TypeConverter.stringIsNotEmpty(uid)){
            UserModel uModel = (UserModel) UserService.getInstance().getById(TypeConverter.stringToInt(uid));
            if(uModel!=null){
                uName= uModel.getUsername();
                fName= uModel.getFirstName();
                lName= uModel.getLastName();
                role= uModel.getRole();
                pNumber= uModel.getPhoneNumber();
                email= uModel.getEmail();
                dob= TypeConverter.localDateToString(uModel.getDob());
            }
        }
        String error = TypeConverter.replaceNull(request.getParameter("error"));
        boolean isError =TypeConverter.stringIsNotEmpty(error);


        String actionUrl = request.getContextPath()+"/users/"+(edit?"update":"create");
    %>
    <title><%=title%></title>
</head>
<body>
<jsp:include page="../root/header.jsp"/>
<!-- Booking Page -->
<section class="booking-page">
    <div class="container">
        <h2 class="section-title" id="titel"><%=title%></h2>
        <div class="booking-container">

            <% if(isError){%>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> <%=error%>
            </div>
            <%}%>
            <% if("true".equalsIgnoreCase(request.getParameter("updated"))){%>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>Updated!</strong> Profile updated successfully!
            </div>
            <%}%>
            <form id="signup-form" action=<%=actionUrl%> method="POST">
                <% if( logUser!=null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){%>
                <div class="form-group">
                    <label for="signup-role">Role</label>
                    <select type="text" id="signup-role" name="role"  required>
                        <option value="">Select User Type</option>
                        <option value="<%=GlobalConst.USER_TYPE_ADMIN%>" <%=GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(role)?"selected":""%>>Admin</option>
                        <option value="<%=GlobalConst.USER_TYPE_USER%>" <%=GlobalConst.USER_TYPE_USER.equalsIgnoreCase(role)?"selected":""%>>User</option>
                    </select>
                </div>
                <%}%>
                <div class="form-group">
                    <label for="signup-firstname">First Name</label>
                    <input type="text" id="signup-firstname" name="firstname" value="<%=fName%>" required>
                    <input type="hidden" id="uid" name="uid" value="<%=uid%>" required>
                </div>
                <div class="form-group">
                    <label for="signup-lastname">last Name</label>
                    <input type="text" id="signup-lastname" name="lastname" value="<%=lName%>" required>
                </div>
                <div class="form-group">
                    <label for="dob">Date of Birth</label>
                    <input type="Date" id="dob" name="dob" value="<%=dob%>" required>
                </div>
                <div class="form-group">
                    <label for="signup-email">Email</label>
                    <input type="email" id="signup-email" name="email" value="<%=email%>" required>
                </div>
                <div class="form-group">
                    <label for="signup-phone">Telephone Number</label>
                    <input type="tel" id="signup-phone" name="phone" value="<%=pNumber%>" required>
                </div>
                <div class="form-group">
                    <label for="signup-username">Username</label>
                    <input <%=edit?"disabled":""%> type="text" id="signup-username" name="username" value="<%=uName%>" required>
                </div>
                <div class="form-group">
                    <label for="signup-password">Password</label>
                    <input <%=edit?"":"required"%> type="password" id="signup-password" name="password">
                </div>
                <button type="submit" class="btn login-btn"><%=btnTxt%></button>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../root/footer.jsp"/>
<script>

</script>

</body>
</html>
