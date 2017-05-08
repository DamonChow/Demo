package com.damon.test.thread.delay.test1;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/1/4 10:11
 */
public class Test {
    static final int STUDENT_SIZE = 45;

    public static void main(String[] args) {
        Random r = new Random();
        DelayQueue<Student> students = new DelayQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < STUDENT_SIZE; i++) {
            students.put(new Student("学生" + (i + 1), 3000 + r.nextInt(9000)));
        }
        students.put(new EndExam(12000, exec));//1200为考试结束时间
        exec.execute(new Teacher(students, exec));

    }
}

class Student implements Runnable, Delayed {
    private String name;
    private long submitTime;//交卷时间
    private long workTime;//考试时间

    public Student() {
        // TODO Auto-generated constructor stub
    }

    public Student(String name, long submitTime) {
        super();
        this.name = name;
        workTime = submitTime;
        //都转为转为ns
        this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public void run() {
        System.out.println(name + " 交卷,用时" + workTime / 100 + "分钟");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(submitTime - System.nanoTime(), unit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Student that = (Student) o;
        return submitTime > that.submitTime ? 1 : (submitTime < that.submitTime ? -1 : 0);
    }
}

class EndExam extends Student {
    private ExecutorService exec;

    public EndExam(int submitTime, ExecutorService exec) {
        super(null, submitTime);
        this.exec = exec;
    }

    @Override
    public void run() {
        exec.shutdownNow();
    }
}

class Teacher implements Runnable {
    private DelayQueue<Student> students;
    private ExecutorService exec;

    public Teacher(DelayQueue<Student> students, ExecutorService exec) {
        super();
        this.students = students;
        this.exec = exec;
    }


    @Override
    public void run() {
        try {
            System.out.println("考试开始……");
            while (!Thread.interrupted()) {
                students.take().run();
            }
            System.out.println("考试结束……");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
