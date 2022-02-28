package com.example.cms.servlets;

import com.example.cms.dao.StudentDao;
import com.example.cms.misc.Helper;
import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
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
            Professor currentProfessor = Helper.checkProfessorFromCookies(session);
            if(currentProfessor==null) {
                response.sendRedirect(request.getContextPath() + "/logout");
                return;
            }
            StudentDao studentDao;
            Student selectedStudent=null;
            studentDao = new StudentDao();
            List<Student> list = null;
            if(request.getParameter("error")!=null) {
                request.setAttribute("error", request.getParameter("error"));
            }


            try {
                if(request.getParameter("search")!=null)
                list = studentDao.searchStudentByString(currentProfessor.getId(),request.getParameter("search"));
                else
                list = studentDao.selecteAllStudents(currentProfessor);
                if(request.getParameter("id")!=null){
                int id= Integer.parseInt(request.getParameter("id"));
                    selectedStudent= studentDao.selectStudent(id,currentProfessor.getId());
                if(request.getParameter("delete")!=null){
                    request.setAttribute("delete", "true");

                }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("prof", currentProfessor);
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
        StudentDao studentDao = new StudentDao();
        try {
            if(request.getParameter("delete")!=null && request.getParameter("id")!=null)
                studentDao.deleteStudent(Integer.parseInt(request.getParameter("id")),((Professor) session.getAttribute("current")).getId());
            else
                if(validateInput(fName,lName,age)){
            if(request.getParameter("id")!=null){
                studentDao.updateStudent(new Student(Integer.parseInt(request.getParameter("id")),Integer.parseInt(age),((Professor) session.getAttribute("current")).getId(),fName,lName),((Professor) session.getAttribute("current")).getId());
            } else
                studentDao.insertStudent(new Student(fName,lName,  Integer.parseInt(age),((Professor) session.getAttribute("current")).getId()));
                }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/students?error="+e.getMessage());
            return;
        }
        //render View
        response.sendRedirect(request.getContextPath() + "/students");

    }
 boolean validateInput(String fName,String lName,String age) throws Exception{
    String fNameRegex="^[A-Za-z]{3,30}$";
        if(fName==null|| Helper.regexChecker(fNameRegex, fName)) throw new Exception("invalid first name");
        if(lName==null|| Helper.regexChecker(fNameRegex, lName)) throw new Exception("invalid last name");
        if(age==null||!(Integer.parseInt(age)>17&&Integer.parseInt(age) <=30))  throw new Exception("invalid last age");
        return true;
    }
}
