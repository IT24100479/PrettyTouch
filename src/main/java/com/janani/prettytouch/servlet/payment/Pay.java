package com.janani.prettytouch.servlet.payment;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.PaymentModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.PaymentService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/payment/pay")
public class Pay extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())) {
            PaymentService paymentService = PaymentService.getInstance();
            AppointmentService appointmentService = AppointmentService.getInstance();
            AppointmentModel appointmentModel = (AppointmentModel)appointmentService.getById(TypeConverter.stringToInt(req.getParameter("aid")));
            if(appointmentModel != null) {
                PaymentModel paymentModel = new PaymentModel();
                String cash = req.getParameter("cash");
                paymentModel.setCash(req.getParameter("cash"));
                paymentModel.setCreatedBy(TypeConverter.intToString(user.getId()));
                paymentModel.setAmount(TypeConverter.doubaleToString(appointmentModel.getService().getRealPrice()));
                paymentModel.setAppointmentId(TypeConverter.intToString(appointmentModel.getId()));
                paymentModel.setStatus("1");
                paymentService.add(paymentModel);
                appointmentModel.setStatus(GlobalConst.APPOINTMENT_STATUS_TYPE.get(0));
                appointmentService.update(appointmentModel);
                resp.getWriter().write("{\"msg\":\"success\"}");
            }else{
                resp.getWriter().write("{\"msg\":\"Can Not Find Appointment\"}");
            }

        }else{
            resp.getWriter().write("{\"msg\":\"No permission\"}");
        }
    }
}
