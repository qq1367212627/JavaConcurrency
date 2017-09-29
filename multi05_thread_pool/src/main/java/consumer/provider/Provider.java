
package consumer.provider;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Provider implements Runnable {
    private BlockingQueue<Data>queue;
    private volatile boolean isRunning = true;
    private static AtomicInteger count = new AtomicInteger();
    private static Random r = new Random();


    public Provider(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    public void run() {
        while(isRunning){

            try {
                Thread.sleep(r.nextInt(1000));
                int id = count.incrementAndGet();
                Data data = new Data(Integer.toString(id),"数据"+id);
                System.out.println("当前线程:" + Thread.currentThread().getName() + ", 获取了数据，id为:" + id + ", 进行装载到公共缓冲区中...");
                if(!this.queue.offer(data,2, TimeUnit.SECONDS)){
                    System.out.println("提交数据失败哦！！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        isRunning = false;
    }
}
