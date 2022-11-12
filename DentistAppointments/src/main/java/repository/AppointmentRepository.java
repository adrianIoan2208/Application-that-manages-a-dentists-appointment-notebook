package repository;

import model.Appointment;
import model.Status;

import java.util.List;

public interface AppointmentRepository extends Repository<Integer, Appointment> {
    List<Appointment> filterAppointmentsByStatus(Status s);
    List<Appointment> appointmentsFromAGivenDay(String date);
    List<Appointment> filterAppointmentsByProcedure(String procedure);
    List<Appointment> getAllDoctors();
    List<Appointment> getAllAppointments();
}
