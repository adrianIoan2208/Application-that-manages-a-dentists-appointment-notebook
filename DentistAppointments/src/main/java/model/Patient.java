package model;
import java.io.Serializable;

public class Patient implements Identifiable<Integer>, Serializable {
    private int ID;
    private String firstName;
    private String lastName;
    private String cnp;
    private String birthDate;
    public Patient()
    {
        ID=0;
        firstName="John";
        lastName="Doe";
        cnp="1234567891234";
        birthDate="00.00.0000";
    }

    public Patient( String firstName, String lastName, String cnp, String birthDate)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.cnp=cnp;
        this.birthDate=birthDate;
    }

    public Patient(Integer id, String firstName, String lastName, String cnp, String birthDate)
    {
        this.ID=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.cnp=cnp;
        this.birthDate=birthDate;
    }

    public void setFirstName(String firstName){ this.firstName=firstName;}
    public void setLastName(String lastName) { this.lastName=lastName;}
    public void setCnp(String cnp){ this.cnp=cnp;}
    public void setBirthDate(String birthDate){ this.birthDate= birthDate;}

    public String getFirstName(){ return firstName;}
    public String getLastName(){ return lastName;}
    public String getCnp(){ return cnp;}
    public String getBirthDate(){ return birthDate;}

    public String getFullName(){ return firstName+" "+lastName;}

    public String toString()
    {
        StringBuffer str= new StringBuffer("Name: " + firstName);
        str.append(" "+ lastName + "\n");
        str.append("CNP: "+ cnp + "\n");
        str.append("Birth date: "+ birthDate + "\n");
        str.append("ID: "+ ID + "\n");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if( obj instanceof Patient)
        {
            Patient p=(Patient) obj;
            return this.ID== p.ID;
        }
        return false;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID=id;
    }
}