
package consumer.provider;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Data> queue;
    private static Random r = new Random();

    public Consumer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    public void run() {
        while(true){
            try {
                Data data = this.queue.take();
                Thread.sleep(r.nextInt(1000));
                System.out.printf("当前消费线程：%s, 消费成功，消费数据id为：%s\n",Thread.currentThread().getName(),data.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
