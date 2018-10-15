package com.example.rishivijaygajelli.pillaiadmissionconcession;

public class RecycledNewStudentItems {

    public String StudentID;
    public String NewName;
    public String Phone;
    public String Email;
    public String Dob;
    public String Caste;
    public String Tenth;
    public String Twelth;
    public String StreamName;
    public String StreamYear;

    public RecycledNewStudentItems(String StudentID, String NewName, String Phone, String Email, String Dob,
                                   String Caste, String Tenth, String Twelth,String StreamName, String StreamYear)
    {
        this.StudentID = StudentID;
        this.NewName = NewName;
        this.Phone = Phone;
        this.Email = Email;
        this.Dob = Dob;
        this.Caste = Caste;
        this.Tenth = Tenth;
        this.Twelth = Twelth;
        this.StreamName = StreamName;
        this.StreamYear = StreamYear;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getNewName() {
        return NewName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getDob() {
        return Dob;
    }

    public String getCaste() {
        return Caste;
    }

    public String getTenth() {
        return Tenth;
    }

    public String getTwelth() {
        return Twelth;
    }

    public String getStreamName() {
        return StreamName;
    }

    public String getStreamYear() {
        return StreamYear;
    }
}
