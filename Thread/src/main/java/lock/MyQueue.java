package lock;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lewis on 2017/9/4.
 */
public class MyQueue {

    //创建对象锁
    private final Object lock = new Object();
    //计数器，保存当前队列大小
    private final AtomicInteger count = new AtomicInteger(0);
    //maxSize,minSize 限制队列大小
    private final int maxSize;
    private final int minSize = 0;
    //作为容器保存对象
    private List<Object> list = new LinkedList<>();

    public MyQueue(int maxSize){
        this.maxSize = maxSize;
    }

    public void put(Object object) throws InterruptedException {
        synchronized (lock) {
            if (count.get() == this.maxSize)
                lock.wait();
            count.incrementAndGet();
            list.add(object);
            System.out.println("新添加了元素为：" + object);
            lock.notify();
        }
    }


    public Object get() throws InterruptedException {
        Object ret;
        synchronized (lock){
            if(count.get()==this.minSize) {
                lock.wait();
            }
            count.decrementAndGet();
            ret = list.remove(0);
            System.out.println("新拿取的元素为："+ret);
            lock.notify();
        }
        return ret;
    }


    public int size(){
        return count.get();
    }

    public static void main(String []args) throws InterruptedException {
        final MyQueue queue = new MyQueue(5);

        try {
            queue.put("a");
            queue.put("b");
            queue.put("c");
            queue.put("d");
            queue.put("e");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前元素个数"+queue.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    queue.put("h");
                    queue.put("i");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Object o1 = queue.get();
                    System.out.println("被取走的元素为：" + o1);
                    Thread.sleep(1000);
                    Object o2 = queue.get();
                    System.out.println("被取走的元素为：" + o2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");
    }

}
