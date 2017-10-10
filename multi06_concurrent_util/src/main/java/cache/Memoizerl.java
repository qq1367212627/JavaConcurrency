package cache;

import java.util.Map;
import java.util.concurrent.*;

public class Memoizerl<A,V> implements Computable<A,V>  {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();//使用ConcurrentHashMap容器作为缓存容器
    private final Computable<A,V> c;                  //计算类

    public Memoizerl(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {//去除 synchronized关键字
        Future<V> f = cache.get(arg);               //查询缓存中是否已经注册 compute(arg) 的计算任务
        if(f==null){                                //若没有添加任务，则添加并计算。
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f =ft;
            cache.putIfAbsent(arg,ft);            //具有原子性的putIfAbsent函数替换掉put函数
            ft.run();
        }
       return f.get();                           //返回计算，若没有算出结果，则一直阻塞到它计算出返回结果。
    }
}
