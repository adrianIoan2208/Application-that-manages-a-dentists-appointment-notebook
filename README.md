# Dentist Appoitnments

This application was built to increase maneuverability over the appointments and to spend less time adding them in the notebook by hand 

## Installation

In order for you to run this program, it is mandatory to use a Java IDE

Examples: IntelliJ Idea, Eclipse, NetBeans, BLUEJ, JDeveloper, etc.

## Usage

Try to navigate towards the directory inside which you would like to copy the repository and after insert this commands inside your terminal

```bash
git init
git clone git@github.com:adrianIoan2208/DentistAppointments.git
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Hints

1. The application uses two txt files as storage (Patients.txt and Appointments.txt). Initially the files contain only the first ID of the patient/appointment situated on the first line of the text file. Every time you add a patient/appointment the file is updated like this: 
    - the number on the first line will get updated with the next ID
    - on the next line it will appear the patient/appointment you have added.

    Example of Patients.txt after adding three patients:
```txt
4
1/Patient #1/Personal Numeric Code/Date
2/Patient #2/Personal Numeric Code/Date
3/Patient #3/Personal Numeric Code/Date
```
    
2. While running the application, in the Patients/Appointments tab, you have an option to update a patient/appointment:
    - it will be easier for you to click on a patient/appointment and click on the 'Fill with info' button.

## Test

The application passes all the tests and also has all exceptions handled.
