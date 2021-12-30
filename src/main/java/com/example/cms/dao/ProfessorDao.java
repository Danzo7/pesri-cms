package com.example.cms.dao;

import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import java.sql.*;

public class ProfessorDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/classroomdb?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";


    private static final String INSERT_PROFESSOR_WiTH_ID = "INSERT INTO professor" + "  (id ,fName,lName,email,password) VALUES " +
            " ( ?, ?, ?, ? , ?);";
    private static final String INSERT_PROFESSOR_WITHOUT_ID = "INSERT INTO professor " + "  (fName,lName,email,password) VALUES " +
            " (?, ?, ? , ?);";

    private static final String SELECT_PROFESSOR_BY_ID = "select fName,lName,email,password from professor where id =?";
    private  static  final String SELECT_PROFESSOR_BY_EMAIL_PASSWORD ="select id,fName ,lName,email,password from professor " +
            "where email = ? AND password = ? ";
    private static final String SELECT_ALL_PROFESSORS = "select fName,lName,email,password from professor ";

    private static final String UPDATE_PROFESSOR = "update professor " +
            "set fName = ?, lName= ?, email =?  , password =? " +
            "where id= ? ;";

    public ProfessorDao (){}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
        // create new Professor
    public Professor  insertProfessor (Professor professor) throws SQLException {
        Connection cnx = getConnection();
         PreparedStatement statement =cnx.prepareStatement(INSERT_PROFESSOR_WITHOUT_ID);
         statement.setString(1,professor.getfName());
        statement.setString(2,professor.getlName());
        statement.setString(3,professor.getEmail());
        statement.setString(4,professor.getPassword());
        statement.executeUpdate();
        return  getProfessorInfoFromDB(professor.getEmail(), professor.getPassword());
    }
    public boolean updateProfessor( Professor professor,String email,String oldPassword) throws Exception {
        if(getProfessorInfoFromDB(email,oldPassword)==null) throw new Exception("Wrong Password !");

        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PROFESSOR)) {
            statement.setString(1, professor.getfName());
            statement.setString(2, professor.getlName());
            statement.setString(3, professor.getEmail());
            statement.setString(4, professor.getPassword());
            statement.setInt(5, professor.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Boolean CheckRegistedProfessor (Professor professor) throws SQLException {
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(SELECT_PROFESSOR_BY_EMAIL_PASSWORD);
        statement.setString(1,professor.getEmail());
        statement.setString(2,professor.getPassword());
        ResultSet resultSet =statement.executeQuery();
        return resultSet.next();
    }

    public Professor  getProfessorInfoFromDB(String email,String password) throws SQLException {
        Professor professor =null ;
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(SELECT_PROFESSOR_BY_EMAIL_PASSWORD);
        statement.setString(1,email);
        statement.setString(2,password);
        ResultSet resultSet =statement.executeQuery();
        while (resultSet.next()){
            professor =new Professor(resultSet.getInt("id"),resultSet.getString("fName"),
                    resultSet.getString("lName"),resultSet.getString("email"),
                    resultSet.getString("password"));
        }
        return professor;

    }


}
