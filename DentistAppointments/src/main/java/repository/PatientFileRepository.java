package repository;

import model.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PatientFileRepository extends PatientInMemoryRepository {
    private String fileName;
    private static int idGenerator=0;
    public PatientFileRepository(String fileName)
    {
        this.fileName=fileName;
        ReadFromFile();
    }
    private void ReadFromFile()
    {
        try(BufferedReader br=new BufferedReader( new FileReader(fileName)))
        {
            String line= br.readLine();
            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator, starting from 0");
            }

            while((line=br.readLine())!=null)
            {
                String[] el=line.split(";");
                if(el.length!=5)
                {
                    System.err.println("This line is not valid"+line);
                    continue;
                }
                try
                {
                    int id=Integer.parseInt(el[0]);
                    Patient p= new Patient(id,el[1],el[2],el[3],el[4]);
                    super.add(p);
                }
                catch(NumberFormatException nr)
                {
                    System.err.println("Id not valid"+el[0]);
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
            for(Patient p: findAll())
            {
                String str=p.getID()+";"+p.getFirstName()+";"+p.getLastName()+";"+p.getCnp()+";"+p.getBirthDate();
                pw.println(str);
            }
        }
        catch(IOException ex)
        {
            throw new RepositoryException(ex);
        }
    }
    @Override
    public Patient add(Patient p)
    {
        p.setID(getNextId());
        super.add(p);
        writeToFile();
        return p;
    }
    @Override
    public void delete(Patient p)
    {
        super.delete(p);
        writeToFile();
    }
    @Override
    public void update(Integer id,Patient p)
    {
        super.update(id,p);
        writeToFile();
    }

    private static int getNextId(){
        return idGenerator++;
    }
}
