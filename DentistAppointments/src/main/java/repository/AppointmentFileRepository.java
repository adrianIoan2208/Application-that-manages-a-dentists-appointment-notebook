package repository;

import model.Appointment;
import model.Patient;
import model.Status;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AppointmentFileRepository extends AppointmentsInMemoryRepository {
    private String fileName;
    private PatientRepository patientRepo;
    private static int idGenerator=0;
    public AppointmentFileRepository(String fileName, PatientRepository patientRepo)
    {
        this.fileName=fileName;
        this.patientRepo=patientRepo;
        ReadFromFile();
    }
    private void ReadFromFile()
    {
        try(BufferedReader br=new BufferedReader(new FileReader(fileName)))
        {
            String line=br.readLine();

            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator, starting from 0");
            }

            while((line=br.readLine())!=null)
            {
                String[] el=line.split(";");
                if(el.length!=7)
                {
                    System.err.println("Line is not valid"+ line);
                    continue;
                }
                try
                {
                    int appointmentId=Integer.parseInt(el[0]);
                    int patientId=Integer.parseInt(el[1]);
                    double hoursNeeded=Double.parseDouble(el[4]);
                    Status st= Status.valueOf(el[6]);
                    Patient p=patientRepo.findByID(patientId);
                    Appointment ap=new Appointment(appointmentId,p,el[2],el[3],hoursNeeded,el[5],st);
                    super.add(ap);
                }
                catch(NumberFormatException ex)
                {
                    throw new RepositoryException("Error" + ex);
                }
            }
        }
        catch(IOException ex)
        {
            throw new RepositoryException("Error" + ex);
        }
    }
    private void writeToFile()
    {
        try(PrintWriter pw=new PrintWriter(fileName))
        {
            pw.println(idGenerator);
            for(Appointment p: findAll())
            {
                String str=p.getID()+";"+p.getPatientId()+";"+p.getDentistName()+";"+p.getProcedureName()+";"+p.getHoursNeeded()+";"+p.getDate()+";"+p.getStatus();
                pw.println(str);
            }
        }
        catch(IOException ex)
        {
            throw new RepositoryException(ex);
        }
    }
    @Override
    public Appointment add(Appointment p)
    {
         p.setID(getNextId());
         super.add(p);
         writeToFile();
         return p;
    }
    @Override
    public void delete(Appointment p)
    {
        super.delete(p);
        writeToFile();
    }
    @Override
    public void update(Integer id,Appointment p)
    {
        super.update(id,p);
        writeToFile();
    }

    private static int getNextId(){
        return idGenerator++;
    }
}
