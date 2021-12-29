package com.example.cms.dao;

import com.example.cms.models.Professor;
import com.example.cms.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/classroomdb?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_STUDENT_WiTH_ID = "INSERT INTO student" + "  (id ,fName,lName,age,profID) VALUES " +
            " ( ?, ?, ?, ? , ?);";
    private static final String INSERT_STUDENT_WITHOUT_ID = "INSERT INTO student" + "  (fName,lName,age,profID) VALUES " +
            " (?, ?, ? , ?);";
//TODO:add profID
    private static final String SELECT_STUDENT_BY_ID = "select * from student where id =?";
    private static final String SELECT_STUDENT_BY_All = "select * from student where " +
            "fName =? AND lName=? AND age=? AND profID=? ";

    private static final String SELECT_ALL_STUDENTS_OF_PROFESSOR = "select * from student " +
            "inner join  professor on " +
            " professor.id = student.profID" +
            " where professor.id=?  ";

    private static final String DELETE_STUDENTS_OF_PROFESSOR = "delete from student,professor " +
            "where student.profID = ?;";
    private static final String DELETE_STUDENT_OF_PROFESSOR = "delete from student,professor " +
            "where  student.id= ? AND student.profID = ?  ;";

    private static final String UPDATE_STUDENT_OF_PROFESSOR = "update student,professor  " +
            "set student.fName = ?,student.lName= ?, student.age =? " +
            "where student.profID = ? AND student.id= ? ;";

    public StudentDao (){}

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
    // create new Student related to a Professor
    public void  insertStudent (Student student) throws SQLException {
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(INSERT_STUDENT_WITHOUT_ID);
        statement.setString(1,student.getfName());
        statement.setString(2,student.getlName());
        statement.setInt(3,student.getAge());
        statement.setInt(4,student.getProfID());
        statement.executeUpdate();

    }

    public  Student getStudentByAll(Student student) throws SQLException {
        Connection cnx = getConnection();
        PreparedStatement statement =cnx.prepareStatement(SELECT_STUDENT_BY_All);
        statement.setString(1,student.getfName());
        statement.setString(2,student.getlName());
        statement.setInt(3,student.getAge());
        statement.setInt(4,student.getProfID());

        ResultSet resultSet =statement.executeQuery();
        while (resultSet.next()){
          return    new Student(
                    resultSet.getInt("id"),
                    resultSet.getInt("age"),
                    resultSet.getInt("profID"),
                    resultSet.getString("fName"),
                    resultSet.getString("lName")
            );
        }
        return null;
    }
    // update Student related to a Professor
    public boolean updateStudent(Student student,int professorID) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_OF_PROFESSOR)) {
             statement.setString(1, student.getfName());
            statement.setString(2, student.getlName());
            statement.setInt(3, student.getAge());
            statement.setInt(4, professorID);
            statement.setInt(5, student.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    // Delete Student
    public boolean deleteStudent(Student student,Professor professor) throws SQLException {
        boolean rowDeleted;
        Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_OF_PROFESSOR);
            statement.setInt(1, student.getId());
            statement.setInt(2, professor.getId());
            rowDeleted = statement.executeUpdate() > 0;
        return rowDeleted;
    }

    // get the list of students of professor
    public List<Student> selecteAllStudents (Professor professor) throws SQLException {
        List < Student > studentList = new ArrayList< >();
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS_OF_PROFESSOR);
        statement.setInt(1,professor.getId());
        ResultSet resultSet =statement.executeQuery();
        while (resultSet.next()){
            studentList.add(new Student(
                    resultSet.getInt("id"),
                    resultSet.getInt("age"),
                    resultSet.getInt("profID"),
                    resultSet.getString("fName"),
                    resultSet.getString("lName")
                    ));
        }
        return  studentList;
    }
    //TODO:add ProfID
    public Student selectStudent (int studentID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_ID);
        statement.setInt(1,studentID);
        ResultSet resultSet =statement.executeQuery();
        if (resultSet.next()){
            return  (new Student(
                    resultSet.getInt("id"),
                    resultSet.getInt("age"),
                    resultSet.getInt("profID"),
                    resultSet.getString("fName"),
                    resultSet.getString("lName")
            ));

        }
        return  null;
    }


}
