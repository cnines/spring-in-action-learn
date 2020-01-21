[TOC]

# Spring 之旅

## Spring 的生命周期

1. Spring 对 bean 进行实例化
2. Spring 将值和 bean 的引用注入到 bean 对应的属性中
3. 如果 bean 实现了 BeanNameAware 接口，Spring 将 bean 的 ID 传递给 setBeanName() 方法
4. 如果 bean 实现了 BeanFactoryAware 接口，Spring 将调用 setBeanFactory() 方法，将 BeanFactory 容器实例传入
5. 如果 bean 实现了 ApplicationContextAware 接口，Spring 将调用 setApplicationContext() 方法，将 bean 所在的应用上下文的引用传入进来
6. 如果 bean 实现了 BeanPostProcessor 接口，Spring 将调用它们的 postProcessBeforeInitialization() 方法
7. 如果 bean 实现了 InitializingBean 接口，Spring 将调用它们的 afterPropertiesSet() 方法，类似的，如果 bean 使用 init-method 声明了初始化方法，该方法也会被调用
8. 如果 bean 实现了 BeanPostProcessor 接口，Spring 将调用它们的 postProcessAfterInitialization() 方法
9. 此时，bean 已经准备就绪了，可以被应用程序使用了，它们将一直驻留在应用的上下文中，直到该应用上下文被销毁
10. 如果 bean 实现了 DisposableBean 接口，Spring 将调用它的 destroy() 接口方法。同样，如果 bean 使用 destroy-method 声明了销毁方法，该方法也会被调用


# 装配 Bean

## Spring 配置的可选方案

1. 在 XML 中进行显式配置
2. 在 Java 中进行显式配置
3. 隐式的 bean 发现机制和自动装配

尽可能地使用自动配置的机制，显式配置越少越好

## 自动化装配Bean

### Spring 从两个角度来实现自动化装配：

- 组件扫描(component scanning)：Spring 会自动发现应用上下文中所创建的 bean
- 自动装配(autowiring)：Spring 自动满足 bean 之间的依赖

## 为组件扫描的 bean 命名

- 默认为 @Component 注解标注的类的**类名首字母小写**

### 设置方法

将想要设置的 ID 作为值传递给 @Component 注解

```java
@Component("xxx")
```

## 设置组件扫描的基础包

- 默认为 @ComponentScan 注解标注的类所在包

### 设置方法

- 将包的名称传递给 @ComponentScan 注解的 value 属性
- 如果想要更清晰的表明设置的是基础包，可以将包的名称传递给 @ComponentScan 注解的 basePackages属性(复数形式，可以设置多个基础包)
- 也可以使用 @ComponentScan 的 basePackageClasses 属性，不再使用 String 类型的名称来指定包，为 basePackageClasses 属性所设置的数组包含了类，这些类所在的包会作为组件扫描的基础包

## 通过为 bean 添加 @ Autowired 注解实现自动装配

@Autowired 注解注释：

```
Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities. 

---------------------
Autowired Constructors
---------------------
Only one constructor of any given bean class may declare this annotation with the required() attribute set to true, indicating the constructor to autowire when used as a Spring bean. Furthermore, if the required attribute is set to true, only a single constructor may be annotated with @Autowired. If multiple non-required constructors declare the annotation, they will be considered as candidates for autowiring. The constructor with the greatest number of dependencies that can be satisfied by matching beans in the Spring container will be chosen. If none of the candidates can be satisfied, then a primary/default constructor (if present) will be used. If a class only declares a single constructor to begin with, it will always be used, even if not annotated. An annotated constructor does not have to be public.

---------------------
Autowired Fields
---------------------
Fields are injected right after construction of a bean, before any config methods are invoked. Such a config field does not have to be public.

---------------------
Autowired Methods
---------------------
Config methods may have an arbitrary name and any number of arguments; each of those arguments will be autowired with a matching bean in the Spring container. Bean property setter methods are effectively just a special case of such a general config method. Such config methods do not have to be public.

---------------------
Autowired Parameters
---------------------
Although @Autowired can technically be declared on individual method or constructor parameters since Spring Framework 5.0, most parts of the framework ignore such declarations. The only part of the core Spring Framework that actively supports autowired parameters is the JUnit Jupiter support in the spring-test module (see the TestContext framework  reference documentation for details).

---------------------
Multiple Arguments and 'required' Semantics
---------------------
In the case of a multi-arg constructor or method, the required() attribute is applicable to all arguments. Individual parameters may be declared as Java-8 style Optional or, as of Spring Framework 5.0, also as @Nullable or a not-null parameter type in Kotlin, overriding the base 'required' semantics.

---------------------
Autowiring Arrays, Collections, and Maps
---------------------
In case of an array, Collection, or Map dependency type, the container autowires all beans matching the declared value type. For such purposes, the map keys must be declared as type String which will be resolved to the corresponding bean names. Such a container-provided collection will be ordered, taking into account Ordered and @Order values of the target components, otherwise following their registration order in the container. Alternatively, a single matching target bean may also be a generally typed Collection or Map itself, getting injected as such.

---------------------
Not supported in BeanPostProcessor or BeanFactoryPostProcessor
---------------------
Note that actual injection is performed through a BeanPostProcessor which in turn means that you cannot use @Autowired to inject references into BeanPostProcessor or BeanFactoryPostProcessor types. Please consult the javadoc for the AutowiredAnnotationBeanPostProcessor class (which, by default, checks for the presence of this annotation).
```

