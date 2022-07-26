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

-------------

## 4. Spring IOC扫描器

​		自动扫描bean对象，进行统一管理，简化开发配置，提高开发效率

### 4.1 Spring IOC扫描器的配置

-------------------

1.设置自动化扫描范围

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 设置自动化扫描的范围 -->
    <context:component-scan base-package="com.Xie"/>
```

2.在需要被实例化的JavaBean的类上添加指定的注解(注解声明在类级别) (Bean对象的id属性默认是类的首字母小写)

```java
@Service
public class TypeService {
	...
	}
```

3.注解类型：

​	Dao层：

​			@Repository

​	Service层：

​			@Service

​	Controller层：

​			@Controller

​	任意类：

​			@Component

<u>**注：开发过程中建议按照规则声明注解。**</u>

-----

### 4.2 Spring 模拟用户登录流程

-----

#### 4.2.1 Dao层(查询用户记录)

1.定义JavaBean User.Java

```java
public class User {
    
    /**
    * User用户实体类
    */
    private String userName;//用户名称
    private String userPwd;//用户密码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
```

2.编写Dao层 UserDao.java

```java
@Repository
public class UserDao {

    //定义登陆的账号密码
    private final String USERNAME = "admin";
    private final String USERPWD = "admin";


    /**
     * 通过用户名查询用户对象
     *      如果存在，返回对应的用户对象；如果不存在则返回null
     * @param userName
     * @return
     */
    public User queryUserByUserName(String userName){
        User user = null;
        //判断用户名是否存在
        if(!USERNAME.equals(userName)){
            return null;
        }

        //给user对象赋值
        user = new User();
        user.setUserName(userName);
        user.setUserPwd(USERPWD);

        return user;
    }
}
```

---------------

#### 4.2.2 Service层（业务逻辑处理）

1.定义业务处理返回消息模型	MessageModel.java

```java
public class MessageModel {
    private int resultCode = 1;// 状态码 (1=成功 0=失败)
    private String resultMsg;// 提示信息

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
```

2.编写Service层 UserService.java

```java
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    /**
     * 验证是否登录成功
     *  1.参数的非空校验
     *  2.通过用户名查询用户对象（调用Dao层的查询方法）
     *  3.判断密码是否正确
     * @param uname
     * @param upwd
     * @return
     */
    public MessageModel checkUserLogin(String uname, String upwd){
        //返回消息模型
        MessageModel messageModel = new MessageModel();

        //1.参数的非空校验
        if(StringUtil.isEmpty(uname) || StringUtil.isEmpty(upwd)){
            //用户名和密码不能为空
            messageModel.setResultCode(0);
            messageModel.setResultMsg("用户名和密码不能为空");
            return messageModel;
        }

        //2.通过用户名查询用户对象(调用Dao层的查询方法)
        User user = userDao.queryUserByUserName(uname);

        //判断用户对象是否为空
        if(user == null){
            messageModel.setResultCode(0);
            messageModel.setResultMsg("用户名不存在！");
            return messageModel;
        }

        //3.判断密码是否正确
        if(!upwd.equals(user.getUserPwd())){
            messageModel.setResultCode(0);
            messageModel.setResultMsg("用户密码不正确！");
            return messageModel;
        }

        //登陆成功
        messageModel.setResultMsg("登陆成功！");

        return messageModel;
    }
}
```

---------------------

#### 4.2.3	Controller层（接受请求）

1.编写Controller层	UserController.java

```java
@Controller
public class UserController {
    @Resource
    private UserService userService;

    public MessageModel userLogin(String uname, String upwd){

        MessageModel messageModel = userService.checkUserLogin(uname, upwd);

        return messageModel;
    }
}
```

-----------------

#### 4.2.4	通过Junit测试

```java
public class UserTest {

    @Test
    public void test() {
        //获取Spring的上下文环境
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring.xml");
        //得到实例化的userController对象
        UserController userController = (UserController) beanFactory.getBean("userController");
        //传入参数调用UserController的方法，返回封装类
        MessageModel messageModel = userController.userLogin("admin", "123");
        
        System.out.println("状态码" + messageModel.getResultCode() + ",提示信息:" + messageModel.getResultMsg());
    }
}
```

## 5.Bean对象的作用域与生命周期

### 5.1 Bean的作用域

-------------------

#### 5.1.1 singleton（单例）作用域

​	SpringIOC容器在启动时，会将所有在singleton作用域中的bean对象实例化，并设置到单例缓存池中

+ **lazy-init属性（懒加载）(一般不会选择懒加载)**
  	如果设置为true，表示懒加载，容器启动时，不会实例化bean对象，在程序调用时才会实例化
  	如果设置为false（默认），表示不懒加载，容器启动则实例化bean对象
+ **lazy-init属性设置为false的优点：**
      1.可以提取发现潜在的配置问题
      2.bean对象在启动时就会设置在单例缓存池中，使用时不需要再去实例化bean对象，提高程序运行效率
+ **什么对象适合作为单例对象（交给IOC容器实例化）？**
       无状态的对象（不存在会改变当前对象状态的成员变量）
       适用对象：Controller层，Service层，Dao层

+ **什么是无状态或状态不可改变的对象？**

​			实际上对象状态的变化往往是由于属性值变化而引起的，比如user类姓名属性会有变化，属性姓名的变化一般会引起user对象状态的变化。对我们的程序来说，无状态对象没有实例变量的存在，保证了线程的安全性，service层业务对象即是无状态对象。线程安全的。

-------

#### 5.1.2 prototype作用域

​		通过是scope="prototype"设置bean的类型，每次向Spring容器请求获取Bean都会返回一个全新的Bean，相对与singleton来说就是不缓存Bean，每次都是一个根据Bean定义创建的全新Bean

-----

#### 5.1.3 Web应用中的作用域

​	**1.request作用域**

​		表示每个请求需要容器创建一个全新的Bean。比如提交表单的数据必须是对每次请求新建一个Bean来保持这些表单数据，请求结束释放这些数据

​	**2.session作用域**

​		表示每个会话需要容器创建一个全新的Bean。

​	**3.globalSession作用域**

​		类似于session作用域，其用于Portlet(基于Java的web组件，由Portlet容器管理，并由容器处理请求，生产动态内容)环境的web应用。如果在非Portlet环境将视为session作用域
