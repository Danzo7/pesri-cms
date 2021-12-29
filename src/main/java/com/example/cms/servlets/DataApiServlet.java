package com.example.cms.servlets;

import com.example.cms.dao.StudentDao;
import com.example.cms.models.Professor;
import com.example.cms.models.Student;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DataApiServlet", value = "/studentsApi")
public class DataApiServlet extends HttpServlet {
    private void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");
        String res = new Gson().toJson(obj);
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("current")==null){
            response.sendRedirect(request.getContextPath() + "/404");
        }
        else {
            StudentDao studentDao;
            studentDao = new StudentDao();
            List<Student> list = null;
            try {
                list = studentDao.selecteAllStudents((Professor) session.getAttribute("current"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(list!=null)
            sendAsJson(response,list);
            else
                response.sendRedirect(request.getContextPath() + "/404");


        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