- 如果没有匹配的 bean，那么在上下文创建的时候，Spring 会抛一个异常。可以通过将 @Autowired 注解的 required 属性设置为 false 来避免异常的出现

## 通过 Java 代码装配 bean

尽管很多场景下通过组件扫描和自动配置实现 Spring 的自动化配置是更为推荐的方式，但有时候自动化配置的方案行不通，因此需要明确配置 Spring。比如说，你想要将第三方库中的组件装配到自己的应用中，在这种情况下，是没有办法在它的类上添加 @Component 和 @Autowired 注解的，因此就不能使用自动化装配的方案了。所以我们必须学会显式装配的方式，在进行显式装配的时候，JavaConfig 是更好的方案，因为它更为强大、类型安全并且对重构友好。

### 使用 @Configuration 注解声明配置类

```java
@Configuration
public class CDPlayerConfig {
}
```

@Configuration 注解表明这个类是一个配置类，该类应该包含在 Spring 应用上下文中如何创建 bean 的细节。

### 使用 @Bean 注解声明简单的 bean

```java
@Bean
public CompactDisc sgtPeppers() {
    return new SgtPeppers();
}
```

@Bean 注解会告诉 Spring 这个方法会返回一个对象，该对象要注册为 Spring 应用上下文中的 bean。方法体中包含了最终产生 bean 实例的逻辑。

默认情况下，bean 的 ID 与带有 @Bean 注解的方法名是一样的。

如果想要设置成一个不同的名字的话，可以通过 name 属性指定一个不同的名字。

```java
@Bean(name="lonelyHeartsClubBand")
public CompactDisc sgtPeppers() {
    return new SgtPeppers();
}
```

### 借助 JavaConfig 实现注入

```java
@Bean
public CompactDisc sgtPeppers() {
    return new SgtPeppers();
}

@Bean
public MediaPlayer cdPlayer() {
    return new CDPlayer(sgtPeppers());
}
```

cdPlayer() 这个方法体与 sgtPeppers() 稍微有些区别。没有使用默认的构造器构建实例，而是调用了需要传入 CompactDisc 对象的构造器来创建 CDPlayer 实例。

看起来 CompactDisc 是通过调用 sgtPeppers() 得到的，但情况并非完全如此。因为 sgtPeppers() 方法上添加了 @Bean 注解，Spring 将会拦截所有对它的调用，并确保直接返回该方法所创建的 bean，而不是每次都对其进行实际的调用。

```java
@Bean
public CompactDisc sgtPeppers() {
    return new SgtPeppers();
}

@Bean
public MediaPlayer cdPlayer() {
    return new CDPlayer(sgtPeppers());
}

@Bean
public MediaPlayer anotherCdPlayer() {
    return new CDPlayer(sgtPeppers());
}
```

如果是调用的普通的方法，那么每个 CDPlayer 实例都会有一个自己特有的 SgtPeppers 实例。但在 Spring 中，默认情况下 bean 都是单例的，Spring 会拦截对 sgtPeppers() 的调用并确保返回的是 Spring 所创建的 bean，因此两个 CDPlayer bean 会得到相同的 SgtPeppers 实例。

另一种简单的方式：

```java
@Bean
public MediaPlayer anotherCdPlayer(CompactDisc disc) {
    return new CDPlayer(disc);
}
```

这个方式是更加通用的，当 Spring 调用 cdPlayer() 创建 CDPlayer bean 的时候，它会自动装配一个 CompactDisc 到配置方法中，然后方法就可以按照合适的方式去使用。这种方式不用明确引用 CompactDisc 的 @Bean 方法，不要求将 CompactDisc 声明到同一个配置类中，甚至没有要求它必须在 JavaConfig 中声明，可以通过自动扫描功能自动发现或者 XML 来进行配置。

## 通过 XML 装配 bean（非重要）

### 借助构造器初始化 bean

- 使用 `<constructor-arg>` 元素

- 使用 Spring 3.0 所引入的 c- 命名空间

#### 构造器注入 bean 引用

在 XML 中声明 CDPlayer 并通过 ID 引用 SgtPeppers：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer">
	<constructor-arg ref="compactDisc"/>
