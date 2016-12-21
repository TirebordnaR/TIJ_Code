package basic;

import java.lang.reflect.Method;

public class UseCaseTracker {

	public static void main(String[] args) {

		Class<?> c = PassWordUtils.class;

		// 取得该类的所有方法(因为该类只有方法上有注解),再从方法中取得关于UseCase类的注解
		for( Method m : c.getDeclaredMethods() ) {
			UseCase uc = m.getAnnotation( UseCase.class );
			if ( uc != null ) {
				System.out
						.println( "Find:" + uc.id() + ":" + uc.description() );
			}
		}
	}
}
