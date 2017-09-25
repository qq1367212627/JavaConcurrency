package conn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewis on 2017/8/19.
 *
 * 利用notify wait 进行线程通信
 *
 */
public class ListAdd {
    private volatile static List list = new ArrayList();



    public void add(){
        list.add("lewis");
    }

    public int size(){
        return list.size();
    }

    public static void main(String []args){
        final Object lock = new Object();
        final ListAdd listAdd = new ListAdd();

        new Thread(new Runnable() {
            public void run() {
                synchronized (lock){
                    while (true){
                        if (listAdd.size()!=5){
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
                        throw new RuntimeException();
                    }
                }
            }
        },"t2").start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (lock){
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
                            lock.notify();
                        }
                    }
                }
            }
        },"t1").start();


    }

}
