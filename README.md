# Spring IOC
Spring IOC 学习

## 1. SpringIOC加载配置文件

-------------------

### 1.2相对路径加载

>`BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");`
-------------------
### 1.3绝对路径加载

>`BeanFactory factory = new FileSystemXmlApplicationContext("C:\\JavaTest\\...");`
-----------------------------
### 1.4多个配置文件加载(相对路径)

>方法一：用 ClassPathXmlApplicationContext() 传入多个参数
> 
>`BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml", "beans.xml, ...");`

> 方法二：新建一个总配置文件，直接导入总配置文件
> 
> 总配置文件：
> 
>```xml  
>  <import resource="spring.xml"/>  
>  <import resource="...."/>
> ```
> 导入：
> 
> `BeanFactory factory = new ClassPathXmlApplicationContext("service.xml");`
----------------------------------------
----------------------------------------
## 2. Spring IOC容器Bean对象实例化 

### 2.1 构造器实例化

​	如果使用默认构造器创建，**空构造方法必须存在**（一般使用此方法）

---------------------------------
### 2.2 静态工厂实例化  

——可以在实例化对象时添加额外操作  
——**spring通过反射调用静态工厂的静态方法，并将返回值作为Bean实例**  
——**Bean实例由用户提供的静态工厂提供**

>定义一个静态工厂类：
> ```java
> public static AccountService creatService(){
>   //添加额外操作
>   ...
> 
>   //返回实例化好的对象
>   return new AccountService();
> }
> ```
> 配置文件：  
> `<bean id="accountService" class="com.Xie.factory.StaticFactory" factory-method="creatService"/>`
--------------------------
### 2.3 实例化工厂实例化  

—— 工厂方法为非静态方法  
—— 需要配置工厂bean，并在业务bean中配置factory-bean，factory-method属性

>定义一个实例化工厂类：
> ```java
> public AccountService creatService(){
>        return new AccountService();
>    }
>```
> 配置文件：
> ```xml
>  <bean id="inatanceFactory" class="com.Xie.factory.InstanceFactory"/>
>  <bean id="accountService" factory-bean="inatanceFactory" factory-method="creatService"/>
> ```
---------------------------
### 2.4 三种方式比较  

方式一：**通过bean的缺省值构造函数创建**，当各个bean的业务逻辑相互比较独立或外界关联
较少的时候使用。  

方式二：利用静态factory方法创建，可以统一管理各个bean的创建，如各个bean在创建前需要
相同的初始化处理，则可用这个方法进行统一处理等。  

方式三：利用实例化factory方法创建，即将factory方法也作为了业务bean来控制。第一，可用于集成
其他框架的bean创建管理方法。第二，能够使bean和factory的角色互换  

&emsp; **开发中项目一般使用一种方式实例化bean，项目开发基本采用第一种方式，交给spring托管，使用时
直接拿来使用即可。**

## 3.Spring IOC注入

-------

### 3.1 手动注入

--------------

#### 3.1.1 set方法注入  

——属性字段需要提供set方法  
——四种方式，推荐使用set方法注入

>创建两个bean对象：  
>&emsp;TypeDao:  
>
>```java
>public class TypeDao {
>public void test(){
>   System.out.println("TypeDao...");
>}
>}
>```
>&emsp;TypeService:(给typeDao设置set方法)
>```java
>public class TypeService {
>//bean对象
>private TypeDao typeDao;
>
>public void setTypeDao(TypeDao typeDao) {
>   this.typeDao = typeDao;
>}
>
>public void test(){
>   System.out.println("TypeService...");
>   typeDao.test();
>   System.out.println("Host:" + host + ",Port:" + port);
>}
>}
>```
>&emsp; 配置文件：
>```xml
><bean id="typeDao" class="com.Xie.dao.TypeDao" />
><bean id="typeService" class="com.Xie.service.TypeService" >
>   <property name="typeDao" ref="typeDao"/>
></bean>
>```
>&emsp;也可以注入String、int、list等基本类型，只需要提供set方法并添加配置文件即可。
---------------------------------------
#### 3.1.2 构造器注入  

——提供带参构造器  

> bean对象：
> ```java
>     private TypeDao typeDao;
> 
>     public TypeService(TypeDao typeDao) {
>           this.typeDao = typeDao;
>     }
> 
>     public void test(){
>           System.out.println("TypeService...");
>           typeDao.test();
>     }
>```
> 配置文件：
> ```xml
> <bean id="typeDao" class="com.Xie.dao.TypeDao" />
> <bean name="typeService" class="com.Xie.service.TypeService">
>        <constructor-arg name="typeDao" ref="typeDao"/>
>    </bean>
>```
> <u>**注：如果两个bean对象相互注入会出现循环依赖问题，此时要使用set方法注入**</u>
----------------------------------------------
#### 3.1.3 静态工厂注入

​		使用set方法注入，bean对象由静态工厂实例化    

------------------------------------------------
#### 3.1.4 实例化工厂注入

​		使用set方法注入，bean对象由实例化工厂实例化

-----------------------------------------------------
#### 3.1.5 注入方式选择

​		开发项目中set方法注入首选

---------------------
#### 3.1.6 在配置文件中引入p名称空间

> 在xml文件开头加入：  
> `xmlns:p="http://www.springframework.org/schema/p"`  
> bean标签改为：  
> `<bean id="xxx" class="xxx" p:xxx-ref="xxx"/>`

------------------------------------

### 3.2 自动注入

------------------------------

#### 3.2.1 准备环境

1.修改配置文件：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">
```

 2.开启自动化注入：

```xml
<!--开启自动化注入（注入）-->
<context:annotation-config/>

<!-- 配置bean对象 -->
<bean id="userService" class="com.Xie.service.UserService"></bean>
<bean id="userDao" class="com.Xie.dao.UserDao"></bean>
```

3.给注入的bean添加注解

-----------------------------------

#### 3.2.2 @Resource注解

- 注解默认通过属性字段名称查找对应的bean对象（属性字段名称与bean标签id属性值一致）
- 如果属性字段不一样，则会通过类型（Class）查找
- 属性字段可以提供set方法，也可以不提供
- 注解可以声明在属性字段上 或set方法级别
- 可以设置注解的name属性，name属性值要与bean标签的id属性值一致（如果设置了name属性，则需要使用name属性查找bean对象）
- 当注入接口时，如果接口只有一个实现类，则正常实例化，如果接口有多个实现类，则需要使用name设置属性指定需要被实例化的bean对象

#### 3.2.3 @Autowired注解

+ 默认通过类型（Class类型）查找bean对象，与属性字段的名称无关

+ 属性可以提供set方法，也可以不提供set方法

+ 注解可以声明在属性级别或set方法级别

+ 可以添加@Qualifier结合使用，通过value属性值查找bean对象（value属性值必须要设置，且值要与bean标签的id属性值对应）

  ```java
  @Autowired
  @Qualifier(value = "...")//value与bean标签的id属性值对应
  private AccountDao accountDao;
  ...
  ```

