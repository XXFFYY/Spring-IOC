# spring_study
spring05 学习

## 2022/7/7 
### 1. SpringIOC加载配置文件
+ **相对路径加载**
>`BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");`
-------------------
+ **绝对路径加载**
>`BeanFactory factory = new FileSystemXmlApplicationContext("C:\\JavaTest\\...");`
-----------------------------
+ **多个配置文件加载(相对路径)**
>方法一：用 ClassPathXmlApplicationContext() 传入多个参数
> 
>`BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml", "beans.xml, ...");`

> 方法二：新建一个总配置文件，直接导入总配置文件
> 
> 总配置文件：
> 
>```  
>  <import resource="spring.xml"/>  
>  <import resource="...."/>
> ```
> 导入：
> 
> `BeanFactory factory = new ClassPathXmlApplicationContext("service.xml");`
----------------------------------------
----------------------------------------
### 2. Spring IOC容器Bean对象实例化 
+ 构造器实例化:如果使用默认构造器创建，**空构造方法必须存在**（一般使用此方法）
---------------------------------
  + 静态工厂实例化：  
——可以在实例化对象时添加额外操作  
——**spring通过反射调用静态工厂的静态方法，并将返回值作为Bean实例**  
——**Bean实例由用户提供的静态工厂提供**

>定义一个静态工厂类：
> ```
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
+ 实例化工厂实例化：  
—— 工厂方法为非静态方法  
—— 需要配置工厂bean，并在业务bean中配置factory-bean，factory-method属性
>定义一个实例化工厂类：
> ```
> public AccountService creatService(){
>        return new AccountService();
>    }
>```
> 配置文件：
> ```
>  <bean id="inatanceFactory" class="com.Xie.factory.InstanceFactory"/>
>  <bean id="accountService" factory-bean="inatanceFactory" factory-method="creatService"/>
> ```
---------------------------
+ **三种方式比较：**  

方式一：**通过bean的缺省值构造函数创建**，当各个bean的业务逻辑相互比较独立或外界关联
较少的时候使用。  

方式二：利用静态factory方法创建，可以统一管理各个bean的创建，如各个bean在创建前需要
相同的初始化处理，则可用这个方法进行统一处理等。  

方式三：利用实例化factory方法创建，即将factory方法也作为了业务bean来控制。第一，可用于集成
其他框架的bean创建管理方法。第二，能够使bean和factory的角色互换  

&emsp; **开发中项目一般使用一种方式实例化bean，项目开发基本采用第一种方式，交给spring托管，使用时
直接拿来使用即可。**


    

