package com.example.cms.misc;

import com.example.cms.dao.ProfessorDao;
import com.example.cms.models.Professor;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    static public boolean regexChecker(String regex, String valueToCheck){
        Pattern regexPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher= regexPattern.matcher(valueToCheck);
        return (!regexMatcher.matches());
    }
    static public Professor checkProfessorFromCookies(HttpSession session){
        ProfessorDao professorDao = new ProfessorDao();
        try {
            String email=(String)session.getAttribute("email");
            String password=(String) session.getAttribute("password");
            if(email==null||password==null)
                return null;
            return professorDao.getProfessorInfoFromDB(email,password);
        } catch (SQLException e) {
            return null;
        }}
}
