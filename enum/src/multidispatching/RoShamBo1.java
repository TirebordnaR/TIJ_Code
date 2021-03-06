package multidispatching;

import java.util.Random;

// 多路分发 有多个未知的具体类型时使用,可以省略掉许多else-if语句来判段类型
public class RoShamBo1 {

	public static void main(String[] args) {
		int ROUND = 20;
		for( int i = 0; i < ROUND; i++ ) {
			match( newItem(), newItem() );
		}
	}

	static Random rand = new Random( 67 );

	public static void match(Item a, Item b) {
		System.out.println( a + " vs " + b + ":" + a.compete( b ) );
	}

	public static Item newItem() {
		switch( rand.nextInt( 3 ) ){
		case 0:
			return new Paper();
		case 1:
			return new Scissors();
		case 2:
			return new Rock();
		default:
			throw new RuntimeException();
		}
	}
}

enum Outcome {
	WIN, LOSE, DRAW
}

interface Item {
	Outcome compete(Item it);

	Outcome eval(Paper p);

	Outcome eval(Scissors p);

	Outcome eval(Rock p);
}

class Paper implements Item {

	@Override
	// 假设it是 Rock类型,那么当a.compete( b )被调用时,Item a = new Paper(), b = new Rock()
	// 则首先因为a是Paper类型,所以先调用Paper的compete方法(第一次分发机制起作用)
	// 在Paper的compete方面内部,又调用b.eval(this)方法,
	// 由于eval方法被重载过,因此可以根据实际参数进行第二次分发,去调用b.eval(Paper p)来判断
	// 假设采用else-if来判断类型,则会显得不那么优雅
	public Outcome compete(Item it) {
		return it.eval( this );
	}

	@Override
	public Outcome eval(Paper p) {
		return Outcome.DRAW;
	}

	@Override
	public Outcome eval(Scissors p) {
		return Outcome.WIN;
	}

	@Override
	public Outcome eval(Rock p) {
		return Outcome.LOSE;
	}

	@Override
	public String toString() {
		return "Paper";
	}
}

class Scissors implements Item {

	@Override
	public Outcome compete(Item it) {
		return it.eval( this );
	}

	@Override
	public Outcome eval(Paper p) {
		return Outcome.LOSE;
	}

	@Override
	public Outcome eval(Scissors p) {
		return Outcome.DRAW;
	}

	@Override
	public Outcome eval(Rock p) {
		return Outcome.WIN;
	}

	@Override
	public String toString() {
		return "Scissors";
	}
}

class Rock implements Item {

	@Override
	public Outcome compete(Item it) {
		return it.eval( this );
	}

	@Override
	public Outcome eval(Paper p) {
		return Outcome.WIN;
	}

	@Override
	public Outcome eval(Scissors p) {
		return Outcome.LOSE;
	}

	@Override
	public Outcome eval(Rock p) {
		return Outcome.DRAW;
	}

	@Override
	public String toString() {
		return "Rock";
	}
}
