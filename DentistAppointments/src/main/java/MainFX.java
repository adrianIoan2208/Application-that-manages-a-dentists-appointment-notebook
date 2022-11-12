import ctrl.DentistController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Patient;
import repository.AppointmentFileRepository;
import repository.AppointmentRepository;
import repository.PatientFileRepository;
import repository.PatientRepository;
import services.DentistServices;
import services.ServicesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientAppointment.fxml"));
            Parent root = loader.load();
            DentistController ctrl = loader.getController();
            ctrl.setDentistServices(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dentist");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }
    public static void main(String[] args)
    {
        launch(args);
        Patient[] arrayOfPatients = {
                new Patient(1, "Jeff", "Bezos", "1231231431421" ,"00.00.0001" ),
                new Patient(2, "Elon", "Musk", "1234567011876", "00.01.0002"),
                new Patient(3, "Mark", "Zuckenberg", "1432567011876", "01.02.0003"),
                new Patient(4, "George", "Becali", "2453675011876", "02.03.0004")
        };

        Stream.of(arrayOfPatients);

        List<Patient> patList = Arrays.asList(arrayOfPatients);

        for (Patient p : arrayOfPatients) { System.out.println(p);}


    }

    static DentistServices getService() throws ServicesException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("DentistData.properties"));
            String patientsFileName=properties.getProperty("PatientFile");
            if (patientsFileName==null){
                patientsFileName="Patients.txt";
                System.err.println("Patients file not found. Using default "+patientsFileName);
            }
            String appointmentsFileName=properties.getProperty("AppointmentsFile");
            if (appointmentsFileName==null){
                appointmentsFileName="Appointments.txt";
                System.err.println("Appointments file not found. Using default "+appointmentsFileName);
            }
            PatientRepository patRepo = new PatientFileRepository(patientsFileName);
            AppointmentFileRepository appRepo = new AppointmentFileRepository(appointmentsFileName, patRepo);
            return new DentistServices(patRepo, appRepo);
        }catch (IOException ex){
            throw new ServicesException("Error starting app "+ex);
        }
    }

}
