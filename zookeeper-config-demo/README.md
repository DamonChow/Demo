## 说明

使用配置如下
~~~
        <bean name="propertiesConfig" class="com.damon.core.ZookeeperPropertiesConfigure">
            <property name="locationList">
                <array>
                    <value>/config/dev/product/dubbo.properties</value>
                    <value>/config/dev/product/jdbc.properties</value>
                    <value>/config/dev/product/product.properties</value>
                    <value>/config/dev/product/redis.properties</value>
                </array>
            </property>
            <!--<property name="locations">
                <list>
                    <value>classpath*:config/dubbo.properties</value>
                    <value>classpath*:config/jdbc.properties</value>
                    <value>classpath*:config/redis.properties</value>
                </list>
            </property>-->
        </bean>
~~~
支持本地配置properties，优先使用本地配置；
<br/>
<br/>

变量使用：<br/>
~~~
String configValue = PropertiesUtils.getString("propertiesKey");
~~~
具体参考PropertiesUtils的获取属性方法。