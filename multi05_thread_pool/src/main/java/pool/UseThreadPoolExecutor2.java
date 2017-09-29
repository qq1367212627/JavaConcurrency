
package pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UseThreadPoolExecutor2 implements Runnable{

    private static AtomicInteger count = new AtomicInteger(0);

    public void run() {
        int temp = count.incrementAndGet();
        System.out.println("任务"+temp);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable>queue = new ArrayBlockingQueue<Runnable>(10);
        ExecutorService executor = new ThreadPoolExecutor(
                5,
                10,
                120,
                TimeUnit.SECONDS,
                queue
        );
        for(int i = 0;i<20;i++){
            executor.execute(new UseThreadPoolExecutor2());
        }
        Thread.sleep(1000);
        System.out.println("queue size:"+queue.size());
        Thread.sleep(2000);
    }
}
