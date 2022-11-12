package repository;

import model.Appointment;
import model.Status;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentsInMemoryRepository extends AbstractRepository<Integer, Appointment> implements AppointmentRepository {
    AppointmentsInMemoryRepository(){}

    @Override
    public List<Appointment> filterAppointmentsByStatus(Status s) {
        return getAll().stream().filter(a->a.getStatus()==s).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> appointmentsFromAGivenDay(String date) {
        return getAll().stream().filter(a->a.getDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> filterAppointmentsByProcedure(String procedure) {
        return getAll().stream().filter(a->a.getProcedureName().equals(procedure)).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAllDoctors() {
        return getAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return getAll().stream().collect(Collectors.toList());
    }
}
