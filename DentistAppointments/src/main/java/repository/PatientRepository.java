package repository;

import model.Patient;

import java.util.List;

public interface PatientRepository extends Repository<Integer, Patient> {
    List<Patient> findByCNP(String cnp);
    List<Patient> filterByBirthDate(String date);
    List<Patient> filterByFirstName(String name);
    List<Patient> filterByFullName(String name);
    List<Patient> getAllPatients();
}
