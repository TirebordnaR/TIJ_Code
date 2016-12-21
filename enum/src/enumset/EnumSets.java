package enumset;

import java.util.EnumSet;

// EnumSet内部用long/long[]来保存元素
public class EnumSets {

	public static void main(String[] args) {
		EnumSet<AlarmPoints> points = EnumSet.noneOf( AlarmPoints.class );

		points.add( AlarmPoints.STATIR1 );
		System.out.println( points );
		points.addAll( EnumSet.of( AlarmPoints.STATIR2, AlarmPoints.KITCHEN ) );
		System.out.println( points );
		points = EnumSet.allOf( AlarmPoints.class );
		System.out.println( points );
		points.removeAll( EnumSet.range( AlarmPoints.STATIR1, AlarmPoints.KITCHEN ) );
		System.out.println( points );
		points = EnumSet.complementOf( points );
		System.out.println( points );
	}
}

enum AlarmPoints {
	STATIR1, STATIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3, KITCHEN
}
