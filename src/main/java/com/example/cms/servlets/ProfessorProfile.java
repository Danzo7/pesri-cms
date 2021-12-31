package com.example.cms.servlets;
import com.example.cms.dao.ProfessorDao;
import com.example.cms.models.Professor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "ProfessorProfile", value = "/profile")
public class ProfessorProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("professorProfile.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String oldPassword=request.getParameter("oldpassword");
        String newPassword=request.getParameter("newpassword");
        String fName=request.getParameter("fName");
        String lName=request.getParameter("lName");


        HttpSession session=request.getSession(false);
        Professor currentProfessor =  (Professor) session.getAttribute("current");


        try {
            if(validateInput(fName,lName,email,oldPassword,newPassword) ) {

                Professor updatedProfessor = new Professor(currentProfessor.getId(),fName,lName,email,newPassword);
                ProfessorDao professorDao = new ProfessorDao();
                boolean updated=false ;
                try {
                 updated = professorDao.updateProfessor(updatedProfessor, currentProfessor.getEmail(),oldPassword);

                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage","Something went wrong!");
                    request.getRequestDispatcher("professorProfile.jsp").forward(request,response);                }
                //Set cookies
                if(updated)
                session.setAttribute("current", updatedProfessor);

                //render View
                response.sendRedirect(request.getContextPath() + "/students");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("professorProfile.jsp").forward(request,response);
        }

    }
    boolean regexChecker(String regex, String valueToCheck){
        Pattern regexPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher= regexPattern.matcher(valueToCheck);
        return(regexMatcher.matches());
    }

    boolean validateInput(String fName,String lName,String email,String oldpassword,String newpassword) throws Exception{

        String fNameRegex="^[A-Za-z]{3,30}$";
        String emailRegex="^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";
        if(fName==null||!regexChecker(fNameRegex,fName)) throw new Exception("invalid first name");
        if(lName==null||!regexChecker(fNameRegex,lName)) throw new Exception("invalid last name");
        if(email==null||!regexChecker(emailRegex,email)) throw new Exception("invalid Email address");
        if(oldpassword==null||!(oldpassword.length() > 7)) throw new Exception("invalid Old password");
        if(newpassword==null||!(newpassword.length() > 7)) throw new Exception("invalid New password");
        return true;
    }


    }

