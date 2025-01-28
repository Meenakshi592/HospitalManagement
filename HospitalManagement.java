package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagement {
	
	private static final String url="jdbc:mysql://localhost:3306/hospitalm";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			
		}
		Scanner sc=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection,sc);
			Doctor doctor=new Doctor(connection);
			
			while(true) {
				System.out.println("welcome to Abc Hospital Management");
				System.out.println("1,Add Patient");
				System.out.println("2,view patient");
				System.out.println("3,view doctors");
				System.out.println("4,book Appointment");
				System.out.println("5,Exit");
				
				System.out.println("Enter your choice");
				int choice=sc.nextInt();
				
				switch(choice) {
				case 1:patient.addPatient();
				break;
				case 2:patient.viewpatient();
				break;
				
				case 3:doctor.viewDoctor();
				break;
				
				case 4:bookappointment(patient, doctor, connection, sc);
				System.out.println();
				break;
				case 5:return;
				
				default:System.out.println("Invalid request");
				break;
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
	}

}
	
	public static void bookappointment(Patient patient,Doctor doctor,Connection connection,Scanner sc) {
		System.out.println("Enter the patient id:");
		int patientid =sc.nextInt();
		
		System.out.println("Enter the Patient Name:");
		String patientname=sc.next();
		
		System.out.println("Enter the Doctor id");
		int doctorid=sc.nextInt();
		
		System.out.println("Enter the Appointment date(YYYY-MM-DD)");
		String appointmentdate=sc.next();
		
		
		if(patient .getPatientid(patientid)&&doctor.getdoctorid(doctorid)) {
			if(checkdoctoravailability(doctorid,appointmentdate,connection)) {
				
				String appointmentquery="INSERT INTO appointments(patient_id,patient_name,doctor_id,appointment_date)VALUES(?,?,?,?)";
				try {
					PreparedStatement p=connection.prepareStatement(appointmentquery);
					p.setInt(1, patientid);
					p.setString(2, patientname);
					p.setInt(3, doctorid);
					p.setString(4, appointmentdate);
					
					int rowsaffected=p.executeUpdate();
					if(rowsaffected > 0) { 
						System.out.println("Appointment Booked");
					}else {
						System.out.println("Failed to book");
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				
				
				
				
				
				
			
			}
		}
		
	}
	public static boolean checkdoctoravailability(int doctorid,String appointmentdate,Connection connection) {
		try {
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		PreparedStatement p=connection.prepareStatement(query);
		p.setInt(1,doctorid);
		p.setString(2, appointmentdate);
		ResultSet r=p.executeQuery();
		
		
		if(r.next()) {
			int count=r.getInt(1);
			
			if(count==0)
				return true;
			
			else
				return false;
		}
		}catch(Exception e) {
			
		}
		return false;
	}
	
}