</bean>
```

当 Spring 遇到这个 <bean> 元素的时候，它会创建一个 CDPlayer 实例。<constructor-arg> 元素会告知 Spring 要将一个 ID 为 compactDisc 的 bean 引用传递到 CDPlayer 的构造器中。

----

你可以使用 Spring 的 c- 命名空间（Spring 3.0 中引入），这是在 XML 中更为简洁地描述构造器参数的方式。要使用它，必须要在 XML 的顶部声明其模式，如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
     xmlns:c="http://www.springframework.org/schema/c"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

声明之后就可以使用它来声明构造器参数了，如下：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer"
      c:cd-ref="compactDisc"/>
```

属性名以 `c:` 开头，也就是命名空间的前缀，接下来就是要装配的构造器参数名，接下来是 `-ref`，这是命名的约定，会告诉 Spring，这是正在装配一个 bean 的引用，这个 bean 的名字叫 compactDisc，而不是字面量 `compactDisc`。

也可以使用参数在整个参数列表中的位置信息来替代：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer"
      c:_0-ref="compactDisc"/>
```

将参数的名称替换成了 `0`，也就是参数的索引，因为在 XML 中不允许数字作为属性的第一个字符，因此必须要添加一个下划线作为前缀。

如果只有一个构造器参数，还可以这样使用：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer"
      c:_-ref="compactDisc"/>
```

省略了 `0`。

#### 将字面量注入到构造器中

如果一个类的构造器有两个字段组成，如：

```java
public class BlankDisc implements CompactDisc{
    private String title;
    private String artist;
    
    public BlankDisc(String title, String artist){
        this.title = title;
        this.artist = artist;
    }
    
    public void play(){
        System.out.println("Playing " + title + " by " + artist);
    }
}
```

我们可以使用以下方式进行装配：

```xml
<bean id="compactDisc" class="soundsystem.BlankDisc">
	<constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band"/>
    <constructor-arg value="The Beatles"/>
</bean>
```

使用了 <constructor-arg> 元素的 value 属性，通过该属性表明给定的值要以字面量的形式注入到构造器中。

---

使用 c- 命名空间：

```xml
<bean id="compactDisc" 
      class="soundsystem.BlankDisc"
      c:_title="Sgt. Pepper's Lonely Hearts Club Band"
      c:_artist="The Beatles"/>
```

装配字面量和装配引用的区别在于属性名中去掉了 `-ref` 后缀。

我们还可以使用参数索引装配相同的字面量值，如下：

```xml
<bean id="compactDisc" 
      class="soundsystem.BlankDisc"
      c:_0="Sgt. Pepper's Lonely Hearts Club Band"
      c:_1="The Beatles"/>
```

如果只有一个构造器参数同样可以省略数字。

#### 装配集合

装配字面量集合：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer">
	<constructor-arg>
        <list>
        	<value>aaa</value>
            <value>bbb</value>
            <value>bbb</value>
        </list>
    </constructor-arg>
</bean>
```

装配 bean 引用集合：

```xml
<bean id="cdPlayer" class="soundsystem.CDPlayer">
	<constructor-arg>
        <list>
        	<ref bean="aaa"/>
            <ref bean="bbb"/>
            <ref bean="ccc"/>
        </list>
    </constructor-arg>
