参考：[Spring官网文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop "显示")<br/>
参考：[AOP中文翻译](http://shouce.jb51.net/spring/aop.html)<br/>
参考：[AOP ASPECT 执行顺序](其中，AOP的执行顺序章节为：http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop-ataspectj-advice-ordering)<br/>

多个@Aspect执行顺序<br/>
方法有两种：<br/>
1、实现org.springframework.core.Ordered接口，实现它的getOrder()方法<br/>
2、给aspect添加@Order注解，该注解全称为：org.springframework.core.annotation.Order<br/>

