package com.zypcy.sharding.dbtablejpa.util;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Twitter_Snowflake ID生成器
 * 调用示例1：IdWorker.getId() 返回18位 String 类型的ID     如131099260681719808
 * 调用示例2：IdWorker.getDateId() 返回24位 String 类型的ID 如190505131099260681719810
 * getId() 连续自增的数字（不一定完全自增，偶尔中间会隔开）
 */
public class IdWorker {

    private static IdWorker idWorker = new IdWorker(2, 1);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyMMdd");
    private static SimpleDateFormat timeFormatFull = new SimpleDateFormat("yyMMddHHmmss");
    private static Random random = new Random();


    /**
     * 测试
     */
    public static void main(String[] args) {
       /*for (int i = 0; i < 100; i++) {
            String id = getId();
            //long id = idWorker.nextId();
            System.out.println("getId:"+ id  + " , length:" + id.length());
        }
        System.out.println("getId:"+getId() + " , length:" + getId().length());
        System.out.println("getDateId:"+getDateId() + " , length:" + getDateId().length());
        System.out.println("getFullDateId:"+getFullDateId() + " , length:" + getFullDateId().length());*/
        SnowflakeShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();
        for (int i = 0; i < 100; i++) {
            //long id = idWorker.nextId();
            long dbId = Long.valueOf(keyGenerator.generateKey().toString());
            long tableId = Long.valueOf(keyGenerator.generateKey().toString());
            long dby = dbId % 2;
            long tabley = tableId % 2;
            System.out.println("ds" + dby + ".t_order"+tabley);
            //System.out.println("getId:"+ keyGenerator.generateKey()  + " , length:" + String.valueOf(keyGenerator.generateKey()).length());
        }
    }

    /**
     * 获取ID
     * @return 18位连续的id
     */
    public static String getId() {
        String id = String.valueOf(idWorker.nextId());
        return id;
    }

    /**
     * 获取ID
     * @return 18位不连续的id
     */
    public static long getLongId(){
        return idWorker.nextId() + random.nextInt(999);
    }

    /**
     * 获取ID，带年月日 , 24位
     * @return
     */
    public static String getDateId(){
        Date nowTime = new Date();
        String id = timeFormat.format(nowTime) + String.valueOf(idWorker.nextId());
        return id;
    }
    /**
     * 获取ID，带年月日时分秒 ， 30位
     * @return
     */
    public static String getFullDateId(){
        Date nowTime = new Date();
        String id = timeFormatFull.format(nowTime) + String.valueOf(idWorker.nextId());
        return id;
    }


    // ==============================Fields===========================================
    /**
     * 开始时间截 (2018-05-08)
     */
    private final long twepoch = 1525785899450L;
    //private final long twepoch = new Date().getTime();

    /**
     * 机器id所占的位数 默认值 5L
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数 默认值 5L
     */
    private final long datacenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在id中占的位数 默认值 12L
     */
    private final long sequenceBits = 14L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


}

