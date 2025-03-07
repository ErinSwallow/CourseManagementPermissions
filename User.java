public abstract class User{

	protected String username;

	protected String password;

	protected String firstName;
	
	protected String lastName;

	public String GetUser(){
		return username;
	}

	public void SetUser(String user){
		username = user;
	}

	public String GetPass(){
		return password;
	}

	public void SetPass(String pass){
		password = pass;
	}

	public String GetFirstName(){
		return firstName;
	}

	public void SetFirstName(String fn){
		firstName = fn;
	}

	public String GetLastName(){
		return lastName;
	}

	public void SetLastName(String ln){
		lastName = ln;
	}

	public String GetName(){
		return firstName + " " + lastName;
	}

	public abstract void ViewCourses(Course[]courses);
}
