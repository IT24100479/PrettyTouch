package com.janani.prettytouch.servlet.user;

import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("application/json");
        if (req.getParameter("username") != null && req.getParameter("password") != null) {
            UserModel user = UserService.getInstance().checkLogin(
                    req.getParameter("username"),
                    req.getParameter("password")
            );

            if (user != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                resp.getWriter().write("{\"msg\":\"login success\"}");
            }else {
                resp.getWriter().write("{\"msg\":\"Invalid User Name Or Password\"}");
            }
        }else {
            resp.getWriter().write("{\"msg\":\"User Name Or Password Can Not Be Empty\"}");

        }

    }
}
