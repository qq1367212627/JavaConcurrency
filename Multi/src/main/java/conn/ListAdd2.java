package conn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lewis on 2017/8/20.
 *
 * 通过CountDownLatch 实现线程通信
 *
 */
public class ListAdd2 {

    private volatile static List list = new ArrayList();

    public void add(){
        list.add("lewis");
    }

    public int size(){
        return list.size();
    }

    public static void main(String []args){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final  ListAdd2 listAdd = new ListAdd2();
        new Thread(new Runnable() {
            public void run() {
                    while (true){
                        if (listAdd.size()!=5){
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
                        throw new RuntimeException();
                    }
                }
        },"t2").start();

        new Thread(new Runnable() {
            public void run() {
                    for (int i=0;i<10;i++){
                        listAdd.add();
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(listAdd.size()==5){
                            System.out.println("已经发出通知！！");
                            countDownLatch.countDown();
                        }
                    }
                }
        },"t1").start();
    }
}
