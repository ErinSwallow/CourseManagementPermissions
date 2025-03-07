import java.util.Scanner;
public interface AdminInterface{
	public Course CreateCourse();
	public Course CreateCourse(String CN, String CID);
	public void DeleteCourse(Course course);
	public void EditCourse(Course course, Scanner input, Student[]students);
	public Student RegisterStudent(Scanner input);
	public void ViewCourses(Course[]courses);
	public void ViewFullCourses(Course[]courses);
	public void WriteFullCoursesToFile(Course[]courses);
	public void ViewStudentsInCourse(Course course);
	public void ViewCoursesOfStudent(Student student, Course[]courses);
	public void SortCourses(Course[]courses); 
}
