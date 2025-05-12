package com.janani.prettytouch.servlet.service;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/services/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())) {
            ServiceService serviceService = ServiceService.getInstance();
            ServiceModel serviceModel = new ServiceModel();
            serviceModel.setServiceName(req.getParameter("sName"));
            serviceModel.setDescription(req.getParameter("description"));
            serviceModel.setIsOffer(req.getParameter("isOffer"));
            serviceModel.setPrintPrice(req.getParameter("pPrice"));
            serviceModel.setRealPrice(req.getParameter("rPrice"));
            serviceModel.setImageUrl(req.getParameter("imgUrl"));
            serviceModel.setCreatedBy(TypeConverter.intToString(user.getId()));
            serviceModel.setStatus("1");
            if(serviceModel.validate()){
                if(!serviceService.isDuplicate(serviceModel)){
                    if(serviceService.add(serviceModel)){
                        resp.sendRedirect(serviceService.createReturnUrl(req.getContextPath(),"/services/serviceManage.jsp",serviceModel,null));
                    }else{
                        String error = "Service Addition Failed Try Again";
                        resp.sendRedirect(serviceService.createReturnUrl(req.getContextPath(),"/services/createService.jsp",serviceModel,error));
                    }

                }else{
                    String error = "Duplicate Service Name";
                    resp.sendRedirect(serviceService.createReturnUrl(req.getContextPath(),"/services/createService.jsp",serviceModel,error));
                }
            }else{
                String error = "Required Fields Are Empty";
                resp.sendRedirect(serviceService.createReturnUrl(req.getContextPath(),"/services/createService.jsp",serviceModel,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
