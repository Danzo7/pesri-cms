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
            Student selectedStudent=null;
            studentDao = new StudentDao();
            List<Student> list = null;
            try {
                list = studentDao.selecteAllStudents(p);
                if(request.getParameter("id")!=null){
                int id= Integer.parseInt(request.getParameter("id"));
                    selectedStudent= studentDao.selectStudent(id,p.getId());
                if(request.getParameter("delete")!=null){
                    request.setAttribute("delete", "true");

                }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("prof", p);
            request.setAttribute("students", list);
            if(selectedStudent!=null|| request.getParameter("add")!=null)
                request.setAttribute("show", "s");

            request.setAttribute("student", selectedStudent);
            request.getRequestDispatcher("studentList.jsp").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fName=request.getParameter("fName");
        String lName=request.getParameter("lName");
        String age=request.getParameter("age");
        HttpSession session=request.getSession(false);
        StudentDao professorDao = new StudentDao();
        try {
            if(request.getParameter("delete")!=null && request.getParameter("id")!=null)
                professorDao.deleteStudent(Integer.parseInt(request.getParameter("id")),((Professor) session.getAttribute("current")).getId());
            else
                if(fName!=null&&lName!=null&&age!=null){
            if(request.getParameter("id")!=null){
               professorDao.updateStudent(new Student(Integer.parseInt(request.getParameter("id")),Integer.parseInt(age),((Professor) session.getAttribute("current")).getId(),fName,lName),((Professor) session.getAttribute("current")).getId());
            } else
            professorDao.insertStudent(new Student(fName,lName,  Integer.parseInt(age),((Professor) session.getAttribute("current")).getId()));
                }
                else throw new Exception("invalid Data"+request.getParameter("delete"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //render View
        response.sendRedirect(request.getContextPath() + "/students");

    }

}
