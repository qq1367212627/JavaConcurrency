

package pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPoolExecutor1 {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            1,          //默认线程大小
            2,      //线程最大线程数
            60,         //线程生命周期
                TimeUnit.SECONDS,       //线程生命周期单位
                new ArrayBlockingQueue<Runnable>(3),//缓存队列，有界阻塞队列
                new MyRejected()//自定义异常类
        );

        MyTask mt1 = new MyTask(1, "任务1");
        MyTask mt2 = new MyTask(2, "任务2");
        MyTask mt3 = new MyTask(3, "任务3");
        MyTask mt4 = new MyTask(4, "任务4");
        MyTask mt5 = new MyTask(5, "任务5");
        MyTask mt6 = new MyTask(6, "任务6");//任务6被拒绝，超过 最大线程数+缓存队列的容量 == 2 + 3

        pool.execute(mt1);
        pool.execute(mt2);
        pool.execute(mt3);
        pool.execute(mt4);
        pool.execute(mt5);
        pool.execute(mt6);

        pool.shutdown();
    }
}
