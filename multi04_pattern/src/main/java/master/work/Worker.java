

package master.work;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable{

    private ConcurrentLinkedQueue<Task> workQueue;
    private ConcurrentHashMap<String,Object> resultMap;

    public Object handle(Task input){
        Object res = null;
        try {
            Thread.sleep(500);
            res = input.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void run() {
        while(true){
            Task input = this.workQueue.poll();
            if(input == null)break;
            Object out = handle(input);
            this.resultMap.put(Integer.toString(input.getId()),out);

        }
    }

    public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
