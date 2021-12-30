package com.example.cms.servlets;

import com.example.cms.dao.ProfessorDao;
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
        if(session==null ||session.getAttribute("current")==null){
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

        String fNameRegex="^[A-Za-z]{3,30}$";
        String emailRegex="^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";

        if(regexChecker(fNameRegex,fName)
                && regexChecker(fNameRegex,lName)
                &&   regexChecker(emailRegex,email)
                && password.length() > 7 ) {

            Professor p = null;
            ProfessorDao professorDao = new ProfessorDao();
            try {
                p = professorDao.insertProfessor(new Professor(fName, lName, email, password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //Set cookies
            HttpSession session = request.getSession();
            session.setAttribute("current", p);
            //render View
            response.sendRedirect(request.getContextPath() + "/students");
        }
        else {
            // send label Error
            request.setAttribute("error","true");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }
    }
    public static boolean regexChecker(String regex, String valueToCheck){
        Pattern regexPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher= regexPattern.matcher(valueToCheck);

        if(regexMatcher.matches()){
            return  true;
        }
        else {
            return  false;
        }
    }

}
