package com.example.cms.servlets;

import com.example.cms.dao.ProfessorDao;
import com.example.cms.dao.StudentDao;
import com.example.cms.models.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action =request.getServletPath();
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("current")==null){
            request.getRequestDispatcher("login.jsp").forward(request,response);}
        else{
        response.sendRedirect(request.getContextPath() + "/students");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String checked=request.getParameter("rememberCheck");

        try {
            if(validateInput(email,password) ) {


    //TODO:check if professor exist
            Professor p =null;
            ProfessorDao professorDao = new ProfessorDao();
            try {
             p =  professorDao.getProfessorInfoFromDB(email,password);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error","Something went wrong!");
                request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
            }
            if(p != null){
                HttpSession session=request.getSession();
                //TODO:selected professor
                session.setAttribute("current",p);
                response.sendRedirect(request.getContextPath() + "/students");
            }
            else{
                request.setAttribute("error","this account does not exist or the password is wrong!");
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
    public static boolean regexChecker(String regex, String valueToCheck){
        Pattern regexPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher= regexPattern.matcher(valueToCheck);

        return regexMatcher.matches();
    }
    boolean validateInput(String email,String password) throws Exception{
        String emailRegex="^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";
        if(email==null||!regexChecker(emailRegex,email)) throw new Exception("invalid Email address");
        if(password==null||!(password.length() > 7)) throw new Exception("invalid password");
        return true;
    }
}
