package com.janani.prettytouch.servlet;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;

public class Feedback extends HttpServlet {
    private void dopost(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {



            String name = request.getParameter("name");
            String message = request.getParameter("message");
            String rating = request.getParameter("rating");
            String feedback ="Name: "+name+"\nMessage: "+message+"\nRating: "+rating;

            String path =getServletContext().getRealPath("/") + "feedback.txt";
            FileWriter writer = new FileWriter(path,true);
            writer.write(feedback);
            writer.close();
        }
    }


