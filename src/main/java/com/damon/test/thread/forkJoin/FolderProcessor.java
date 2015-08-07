package com.damon.test.thread.forkJoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 功能：在一个文件夹及其子文件夹内查找确定扩展名的文件
 *
 * Created by ZhouJW on 2015/5/11 16:10.
 */
public class FolderProcessor extends RecursiveTask<List<String>> {
    // 2.声明这个类的序列号版本UID。这个元素是必需的，因为RecursiveTask类的父类，
    // ForkJoinTask类实现了Serializable接口。
    private static final long serialVersionUID = 1L;
    // 3.声明一个私有的、String类型的属性path。这个属性将存储任务将要处理的文件夹的全路径。
    private String path;
    // 4.声明一个私有的、String类型的属性extension。这个属性将存储任务将要查找的文件的扩展名。
    private String extension;

    // 5.实现这个类的构造器，初始化它的属性。
    public FolderProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    // 6.实现compute()方法。正如你用List<String>类型参数化RecursiveTask类，
    // 这个方法将返回这个类型的一个对象。
    @Override
    protected List<String> compute() {
// 7.声明一个String对象的数列，用来保存存储在文件夹中的文件。
        List<String> list = new ArrayList<>();
// 8.声明一个FolderProcessor任务的数列，用来保存将要处理存储在文件夹内的子文件夹的子任务。
        List<FolderProcessor> tasks = new ArrayList<>();
// 9.获取文件夹的内容。
        File file = new File(path);
        File content[] = file.listFiles();
// 10.对于文件夹里的每个元素，如果是子文件夹，则创建一个新的FolderProcessor对象，
// 并使用fork()方法异步地执行它。
        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    FolderProcessor task = new FolderProcessor(content[i].
                            getAbsolutePath(), extension);
                    task.fork();
                    tasks.add(task);
// 11.否则，使用checkFile()方法比较这个文件的扩展名和你想要查找的扩展名，
// 如果它们相等，在前面声明的字符串数列中存储这个文件的全路径。
                } else {
                    if (checkFile(content[i].getName())) {
                        list.add(content[i].getAbsolutePath());
                    }
                }
            }
// 12.如果FolderProcessor子任务的数列超过50个元素，写入一条信息到控制台表明这种情况。
            if (tasks.size() > 50) {
                System.out.printf("%s: %s: %d tasks ran.\n",
                        Thread.currentThread().getName(),file.getAbsolutePath(), tasks.size());
            }
// 13.调用辅助方法addResultsFromTask()，将由这个任务发起的子任务返回的结果添加到文件数列中。
// 传入参数：字符串数列和FolderProcessor子任务数列。
            addResultsFromTasks(list, tasks);
// 14.返回字符串数列。
        }
            return list;
    }

    // 15.实现addResultsFromTasks()方法。对于保存在tasks数列中的每个任务，调用join()方法，
    // 这将等待任务执行的完成，并且返回任务的结果。使用addAll()方法将这个结果添加到字符串数列。
    private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
        for (FolderProcessor item : tasks) {
            list.addAll(item.join());
        }
    }

    // 16.实现checkFile()方法。这个方法将比较传入参数的文件名的结束扩展是否是你想要查找的。如果是，这个方法返回true，否则，返回false。
    private boolean checkFile(String name) {
        return name.endsWith(extension);
    }

}

//17.实现这个例子的主类，通过创建Main类，并实现main()方法。
class Main {

    public static void main(String[] args) {
// 18.使用默认构造器创建ForkJoinPool。
        ForkJoinPool pool = new ForkJoinPool();
        long begin = System.currentTimeMillis();
// 19.创建3个FolderProcessor任务。用不同的文件夹路径初始化每个任务。
        FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
        FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
        FolderProcessor documents = new FolderProcessor("C:\\Documents And Settings", "log");
// 20.在池中使用execute()方法执行这3个任务。
        pool.execute(system);
        pool.execute(apps);
        pool.execute(documents);
// 21.将关于池每秒的状态信息写入到控制台，直到这3个任务完成它们的执行。
        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.printf("******************************************\n");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!system.isDone()) || (!apps.isDone()) || (!documents.isDone()));
// 22.使用shutdown()方法关闭ForkJoinPool。
        pool.shutdown();
// 23.将每个任务产生的结果数量写入到控制台。
// 查看源代码打印帮助
        List<String> results;
        results = system.join();
        System.out.printf("System: %d files found.\n", results.size());
        results = apps.join();
        System.out.printf("Apps: %d files found.\n", results.size());
        results = documents.join();
        System.out.printf("Documents: %d files found.\n", results.size());
        long end = System.currentTimeMillis();
        System.out.printf("共花了 %s 秒", (end - begin) /1000);
    }
}
