package com.damon;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018/1/4 9:54
 */
public class Expression2Test {

    private static final Logger log = LoggerFactory.getLogger(Expression2Test.class);

    public void method(Apple apple, String name) {

    }

    @Test
    public void test() {
        String expressionString = "'id:'+#apple.id+':name:'+#apple.name+' ask '+#name";
        Method[] methods = Expression2Test.class.getMethods();
        for (Method method : methods) {
            if (!method.getName().equals("method")) {
                continue;
            }

            String lockKey = getLockKey(method, expressionString, new Object[]{new Apple(1L, "6S"), "7"});
            log.info("lockKey | {}", lockKey);
        }
    }

    private String getLockKey(Method method, String key, Object[] arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append("lock:");
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameters = discoverer.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        int length = parameters.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                context.setVariable(parameters[i], arguments[i]);
            }
        }
        String keysValue = expression.getValue(context, String.class);
        sb.append(keysValue);
        return sb.toString();
    }

}