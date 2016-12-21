package basic;

// 对enum添加自己的方法,自己设定其值,重写父类方法
public enum EnumWithMethod {
	// enum实例必须在最前面
	WEST("This is WEST",100), 
	NORTH("This is North",200), 
	EAST("This is EAST",300), 
	SOUTH("This is South",200);

	private String description;
	private int ordinal;

	EnumWithMethod(String description, int ordinal) {
		this.description = description;
		this.ordinal = ordinal;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "(" + description + ":" + ordinal + ")";
	}
	
	public static void main(String[] args) {
		for(EnumWithMethod e : EnumWithMethod.values()){
			System.out.println( e + ":" + e.getDescription() );
		}
	}
}