</bean>
```

当构造器参数的类型是 `java.util.List` 时，使用 <list> 元素；当构造器参数的类型是 `java.util.Set` 时，使用 <set> 元素。

在装配集合方面，c- 命名空间的属性无法实现。

### 设置属性

p- 命名空间

util- 命名控件来简化配置

## 导入和混合配置

### 在 JavaConfig 中引用配置

在一个配置类上想要引入其他的配置类，可以使用 @Import 注解；

在一个配置类上想要引入 XML 的配置，可以使用 @ImportResource 注解。

### 在 XML 中引用配置

在一个 XML 中想要引入其他的 XML 配置，可以使用 <import> 元素，resource 属性指定类路径下文件位置；

在一个 XML 中想要引入其他的 Java 配置，使用 <bean> 元素，class 属性指定 Java 配置类的全限定名。

### 最佳实践

无论使用 JavaConfig 还是使用 XML 进行配置，通常都会创建一个根配置（root configuration），将两个或者更多的配置类和 / 或 XML 文件组合起来。

## 通用规则

对强依赖使用构造器注入，对可选依赖使用属性注入。

## 小结

1.  Spring 框架的核心是 Spring 容器。容器负责管理应用中组件的声明周期，它会创建组件并保证依赖关系得到满足，这样组件才能完成预定的任务。
2. Spring 中配置 bean 的三种主要方式：自动化配置、基于 Java 的显式配置、基于 XML 的显式配置。
3. 尽可能使用自动化装配，以避免显式装配所带来的维护成本。但是如果确实需要使用显式装配 Spring 的话，应该优先选择基于 Java 的配置，比基于 XML 的配置更加强大、类型安全并且容易重构。

# 高级装配

## 环境与 profile

### 简述

面临的问题：不同环境中某些 bean 可能会有所不同，我们需要用一种方法来配置这些 bean，使其在每种环境中都会选择最为合适的配置。

解决方案：在一个单独的配置类（或者 XML 文件）中配置每个 bean，然后在 ***构建***阶段（可能使用 Maven 的 profiles）确定要将哪一个配置编译到可部署的应用中，这种方式的问题在于要为每种配置重新构建应用。

**Spring 所提供的解决方案并不需要重新构建。**

### 配置 profile bean

根据环境来决定该创建哪些 bean 和不创建哪些 bean，不过 Spring ***并不是在构建时*** 做出这样的决策，而是等到 ***运行时*** 再进行确定。这样的好处是同一个部署单元（可能是 WAR 文件或者 JAR 文件）能够适用于所有的环境，不需要进行重新构建。

#### 在配置类中配置 profile bean

使用 @Profile 注解来标注即可。在 Spring 3.1 时，只能在类级别上使用 @profile 注解，Spring 3.2 开始，可以在方法级别上使用 @Profile 注解，与 @Bean 注解一同使用。在方法级别上使用 @Profile 注解的好处就是可以将两个不同环境的 bean 的声明放在同一个配置类中。

尽管多个环境的 bean 声明在一个类中，但是只有当规定的 profile 激活时，相应的 bean 才能创建，但是可能会有其他的 bean 并没有使用 @Profile 注解进行 profile 声明，那么这些没有指定 profile 的 bean 始终都会被创建。

#### 在 XML 中配置 profile

通过 <beans> 元素的 profile 属性在 XML 中配置 profile。

### 激活 profile

Spring 根据 `spring.profiles.active` 和 `spring.profiles.default` 两个独立的属性来确定哪一个 profile 处于激活状态。如果设置了  `spring.profiles.active`属性，它的值就会用来确定哪个 profile 是激活的；如果没有设置 `spring.profiles.active` 属性，Spring 会去寻找 `spring.profiles.default` 属性；如果两个属性都没有设置，那就没有激活的 profile，只会创建没有定义 profile 的 bean。

可以使用以下方式设置这两个属性：

- 作为 `DispatcherServlet` 的初始化参数
- 作为 Web 应用的上下文参数
- 作为 JNDI 条目
- 作为环境变量
- 作为 JVM 的系统属性
- 在集成测试类上，使用 @ActiveProfiles 注解进行设置

## 条件化的 bean

### 场景

希望一个或者多个 bean 只有在应用的类路径下包含了特定的库才能创建，或者希望某个 bean 只有当另外某个特定的 bean 声明了之后才会创建，或者希望某个 bean 只有某个特定的环境变量设置之后才会被创建。

### Spring 的解决方案

Spring 4 引入了 @Conditional 注解，它可以用到带有 @Bean 注解的方法上，如果给定的条件结果为 true，就会创建这个 bean，否则这个 bean 就会被忽略。

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
    // 设置给 @Conditional 的类可以是任意实现了 Condition 接口的类型
	Class<? extends Condition>[] value();
}
// @Conditional 中给定了一个 class，它指明了条件，@Conditional 将会通过 Condition 接口进行条件对比
public interface Condition {
    // 如果 matches() 方法返回 true，就会创建带有 @Conditional 注解的 bean，否则忽略这个 bean
	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
}
```

比如，有一个名为 MagicBean 的类，希望只有设置了 magic 环境属性的时候才会实例化：

```java
@Bean
@Conditional(MagicExistsCondition.class)
public MagicBean magicBean(){
    return new MagicBean();
}
public MagicExistsCondition implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata){
        Environment env = context.getEnvironment();
        return env.containsProperty("magic");
    }
}
```

ConditionContext 是一个接口：

```java
public interface ConditionContext {

	/**
	 * 借助 getRegistry() 返回的 BeanDefinitionRegistry 检查 bean 的定义
	 */
	BeanDefinitionRegistry getRegistry();

	/**
	 * 借助 getBeanFactory() 返回的 ConfigurableListableBeanFactory 检查 bean 是否存在，甚至查看 bean 的属性
	 */
	ConfigurableListableBeanFactory getBeanFactory();

	/**
	 * 借助 getEnvironment() 返回的 Environment 检查环境变量是否存在以及值是什么
	 */
	Environment getEnvironment();

	/**
	 * 读取并查看 getResourceLoader() 返回的 ResourceLoader 所加载的资源
	 */
	ResourceLoader getResourceLoader();

	/**
	 * 借助 getClassLoader() 返回的 ClassLoader 加载并检查类是否存在
	 */
	ClassLoader getClassLoader();
}

```

AnnotatedTypeMetadata 接口能让我们检查带有 @Bean 注解的方法上其他注解的信息。

