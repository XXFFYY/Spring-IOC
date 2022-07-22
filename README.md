# spring_study
spring05 学习

## 2022/7/7
1. SpringIOC加载配置文件
+ 相对路径加载
>BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
+ 绝对路径加载
>BeanFactory factory = new FileSystemXmlApplicationContext("C:\\JavaTest\\...");
+ 多个配置文件加载(相对路径)
>//方法一：用 ClassPathXmlApplicationContext() 传入多个参数
> 
> BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml", "beans.xml, ...");

> //方法二：新建一个总配置文件，直接导入总配置文件
> //总配置文件：
> 
>  import resource="spring.xml"
> 
>  import resource="...."
> 
> //导入：
> 
> BeanFactory factory = new ClassPathXmlApplicationContext("service.xml");
    

