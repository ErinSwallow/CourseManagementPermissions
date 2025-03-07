import java.io.*;
import java.util.*;

public class Driver{

	private static Student[]students = new Student[0];
	private static Course[]courses = new Course[0];

	public static void main (String[]args){
		String readFile = "MyUniversitycourses.csv";
		String writtenFile = "SavedData.ser";
		String line = null;
		File courseFile = new File(readFile);
		Admin admin = new Admin("Admin", "Admin");
		students = DynamicArray.Add(students, new Student("user","pass","first","last"));

		if(!new File(writtenFile).isFile()){
			try{
				Scanner sc = new Scanner(courseFile);
				sc.useDelimiter(",");
	
				FileOutputStream fos = new FileOutputStream(writtenFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
	
				sc.nextLine();
				line = sc.nextLine();
				Scanner lsc = new Scanner(line);
				lsc.useDelimiter(",");
				while (lsc.hasNext()){
					Course temp = new Course();
					temp.SetCourseName(lsc.next());
					temp.SetCourseID(lsc.next());
					temp.SetMaxStudent(lsc.nextInt());
					lsc.next();
					lsc.next();
					temp.SetInstructor(lsc.next());
					temp.SetSectionNumber(lsc.nextInt());
					temp.SetLocation(lsc.next());
					courses = DynamicArray.Add(courses, temp);
					if(sc.hasNext()){
						line = sc.nextLine();
						lsc = new Scanner(line);
						lsc.useDelimiter(",");
					}
					else{
						break;
					}
				}
				for(int i = 0; i < courses.length; i++){
					oos.writeObject(courses[i]);
				}
				oos.close();
				fos.close();
				System.out.print("Saved");
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

		try{
			FileInputStream cfis = new FileInputStream(writtenFile);
			ObjectInputStream cois = new ObjectInputStream(cfis);
			while((line = cfis.readLine()) != null){
				courses = DynamicArray.Add(courses,(Course)cois.readObject());
			}
			cois.close();
			cfis.close();
			if(new File("SavedStudents.ser").isFile()){
				FileInputStream sfis = new FileInputStream("SavedStudents.ser");
				ObjectInputStream sois = new ObjectInputStream(sfis);
				Scanner s = new Scanner(new File("SavedStudents.ser"));
				while(s.hasNext()){
					students = DynamicArray.Add(students, (Student)sois.readObject());
					if(s.hasNextLine()){
						s.nextLine();
					}
				}
				sois.close();
				sfis.close();
			}
		}
		catch(Exception e){
			System.out.println("Something went wrong :(");
		}
		for(int i = 0; i < courses.length; i++){
			//courses[i].PrintInfo();
		}

		Scanner input;
		String user;
		String password;

		while (true){
			input = new Scanner(System.in);
			System.out.print("username: ");
			user = input.next();
			if(user.equals("Admin")){
				System.out.print("password: ");
				password = input.next();
				if(password.equals("Admin001")){
					AdminLogin(admin);
				}
			}
			else{
				for(int i = 0; i < students.length; i++){
					if(user.equals(students[i].GetUser())){
						System.out.print("password: ");
						password = input.next();
						if(password.equals(students[i].GetPass()))
						StudentLogin(students[i]);
					}
				}
			}
		}
	}

	public static void AdminLogin(Admin admin){
		boolean running = true;
		int choice = 0;
		while(running){
			Scanner input = new Scanner(System.in);
			System.out.println("What would you like to do?");
			System.out.println("COURSE MANAGEMENT:");
			System.out.println("1. Create a new course");
			System.out.println("2. Delete a course");
			System.out.println("3. Edit a course");
			System.out.println("4. Display information for a course");
			System.out.println("5. Register a student");
			System.out.println();
			System.out.println("REPORTS:");
			System.out.println("6. View all courses");
			System.out.println("7. View all full courses");
			System.out.println("8. Write full courses to file named \"FullCourses.txt\"");
			System.out.println("9. View students who are registered in a course");
			System.out.println("10. View the courses a student is registered in");
			System.out.println("11. Sort the courses based on number of registered students");
			System.out.println("12. Save & Exit");

			try{
				choice = input.nextInt();
			}
			catch(InputMismatchException ime){
				System.out.println("Please enter a number.");
			}
			switch(choice){
			case 1 : {
				courses = DynamicArray.Add(courses, admin.CreateCourse());
				continue;
			}
			case 2 : {
				System.out.println("Please input the ID of the course you would like to delete");
				String cid = input.next();
				Course course = FindCourse(cid);
				for(int i = 0; i < courses.length; i++){
					if(courses[i].equals(course)){
						System.out.println("hellowo");
						courses = DynamicArray.Delete(courses, courses[i]);
					}
				}
				continue;
			}
			case 3: {
				System.out.println("Which course would you like to edit? Please print the course ID.");
				String cid = input.next();
				admin.EditCourse(FindCourse(cid), input, students);
				continue;
			}
			case 4 : {
				System.out.println("Please input the ID of the course you would like to know about");
				String cid = input.next();
				FindCourse(cid).PrintInfo();
				continue;
			}
			case 5 : {
				Student student = admin.RegisterStudent(input);
				students = DynamicArray.Add(students, student);
				continue;
			}

			case 6 : {
				admin.ViewCourses(courses);
				continue;
			}
			case 7 : {
				admin.ViewFullCourses(courses);
				continue;
			}
			case 8 : {
				admin.WriteFullCoursesToFile(courses);
				continue;
			}
			case 9 : {
				System.out.println("Which course would you like to see? Please enter the course ID.");
				admin.ViewStudentsInCourse(FindCourse(input.next()));
				continue;
			}
			case 10 : {
				System.out.println("Please enter the name of the student");
				input.nextLine();
				admin.ViewCoursesOfStudent(FindStudent(input.nextLine()), courses);
				continue;
			}
			case 11 : {
				admin.SortCourses(courses);
				continue;
			}
			case 12 : {
				Exit();
				running = false;
				continue;
			}
			}
		}
	}
	
	public static void StudentLogin(Student student){
		boolean running = true;
		while (running){
			Scanner input = new Scanner(System.in);
			System.out.println("What would you like to do?");
			System.out.println("COURSE MANAGEMENT");
			System.out.println("1. View all courses");
			System.out.println("2. View all open courses");
			System.out.println("3. Register in a course");
			System.out.println("4. Withdraw from a course");
			System.out.println("5. View all courses you are registered in");
			System.out.println("6. Save & Exit");
			int i = input.nextInt();
			switch(i){
			case 1 : {
				student.ViewCourses(courses);
				continue;
				}
			case 2 : {
				student.ViewOpenCourses(courses);
				continue;
				}
			case 3 : {
				student.RegisterInCourse(courses);
				continue;
			}
			case 4 : {
				student.WithdrawFromCourse(courses);
				continue;
			}
			case 5 : {
				student.ViewRegisteredCourses(courses);
				continue;
			}
			case 6 : {
				Exit();
				running = false;
				continue;
			}
			}
		}
	}

	public static Course FindCourse(String cid){
		Course course = new Course();
		cid = cid.toLowerCase().strip();
		for(int i = 0; i < courses.length; i++){
			if(cid.equals(courses[i].GetCourseID().toLowerCase().strip())){
				course = courses[i];
			}
		}
		return course;
	}

	public static Student FindStudent(String name){
		Student student = new Student();
		for(int i = 0; i < students.length; i++){
			if(name.toLowerCase().strip().equals(students[i].GetName().toLowerCase().strip())){
				student = students[i];
			}
		}
		return student;
	}

	public static void Exit(){
		try{
			FileOutputStream cfos = new FileOutputStream("SavedData.ser");
			ObjectOutputStream coos = new ObjectOutputStream(cfos);
			for(int i = 0; i < courses.length; i++){
				coos.writeObject(courses[i]);
			}
			coos.close();
			cfos.close();
		}
		catch(IOException e){
			System.out.println("Process was not completed");
		}
		try{
			FileOutputStream sfos = new FileOutputStream("SavedStudents.ser");
			ObjectOutputStream soos = new ObjectOutputStream(sfos);
			for(int j = 0; j < students.length; j++){
				System.out.println("hi");
				soos.writeObject(students[j]);
			}
			soos.close();
			sfos.close();
		}
		catch(IOException e){
			System.out.println("Process was not completed.");
		}
	}
}

class DynamicArray{
	public static Course[] Add(Course[]courses, Course course){
		Course[]newcourses = new Course[courses.length + 1];
		for(int i = 0; i < courses.length; i++){
			newcourses[i] = courses[i];
		}
		newcourses[newcourses.length - 1] = course;
		return newcourses;
	}

	public static Student[] Add(Student[]students, Student student){
		Student[]newstudents = new Student[students.length + 1];
		for(int i = 0; i < students.length; i++){
			newstudents[i] = students[i];
		}
		newstudents[newstudents.length - 1] = student;
		return newstudents;
	}

	public static Course[] Delete(Course[]courses, Course course){
		Course[]newcourses = new Course[courses.length - 1];
		int j = 0;
		for(int i = 0; i < newcourses.length; i++){
			if(!courses[i].equals(course)){
				newcourses[j] = courses[i];
				j++;
			}
			else{
				continue;
			}
		}
		return newcourses;
	}

	public static Student[] Delete(Student[]students, Student student){
		Student[]newstudents = new Student[students.length - 1];
		int j = 0;
		for(int i = 0; i < newstudents.length; i++){
			if(!students[i].equals(student)){
				newstudents[j] = students[i];
				j++;
			}
		}
		return newstudents;
	}
}
