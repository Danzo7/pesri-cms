package com.example.cms.servlets;

import com.example.cms.dao.StudentDao;
import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "studentsServlet", value = "/students")
public class studentsServlet extends HttpServlet {
    static final ArrayList<Student> studentList=new ArrayList<Student>(Arrays.asList(new Student(1,2,12,"daoudji","aymen"),new Student(1,2,12,"daoudji","aymen")));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("current")==null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        else {
            StudentDao studentDao;
            studentDao = new StudentDao();
            Professor p = (Professor) session.getAttribute("current");
            List<Student> list = null;
            try {
                list = studentDao.selecteAllStudents(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("prof", p);
            request.setAttribute("students", list);
            request.getRequestDispatcher("studentList.jsp").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
