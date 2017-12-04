package com.damon.jdk8.stream;

import com.damon.jdk8.date.LocalDateTest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Slf4j
@Log
public class SimpleTenTest {

    private Logger logger = LoggerFactory.getLogger(SimpleTenTest.class);

    //1. 对列表/数组中的每个元素都乘以2
    @Test
    public void sum() {
        int sum1 = IntStream.range(1, 1000).sum();
        int sum2 = IntStream.range(1, 1000).reduce(0, Integer::sum);
        Integer sum3 = Stream.iterate(0, i -> i + 1).limit(1000).reduce(0, Integer::sum);
        int sum4 = IntStream.iterate(0, i -> i + 1).limit(1000).reduce(0, Integer::sum);
        logger.info("sum={}", sum1);
        logger.info("sum={}", sum2);
        logger.info("sum={}", sum3);
        logger.info("sum={}", sum4);
    }

    //1. 对列表/数组中的每个元素都乘以2
    @Test
    public void add() {
        // Range是半开区间
        int[] ia = IntStream.rangeClosed(0, 9).map(i -> i * 2).toArray();
        logger.info("ia = {}", Arrays.toString(ia));
        List<Integer> result = IntStream.range(1, 10).map(i -> i * 2).boxed().collect(Collectors.toList());
        logger.info("result = {}", Objects.toString(result));
    }

    @Test
    //验证字符串是否包含集合中的某一字符串
    public void match() {
        final List<String> keywords = Arrays.asList("brown", "fox", "dog", "pangram");
        final String tweet = "The quick brown fox jumps over a lazy dog. #pangram http://www.rinkworks.com/words/pangrams.shtml";

        boolean anyMatch = keywords.stream().anyMatch(tweet::contains);
        Boolean reduce = keywords.stream().reduce(false, (b, keyword) -> b || tweet.contains(keyword), (l, r) -> l || r);
        logger.info("anyMatch = {}", anyMatch);
        logger.info("reduce = {}", reduce);
    }

    @Test
    public void testFlat() {
        List<String> a = Lists.newArrayList("aa","bb","cc");
        List<String> b = Lists.newArrayList("aa2","bb2","cc2");
        List<String> c = Lists.newArrayList("aa3","bb3","cc3");
        List<String> d = Lists.newArrayList("aa4","bb4","cc4");

        Map<String, List<String>> map = Maps.newHashMap();
        map.put("a", a);
        map.put("b", b);
        map.put("c", c);
        map.put("d", d);

        List<String> list = map.entrySet().stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList());
        System.out.println(list);

        List<String> list2 = map.values().stream().flatMap(item -> item.stream()).collect(Collectors.toList());
        System.out.println(list2);


    }

}
