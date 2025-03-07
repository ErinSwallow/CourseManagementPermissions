import java.io.*;

public class Course implements Serializable{
	private String CourseName;
	private String CourseID;
	private int MaxStudentAmount;
	private int CurrentStudentAmount;
	private Student[]StudentNames;
	private String CourseInstructor;
	private int CourseSectionNumber;
	private String CourseLocation;

	public Course(){
		CourseName = "";
		CourseID = "";
		MaxStudentAmount = 0;
		CurrentStudentAmount = 0;
		StudentNames = new Student[0];
		CourseInstructor = "";
		CourseSectionNumber = 0;
		CourseLocation = "";
	}

	public Course(String CN, String CID){
		CourseName = CN.toLowerCase().strip();
		CourseID = CID;
	}

	public Course(String CN, String CID, int MSA, String CI, int CSN, String CL){
		CourseName = CN.toLowerCase().strip();
		CourseID = CID;
		MaxStudentAmount = MSA;
		StudentNames = new Student[MaxStudentAmount];
		CurrentStudentAmount = GetStudentAmount(StudentNames);
		CourseInstructor = CI;
		CourseSectionNumber = CSN;
		CourseLocation = CL;
	}

	public String GetCourseName(){
		return CourseName;
	}

	public void SetCourseName(String name){
		CourseName = name;
	}

	public String GetCourseID(){
		return CourseID;
	}

	public void SetCourseID(String ID){
		CourseID = ID;
	}

	public int GetMaxStudent(){
		return MaxStudentAmount;
	}

	public void SetMaxStudent(int amnt){
		if(amnt < GetStudentAmount(StudentNames)){
			System.out.println("Sorry! There are more than " + amnt + " students in the class. Process failed.");
		}
		else{
			MaxStudentAmount = amnt;
			Student[]newNames = new Student[MaxStudentAmount];
			for(int i = 0; i < StudentNames.length; i++){
				newNames[i] = StudentNames[i];
			}
			StudentNames = newNames;
		}
	}

	public int GetStudentAmount(){
		return CurrentStudentAmount;
	}

	public int GetStudentAmount(Student[]students){
		int amnt = 0;
		for(int i = 0; i < students.length; i++){
			if(students[i] != null){
				amnt++;
			}
		}
		return amnt;
	}

	public Student[] GetStudentNames(){
		return StudentNames;
	}

	public void AddStudentName(Student student){
		for(int i = 0; i < StudentNames.length; i++){
			if(StudentNames[i] == null){
				StudentNames[i] = student;
				CurrentStudentAmount++;
				System.out.println("Process successful!");
				return;
			}
			else{
				continue;
			}
		}
		System.out.println("Sorry! The class has reached max capacity and cannot take more students.");
		return;
	}

	public void RemoveStudentName(Student student){
		int index = -1;
		for(int i = 0; i < StudentNames.length; i++){
			index = i;
			if(StudentNames[i].equals(student)){
				break;
			}
		}
		if(index >= 0){
			StudentNames[index] = null;
			for(int j = 0; j < StudentNames.length - 1; j++){
				if(StudentNames[j] == null){
					StudentNames[j] = StudentNames[j + 1];
					StudentNames[j + 1] = null;
				}
			}
			CurrentStudentAmount--;
			System.out.println("Process successful!");
			return;
		}
		else{
			System.out.println("error");
		}
	}

	public String GetInstructor(){
		return CourseInstructor;
	}

	public void SetInstructor(String ci){
		CourseInstructor = ci;
	}

	public int GetSectionNumber(){
		return CourseSectionNumber;
	}

	public void SetSectionNumber(int sn){
		CourseSectionNumber = sn;
	}

	public String GetLocation(){
		return CourseLocation;
	}

	public void SetLocation(String loc){
		CourseLocation = loc;
	}

	public boolean isOpen(){
		if(CurrentStudentAmount >= MaxStudentAmount){
			return false;
		}
		else{
			return true;
		}
	}

	public String isOpen(boolean b){
		if(b){
			return "YES";
		}
		else{
			return "NO";
		}
	}

	public void PrintInfo(){
		String[]words = CourseName.split(" ");
		char temp;
		String coursename;
		for(int i = 0; i < words.length; i++){
			temp = words[i].toUpperCase().charAt(0);
			words[i] = words[i].substring(1,words[i].length());
			words[i] = temp + words[i];

		}

		System.out.println();
		System.out.println();
		System.out.print("Course Name: " + CourseName + " | ");
		System.out.print("Course ID: " + CourseID + " | ");
		System.out.print("Course Instructor: " + CourseInstructor + " | ");
		System.out.print("Section Number: " + CourseSectionNumber + " | ");
		System.out.print("Location: " + CourseLocation + " | ");
		System.out.print("Is Open: " + isOpen(isOpen()));
	}
}
