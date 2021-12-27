package com.example.cms.dao;

import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_STUDENT_WiTH_ID = "INSERT INTO student" + "  (id ,fName,lName,age,profID) VALUES " +
            " ( ?, ?, ?, ? , ?);";
    private static final String INSERT_STUDENT_WiTHOUT_ID = "INSERT INTO student" + "  (fName,lName,age,profID) VALUES " +
            " (?, ?, ? , ?);";

    private static final String SELECT_STUDENT_BY_ID = "select fName,lName,age from student where id =?";
    private static final String SELECT_ALL_STUDENTS_OF_PROFESSOR = "select student.fName,student.lName,student.age " +
            "from student,professor" +
            " where student.profID = professor.? ";

    private static final String DELETE_STUDENTS_OF_PROFESSOR = "delete from student,professor " +
            "where student.profID = professor.?;";
    private static final String DELETE_STUDENT_OF_PROFESSOR = "delete from student,professor " +
            "where student.profID = professor.? AND student.id= ?  ;";

    private static final String UPDATE_STUDENT_OF_PROFESSOR = "update student,professor " +
            "set student.fName = ?,student.lName= ?, student.age =? " +
            "where student.profID = professor.? AND student.id= ? ;";

    public StudentDao (){}

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
    // create new Student related to a Professor
    public void  insertStudent (Student student) throws SQLException {
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(INSERT_STUDENT_WiTHOUT_ID);
        statement.setString(1,student.getfName());
        statement.setString(2,student.getlName());
        statement.setInt(3,student.getAge());
        statement.setInt(4,student.getProfID());
        statement.executeUpdate();
    }
    // update Student related to a Professor
    public boolean updateUser(Student student,Professor professor) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_OF_PROFESSOR);) {
             statement.setString(1, student.getfName());
            statement.setString(2, student.getfName());
            statement.setInt(3, student.getAge());
            statement.setInt(4, professor.getId());
            statement.setInt(5, student.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
