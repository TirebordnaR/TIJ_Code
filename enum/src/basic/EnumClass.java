package basic;

public class EnumClass {
	public static void main(String[] args) {

		// Enum类并没有values方法,values()方法是编译器添加的static方法,并且还添加了valuesOf(只有一个参数)的方法
		for( Color c : Color.values() ) {
			System.out.println( c.ordinal() );
			System.out.println( c.compareTo( Color.BLUE ) );
			System.out.println( c.equals( Color.BLUE ) );
			System.out.println( c == Color.BLUE );
			System.out.println( c.getClass() );
			System.out.println( c.getDeclaringClass() );
			System.out.println( c.name() );
			System.out.println( "===========" );
		}

		System.out.println( Enum.valueOf( Color.class, "BLACK" ) );

		// values()由于是被编译器插入到某个具体的Enum类中,所以如果把某个enum实例向上转型
		// 那么values()方法就无法看见了,但是可以通过Class对象的getEnumConstants获得
		Color c = Color.RED;
		for( Color c1 : c.getClass().getEnumConstants() ) {
			System.out.println(c1);
		}
	}
}

enum Color {
	RED, BLUE, BLACK
}
