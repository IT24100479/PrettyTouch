package com.janani.prettytouch.servlet.appointment;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/appointment/cancel")
public class Cancel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null) {
            AppointmentService appointmentService = AppointmentService.getInstance();
            AppointmentModel appointmentModel = (AppointmentModel) appointmentService.getById(TypeConverter.stringToInt(req.getParameter("aid")));
            if(appointmentModel != null) {
                appointmentModel.setStatus(GlobalConst.APPOINTMENT_STATUS_TYPE.get(2));
                appointmentService.update(appointmentModel);
            }
            resp.sendRedirect(appointmentService.createReturnUrl(req.getContextPath(),"/appointment/appointments.jsp",appointmentModel,null));
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
