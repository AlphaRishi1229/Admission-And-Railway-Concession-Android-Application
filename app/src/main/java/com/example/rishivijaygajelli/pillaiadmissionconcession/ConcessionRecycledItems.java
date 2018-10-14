package com.example.rishivijaygajelli.pillaiadmissionconcession;

public class ConcessionRecycledItems {

    public String pass_code;
    public String AdmissionNo;
    public String RollNo;
    public String StationName;
    public String PassType;
    public String PassPeriod;

    public ConcessionRecycledItems(String pass_code, String AdmissionNo, String RollNo, String StationName, String PassType, String PassPeriod)
    {
        this.pass_code = pass_code;
        this.AdmissionNo = AdmissionNo;
        this.RollNo = RollNo;
        this.StationName = StationName;
        this.PassType = PassType;
        this.PassPeriod = PassPeriod;
    }

    public String getPass_code() {
        return pass_code;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public String getRollNo() {
        return RollNo;
    }

    public String getStationName() {
        return StationName;
    }

    public String getPassType() {
        return PassType;
    }

    public String getPassPeriod() {
        return PassPeriod;
    }
}
