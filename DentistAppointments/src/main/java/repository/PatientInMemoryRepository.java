package repository;

import model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class PatientInMemoryRepository extends AbstractRepository<Integer, Patient> implements PatientRepository {
    public PatientInMemoryRepository(){}

    @Override
    public List<Patient> findByCNP(String cnp) {
        return getAll().stream().filter(p->p.getCnp().equals(cnp)).collect(Collectors.toList());
    }

    @Override
    public List<Patient> filterByBirthDate(String date) {
        return getAll().stream().filter(p->p.getBirthDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public List<Patient> filterByFirstName(String name) {
        return getAll().stream().filter(p->p.getFirstName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Patient> filterByFullName(String name){
        return getAll().stream().filter(p->p.getFullName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Patient> getAllPatients() {
        return getAll().stream().collect(Collectors.toList());
    }
}
