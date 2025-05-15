package com.janani.prettytouch.servlet.user;

import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.model.UserModel;
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

@WebServlet("/users/remove")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && GlobalConst.USER_TYPE_ADMIN.equalsIgnoreCase(user.getRole())) {
            UserService userService = UserService.getInstance();
            int id =TypeConverter.stringToInt(req.getParameter("uid"));
            UserModel serviceModel = (UserModel) userService.getById(id);
            if(userService.delete(id)){
                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/userManage.jsp",serviceModel,null));

            }else{
                String error = "Service Delete Failed Try Again";
                resp.sendRedirect(userService.createReturnUrl(req.getContextPath(),"/user/userManage.jsp",serviceModel,error));
            }
        }else{
            resp.sendRedirect(req.getContextPath()+"/user/logout");
        }
    }

}
