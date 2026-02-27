package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalmanagementSystem {
    private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username="root";
    private static final String password="En+erT@!nMenT@M5";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner= new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url, username,password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor= new Doctor(connection);

            while(true){
                System.out.println("Hospital management System");
                System.out.println("1. Add Patients");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice");

                int choice= scanner.nextInt();
                switch(choice){
                    case 1:
                        //Add Patients
                        patient.addpatient();
                        System.out.println();
                         break;
                    case 2:
                        //View Patients
                        patient.viewPatient();
                        System.out.println();
                         break;
                    case 3:
                         //View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                         break;
                    case 4:
                         //Book appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                         break;
                    case 5:
                         //Exit
                        return;
                    default:
                        System.out.println("Enter a valid choice");
                        break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor,Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id");
        int patientId= scanner.nextInt();
        System.out.println("Enter Doctor Id");
        int doctorId= scanner.nextInt();
        System.out.println("Enter appointment date(YYYY-MM-DD)");
        String appointmentDate= scanner.next();

        if(patient.getPatientById(patientId)&& doctor.getDoctorById(doctorId)){
         if(checkDoctorAvailablity(doctorId,appointmentDate, connection)){
            String appointmentQuarry= "INSERT INTO appointments(patient_id,doctors_id,appointment_date)VALUES(?, ?, ?)";
            try{
                PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuarry);
                preparedStatement.setInt(1,patientId);
                preparedStatement.setInt(2,doctorId);
                preparedStatement.setString(3, appointmentDate);

                int rowsAffected= preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Appointment Booked");
                }else{
                    System.out.println("Failed to book appointment");
                }
            }catch(SQLException e){
               e.printStackTrace();
             }
         }else {
             System.out.println("Doctor not available on this date");
         }
        }else{
            System.out.println("Either doctor or patient doesn't Exist!!!");
        }
    }

    public static boolean checkDoctorAvailablity(int doctorId, String appointmentDate, Connection connection){
        String quarry="SELECT COUNT(*) FROM appointments WHERE doctors_id= ? AND appointment_date =?";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(quarry);
        preparedStatement.setInt(1, doctorId);
        preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                int count =resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
         return false;
    }

}
