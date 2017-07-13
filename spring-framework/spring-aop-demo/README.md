参考：[Spring官网文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop "显示")<br/>
参考：[AOP中文翻译](http://shouce.jb51.net/spring/aop.html)<br/>
参考：[AOP ASPECT 执行顺序](其中，AOP的执行顺序章节为：http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop-ataspectj-advice-ordering)<br/>

多个@Aspect执行顺序<br/>
方法有两种：<br/>
1、实现org.springframework.core.Ordered接口，实现它的getOrder()方法<br/>
2、给aspect添加@Order注解，该注解全称为：org.springframework.core.annotation.Order<br/>

@Pointcut通用切入点表达式的例子<br/>
任意公共方法的执行：
~~~
execution（public * *（..））
~~~
任何一个名字以“set”开始的方法的执行：
~~~
execution（* set*（..））
~~~
AccountService接口定义的任意方法的执行：
~~~
execution（* com.xyz.service.AccountService.*（..））
~~~
在service包中定义的任意方法的执行：
~~~
execution（* com.xyz.service.*.*（..））
~~~
在service包或其子包中定义的任意方法的执行：
~~~
execution（* com.xyz.service..*.*（..））
~~~
在service包中的任意连接点（在Spring AOP中只是方法执行）：
~~~
within（com.xyz.service.*）
~~~
在service包或其子包中的任意连接点（在Spring AOP中只是方法执行）：
~~~
within（com.xyz.service..*）
~~~
实现了AccountService接口的代理对象的任意连接点 （在Spring AOP中只是方法执行）：
'this'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得代理对象在通知体内可用。
~~~
this（com.xyz.service.AccountService）
~~~
'target'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得目标对象在通知体内可用。
~~~
target（com.xyz.service.AccountService）
~~~
任何一个只接受一个参数，并且运行时所传入的参数是Serializable 接口的连接点（在Spring AOP中只是方法执行）
~~~
args（java.io.Serializable）
~~~
'@target'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得注解对象在通知体内可用。
任何一个目标对象声明的类型有一个 @Transactional 注解的连接点 （在Spring AOP中只是方法执行）：
~~~
@target（org.springframework.transaction.annotation.Transactional）
~~~
'@within'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得注解对象在通知体内可用。
任何一个执行的方法有一个 @Transactional 注解的连接点 （在Spring AOP中只是方法执行）
~~~
@within（org.springframework.transaction.annotation.Transactional）
~~~
'@annotation'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得注解对象在通知体内可用。
~~~
@annotation（org.springframework.transaction.annotation.Transactional）
~~~
任何一个只接受一个参数，并且运行时所传入的参数类型具有@Classified 注解的连接点（在Spring AOP中只是方法执行）
'@args'在绑定表单中更加常用：- 请参见后面的通知一节中了解如何使得注解对象在通知体内可用。
~~~
@args（com.xyz.security.Classified）
~~~
任何一个在名为'tradeService'的Spring bean之上的连接点 （在Spring AOP中只是方法执行）：
~~~
bean（tradeService）
~~~
任何一个在名字匹配通配符表达式'*Service'的Spring bean之上的连接点 （在Spring AOP中只是方法执行）：
~~~
bean（*Service）
~~~