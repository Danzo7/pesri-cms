package com.example.cms.servlets;

import com.example.cms.models.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
        Professor p=new Professor(1,fName,lName,email,password);
      //Set cookies
        HttpSession session=request.getSession();
        session.setAttribute("current",p);
      //render View
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
