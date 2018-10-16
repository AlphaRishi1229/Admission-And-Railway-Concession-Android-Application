package com.example.rishivijaygajelli.pillaiadmissionconcession;

public class ConcessionRecycledItems {

    public String pass_code;
    public String AdmissionNo;
    public String RollNo;
    public String StationName;
    public String PassType;
    public String PassPeriod;
    public String last_vch_no;
    public String last_ticket_no;
    public String last_station_name;
    public String last_ticket_period;
    public String last_issue_date;
    public String last_pass_type;

    public ConcessionRecycledItems(String pass_code, String AdmissionNo, String RollNo, String StationName, String PassType,
                                   String PassPeriod, String last_vch_no, String last_ticket_no, String last_station_name,
                                   String last_ticket_period, String last_issue_date, String last_pass_type)
    {
        this.pass_code = pass_code;
        this.AdmissionNo = AdmissionNo;
        this.RollNo = RollNo;
        this.StationName = StationName;
        this.PassType = PassType;
        this.PassPeriod = PassPeriod;
        this.last_vch_no = last_vch_no;
        this.last_ticket_no = last_ticket_no;
        this.last_station_name = last_station_name;
        this.last_ticket_period = last_ticket_period;
        this.last_issue_date = last_issue_date;
        this.last_pass_type = last_pass_type;
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

    public String getLast_vch_no() {
        return last_vch_no;
    }

    public String getLast_ticket_no() {
        return last_ticket_no;
    }

    public String getLast_station_name() {
        return last_station_name;
    }

    public String getLast_ticket_period() {
        return last_ticket_period;
    }

    public String getLast_issue_date() {
        return last_issue_date;
    }

    public String getLast_pass_type() {
        return last_pass_type;
    }
}
