package com.janani.prettytouch.servlet.user;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.UserService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/users/update")
public class Update extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        int id = TypeConverter.stringToInt(req.getParameter("uid"));
        if (user != null && (GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())||user.getId()==id)) {
            UserService userService = UserService.getInstance();
            UserModel userModel = (UserModel) userService.getById(id);
            if(userModel!=null) {
                userModel.setFirstName(req.getParameter("firstname"));
                userModel.setLastName(req.getParameter("lastname"));
                userModel.setPhoneNumber(req.getParameter("phone"));
                userModel.setEmail(req.getParameter("email"));
                userModel.setDOB(req.getParameter("dob"));
                if (GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())){
                    userModel.setRole(req.getParameter("role"));
                }
                String password = req.getParameter("password");
                if(TypeConverter.stringIsNotEmpty(password)){
                    userModel.setPassword(password);
                }
                if(userModel.validate()){
                    if(!userService.isDuplicate(userModel)){
                        if(userService.update(userModel)){
                            if (GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())){
                                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/userManage.jsp",userModel,null));
                            }else{
                                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/signUp.jsp",userModel,null)+"&updated=true");
                            }

                        }else{
                            String error = "User Update Failed Try Again";
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
                String error = "Can't Find User";
                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/signUp.jsp",userModel,error));
            }

        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }
}
