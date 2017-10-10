## 声明：本文的程序样例以及程序的演进优化均来自《java并发编程实战》，个人觉得文章写的很好，加上一些自己的理解分享给大家。（原文在原书：85页开始）


###假设需求场景：几乎所有的服务器都有某种形式的缓存，如现在流行的redis，现在要构造一个缓存系统，用来存储一种非常耗时计算系统的结果，用来避免重复的计算和提高系统的响应，缓存系统会有多个线程并发访问。

#### 计算类的接口
```
package cache;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
```
#### 接口的实现类
```
package cache.Impl;

import cache.Computable;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //.
        //.
        //.
        //大量的耗时计算
        return new BigInteger(arg);
    }
}

```
#### 缓存实现类 版本1
```
package cache;

import java.util.HashMap;
import java.util.Map;

public class Memoizerl<A,V> implements Computable<A,V>  {
    private final Map<A,V> cache = new HashMap<A,V>();//使用HashMap容器作为缓存容器
    private final Computable<A,V> c;                  //计算类

    public Memoizerl(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);//尝试从缓存中取计算结果
        if(result==null){         //若缓存中没有计算结果
            result = c.compute(arg);//调用方法进行计算
            cache.put(arg,result);//将计算结果添加到缓存中
        }
        return result;            //放回结果
    }
}

```
显然版本1有比较显然的设计失误，HashMap不是线程安全，而且compute方法也没有加上同步的关键字，因此要保证2个线程不会同时访问HashMap对象需要对`public V compute(A arg) throws InterruptedException`方法加上锁，以下是版本2的迭代
#### 版本2
```
 @Override
    public synchronized V compute(A arg) throws InterruptedException {//给方法加上同步
        V result = cache.get(arg);//尝试从缓存中取计算结果
        if(result==null){         //若缓存中没有计算结果
            result = c.compute(arg);//调用方法进行计算
            cache.put(arg,result);//将计算结果添加到缓存中
        }
        return result;            //放回结果
    }
```
不错，现在版本2缓存系统在多线程下也可以正确的使用，不会出现竞态条件（race condition），不过虽然能够正常使用，但是现在若多个线程向缓存查询，而恰好某个结果正在计算，其他线程只能阻塞等待，多个线程阻塞必定会导致整个计算系统的效率下降，我们需要更高的并发量。现在把方法的同步关键字去掉，把HashMap换成线程安全的ConcurrentHashMap，由于ConcurrentHashMap是线程安全的，因此在访问底层Map是就不需要进行同步。取消使用方法同步， 而使用ConcurrentHashMap代替HashMap，其实某种程度是降低了锁的粒度，从开始的最多一个线程访问缓存系统到最多16个线程访问缓存（ConcurrentHashMap底层分为16个数据段，每个段独立维护锁），细化了数据的访问，下面是版本3的实现。
#### 版本3
```
package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizerl<A,V> implements Computable<A,V>  {
    private final Map<A,V> cache = new ConcurrentHashMap<A,V>();//使用ConcurrentHashMap容器作为缓存容器
    private final Computable<A,V> c;                  //计算类

    public Memoizerl(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {//去除 synchronized关键字
        V result = cache.get(arg);//尝试从缓存中取计算结果
        if(result==null){         //若缓存中没有计算结果
            result = c.compute(arg);//调用方法进行计算
            cache.put(arg,result);//将计算结果添加到缓存中
        }
        return result;            //放回结果
    }
}

```
很好，现在版本3 拥有了很好的并发能力。但是还是有漏洞！想想若有2个线程同时计算同一个值，然而这2个线程都并不知道，因为按照程序的逻辑，若缓存中没有则直接计算，此时会造成重复计算，资源的浪费。这是我们需要将`ConcurrentHashMap<A,V>()`的定义换为`ConcurrentHashMap<A,Future<V>>()`，Future在这里类似一个事件注册的行为，他是一种非阻塞式的计算行为。当某个线程执行了某个计算，无需等到这个线程计算出结果之后将结果放入缓存才能被其他线程知道，而是添加到缓存，其他线程发现已经存在这样的计算事件，只需等待其他线程算好或者直接从缓存中拿到结果即可，这样实现了避免重复计算的现象。
#### 版本4
```
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
            cache.put(arg,ft);
            ft.run();
        }
       return f.get();                           //返回计算，若没有算出结果，则一直阻塞到它计算出返回结果。
    }
}
```
现在我们的程序看上去非常好了，拥有高效的并发性，高效的计算。然而这个程序仍然还是有漏洞。由于`cache.put(arg,ft);`操作不具有原子性，所以可能多个线程在添加任务时添加相同的任务，导致重复计算，这里可以使用`cache.putIfAbsent(arg,ft);`具有原子操作的函数添加避免重复的任务添加。

#### 版本5 
```
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
```
好了，版本5就是我们的final版啦 O(∩_∩)O~ ，从上面可以总结出写出一个高效，正确的并发类是非常不容易的，需要考虑方面非常的多，尤其是对细节的把握非常考验一个人的并发程序设计的能力，好了这就是这篇文章的全部内容。我从《java并发编程实战》看完这个片段，非常佩服作者的设计能力和设计思路，都是非常棒的设计思想，这里把文章加上自己的理解特地分享出来，希望和大家交流，指正。