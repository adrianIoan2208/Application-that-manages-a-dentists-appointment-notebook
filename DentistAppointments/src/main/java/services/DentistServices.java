package services;

import model.Appointment;
import model.Patient;
import model.Status;
import repository.AppointmentRepository;
import repository.PatientRepository;
import repository.RepositoryException;

import java.util.List;

public class DentistServices {
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;

    public DentistServices(PatientRepository patientRepository, AppointmentRepository appointmentRepository)
    {
        this.patientRepository=patientRepository;
        this.appointmentRepository=appointmentRepository;
    }

    public int addPatient(String firstName, String lastName, String cnp, String birthDate) throws ServicesException
    {
        try
        {
            Patient p= new Patient(firstName,lastName,cnp,birthDate);
            Patient patientAdded= patientRepository.add(p);
            return patientAdded.getID();
        }
        catch(RepositoryException ex)
        {
            throw new ServicesException("Error adding patient"+ex);
        }
    }

    public void deletePatient(Patient p)throws ServicesException
    {
        try
        {
            patientRepository.delete(p);
        }
        catch(RepositoryException ex)
        {
            throw new ServicesException("Error deleting patient"+ex);
        }

    }
    public void updatePatient(Integer id, Patient p )throws ServicesException
    {
        try
        {
            patientRepository.update(id,p);

        }
        catch (RepositoryException ex)
        {
            throw new ServicesException("Error updating patient"+ex);
        }
    }
    public void addAppointment(Patient p, String dentistName, String procedureName, double hoursNeeded, String date, Status status) throws ServicesException
    {
        try
        {
            Appointment a=new Appointment(p,dentistName,procedureName,hoursNeeded,date,status);
            patientRepository.update(p.getID(),p);
            appointmentRepository.add(a);
        }
        catch(RepositoryException ex)
        {
            throw  new ServicesException("Error adding appointment"+ ex);
        }
    }

    public void updateAppointment(Integer id, Appointment a) throws ServicesException
    {
        try
        {
            appointmentRepository.update(id,a);
        }
        catch(RepositoryException ex)
        {
            throw new RepositoryException("Eroor updating appointment"+ ex);
        }
    }

    public List<Patient> getPatientsByFullName(String name)
    {
        return patientRepository.filterByFullName(name);
    }

    public List<Patient> getAllPatients(){return patientRepository.getAllPatients();}

    public List<Appointment> getAllAppointments(){return appointmentRepository.getAllAppointments();}

    public List<Appointment> getDoctorsName(){return appointmentRepository.getAllDoctors();}

    public List<Appointment> getAppointmentsByStatus(Status status){ return appointmentRepository.filterAppointmentsByStatus(status);}
}
