package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection= connection;
        this.scanner= scanner;
    }

    public void addpatient(){
        System.out.print("Enter Patient's Name");
        String name = scanner.next();
        System.out.print("Enter Patient's Age");
        int age= scanner.nextInt();
        System.out.print("Enter Patient's Age");
        String gender= scanner.next();


        try{

            String quarry="INSERT INTO patients(name,age.gender) VALUES(?, ?, ?)";
            PreparedStatement  preparedStatement =connection.prepareStatement(quarry);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows= preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Patient Added Successfully!!");
            }
            else{
                System.out.println("Failed to Add Patient!!");
            }
        }catch(SQLException e){
           e.printStackTrace();
        }
    }
    public void viewPatient(){
        String quarry="SELECT * FROM patients";
        try{
           PreparedStatement preparedStatement= connection.prepareStatement(quarry) ;
            ResultSet resultSet =preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+-------------------+------------+--------------+");
            System.out.println("| Patient Id-| Name              | Age        | Gender       |");
            System.out.println("+------------+-------------------+------------+--------------+");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age= resultSet.getInt("age");
                String gender= resultSet.getString("gender");
                System.out.printf("|%-12s|%-18s|%-12s|%-14s|\n",id,name, age, gender);
                System.out.println("+------------+-------------------+------------+--------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String quarry= "SELECT * FROM patients WHERE id=?";
        try{
         PreparedStatement preparedStatement= connection.prepareStatement(quarry);
         preparedStatement.setInt(1,id);
         ResultSet resultSet= preparedStatement.executeQuery();

         if(resultSet.next()){
             return true;
         }else{
             return false;
         }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
