import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueTest {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<Task>();
        queue.add(new Task(2,"2"));
        queue.add(new Task(1,"1"));
        queue.add(new Task(3,"3"));

        while(queue.size()>0)
            System.out.println(queue.take()); //调用take方法才会进行堆排序，使用iteration 拿到的为无序的。
    }
}
