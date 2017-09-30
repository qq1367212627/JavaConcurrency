## 个人对锁和并发的理解，说错欢迎纠错更正


### 线程安全
*  1 对象封闭（不做共享）

     1).该对象封闭在某个作用域内，如局部变量，其他线程无法访问
     2).封闭在线程内，由某个线程独占,与上思路相同，不做对象共享
       
      ThreadLocal 的思想便是将对象封闭在线程上，
      ThreadLocal为每个使用该变量的线程提供独立的变量副本。

*  2 锁
       向 对象 加锁 ，确保不会多个线程发生竞争。
*  3 对象本身已经不可变
       一些对象本身不可再改变，那么就算被多线程并发使用
       也不会发生非线程安全的问题。
       如一个类成员定义如下：
```java
         public static final int MAX_SIZE = 100;
         //   MAX_SIZE 本身是不可改变的变量了。
```
### 锁与并发
* 这里我想说的是关于并发容器的锁。这里我用Hashtable和ConcurrentHashMap举例。

#### 1.Hashtable
  1.Hashtable 在说锁与并发前 ，首先我们要知道他的数据结构的实现:
``` java 
     private transient Entry<?,?>[] table;
```   
   简单讲Hashtable是由Entry对象构成的数组而已。那么我们继续说并发；

   HashTable之所以是线程安全是因为在对数据读写操作时都加上了互斥锁，
   以下是HashTable的一段源码：
``` java   
        public synchronized V get(Object key) {
               Entry<?,?> tab[] = table;
               int hash = key.hashCode();
               int index = (hash & 0x7FFFFFFF) % tab.length;
               for (Entry<?,?> e = tab[index] ; e != null ; e = e.next) {
                   if ((e.hash == hash) && e.key.equals(key)) {
                       return (V)e.value;
                   }
               }
               return null;
           }
```
   可以看出每次调用 get 方法，都需要获取到HashTable实例的对象锁，
   那么每次只能有一个线程可以调用，并发量可以说极差了。那么如何解决
   呢？于是jdk推出了并发包 java.util.concurrent。


####  2.ConcurrentHashMap 
   ConcurrentHashMap 是 HashTable 的升级版啦！！！他可以支持多个线程
   并发的读写容器。
   那么ConcurrentHashMap 究竟实现了什么呢？
   这里 同样要说 ConcurrentHashMap 的实现原理 
``` java     
        private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

        Segment<K,V>[] segments = (Segment<K,V>[]) new Segment<?,?>[DEFAULT_CONCURRENCY_LEVEL];

```    
   上面代码实例化了一个长度为16的Segment数组，每一个Segment 代表一个数据段，每个数据段有
   自己的空间 和 独立的 Segment 锁，那么 每次访问只要获取对应的 段 的锁即可，那么最多可以
   同时可以访问 16 个段的数据，这时就达到了提高并发数的目的了。
   那么实际上ConcurrentHashMap的思想就是通过
   降低锁的粒度，实现了对容器的多并发。由 每次需要获取对象内置锁 到 获取段锁。
   
   打个比方就是：
   一个大楼原本只有1个门，这个大楼只有1个房间，房间里面只能待1个人。
   这时 将大楼装修成 16 个独立的房间，并且修了 16 个门，那么在大楼大小不变的情况下
   可以同时容纳 16 个人，这就提高了并发啦。
   
### 容器解决高并发的思路：
   * 1.降低锁粒度。 如：  ConCurrentHashMap内部使用段（segment）来表示不同的部分，每个段就是一个HashTable
    ,每个段拥有自己的段锁，用来保证线程安全，在ConCurrentHashMap中把一个整体分成了 16 个段（segment），这样
    并发量从 1 个线程 提升到 同时最多可以有16个线程

   * 2.读写分离。 CopyOnWriteArrayList 内部使用 读写分离的思路，在写入数据时创建一个新的容器，将数据写入新的容器中
    ，添加完之后，在将原容器的指针指向新的容器地址，在多读少写的情况下，并发的读无需加锁，则可以达到高并发的目的
    
    
    
    
