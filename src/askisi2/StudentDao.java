/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package askisi2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Μπαλτατζίδης Χαράλαμπος
 */
public class StudentDao {

    public static Connection getConnection() {      //method για συνδεση με ΒΔ
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");       //χρησημοποιω JDBC Driver mariaDB v2.7.1
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/askisi1_db", "baltatzidis", "1234");
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return conn;
    }

    public static Student getStudent(String lname) {        //βρες student με αντισοιχο lname
        Student student = new Student();
        Connection conn = StudentDao.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE Lastname=?");
            ps.setString(1, lname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student.setFname(rs.getString(1));
                student.setLname(rs.getString(2));
                student.setSchool(rs.getString(3));
                student.setSchool(rs.getString(4));
                student.setSemester(rs.getInt(5));
                student.setPassedSubj(rs.getInt(6));
            }
            conn.close();
        } catch (SQLException ex) {
        }
        return student;
    }

    public static int createStudent(Student student) {   //δημιουργησε νεο entry στο database
        int status = 0;
        Connection conn = StudentDao.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO students VALUES (?,?,?,?,?)");
            ps.setString(1, student.getFname());
            ps.setString(2, student.getLname());
            ps.setString(3, student.getSchool());
            ps.setInt(4, student.getSemester());
            ps.setInt(5, student.getPassedSubj());
            status = ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
        }
        return status;
    }

}
