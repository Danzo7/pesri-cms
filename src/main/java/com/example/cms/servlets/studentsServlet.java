package com.example.cms.servlets;

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
      request.setAttribute("students",studentList);
        request.getRequestDispatcher("studentList.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
