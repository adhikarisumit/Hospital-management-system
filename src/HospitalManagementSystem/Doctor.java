package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection= connection;
    }


    public void viewDoctors(){
        String quarry="SELECT * FROM patients";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(quarry) ;
            ResultSet resultSet =preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+------------+-------------------+----------------+");
            System.out.println("| Doctor Id- | Name              | Specialazation |");
            System.out.println("+------------+-------------------+----------------+");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age= resultSet.getInt("age");
                String specialazation= resultSet.getString("specialazation");
                System.out.printf("|%-12s|%-18s|%-18s|\n",id,name,age,specialazation);
                System.out.println("+------------+-------------------+----------------+");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String quarry= "SELECT * FROM doctors WHERE id=?";
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
