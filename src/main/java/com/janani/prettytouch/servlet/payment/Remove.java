package com.janani.prettytouch.servlet.payment;

import com.janani.prettytouch.model.PaymentModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.PaymentService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/payment/remove")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null) {
            PaymentService paymentService = PaymentService.getInstance();
            PaymentModel paymentModel = (PaymentModel) paymentService.getById(TypeConverter.stringToInt(req.getParameter("pid")));
            if(paymentModel != null) {
                paymentService.delete(paymentModel.getId());
            }
            resp.sendRedirect(paymentService.createReturnUrl(req.getContextPath(),"/payment/report.jsp",paymentModel,null));
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
