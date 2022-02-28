package com.example.cms.servlets;

import com.example.cms.dao.ProfessorDao;
import com.example.cms.misc.Helper;
import com.example.cms.models.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null || Helper.checkProfessorFromCookies(session)==null){
            request.getRequestDispatcher("register.jsp").forward(request,response);}
        else{
            response.sendRedirect(request.getContextPath() + "/students");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String fName=request.getParameter("fName");
        String lName=request.getParameter("lName");
        try {
            if(validateInput(fName,lName,password,email) ) {

                Professor p = null;
                ProfessorDao professorDao = new ProfessorDao();
                try {
                    p = professorDao.insertProfessor(new Professor(fName, lName, email, password));
                    //Set cookies
                    HttpSession session = request.getSession();
                    session.setAttribute("current", p);
                    session.setAttribute("email", p.getEmail());
                    session.setAttribute("password", p.getPassword());

                    //render View
                    response.sendRedirect(request.getContextPath() + "/students");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage","Something went wrong!");
                    request.getRequestDispatcher("register.jsp").forward(request,response);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }

    }
    boolean validateInput(String fName,String lName,String password,String email) throws Exception{

        String fNameRegex="^[A-Za-z]{3,30}$";
        String emailRegex="^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";
        if(fName==null|| Helper.regexChecker(fNameRegex, fName)) throw new Exception("invalid first name");
        if(lName==null|| Helper.regexChecker(fNameRegex, lName)) throw new Exception("invalid last name");
        if(email==null|| Helper.regexChecker(emailRegex, email)) throw new Exception("invalid Email address "+email);
        if(password==null||!(password.length() > 7)) throw new Exception("invalid password");
        return true;
    }



}
