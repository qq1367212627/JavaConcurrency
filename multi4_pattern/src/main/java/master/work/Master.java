
package master.work;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    //任务队列
   private ConcurrentLinkedQueue<Task>workQueue = new ConcurrentLinkedQueue<Task>();
   //Work 线程集，用于放置处理任务的线程
   private HashMap<String,Thread> workers = new HashMap<String, Thread>();
   //结果集
   private ConcurrentHashMap<String,Object>resultMap = new ConcurrentHashMap<String, Object>();
   //构造方法
   public Master(Worker worker,int count){
        worker.setResultMap(resultMap);
        worker.setWorkQueue(workQueue);
        for(int i = 0;i<count;i++){
            this.workers.put(Integer.toString(i),new Thread(worker));
        }
    }

    //添加任务
    public void submit(Task task){
       this.workQueue.add(task);
    }
    //执行任务
    public void excute(){
        for(Map.Entry<String,Thread> w:workers.entrySet()){
            w.getValue().start();
        }
    }
    // 判断工作线程是否都全完成
    public boolean isDone(){
        for(Map.Entry<String,Thread> w:workers.entrySet()){
            if(w.getValue().getState()!=Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }
    //返回结果汇总
    public int getResult(){
        int res = 0;
        for(Map.Entry<String,Object> r:resultMap.entrySet()){
            res += (Integer)r.getValue();
        }
        return res;
    }
}