```java
public interface AnnotatedTypeMetadata {

	/**
	 * 判断带有 @Bean 注解的方法是不是还有其他特定的注解
	 */
	boolean isAnnotated(String annotationName);

	/**
	 * 检查 @Bean 注解的方法上其他注解的属性
	 */
    /**
	 * Retrieve the attributes of the annotation of the given type, if any (i.e. if
	 * defined on the underlying element, as direct annotation or meta-annotation),
	 * also taking attribute overrides on composed annotations into account.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @return a Map of attributes, with the attribute name as key (e.g. "value")
	 * and the defined attribute value as Map value. This return value will be
	 * {@code null} if no matching annotation is defined.
	 */
	@Nullable
	Map<String, Object> getAnnotationAttributes(String annotationName);

	/**
	 * Retrieve the attributes of the annotation of the given type, if any (i.e. if
	 * defined on the underlying element, as direct annotation or meta-annotation),
	 * also taking attribute overrides on composed annotations into account.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @param classValuesAsString whether to convert class references to String
	 * class names for exposure as values in the returned Map, instead of Class
	 * references which might potentially have to be loaded first
	 * @return a Map of attributes, with the attribute name as key (e.g. "value")
	 * and the defined attribute value as Map value. This return value will be
	 * {@code null} if no matching annotation is defined.
	 */
	@Nullable
	Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString);

	/**
	 * Retrieve all attributes of all annotations of the given type, if any (i.e. if
	 * defined on the underlying element, as direct annotation or meta-annotation).
	 * Note that this variant does <i>not</i> take attribute overrides into account.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @return a MultiMap of attributes, with the attribute name as key (e.g. "value")
	 * and a list of the defined attribute values as Map value. This return value will
	 * be {@code null} if no matching annotation is defined.
	 * @see #getAllAnnotationAttributes(String, boolean)
	 */
	@Nullable
	MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationName);

	/**
	 * Retrieve all attributes of all annotations of the given type, if any (i.e. if
	 * defined on the underlying element, as direct annotation or meta-annotation).
	 * Note that this variant does <i>not</i> take attribute overrides into account.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @param classValuesAsString  whether to convert class references to String
	 * @return a MultiMap of attributes, with the attribute name as key (e.g. "value")
	 * and a list of the defined attribute values as Map value. This return value will
	 * be {@code null} if no matching annotation is defined.
	 * @see #getAllAnnotationAttributes(String)
	 */
	@Nullable
	MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationName, boolean classValuesAsString);
}

```

Spring 4 之后，@Profile 注解基于 @Conditional 和 Condition 接口进行的重构。

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ProfileCondition.class)
public @interface Profile {
	/**
	 * The set of profiles for which the annotated component should be registered.
	 */
	String[] value();

}

class ProfileCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
		if (attrs != null) {
			for (Object value : attrs.get("value")) {
				if (context.getEnvironment().acceptsProfiles((String[]) value)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
```

ProfileCondition 通过 AnnotatedTypeMetadata 得到了用于 @Profile 注解的所有属性，借助该信息，它会明确地检查 value 属性，该属性包含了 bean 的 profile 名称，再根据通过ConditionContext 得到的 Environment 来检查（借助 acceptsProfiles() 方法）该 profile 是否处于激活状态。

## 处理自动装配的歧义性

仅有一个 bean 匹配所需的结果时，自动装配才是有效的，如果不只有一个 bean 能够匹配结果时，这种歧义性会阻碍 Spring 自动装配属性、构造器参数或方法参数。如下情况：

```java
@Autowired
public void setDessert(Dessert dessert){
    this.dessert = dessert;
}

@Component
public class Cake implements Dessert {...}

@Component
public class Cookies implements Dessert {...}

@Component
public class IceCream implements Dessert {...}
```

因为这三个实现均使用了 @Component 注解，在组件扫描的时候，能够发现他们并将其创建为 Spring 应用上下文里面的 bean。当 Spring 试图自动装配 setDessert() 中的 Dessert 参数时，它并没有唯一、无歧义的可选值，此时 Spring 就会抛出 NoUniqueBeanDefinitionException 异常。

Spring 提供了多种可选方案来解决这样的问题，可以将可选 bean 中的某一个设为首选（primary）的 bean，或者使用限定符（qualifier）来帮助 Spring 将可选的 bean 的范围缩小到只有一个 bean。

### 标示首选的 bean

自动装配时，@Primary 能够与 @Component 组合用在组件扫描的 bean 上；Java 配置显式声明时，@Primary 可以与 @Bean 组合用在 bean 的声明上；使用 XML 配置 bean 时，可以使用 <bean> 元素的 primary 属性指定首选的 bean。

如果不只一个 bean 被设置成了首选 bean，那么实际上就没有首选 bean 了，Spring 同样无法从多个可选的 bean 中做出选择。

因此，在解决歧义性问题上，限定符是一种更为强大的机制。

### 限定自动装配的 bean

Spring 的限定符能够在所有可选的 bean 上进行缩小范围的操作，最终能够达到只有一个 bean 满足所规定的限制条件的目的。如果将所有的限定符都用上后依然存在歧义性，那么你可以继续使用更多的限定符来缩小选择范围。

@Qualifier 注解是使用限定符的主要方式，它可以与 @Autowired 注解一起使用，在注入的使用指定想要注入进去的是哪个  bean，为 @Qualifier 注解所设置的参数就是想要注入的 bean 的 ID。

更准确来讲，@Qualifier("xxx") 所引用的 bean 要具有 String 类型的 "xxx" 作为限定符，如果没有指定其他的限定符的话，所有的 bean 都会给定一个默认的限定符，这个默认的限定符与 bean 的 ID 相同。

基于默认的 bean 的 ID 作为限定符是非常简单的，但是有可能会有一些问题，如果重构了这个类，将类名换为了另一个，bean 的 ID 和默认的限定符就会发生变化，此时自动装配就会失败。

#### 创建自定义的限定符

我们可以为 bean 设置自己的限定符，而不是依赖于将 bean 的 ID 作为限定符，因此我们需要做的就是在 bean 声明上添加 @Qualifier 注解。

自动装配时，可以与 @Component 注解联合使用来分配限定符：

```java
@Component
@Qualifier("cold")
public class IceCream implements Dessert {...}
```

在使用时，引入相对应的限定符就可以了：

```java
@Autowired
@Qualifier("cold")
public void setDessert(Dessert dessert){
    this.dessert = dessert;
}
```

当通过 Java 配置显式定义 bean 时，@Qualifier 注解可以与 @Bean 注解一起使用：

```java
@Bean
@Qualifier("cold")
public Dessert iceCream(){
    return new IceCream();
}
```

这里使用自定义的限定符的最佳实践是为 bean 选择特征性或者描述性的术语，而不是使用随意的名字。

#### 使用自定义的限定符注解

如果出现以下情况：

```java
@Component
@Qualifier("cold")
public class Popsicle implements Dessert {...}

@Component
@Qualifier("cold")
public class IceCream implements Dessert {...}
```

我们在自动装配 Dessert 的时候，就会再次遇到歧义性的问题，这时我们需要使用更多的限定符来将可选范围限定到只有一个 bean。

可能想到的一个解决方案是在 注入点和 bean 定义的地方同时再添加一个 @Qualifier 注解：

```java
@Component
@Qualifier("cold")
@Qualifier("fruity")
public class Popsicle implements Dessert {...}

@Component
@Qualifier("cold")
@Qualifier("creamy")
public class IceCream implements Dessert {...}
```

但是，在 Java 中不允许在同一个条目上重复出现相同类型的多个注解，编译器会发生错误，Java 8 之后允许出现重复的注解，但是需要这个注解本身在定义时带有 @Repeatable 注解，然而 @Qualifier 的定义如下：

```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
	String value() default "";
}
```

并没有使用 @Repeatable 注解，因此我们不能通过多个 @Qualifier 注解来进行缩小可选 bean 的范围。

此时就需要我们创建自定义的限定符注解：创建一个注解，该注解使用 @Qualifier 注解来进行标注

```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Cold {}
```

这样我们就不再使用 @Qualifier("cold") 这个注解了，而是使用我们自定义的 @Cold 注解来替换。

同理，我们可以使用 @Fruity 来代替 @Qualifier("fruity")，使用 @Creamy 来代替 @Qualifier("creamy")。在声明点和注入点我们就可以使用必要的自定义限定符注解进行注解组合，从而将可选方位缩小到只有一个 bean 满足要求。

这种自定义的限定符注解相对于使用原始的 @Qualifier 并借助 String 类型来指定限定符也更加的类型安全。

## bean 的作用域

### Spring 的多种作用域

- 单例（Singleton）：在整个应用中，只创建 bean 的一个实例。默认情况下都是单例的。
- 原型（Prototype）：每次注入或者通过 Spring 应用上下文获取的时候，都会创建一个新的 bean 实例。
- 会话（Session）：在 Web 应用中，为每个会话创建一个 bean 实例。
- 请求（Request）：在 Web 应用中，为每个请求创建一个 bean 实例。

### 配置 bean 的作用域

#### 使用 @Scope 注解进行 Java 配置

自动装配时，和 @Component 注解搭配使用，如下：

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public calss Notepad {...}
```

手动装配时，和 @Bean 注解搭配使用，如下：

```java
@Bean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public Notepad notepad {
    return new Notepad();
}
```

#### XML 配置

使用 <bean> 元素的 scope 属性来设置作用域，如下：

```xml
<bean id="notepad" class="com.myapp.Notepad" scope="prototype"/>
```

### 使用会话和请求作用域

例如，在典型的电子商务应用中，可能会有一个 bean 代表用户的购物车，如果购物车是单例的话，那么将会导致所有的用户都会向同一个购物车中添加商品，如果购物车是原型的话，那么在应用中某一个地方往购物车中添加商品，在应用的其他地方可能就不可用了，因为在这里注入的是另一个原型作用域的购物车。就购物车 bean 来说，会话作用域更为合适，因为它与给定用户的关联性最大，使用方式如下：

```java
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.INTERFACES)
public ShoppingCart cart(){...}
```

假设我们要将 ShoppingCart bean 注入到单例 StoreService bean 的 Setter 方法中，如下所示：

```java
@Component
public class StoreService{
    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }
}
```

StoreService 是一个单例的 bean，所以在 Spring 应用上下文加载的时候就创建了，此时，Spring 试图将一个 ShoppingCart bean 注入到 setShoppingCart 方法中，但是 ShoppingCart bean 因为是会话作用域的，此时并不存在，只有当某个用户创建了会话的时候，才会出现 ShoppingCart bean 实例。系统中每个用户都会有一个 ShoppingCart bean，我们在注入 ShoppingCart bean 的时候，希望注入的是当前用户对应的那个 ShoppingCart bean。

Spring 并不会将实际的 ShoppingCart bean 注入到 StoreService 中，Spring 会将它注入到 ShoppingCart bean 的代理中，这个代理暴露和 ShoppingCart 相同的方法，当 StoreService 调用 ShoppingCart 的方法时，代理会对其进行懒解析并将调用委托给会话作用域内真正的 ShoppingCart bean。

如配置所示，`proxyMode=ScopedProxyMode.INTERFACES`，proxyMode 属性设置为 ScopedProxyMode.INTERFACES，这表明这个代理要实现 ShoppingCart 接口，并将调用委托给实现bean。但是如果 ShoppingCart 是一个具体的实现类而不是接口的话，Spring 就无法创建基于接口的代理，此时必须使用 CGLib 生成基于类的代理，将 proxyMode 属性设置为 ScopedProxyMode.TARGET_CLASS，来表明以生成目标类扩展的方式创建代理。

同理，请求作用域的 bean 也会遇到同样的问题，也应该以作用域代理的方式进行注入。

### 在 XML 中声明作用域代理

要设置作用域代理，需要 Spring aop 命名空间下的一个新元素 `<aop:scoped-proxy>`，如下：

```xml
<bean id="cart" class="com.myapp.ShoppingCart" scope="session">
	<aop:scoped-proxy />
</bean>
```

默认情况下，会使用 CGLib 创建目标类的代理，但是我们可以设置 proxy-target-class 属性为 false，生成基于接口的代理。

## 运行时值注入

我们一般希望在代码中避免硬编码值，而是想让值在运行时再确定，Spring 提供了两种运行时求值的方式：

- 属性占位符（Property placeholder）
- Spring 表达式语言（SpEL）

### 注入外部的值

在 Spring 中，处理外部的值的最简单的方式是通过 @PropertySource 注解来声明属性源，并通过 Spring 的 Environment 来检索属性，一般是通过 Environment 的 getProperty 方法来实现

#### Environment

属性解析相关方法：

```java
/*
 * getProperty() 方法重载
 */
@Nullable
String getProperty(String key);

String getProperty(String key, String defaultValue);

@Nullable
<T> T getProperty(String key, Class<T> targetType);

<T> T getProperty(String key, Class<T> targetType, T defaultValue);

// 检查属性是否存在
boolean containsProperty(String key);

// 获取属性的值，如果不存在抛出 IllegalStateException 异常
String getRequiredProperty(String key) throws IllegalStateException;

<T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;
```

检查哪些 profile 处于激活状态的方法：

```java
// 返回激活 profile 名称的数组
String[] getActiveProfiles();

// 返回默认 profile 名称的数组
String[] getDefaultProfiles();

// 如果 environment 支持给定 profile 就返回 true
boolean acceptsProfiles(String... profiles);
```

#### 解析属性占位符

解析外部属性能够将值的处理推迟到进行时，关注点在于根据名称解析来自于 Spring Environment 和属性源的属性。

占位符表达式的形式为使用 `${...}` 包装的属性名称。

如果要使用占位符，必须要配置 PropertyPlaceholderConfigurer bean 或者 PropertySourcePlaceholderConfigurer bean。

Spring 3.1 开始，推荐 PropertySourcePlaceholderConfigurer，它能够基于 Spring Environment 及其属性源来解析占位符。

##### 配置方法

Java 配置方法：

```java
// 注意，这里必须使用 static
@Bean
public static PropertySourcePlaceholderConfigurer propertySourcePlaceholderConfigurer(){
    return new PropertySourcePlaceholderConfigurer();
}
```

XML 配置方法：

Spring context 命名空间中的 `<context:property-placeholder>` 元素将会生成这个 bean。

```xml
<context:property-placeholder/>
```

### 使用 Spring 表达式语言进行装配

SpEL 拥有以下特性：

- 使用 bean 的 ID 来引用 bean
- 调用方法和访问对象的属性
- 对值进行算术、关系和逻辑运算
- 正则表达式匹配
- 集合操作

SpEL 表达式是要放到 `#{...}` 中的。

#### 表示字面量

可以用来表示整数、浮点数、String 值以及 Boolean 值。

```java
#{12}
#{3.1415926}
#{'Hello'}
#{false}
// 整数类型可以使用科学计数法方式表示，如98700 可以用 #{9.87E4} 来表示
```

#### 引用 bean、属性和方法

SpEL 可以通过 ID 引用其他的bean，如 `#{sgtPeppers}`来引用 ID 为 sgtPeppers 的 bean。

如果想要引用 sgtPeppers 的 artist 属性，使用 `#{sgtPeppers.artist}` 来引用 sgtPeppers 的 artist 属性。

我们还可以调用 bean 上的方法，使用 `#{artistSelector.selectArtist()}` 来调用 artistSelector bean 的 selectArtist() 方法。

调用方法之后，还可以继续调用返回值的方法，比如，如果上面的 selectArtist() 方法的返回值是 String 类型，就可以使用 `#{artistSelector.selectArtist().toUpperCase()}` 来继续进行调用，但是如果 artistSelector.selectArtist() 返回值是 null 的话，就会出现 NPE ，使用类型安全的运算符`#{artistSelector.selectArtist()?.toUpperCase()}` 可以避免 NPE，如果 artistSelector.selectArtist() 返回值是 null ，就不会调用 toUpperCase() 方法，表达式的值为 null。

#### 在表达式中使用类型

如果要在 SpEL 中访问类作用域的方法和常量的话，要依赖 `T()` 这个关键的运算符。例如：

```
可以使用 #{T(java.lang.Math).PI} 来访问 Math 类的静态常量 PI 的值
可以使用 #{T(java.lang.Math).random()} 来调用 Math 类的静态方法 random() 生成一个 0~1 之间的随机数
可以使用 #{T(java.lang.System).currentTimeMillis()} 来得到当前时间的毫秒数
```

#### SpEL 运算符

| 运算符类型 | 运算符                                                   |
| ---------- | -------------------------------------------------------- |
| 算术运算   | `+`  `-`  `*`  `/`  `%`  `^`                             |
| 比较运算   | `<`  `>`  `==`  `<=`  `>=`  `lt`  `gt`  `eq`  `le`  `ge` |
| 逻辑运算   | `and`  `or`  `not`  `|`                                  |
| 条件运算   | `?:(ternary)`  `?:(Elvis)`                               |
| 正则表达式 | `matches`                                                |

```java
// 在 SpEL 中不能使用双引号，因为 SpEL 表达式是放在双引号里面的
#{2 * T(java.lang.Math).PI * circle.radius}
#{T(java.lang.Math).PI * circle.radius ^ 2}
#{disc.title + ' by ' + disc.artist}
#{counter.total == 200}     #{counter.total eq 200}
#{scoreboard.score > 100 ? 'Winner!' : 'Loser'}   三元运算符ternary
#{disc.title ?: 'Rattle and Hum'}     如果 disc.title 是 null,该 Elvis 表达式的计算结果就是 "Rattle and Hum"
```

#### 计算正则表达式

SpEL 通过 matches 运算符支持表达式中的模式匹配。matches 运算符对 String 类型的文本（作为左边参数）应用正则表达式（作为右边参数）。matches 的运算结果会返回一个 Boolean 类型的值，如果与正则表达式相匹配，则返回 true，否则返回 false。

```
#{admin.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com'}
```

#### 计算集合

引用列表中的一个元素，例如，使用 `#{jukebox.songs[4].title}` 来计算 songs 集合中第五个（基于零开始）元素的 title 属性，这个集合来源于 ID 为 jukebox 的 bean。

jukebox 中随机选歌：`#{jukebox.songs[T(java.lang.Math).random() * jukebox.songs.size()].title}`

获取 String 的第四个字符（也就是 's'）：`#{'This is a test'[3]}`

SpEL 提供了 ***查询运算符***，用来对集合进行过滤，得到集合的子集。

- `.?[]`，用来得到集合中所有的匹配项，例如，使用 `#{jukebox.songs.?[artist eq 'Aerosmith']}` 得到 jukebox 中 artist 属性为 Aerosmith 的所有歌曲。

- `.^[]`，用来在集合中查询第一个匹配项，例如，使用 `#{jukebox.songs.^[artist eq 'Aerosmith']}` 查找列表中第一个 artist 属性为 Aerosmith 的歌曲。

- `.$[]`，用来在集合中查询UI周一个匹配项，例如，使用 `#{jukebox.songs.$[artist eq 'Aerosmith']}` 查找列表中第一个 artist 属性为 Aerosmith 的歌曲。

SpEL 还提供了***投影运算符***（`.![]`），将集合的每个成员中选择特定的属性放到另一个集合中，例如，我们可以使用 `#{jukebox.songs.![title]}` 将 jukebox 中歌曲的 title 属性投影到一个新的 String 类型的集合中。

投影操作可以与其他任意的 SpEL 运算符一起使用。

**我们应该尽可能让表达式保持简洁，降低测试难度。**



