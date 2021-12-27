package com.example.cms.dao;

public class ProfessorDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";


    private static final String INSERT_PROFESSOR_WiTH_ID = "INSERT INTO professor" + "  (id ,fName,lName,email,password) VALUES " +
            " ( ?, ?, ?, ? , ?);";
    private static final String INSERT_PROFESSOR_WiTHOUT_ID = "INSERT INTO professor" + "  (fName,lName,email,password) VALUES " +
            " (?, ?, ? , ?);";

    private static final String SELECT_PROFESSOR_BY_ID = "select fName,lName,email,password from professor where id =?";
    private static final String SELECT_ALL_PROFESSORS = "select fName,lName,email,password from professor ";

    private static final String UPDATE_PROFESSOR = "update professor " +
            "set professor.fName = ?,professor.lName= ?, professor.email =?  , professor.password " +
            "where id= ? ;";
}
