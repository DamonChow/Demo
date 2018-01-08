## 说明

generatorConfig.xml添加配置如下
~~~
    <commentGenerator type="com.damon.plugin.RemarkCommentGenerator">
        <property name="suppressDate" value="true" />
        <property name="suppressAllComments" value="true" />
        <property name="author" value="Damon" />
    </commentGenerator>
~~~

mybatis自动生成对象使用lombok注释，参考[mybatis-generator-lombok-plugin](https://github.com/softwareloop/mybatis-generator-lombok-plugin)