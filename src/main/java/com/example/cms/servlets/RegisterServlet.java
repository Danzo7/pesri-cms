package com.example.cms.servlets;

import com.example.cms.dao.ProfessorDao;
import com.example.cms.models.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

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

        //TODO:add professor.
        Professor p = null;
        ProfessorDao professorDao = new ProfessorDao();
        try {
          p =   professorDao.insertProfessor(new Professor(fName,lName,email,password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Set cookies
        HttpSession session=request.getSession();
        session.setAttribute("current",p);
      //render View
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
