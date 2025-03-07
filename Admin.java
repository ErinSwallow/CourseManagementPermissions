import java.util.*;
import java.io.*;

public class Admin extends User implements AdminInterface{

	public Admin(String firstName, String lastName){

		username = "Admin";
		password = "Admin001";
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Course CreateCourse(){
		Scanner input = new Scanner(System.in);
		try{
			System.out.println("What is the name of the course?");
			String CN = input.nextLine();
			System.out.println("What is the course ID?");
			String CID = input.nextLine();
			System.out.println("What is the max student amount?");
			int MSA = input.nextInt();
			input.nextLine();
			System.out.println("Who is the course instructor?");
			String CI = input.nextLine();
			System.out.println("What is the section number?");
			int CSN = input.nextInt();
			input.nextLine();
			System.out.println("What is the courses location?");
			String CL = input.nextLine();
			System.out.println(CN + " course has been created.");
			return new Course(CN, CID, MSA, CI, CSN, CL);
		}
		catch(InputMismatchException im){
			System.out.println("Sorry! Couldn't complete process.");
			return null;
		}
	}

	public Course CreateCourse(String CN, String CID){
		return new Course(CN, CID);
	}

	public void DeleteCourse(Course course){
		//unserialize it, perhaps
		//so we probably need a place to store course classes
		//as well as student classes
	}

	public void EditCourse(Course course, Scanner input, Student[]students){
		System.out.println("What would you like to edit?");
		System.out.println("Maximum Student Amount");
		System.out.println("Student Names");
		System.out.println("Course Instructor");
		System.out.println("Section Number");
		System.out.println("Location");
		input.nextLine();
		String edited = input.nextLine().toLowerCase().strip();
		switch(edited){
		case "maximum student amount" : {
			System.out.println("What would you like to set it to?");
			int amnt = input.nextInt();
			course.SetMaxStudent(amnt);
			break;
		}
		case "student names" : {
			System.out.println("would you like to ADD, REMOVE, or CHANGE a student?");
			String answ = input.next().toLowerCase().strip();
			switch (answ){
			case "add" : {
				System.out.println("Who would you like to add?");
				input.nextLine();
				String name = input.nextLine().toLowerCase().strip();
				for(int i = 0; i < students.length; i++){
					if(students[i].GetName().toLowerCase().strip().equals(name.toLowerCase().strip())){
						course.AddStudentName(students[i]);
					}
				}
				break;
			}
			case "remove" : {
				System.out.println("Please give the index of the student you would like to remove.");
				for(int i = 0; i < course.GetMaxStudent(); i++){
					System.out.println((i + 1) + ". " + course.GetStudentNames()[i]);
				}
				int index = input.nextInt() - 1;
				course.RemoveStudentName(index);
				break;
			}
			case "change" : {
				System.out.println("Please give the index of the student name you would like to change.");
				for(int i = 0; i < course.GetMaxStudent(); i++){
					System.out.println((i + 1) + ". " + course.GetStudentNames()[i]);
				}
				int index = input.nextInt() - 1;
				System.out.println("What would you like to change it to?");
				String name = input.next().strip();
				break;
			}
			}
			break;
		}
		case "course instructor" : {
			System.out.println("What would you like to set it to?");
			String name = input.next();
			course.SetInstructor(name);
			break;
		}
		case "section number" : {
			System.out.println("What would you like to set it to?");
			int num = input.nextInt();
			course.SetSectionNumber(num);
			break;
		}
		case "location" : {
			System.out.println("What would you like to set it to?");
			String name = input.next();
			course.SetLocation(name);
			break;
		}
	}
	}

	public Student RegisterStudent(Scanner input){
		System.out.println("What is this student's username?");
		String un = input.next();
		System.out.println("What is this student's password?");
		String pw = input.next();
		System.out.println("What is this student's first name?");
		String fn = input.next();
		System.out.println("What is this student's last name?");
		String ln = input.next();
		return new Student(un, pw, fn, ln);
	}

	public void ViewCourses(Course[]courses){
		Scanner input = new Scanner(System.in);
		for(int i = 0; i < courses.length; i++){
			courses[i].PrintInfo();
			System.out.println();
		}
		System.out.println("Would you like more info? (Y/N)");
		char answer = input.next().toLowerCase().charAt(0);
		if(answer == 'y'){
			System.out.println("Which course would you like more info on?");
			Course course = FindCourse(courses, input.next());
			System.out.println("Enrolled student names: ");
			for(int j = 0; j < course.GetStudentAmount(); j++){
				System.out.println(course.GetStudentNames()[j].GetName());
			}
			System.out.println(course.GetStudentAmount() + " students registered");
			System.out.println("Holds maximum " + course.GetMaxStudent());
		}
		else if(answer == 'n'){
			return;
		}

	}

	public void ViewFullCourses(Course[]courses){
		for(int i = 0; i < courses.length; i++){
			if(!courses[i].isOpen()){
				courses[i].PrintInfo();
				System.out.println();
			}
		}
	}

	public void WriteFullCoursesToFile(Course[]courses){
		try{
			FileWriter fw = new FileWriter("FullCourses.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i < courses.length; i++){
				if(!courses[i].isOpen()){
					bw.write(courses[i].GetCourseID());
				}
			}
		}
		catch(IOException e){
			System.out.println("Process could not be completed");
			return;
		}
	}

	public void ViewStudentsInCourse(Course course){
		System.out.println("Students in " + course.GetCourseName());
		for(int i = 0; i < course.GetStudentNames().length; i++){
			if(course.GetStudentNames()[i] != null){
				System.out.println(course.GetStudentNames()[i].GetName());
			}
		}	
	}

	public void ViewCoursesOfStudent(Student student, Course[]courses){
		Student[]students;
		for (int i = 0; i < courses.length; i++){
			students = courses[i].GetStudentNames();
			for(int j = 0; j < students.length; j++){
				if(student.equals(students[j])){
					courses[i].PrintInfo();
				}
			}
		}
	}

	public void SortCourses(Course[]courses){
		Course temp;
		for(int i = 0; i < courses.length; i++){
			for(int j = i; j < courses.length; j++){
				if(courses[j].GetStudentAmount() > courses[i].GetStudentAmount()){
					temp = courses[i];
					courses[i] = courses[j];
					courses[j] = temp;
				}
			}
		}
		for(int k = 0; k < courses.length; k++){
			System.out.println((k+1) + ". " + courses[k].GetCourseID() + " has " + courses[k].GetStudentAmount() + " students.");
		}
	}

	public Course FindCourse(Course[]courses, String cid){
		Course course = new Course();
		cid = cid.toLowerCase().strip();
		for(int i = 0; i < courses.length; i++){
			if(cid.equals(courses[i].GetCourseID().toLowerCase().strip())){
				course = courses[i];
			}
		}
		return course;
	}
}
