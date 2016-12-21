package basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 说明该注解作用在方法上,省略该行表示可以用在所有的ElementType上
// 可以定义多个范围:{ElementType.ANNOTATION_TYPE,ElementType.METHOD}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 说明注解保留到JVM运行时
public @interface UseCase {

	// 注解元素有id和description
	// 注解元素可以用所有基本类型、String、Class、enum、Annotation、以及上述类型的数组
	// 注解元素默认值不能是null,所以惯用用法默认值是-1/""
	public int id() default -1;

	public String description() default "默认值";
}
