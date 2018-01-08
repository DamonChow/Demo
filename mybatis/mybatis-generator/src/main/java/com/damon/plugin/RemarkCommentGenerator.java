package com.damon.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * 功能：mybatis生成字段中文注释
 *
 * @author Damon
 * @since 2017/11/15 14:05
 */
public class RemarkCommentGenerator extends DefaultCommentGenerator {

    private String author;
    private String currentDateStr;

    public RemarkCommentGenerator() {
        super();
        currentDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        author = properties.getProperty("author");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (Objects.isNull(remarks)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/** ");
        sb.append(" * ");
        sb.append(remarks);
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/** ");
        topLevelClass.addJavaDocLine(" * 描述： " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @since " + currentDateStr);
        topLevelClass.addJavaDocLine(" */");
    }
}