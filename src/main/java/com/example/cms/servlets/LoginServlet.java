package com.example.cms.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action =request.getServletPath();
        HttpSession session=request.getSession(false);
        if(session==null ||session.getAttribute("name")==null){
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
        if(password.equals("password")){
            HttpSession session=request.getSession();
            session.setAttribute("name",email);
            request.setAttribute("prof",email);

            response.sendRedirect(request.getContextPath() + "/students");
        }
        else{
            request.setAttribute("error","true");
            request.getRequestDispatcher("login.jsp").forward(request,response);}
    }
}
