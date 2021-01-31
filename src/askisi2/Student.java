/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package askisi2;

import java.io.Serializable;

/**
 *
 * @author Μπαλτατζίδης Χαράλαμπος
 */
public class Student implements Serializable {

    private String fname;
    private String lname;
    private String school;
    private int semester;
    private int passedSubj;

    public Student() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getPassedSubj() {
        return passedSubj;
    }

    public void setPassedSubj(int passedSubj) {
        this.passedSubj = passedSubj;
    }

}
