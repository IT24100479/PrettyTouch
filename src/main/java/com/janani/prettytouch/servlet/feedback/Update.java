package com.janani.prettytouch.servlet.feedback;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.FeedbackModel;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.FeedbackService;
import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/feedback/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_USER.equalsIgnoreCase(user.getRole())) {
            FeedbackService feedbackService = FeedbackService.getInstance();
            int id = TypeConverter.stringToInt(req.getParameter("fid"));
            FeedbackModel  model = (FeedbackModel) feedbackService.getById(id);
            if(model!=null) {
                model.setRating(req.getParameter("rating"));
                model.setComment(req.getParameter("comment"));
                if(model.validate()){
                    if(feedbackService.update(model)){
                        String msg = "Rating is Successfully Edited";
                        resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",model,null)+"&success=tue&msg="+msg);
                    }else{
                        String error = "Rating Update Failed Try Again";
                        resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",model,error));
                    }
                }else{
                    String error = "Required Fields Are Empty";
                    resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",model,error));
                }
            }else{
                String error = "Can't find Rating";
                resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",model,error));
            }

        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
