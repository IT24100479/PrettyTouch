package com.janani.prettytouch.servlet.feedback;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.FeedbackModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.FeedbackService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Remove  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())) {
            FeedbackService feedbackService = FeedbackService.getInstance();
            int id = TypeConverter.stringToInt(req.getParameter("fid"));
            FeedbackModel model = (FeedbackModel) feedbackService.getById(id);
            AppointmentService appointmentService = AppointmentService.getInstance();
            if(feedbackService.delete(id)){

                resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/manageFeedback.jsp",model,null));

            }else{
                String error = "Rating Delete Failed Try Again";
                resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/manageFeedback.jsp",model,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }

}


