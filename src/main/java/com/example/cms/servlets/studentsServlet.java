package com.example.cms.servlets;

import com.example.cms.dao.ProfessorDao;
import com.example.cms.dao.StudentDao;
import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "studentsServlet", value = "/students")
public class studentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("current")==null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        else {
            Professor p = (Professor) session.getAttribute("current");
            StudentDao studentDao;
            studentDao = new StudentDao();
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
        String fName=request.getParameter("fName");
        String lName=request.getParameter("lName");
        String age=request.getParameter("age");
        HttpSession session=request.getSession(false);
        Student s = null;
        StudentDao professorDao = new StudentDao();
        try {
            s =   professorDao.insertStudent(new Student(fName,lName,  Integer.parseInt(age),((Professor) session.getAttribute("current")).getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //render View
        response.sendRedirect(request.getContextPath() + "/students");

    }
}
