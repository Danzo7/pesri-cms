package com.example.cms.dao;

import com.example.cms.models.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfessorDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";


    private static final String INSERT_PROFESSOR_WiTH_ID = "INSERT INTO professor" + "  (id ,fName,lName,email,password) VALUES " +
            " ( ?, ?, ?, ? , ?);";
    private static final String INSERT_PROFESSOR_WiTHOUT_ID = "INSERT INTO professor" + "  (fName,lName,email,password) VALUES " +
            " (?, ?, ? , ?);";

    private static final String SELECT_PROFESSOR_BY_ID = "select fName,lName,email,password from professor where id =?";
    private  static  final String SELECT_PROFESSOR_BY_EMAIL_PASSWORD ="select id,fName ,lName,email,password from professor" +
            "where email =? AND password=? ";
    private static final String SELECT_ALL_PROFESSORS = "select fName,lName,email,password from professor ";

    private static final String UPDATE_PROFESSOR = "update professor " +
            "set professor.fName = ?,professor.lName= ?, professor.email =?  , professor.password " +
            "where id= ? ;";

    public ProfessorDao (){}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
        // create new Professor
    public void  insertProfessor (Professor professor) throws SQLException {
        Connection cnx = getConnection();
         PreparedStatement statement =cnx.prepareStatement(INSERT_PROFESSOR_WiTHOUT_ID);
         statement.setString(1,professor.getfName());
        statement.setString(2,professor.getlName());
        statement.setString(3,professor.getEmail());
        statement.setString(4,professor.getPassword());
        statement.executeUpdate();
    }

  /*  public void selectAddedProfessor (Professor professor) throws SQLException {
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(INSERT_PROFESSOR_WiTHOUT_ID);
    }  */

}
