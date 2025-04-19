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

@WebServlet("/appointment/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null) {
            AppointmentService appointmentService = AppointmentService.getInstance();
            AppointmentModel appointmentModel = new AppointmentModel();
            appointmentModel.setCreatedBy(TypeConverter.intToString(user.getId()));
            appointmentModel.setUserId(req.getParameter("client"));
            appointmentModel.setServiceId(req.getParameter("service"));
            appointmentModel.setDate(req.getParameter("date"));
            appointmentModel.setTimeSlotId(req.getParameter("time"));
            appointmentModel.setRequestData(req.getParameter("notes"));
            appointmentModel.setStatus(GlobalConst.APPOINTMENT_STATUS_TYPE.get(1));
            if(appointmentModel.validate()){
                if(appointmentService.add(appointmentModel)){
                    resp.sendRedirect(req.getContextPath()+"/appointment/createAppointment.jsp");
                }else{
                    String error = "Selected Time Slot Is Full. Please Select Different Date Or Time Slot";
                    resp.sendRedirect(createReturnUrl(req.getContextPath(),appointmentModel,error));
                }
            }else{
                String error = "Service , Date And Time Slot Are Required";
                resp.sendRedirect(createReturnUrl(req.getContextPath(),appointmentModel,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }

    private String createReturnUrl(String base,AppointmentModel appointmentModel,String error){
        String url = base+"/appointment/createAppointment.jsp?";
        url+="uid="+appointmentModel.getUserId();
        url+="&id="+appointmentModel.getServiceId();
        url+="&date="+appointmentModel.getDate();
        url+="&time="+appointmentModel.getTimeSlotId();
        url+="&req="+appointmentModel.getRequestData();
        if(TypeConverter.stringIsNotEmpty(error)){
            url+="&error="+error;
        }
        return url;
    }
}
