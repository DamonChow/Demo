KafKa Consumer接口

kafka的consumer接口，有两种版本：
A.high-level 比较简单不用关心offset, 会自动的读zookeeper中该Consumer group的last offset
B.就是官网上提供的 SimpleConsumer Example low-level

几点说明：
1. 如果consumer比partition多，是浪费，因为kafka的设计是在一个partition上是不允许并发的，所以consumer数不要大于partition数 
2. 如果consumer比partition少，一个consumer会对应于多个partitions，这里主要合理分配consumer数和partition数，否则会导致partition里面的数据被取的不均匀
最好partiton数目是consumer数目的整数倍，所以partition数目很重要，比如取24，就很容易设定consumer数目 
3. 如果consumer从多个partition读到数据，不保证数据间的顺序性，kafka只保证在一个partition上数据是有序的，但多个partition，根据你读的顺序会有不同 
4. 增减consumer，broker，partition会导致rebalance，所以rebalance后consumer对应的partition会发生变化 
5. High-level接口中获取不到数据的时候是会block的;

关于Offset初始值的问题：
先produce一些数据，然后再用consumer读的话，需要加上一句offset读取设置

props.put("auto.offset.reset", "earliest"); //必须要加，如果要读旧数据  
 
因为初始的offset默认是非法的，然后这个设置的意思 是，当offset非法时，如何修正offset，默认是largest，即最新，所以不加这个配置，你是读不到你之前produce的数据的，而且这个 时候你再加上smallest配置也没用了，因为此时offset是合法的，不会再被修正了，需要手工或用工具改重置offset

