package com.example.rishivijaygajelli.pillaiadmissionconcession;

public class RecycledItems {

    public String AdmNo;
    public String Name;
    public String Roll;
    public String Stream;
    public String Sem;

    public RecycledItems(String AdmNo, String Name, String Roll, String Stream, String Sem)
    {
        this.AdmNo = AdmNo;
        this.Name = Name;
        this.Roll = Roll;
        this.Stream = Stream;
        this.Sem = Sem;
    }

    public String getAdmNo() {
        return AdmNo;
    }

    public String getName() {
        return Name;
    }

    public String getRoll() {
        return Roll;
    }

    public String getStream() {
        return Stream;
    }

    public String getSem() {
        return Sem;
    }
}
