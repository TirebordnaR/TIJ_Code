package basic;

import basic.Meal.Food;

public class EnumEnumInterface {
	public static void main(String[] args) {
		for(Meal m : Meal.values()){
			System.out.println("#" +  m.toString() + "#");
			Food f = m.randomSelection();
			System.out.println( f );
		}
	}
}

enum Meal {
	
	// 下面三个枚举实例,每个里面都包含了一个Food枚举实例
	APPETIZE(Food.Appetizer.class),
	MAINCOURSE(Food.MainCourse.class),
	COFFE(Food.Coffe.class);
	
	private Food[] foods;
	Meal(Class<? extends Food> kind ){
		foods = kind.getEnumConstants();
	}
	
	public Food randomSelection(){
		return Enums.random( foods );
	}
	
	public interface Food {
		enum Appetizer implements Food {
			SALAD, SOUP
		}

		enum MainCourse implements Food {
			LASAGNE, GELATO
		}

		enum Coffe implements Food {
			TEA, LATTE
		}
	}
}