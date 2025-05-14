package com.janani.prettytouch.servlet.feedback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.janani.prettytouch.adapter.LocalDateAdapter;
import com.janani.prettytouch.adapter.LocalDateTimeAdapter;
import com.janani.prettytouch.model.responces.FeedBackResponseModel;
import com.janani.prettytouch.services.FeedbackService;
import com.janani.prettytouch.util.TypeConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class getFeedbacks extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int start = TypeConverter.stringToInt(req.getParameter("start"));
            int count = TypeConverter.stringToInt(req.getParameter("count"));
            FeedbackService feedbackService = FeedbackService.getInstance();
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();
            resp.getWriter().write(gson.toJson(new FeedBackResponseModel(feedbackService.getFeedbackWithLimit(start,count),feedbackService.nextStart(start,count))));
        }


}
