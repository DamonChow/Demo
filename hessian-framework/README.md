## 说明
hessian调用在服务端和客户端需要大量手动配置bean，过于麻烦，简化配置。<br/>
服务端通过服务端统一扫描接口，暴露服务。<br/>
客户端通过服务端统一扫描接口，引用服务。<br/>

### 服务端
参考 hessian-web项目<br/>
配置如下
~~~
    <context:component-scan base-package="com.damon.service"/>

    <bean id="hessianServerConfiguration" class="com.damon.hessian.producer.HessianServerConfiguration">
        <property name="basePackage" value="com.damon.service"></property>
    </bean>
~~~
1、扫描需要暴露的服务实现；<br/>
2、暴露服务：basePackage配置需要暴露服务的接口包名可以使用,;等拼接；<br/>
<br/>
<br/>

### 客户端
参考 hessian-client<br/>
配置如下

~~~
demo.app.url=http://localhost:8080/demo-app/remoting/ServiceExporter
readTimeout=2000
~~~

~~~
    <!--读取属性文件 -->
    <bean id="propertyPlaceholderConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <array>
                <value>classpath:hessian.properties</value>
            </array>
        </property>
    </bean>

    <bean id="hessianClientConfiguration" class="com.damon.hessian.consumer.HessianClientConfiguration">
        <property name="basePackage" value="com.damon.service"></property>
        <property name="serviceUrl" value='${demo.app.url}'></property>
        <property name="readTimeout" value='${readTimeout}'></property>
    </bean>
~~~
1、basePackage配置需要暴露服务的接口包名可以使用,;等拼接；<br/>
2、context 引用服务的地址前缀，参考hessian.properties中的配置；<br/>
3、readTimeout hessian客户端读取的超时时间；<br/>
<br/>
<br/>

### 服务接口
参考 hessian-api重定义的接口
<br/>