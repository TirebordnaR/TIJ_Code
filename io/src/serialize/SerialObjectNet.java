package serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerialObjectNet {

	public static void main(String[] args) throws Exception {

		House h = new House();
		List<Animal> animals = new ArrayList<>();
		animals.add( new Animal( "DOG", h ) );
		animals.add( new Animal( "PIG", h ) );
		animals.add( new Animal( "CAT", h ) );
		System.out.println( animals );

		ObjectOutputStream oos1 = new ObjectOutputStream( new FileOutputStream( "out1" ) );
		oos1.writeObject( animals );
		animals.get( 1 ).setName( "UN" ); // 改了一个值,但是不会被序列化成修改的值
		oos1.writeObject( animals );
		oos1.writeObject( animals );
		oos1.close();

		// 序列化到单一流过程中,对对象的改变从第一个对象写入流后就无法将改变序列化出去
		ObjectInputStream ois1 = new ObjectInputStream( new FileInputStream( "out1" ) );
		@SuppressWarnings("unchecked")
		List<Animal> animals11 = (List<Animal>) ois1.readObject();
		List<Animal> animals12 = (List<Animal>) ois1.readObject();
		System.out.println( animals11 );
		System.out.println( animals12 );
		ois1.close();

		animals.get( 1 ).setName( "UN" );
		System.out.println( "修改后:" + animals );
		ObjectOutputStream oos2 = new ObjectOutputStream( new FileOutputStream( "out2" ) );
		oos2.writeObject( animals );
		oos2.close();
		ObjectInputStream ois2 = new ObjectInputStream( new FileInputStream( "out2" ) );
		List<Animal> animals2 = (List<Animal>) ois2.readObject();
		System.out.println( animals2 );
	}
}

class House implements Serializable {
	private static final long serialVersionUID = 1L;

}

class Animal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private House house;

	public Animal(String name, House house) {
		this.name = name;
		this.house = house;
	}

	@Override
	public String toString() {
		return name + "[" + super.toString() + "-->" + house + "]";
	}
}
