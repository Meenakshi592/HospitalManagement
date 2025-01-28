package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scan;
	public Patient(Connection connection, Scanner scan) {
		this.connection = connection;
		this.scan = scan;
	}
	public void addPatient() {
		System.out.println("Enter the patient Details");
		String name=scan.next();
		System.out.println("Enter the patient age");
		int age=scan.nextInt();
		System.out.println("Enter the patient Gender");
		String gender=scan.next();
		
		
		try {
			String query ="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			
			
			int affectedrows=ps.executeUpdate();
			
			if(affectedrows>0) {
				System.out.println("patient Added");
			}else {
				System.out.println("failed");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewpatient() {
		String query="SELECT * from patients";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("patients ");
			System.out.println("--------------------");
			System.out.println("|patient id | name | Age | Gender | ");
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				int age=rs.getInt("age");
				String gender=rs.getString("gender");
				System.out.printf(" |%-12s|%-8s|%-8s|%-10s\n",id,name,age,gender);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean getPatientid(int id) {
		String query="SELECT * from patients where id=?";
		try {
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet r=ps.executeQuery();
		if(r.next()) {
			return true;
		}
		
		else {
			return false;
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
