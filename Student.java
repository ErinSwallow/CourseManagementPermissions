import java.io.*;
import java.util.*;

public class Student extends User implements Serializable, StudentInterface{

	public Student(String username, String password, String firstName, String lastName){

		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Student(){

	}

	public void ViewCourses(Course[]courses){
		for(int i = 0; i < courses.length; i++){
			courses[i].PrintInfo();
			System.out.println();
		}
	}

	public void ViewOpenCourses(Course[]courses){
		for(int i = 0; i < courses.length; i++){
			if(courses[i].isOpen()){
				courses[i].PrintInfo();
				System.out.println();
			}
		}
	}

	public void RegisterInCourse(Course[]courses){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the name and section number of the course you would like to enroll in.");
		String [] course = input.nextLine().toLowerCase().strip().split(" ");
		String coursename = "";
		int section = -1;
		try{
			section = Integer.valueOf(course[course.length-1]);
			for(int j = 0; j < course.length-1; j++){
				coursename += (course[j] + " ");
			}
			coursename = coursename.strip();
			
		}
		catch(InputMismatchException e){
			System.out.println("Incorrect formatting");
		}
		for(int i = 0; i < courses.length; i++){
			if(coursename.equals(courses[i].GetCourseName().toLowerCase().strip()) && section == courses[i].GetSectionNumber()){
				System.out.println("Course found! Please enter your full name.");
				String studentname = input.nextLine().toLowerCase().strip();
				if(studentname.equals(this.GetName())){
					courses[i].AddStudentName(this);
					System.out.println("Process complete!");
					return;
				}
				else{
					System.out.println("Process could not be completed.");
					return;
				}
			}
		}
		System.out.println("Sorry! The course could not be found. Check your spelling and try again.");
		return;
	}

	public void WithdrawFromCourse(Course[]courses){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the name and section number of the course you would like to withdraw from.");
		String [] course = input.nextLine().toLowerCase().strip().split(" ");
		String coursename = "";
		int section = -1;
		try{
			for(int j = 0; j < course.length-1; j++){
				coursename += (course[j] + " ");
			}
			coursename = coursename.strip();
			section = Integer.valueOf(course[course.length-1]);
		}
		catch(InputMismatchException e){
			System.out.println("Incorrect formatting");
		}
		for(int i = 0; i < courses.length - 1; i++){
			if(coursename.equals(courses[i].GetCourseName().toLowerCase().strip()) && section == courses[i].GetSectionNumber()){
				System.out.println("Course found! Please enter your full name.");
				String studentname = input.nextLine().toLowerCase().strip();
				if(studentname.equals(this.GetName())){
					courses[i].RemoveStudentName(this);
					System.out.println("Process complete!");
					return;
				}
				else{
					System.out.println("Process could not be completed.");
					return;
				}
			}
		}
		System.out.println("Sorry! The course could not be found. Check your spelling and try again.");
		return;
	}

	public void ViewRegisteredCourses(Course[]courses){
		Student[]students;
		for(int i = 0; i < courses.length; i++){
			students = courses[i].GetStudentNames();
			for(int j = 0; j < courses[i].GetStudentAmount(); j++){
				if(students[j].equals(this)){
					courses[i].PrintInfo();
				}
			}
		}
	}
}
