package ctrl;

import javafx.fxml.FXML;
import javafx.util.StringConverter;
import model.Appointment;
import model.Patient;
import model.Status;
import repository.AppointmentRepository;
import services.DentistServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextField;
import services.ServicesException;


import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class      DentistController {

    private DentistServices dentistServices;

    //for patient pane
    @FXML
    private TextField dentistName,patientName,addFirstName,addLastName,addCnp;
    @FXML
    private TableView<Patient> patientList;
    @FXML
    private DatePicker patientBirthDate;

    //for adding appointments pane
    @FXML
    private TextField patientNameSearch,procedure,hours;
    @FXML
    private TableView<Patient> patientToAddList;
    @FXML
    private DatePicker appointmentDate;


    //for appointments
    @FXML
    private TableView<Appointment> appointmentsList;
    @FXML
    private ChoiceBox<Status> chooseStatus;
    @FXML
    private TextField updateDentist,updateProcedure,updateHours;
    @FXML
    private DatePicker updateDate;
    @FXML
    private ChoiceBox<Status> updateStatus;


    //for patients
    @FXML
    private TextField pName,updateFirstName,updateLastName,updateCnp;
    @FXML
    private DatePicker updateBirthDate;
    @FXML
    private TableView<Patient> patientListReport;



    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public DentistController() {
    }

    public void setDentistServices(DentistServices dentistServices) {
        this.dentistServices = dentistServices;
    }

    @FXML
    public void initialize() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty())
                        ? LocalDate.parse(string, dateFormatter)
                        : null;
            }

        };

        patientBirthDate.setConverter(converter);
        patientBirthDate.setValue(LocalDate.now());
        patientBirthDate.setEditable(false);

        appointmentDate.setConverter(converter);
        appointmentDate.setValue(LocalDate.now());
        appointmentDate.setEditable(false);

        updateBirthDate.setConverter(converter);
        updateBirthDate.setValue(LocalDate.now());
        updateBirthDate.setEditable(false);

        chooseStatus.getItems().add(Status.active);
        chooseStatus.getItems().add(Status.cancelled);
        chooseStatus.getItems().add(Status.unknown);
        chooseStatus.getSelectionModel().selectFirst();

        updateStatus.getItems().add(Status.active);
        updateStatus.getItems().add(Status.cancelled);
        updateStatus.getItems().add(Status.unknown);
        updateStatus.getSelectionModel().selectFirst();

    }
    private boolean checkString(String s){return s==null || s.isEmpty() ? false: true;}

    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void addPatientHandler(ActionEvent actionEvent)
    {
        String fName=addFirstName.getText();
        String lName=addLastName.getText();
        String cnp= addCnp.getText();
        String birthDate=patientBirthDate.getValue().format(dateFormatter);
        if(checkString(fName)&&checkString(lName)&&checkString(cnp))
        {
            try
            {
                int id=dentistServices.addPatient(fName,lName,cnp,birthDate);
                String mess="Patient successfully added! Its identification number is " +id;
                showNotification(mess, Alert.AlertType.INFORMATION);
                clearDataPatient();

            }
            catch(ServicesException ex)
            {
                showNotification(ex.getMessage(),Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void searchPatientHandler(ActionEvent actionEvent) {
        String fullName=patientName.getText();
        if(!checkString(fullName))
        {
            showNotification("You must introduce a name!", Alert.AlertType.ERROR);
            return;
        }
        if(!fullName.contains(" "))
        {
            showNotification("The name must be like: John Doe", Alert.AlertType.INFORMATION);
            return;
        }
        List<Patient> patients= dentistServices.getPatientsByFullName(fullName);
        patientList.getItems().clear();
        patientList.getItems().addAll(patients);

    }
    @FXML
    public void searchPatientForAppointmentHandler(ActionEvent actionEvent)
    {
        String fullName=patientNameSearch.getText();
        if(!checkString(fullName))
        {
            showNotification("You must introduce a name!", Alert.AlertType.ERROR);
            return;
        }
        if(!fullName.contains(" "))
        {
            showNotification("The name must be like: John Doe", Alert.AlertType.INFORMATION);
            return;
        }
        List<Patient> patients= dentistServices.getPatientsByFullName(fullName);
        patientToAddList.getItems().clear();
        patientToAddList.getItems().addAll(patients);
    }

    @FXML
    public void showAllPatientsHandler(ActionEvent actionEvent)
    {
        List<Patient> allPatients=dentistServices.getAllPatients();
        patientToAddList.getItems().clear();
        patientToAddList.getItems().addAll(allPatients);
    }

    @FXML
    public void addAppointmentHandler(ActionEvent actionEvent)
    {
        int selectedPatient=patientToAddList.getSelectionModel().getSelectedIndex();
        if(selectedPatient<0)
        {
            showNotification("You must choose a patient from the list!", Alert.AlertType.ERROR);
            return;
        }
        String dentist=dentistName.getText();
        String proc=procedure.getText();
        String hoursNeeded=hours.getText();
        String appDate=appointmentDate.getValue().format(dateFormatter);
        if(checkString(dentist)&&checkString(proc)&&checkString(hoursNeeded))
        {
            try
            {
                double hoursNeededConverted=Double.parseDouble(hoursNeeded);
                Patient p=patientToAddList.getItems().get(selectedPatient);
                dentistServices.addAppointment(p,dentist,proc,hoursNeededConverted,appDate, Status.active);
                clearDataAppointment();
                showNotification("Appointment successfully added", Alert.AlertType.INFORMATION);
            }
            catch(NumberFormatException ex)
            {
                showNotification("Hours must be a number!", Alert.AlertType.ERROR);
                return;
            }
            catch(ServicesException ex)
            {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void showAllAppointments(ActionEvent actionEvent)
    {
        List<Appointment> allAppointments= dentistServices.getAllAppointments();
        appointmentsList.getItems().clear();
        appointmentsList.getItems().addAll(allAppointments);

    }

    @FXML
    public void showAppointmentsByStatus(ActionEvent actionEvent)
    {
        Status sts= chooseStatus.getSelectionModel().getSelectedItem();
        List<Appointment> filteredList= dentistServices.getAppointmentsByStatus(sts);
        appointmentsList.getItems().clear();
        appointmentsList.getItems().addAll(filteredList);
    }

    @FXML
    public void cancelAppointment(ActionEvent actionEvent)
    {
        int selectedAppointment= appointmentsList.getSelectionModel().getSelectedIndex();
        if(selectedAppointment<0)
        {
            showNotification("You must select an appointment!", Alert.AlertType.ERROR);
            return;
        }
        Appointment a= appointmentsList.getItems().get(selectedAppointment);
        a.setStatus(Status.cancelled);
        int id= a.getID();
        try
        {
            dentistServices.updateAppointment(id,a);
            showNotification("Appointment cancelled successfully", Alert.AlertType.INFORMATION);
            showAllAppointments(actionEvent);
        }
        catch(ServicesException ex)
        {
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void fillTextFileds(ActionEvent actionEvent)
    {
        int selectedAppointment= appointmentsList.getSelectionModel().getSelectedIndex();
        if(selectedAppointment<0)
        {
            showNotification("You must select an appointment!", Alert.AlertType.ERROR);
            return;
        }
        Appointment a= appointmentsList.getItems().get(selectedAppointment);
        updateDentist.setText(a.getDentistName());
        updateProcedure.setText(a.getProcedureName());
        updateHours.setText(String.valueOf(a.getHoursNeeded()));
        updateDate.setValue(LocalDate.parse(a.getDate(),dateFormatter));
        updateStatus.getSelectionModel().select(a.getStatus());
    }

    @FXML
    public void updateAppointments(ActionEvent actionEvent)
    {
        int selectedAppointment= appointmentsList.getSelectionModel().getSelectedIndex();
        if(selectedAppointment<0)
        {
            showNotification("You must select an appointment!", Alert.AlertType.ERROR);
            return;
        }
        String dentist=updateDentist.getText();
        String proc=updateProcedure.getText();
        String hoursNeeded=updateHours.getText();
        String appDate=updateDate.getValue().format(dateFormatter);

        if(checkString(dentist)&&checkString(proc)&&checkString(hoursNeeded))
        {
            try
            {
                Appointment a= appointmentsList.getItems().get(selectedAppointment);
                double hoursNeededConverted=Double.parseDouble(hoursNeeded);
                a.setHoursNeeded(hoursNeededConverted);
                a.setDentistName(dentist);
                a.setProcedureName(proc);
                a.setDate(appDate);
                a.setStatus(updateStatus.getValue());
                dentistServices.updateAppointment(a.getID(),a);
                showNotification("Appointment successfully updated", Alert.AlertType.INFORMATION);
            }
            catch(NumberFormatException ex)
            {
                showNotification("Hours must be a number!" + hoursNeeded, Alert.AlertType.ERROR);
                return;
            }
            catch(ServicesException ex)
            {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    @FXML
    public void searchPatientReportsHandler(ActionEvent actionEvent) {
        String fullName=pName.getText();
        if(!checkString(fullName))
        {
            showNotification("You must introduce a name!", Alert.AlertType.ERROR);
            return;
        }
        if(!fullName.contains(" "))
        {
            showNotification("The name must be like: John Doe", Alert.AlertType.INFORMATION);
            return;
        }
        List<Patient> patients= dentistServices.getPatientsByFullName(fullName);
        patientListReport.getItems().clear();
        patientListReport.getItems().addAll(patients);

    }

    @FXML
    public void showAllPatientsHandlerReports(ActionEvent actionEvent)
    {
        List<Patient> allPatients=dentistServices.getAllPatients();
        patientListReport.getItems().clear();
        patientListReport.getItems().addAll(allPatients);
    }
    @FXML
    public void deletePatientHandler(ActionEvent actionEvent)
    {
        int selectedPatient = patientListReport.getSelectionModel().getSelectedIndex();
        if(selectedPatient<0)
        {
            showNotification("You must select a patient!", Alert.AlertType.ERROR);
            return;
        }
        Patient p= patientListReport.getItems().get(selectedPatient);
        try
        {
            dentistServices.deletePatient(p);
            showNotification("Patient deleted successfully!", Alert.AlertType.INFORMATION);
            showAllPatientsHandlerReports(actionEvent);
        }
        catch (ServicesException ex)
        {
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void fillDataHandler(ActionEvent actionEvent)
    {
        int selectedPatient = patientListReport.getSelectionModel().getSelectedIndex();
        if(selectedPatient<0)
        {
            showNotification("You must select a patient!", Alert.AlertType.ERROR);
            return;
        }
        Patient p= patientListReport.getItems().get(selectedPatient);
        updateFirstName.setText(p.getFirstName());
        updateLastName.setText(p.getLastName());
        updateCnp.setText(p.getCnp());
        updateBirthDate.setValue(LocalDate.parse(p.getBirthDate(),dateFormatter));

    }

    @FXML
    public void updatePatientDataHandler(ActionEvent actionEvent)
    {
        int selectedPatient = patientListReport.getSelectionModel().getSelectedIndex();
        if(selectedPatient<0)
        {
            showNotification("You must select a patient!", Alert.AlertType.ERROR);
            return;
        }

        String first=updateFirstName.getText();
        String last= updateLastName.getText();
        String cnp=updateCnp.getText();
        String bd= updateBirthDate.getValue().format(dateFormatter);
        if(checkString(first)&&checkString(last)&&checkString(cnp))
        {
            try
            {
                Patient p=patientListReport.getItems().get(selectedPatient);
                p.setFirstName(first);
                p.setLastName(last);
                p.setCnp(cnp);
                p.setBirthDate(bd);
                dentistServices.updatePatient(p.getID(),p);
                showNotification("Patient was successfully updated", Alert.AlertType.INFORMATION);
            }
            catch(ServicesException ex)
            {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void clearDataAppointment()
    {
        dentistName.setText("");
        procedure.setText("");
        hours.setText("");
    }
    @FXML
    public void clearDataPatient()
    {
        addFirstName.setText("");
        addLastName.setText("");
        addCnp.setText("");
    }


}
