<%@ page import="com.janani.prettytouch.model.UserModel" %>
<%@ page import="com.janani.prettytouch.constVar.GlobalConst" %>
<%@ page import="com.janani.prettytouch.model.FeedbackModel" %>
<%@ page import="com.janani.prettytouch.services.FeedbackService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.janani.prettytouch.util.TypeConverter" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/9/2025
  Time: 10:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedback Management</title>
    <link rel="stylesheet" href="<%=request.getContextPath()+"/css/DataTables/datatables.min.css"%>">
    <script src="<%=request.getContextPath()+"/css/DataTables/datatables.min.js"%>"></script>
    <%
        UserModel logUser = (UserModel)session.getAttribute("user");

        if(logUser==null || !GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(logUser.getRole())){
            response.sendRedirect(request.getContextPath()+"/user/logout");
            return;
        }
        List<FeedbackModel> list = FeedbackService.getInstance().getAllForShow();


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
            <h2 class="section-title">Feedback Management</h2>

            <div style="margin-top: 10px;">
                <table class="table table-striped table-hover" id="dataTable">
                    <thead>
                    <tr class="bg-info">
                        <th scope="col">#</th>
                        <th scope="col">User #</th>
                        <th scope="col">User Name</th>
                        <th scope="col">User</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Comment</th>
                        <th scope="col">Crated At</th>
                        <th scope="col">Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%if(!list.isEmpty()){
                        for(int i=list.size()-1;i>=0;i--){
                            FeedbackModel f = list.get(i);
                            UserModel u = f.getCreatedByUser();
                    %>
                    <tr>
                        <th><%=f.getId()%></th>
                        <td><%=u.getId()%></td>
                        <td><%=u.getUsername()%></td>
                        <td><%=u.getFirstName()+" "+u.getLastName()%></td>
                        <td><%=f.getRating()%></td>
                        <td title="<%=f.getComment()%>"><%=f.getCommentShort()%></td>
                        <td><%=TypeConverter.formatLocalDateTime(f.getCreatedAt())%></td>
                        <td>
                            <a href="<%=request.getContextPath()+"/feedback/remove?fid="+f.getId()%>" class="btn btn-danger">Remove</a>
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
            emptyTable: "No Feedback Available"
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

