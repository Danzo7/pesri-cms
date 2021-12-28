package com.example.cms.servlets;

import com.example.cms.dao.StudentDao;
import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "studentsServlet", value = "/students")
public class studentsServlet extends HttpServlet {
    static final ArrayList<Student> studentList=new ArrayList<Student>(Arrays.asList(new Student(1,2,12,"daoudji","aymen"),new Student(1,2,12,"daoudji","aymen")));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("current")==null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        else{
            //TODO:get current professor from cookies.
            //Professor p=new Professor(1,(Professor)session.getAttribute("name"),"catalonya","name");
            request.setAttribute("prof",(Professor)session.getAttribute("current"));
            request.setAttribute("students",studentList);
            request.getRequestDispatcher("studentList.jsp").forward(request,response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
