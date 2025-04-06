package com.janani.prettytouch.servlet;

import com.janani.prettytouch.model.UserModel;
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
        UserModel user = new UserModel();
        resp.setContentType("application/json");
        if (req.getParameter("username") != null && req.getParameter("password") != null) {
            if (req.getParameter("username").equals("j") && req.getParameter("password").equals("123")) {
                HttpSession session = req.getSession(true);
                user.setUsername(req.getParameter("username"));
                user.setRole("user");
                session.setAttribute("user", user);
                resp.getWriter().write("{\"msg\":\"login success\"}");
            } else if (req.getParameter("username").equals("s") && req.getParameter("password").equals("456")) {
                HttpSession session = req.getSession(true);
                user.setUsername(req.getParameter("username"));
                user.setRole("admin");
                session.setAttribute("user", user);
                resp.getWriter().write("{\"msg\":\"login success\"}");
            } else{
                resp.getWriter().write("{\"msg\":\"Invalid User Name Or Password\"}");
            }
        }else {
            resp.getWriter().write("{\"msg\":\"User Name Or Password Can Not Be Empty\"}");

        }

    }
}
