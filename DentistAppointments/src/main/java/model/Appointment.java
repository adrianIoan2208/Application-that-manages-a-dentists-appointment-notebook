package model;
public class Appointment implements Identifiable <Integer> {
    private int ID;
    private Patient patient;
    private String dentistName;
    private String procedureName;
    private double hoursNeeded;
    private String date;
    private Status status;

    public Appointment()
    {
        ID=0;
        patient=null;
        dentistName="";
        procedureName="unknown";
        hoursNeeded=0;
        date="00.00.0000";
        status=Status.unknown;
    }
    public Appointment(Patient patient, String dentistName, String procedureName, double hoursNeeded, String date, Status status)
    {
        this.patient = patient;
        this.dentistName=dentistName;
        this.procedureName=procedureName;
        this.hoursNeeded=hoursNeeded;
        this.date=date;
        this.status=status;
    }
    public Appointment(Integer id, Patient patient, String dentistName, String procedureName, double hoursNeeded, String date, Status status)
    {
        this.ID=id;
        this.patient = patient;
        this.dentistName=dentistName;
        this.procedureName=procedureName;
        this.hoursNeeded=hoursNeeded;
        this.date=date;
        this.status=status;
    }

    public void setPatientId(Patient patient){ this.patient = patient;}
    public void setDentistName(String dentistName){ this.dentistName=dentistName;}
    public void setProcedureName(String procedureName){ this.procedureName= procedureName;}
    public void setHoursNeeded(double hoursNeeded){ this.hoursNeeded=hoursNeeded;}
    public void setDate(String date){ this.date=date;}
    public void setStatus(Status status){ this.status=status;}

    public int getPatientId() { return patient.getID(); }
    public String getDentistName() { return dentistName; }
    public String getProcedureName(){ return procedureName; }
    public double getHoursNeeded(){ return hoursNeeded; }
    public String getDate(){ return date;}
    public Status getStatus(){ return status;}

    public String toString()
    {
        StringBuffer str= new StringBuffer("Patient: " + patient + "\n");
        str.append("Appointment ID: "+ ID + "\n");
        str.append("Dentist: "+ dentistName+ "\n");
        str.append("Procedure: "+ procedureName+ "\n");
        str.append("Hours needed: "+ hoursNeeded+ "\n");
        str.append("Date: "+ date + "\n");
        str.append("Status: "+ status+ "\n\n");
        return str.toString();

    }
    @Override
    public boolean equals(Object obj)
    {
        if( obj instanceof Appointment)
        {
            Appointment a=(Appointment) obj;
            return this.ID==a.ID;
        }
        return false;
    }

    @Override
    public Integer getID()
    {
        return ID;
    }

    @Override
    public void setID(Integer id)
    {
        this.ID=id;
    }


}
