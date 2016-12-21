package enummap;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapDemo {
	public static void main(String[] args) {

		EnumMap<AlarmPoints, Command> em = new EnumMap<>( AlarmPoints.class );

		// enum实例的顺序绝对了其在EnumMap中顺序
		// EnumMap允许改变值,因此就可以实现动态的调用与常量相关的方法
		em.put( AlarmPoints.KITCHEN, new Command() {
			@Override
			public void action() {
				System.out.println( " in kitchen...." );
			}
		} );

		em.put( AlarmPoints.LOBBY, new Command() {
			@Override
			public void action() {
				System.out.println( " in LOBBY...." );
			}
		} );

		for( Map.Entry<AlarmPoints, Command> e : em.entrySet() ) {
			System.out.println( e.getKey() + ": " );
			e.getValue().action();
		}
	}
}

interface Command {
	void action();
}

enum AlarmPoints {
	STATIR1, STATIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3, KITCHEN
}
