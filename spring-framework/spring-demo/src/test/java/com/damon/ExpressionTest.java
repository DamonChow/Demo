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
public class ExpressionTest {

    private static final Logger log = LoggerFactory.getLogger(ExpressionTest.class);

    public void test3(int id, String name) {

    }

    @Test
    public void test() {
        String[] keys = {"#id", "#name"};
        String keyStr = Joiner.on(".").skipNulls().join(keys);
        log.info("key | {}", keyStr);
    }

    @Test
    public void test2() {
        String[] keys = {"#id", "#name"};
        Method[] methods = ExpressionTest.class.getMethods();
        for (Method method : methods) {
            if (!method.getName().equals("test3")) {
                continue;
            }

            String lockKey = getLockKey(method, keys, new Object[]{123, "nihao"});
            log.info("lockKey | {}", lockKey);
        }
    }

    private String getLockKey(Method method, String[] keys, Object[] arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append("lock:");

        if (keys != null) {
            String keyStr = Joiner.on("+':'+").skipNulls().join(keys);
            log.info("keyStr|{}", keyStr);
            if (!StringUtils.isEmpty(keyStr)) {
                LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
                String[] parameters = discoverer.getParameterNames(method);
                ExpressionParser parser = new SpelExpressionParser();
                Expression expression = parser.parseExpression(keyStr);
                EvaluationContext context = new StandardEvaluationContext();
                int length = parameters.length;
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        context.setVariable(parameters[i], arguments[i]);
                    }
                }
                Object keysValue = expression.getValue(context);
                sb.append(keysValue);
            }
        }
        return sb.toString();
    }

    @Test
    public void main() {
        //创建SpEL表达式的解析器
        ExpressionParser ep = new SpelExpressionParser();
        //输出结果
        System.out.println(ep.parseExpression("'Hello '+' World!'").getValue());
        System.out.println(ep.parseExpression("'HelloWorld'").getValue());
        System.out.println(ep.parseExpression("0xffffff").getValue());
        System.out.println(ep.parseExpression("1.234345e+3").getValue());
        System.out.println(ep.parseExpression("new java.util.Date()").getValue());
    }

    @Test
    public void main2() {
        String greetingExp = "Hello, #{ #user }";
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", "Gangyou");
        Expression expression = parser.parseExpression(greetingExp, new TemplateParserContext());
        System.out.println(expression.getValue(context, String.class));
    }


}
