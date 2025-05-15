package com.janani.prettytouch.servlet.user;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.services.UserService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/users/create")
public class Create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null || GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())) {
            UserService userService = UserService.getInstance();
            UserModel userModel = new UserModel();
            userModel.setUsername(req.getParameter("username"));
            userModel.setPassword(req.getParameter("password"));
            userModel.setFirstName(req.getParameter("firstname"));
            userModel.setLastName(req.getParameter("lastname"));
            userModel.setPhoneNumber(req.getParameter("phone"));
            userModel.setEmail(req.getParameter("email"));
            userModel.setDOB(req.getParameter("dob"));
            if (user != null){
                userModel.setCreatedBy(TypeConverter.intToString(user.getId()));
                userModel.setRole(req.getParameter("role"));
            }else{
                userModel.setRole(GlobalConst.USER_TYPE_USER);
            }
            userModel.setStatus("1");
            if(userModel.validate()){
                if(!userService.isDuplicate(userModel)){
                    if(userService.add(userModel)){
                        if (user != null) {
                            resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/userManage.jsp",userModel,null));
                        }else {
                            resp.sendRedirect(req.getContextPath()+"/index.jsp?login=true");
                        }
                    }else{
                        String error = "User Addition Failed Try Again";
                        resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/signUp.jsp",userModel,error));
                    }

                }else{
                    String error = "Duplicate User Name , Email or Telephone Number";
                    resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/signUp.jsp",userModel,error));
                }
            }else{
                String error = "Required Fields Are Empty";
                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/signUp.jsp",userModel,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
