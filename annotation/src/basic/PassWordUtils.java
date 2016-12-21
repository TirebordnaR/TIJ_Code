package basic;

public class PassWordUtils {

	@UseCase(id=67,description="passwd must be 123")
	 public boolean validate(String passwd){
		 return passwd.equals( "123" );
	 }
	
	@UseCase(id=68)
	public String encrypt(String passwd){
		 return "321";
	 }
	
	@UseCase
	public boolean check(String passwd){
		return passwd == "321";
	}
	
}
