package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lewis on 2017/8/19.
 */
public class AtomicUse {
    private static AtomicInteger count = new AtomicInteger(0);

    /***
     *  执行多个原子操作执行 不能保证 执行的过程具有原子性 ，必须对这个过程加上锁，使其具有原子性
     */
    public synchronized int multiAdd()  {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count.addAndGet(1);
        count.addAndGet(2);
        count.addAndGet(3);
        count.addAndGet(4);
        /* 每次做 加10 操作，若具有原子性则 得到的每次数字个位数都是0 */
        return count.get();
    }
    public static void main(String []args){
        final AtomicUse au = new AtomicUse();

        List<Thread> ts = new ArrayList<Thread>();
        for (int i = 0; i < 100; i++) {
            ts.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(au.multiAdd());
                }
            }));
        }

        for(Thread t : ts){
            t.start();
        }

        /**
         * 开启100个线程并发添加数字
         * */

    }
}
