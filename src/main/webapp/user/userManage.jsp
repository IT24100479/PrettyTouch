<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %>
<%@ page import="com.janani.prettytouch.model.ServiceModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.janani.prettytouch.services.ServiceService" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.services.UserService" %><%--
<%--
  Created by IntelliJ IDEA.
  User: Garawa
  Date: 5/9/2025
  Time: 5:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/DataTables/datatables.min.css"%>">
    <script src="<%=request.getContextPath()+"/css/DataTables/datatables.min.js"%>"></script>
    <%
        UserModel logUser = (UserModel)session.getAttribute("user");

        if(logUser==null || !GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
            response.sendRedirect(request.getContextPath()+"/user/logout");
            return;
        }
        String role=TypeConverter.replaceNull(request.getParameter("role"));
        List<UserModel> list = UserService.getInstance().getUserWithFilter(role);
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
    <div id="payment" class="container">
        <div class="innerContainer">
            <h2 class="section-title">User Management</h2>
            <form method="get" id="form">
                <div class="form-row justify-content-md-center">

                    <div class="col-md-6">
                        <div class="form-group">
                            <select type="text" id="signup-role" name="role" >
                                <option value="">Select User Type</option>
                                <option value="<%=GlobalConst.USER_TYPE_ADMIN%>" <%=GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(role)?"selected":""%>>Admin</option>
                                <option value="<%=GlobalConst.USER_TYPE_USER%>" <%=GlobalConst.USER_TYPE_USER.equalsIgnoreCase(role)?"selected":""%>>User</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <input type="submit" class="form-control btn2" value="Search">
                    </div>
                </div>
            </form>
            <div style="margin-top: 10px;">
                <table class="table table-striped table-hover" id="dataTable">
                    <thead>
                    <tr class="bg-info">
                        <th scope="col">User ID</th>
                        <th scope="col">User Name</th>
                        <th scope="col">First Name</th>
                        <th scope="col">Last Name</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">Email</th>
                        <th scope="col">Tel</th>
                        <th scope="col">Role</th>
                        <th scope="col">Crated At</th>
                        <th scope="col">Update</th>
                        <th scope="col">Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if(!list.isEmpty()){
                            for(int i=list.size()-1;i>=0;i--){
                                UserModel s = list.get(i);
                    %>
                    <tr>
                        <th><%=s.getId()%></th>
                        <td><%=s.getUsername()%></td>
                        <td><%=s.getFirstName()%></td>
                        <td><%=s.getLastName()%></td>
                        <td><%=TypeConverter.localDateToString(s.getDob())%></td>
                        <td><%=s.getEmail()%></td>
                        <td><%=s.getPhoneNumber()%></td>
                        <td><%=s.getRole()%></td>
                        <td><%=TypeConverter.formatLocalDateTime(s.getCreatedAt())%></td>
                        <td>
                            <a href="<%=request.getContextPath()+"/user/signUp.jsp?edit=true&uid="+s.getId()%>" class="btn btn-warning">Edit</a>
                        </td>
                        <td>
                            <a href="<%=request.getContextPath()+"/users/remove?uid="+s.getId()%>" class="btn btn-danger">Remove</a>
                        </td>
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
        language: {
            emptyTable: "No Services Available"
        },
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
