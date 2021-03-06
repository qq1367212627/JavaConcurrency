##思维笔记

### 容器解决高并发的思路：
   * 1.降低锁粒度。 如：  ConCurrentHashMap内部使用段（segment）来表示不同的部分，每个段就是一个HashTable
    ,每个段拥有自己的段锁，用来保证线程安全，在ConCurrentHashMap中把一个整体分成了 16 个段（segment），这样
    并发量从 1 个线程 提升到 同时最多可以有16个线程

   * 2.读写分离。 CopyOnWriteArrayList 内部使用 读写分离的思路，在写入数据时创建一个新的容器，将数据写入新的容器中
    ，添加完之后，在将原容器的指针指向新的容器地址，在多读少写的情况下，并发的读无需加锁，则可以达到高并发的目的

### Queue并发包
   * 1.ArrayBlockQueue      数组实现的阻塞有界队列
   * 2.LinkedBlockingQueue  链表实现的阻塞无界队列
   * 3.SynchronousQueue     一种没有缓冲的队列（容量为0），生产者的数据会直接提交给消费者
   #### 应用思路
   * ArrayBlockQueue 适合高并发的情况下，做队列缓存，限制过多的并发数
   * LinkedBlockingQueue 适合较低的并发下做队列缓存，将少量任务全部直接放入队列
   * SynchronousQueue  适合极低的并发下，拒绝任务放入队列，直接由消费者消费数据即可，无需经过队列，浪费时间排队。
   
   
   
    
