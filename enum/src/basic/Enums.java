package basic;

import java.util.Random;

// 从枚举实例中随机选取一个值
public class Enums {

	private static Random rand = new Random( 67 );

	// 泛型方法,只需要将泛型参数放在方法返回值之前
	// 下面的方法返回的类型是T： T是<T extends Enum<T>>类型,即是一个Enum<T>的实例
	public static <T extends Enum<T>> T random(Class<T> enumClass) {
		return random( enumClass.getEnumConstants() );
	}

	// 随机返回enum实例中的常量的一个
	public static <T> T random(T[] ec) {
		return ec[rand.nextInt( ec.length )];
	}

	public static void main(String[] args) {
		for( int i = 0; i < 10; i++ ) {
			System.out.println( Enums.random( Status.class ) );
		}

	}
}

enum Status {
	SITTING, LYING, STANDING, SLEEPING
}
