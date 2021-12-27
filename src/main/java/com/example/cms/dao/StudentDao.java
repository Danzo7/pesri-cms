package com.example.cms.dao;

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
            "where student.profID = professor.id AND student.id= ? ;";
}
