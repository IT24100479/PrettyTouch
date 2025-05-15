package com.janani.prettytouch.servlet.feedback;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.FeedbackModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.FeedbackService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/feedback/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_USER.equalsIgnoreCase(user.getRole())) {
            FeedbackService feedbackService = FeedbackService.getInstance();
            FeedbackModel feedback = new FeedbackModel();
            feedback.setAppointmentId(req.getParameter("aid"));
            feedback.setRating(req.getParameter("rating"));
            feedback.setComment(req.getParameter("comment"));
            feedback.setCreatedBy(TypeConverter.intToString(user.getId()));
            feedback.setStatus("1");

            if(feedback.validate()){

                    if(feedbackService.add(feedback)){
                        String msg = "Rating is Successfully Added";
                        resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",feedback,null)+"&success=tue&msg="+msg);
                    }else{
                        String error = "Rating Addition Failed Try Again";
                        resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",feedback,error));
                    }


            }else{
                String error = "Required Fields Are Empty";
                resp.sendRedirect(feedbackService.createReturnUrl(req.getContextPath(),"/feedback/createFeedback.jsp",feedback,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
