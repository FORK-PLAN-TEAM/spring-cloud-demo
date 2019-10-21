package com.zypcy.framework.fast.ScheduleTask;

import cn.hutool.core.date.DateUtil;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.async.InitLoaderAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务示例
 * 如果需要分布式发布，则定时任务需要使用 redisson 分布式锁，防止多个定时任务同时操作同一份数据
 */
//@Scheduled(cron = "*/5 * * * * ? 		")//每隔5秒执行一次
//@Scheduled(cron = "0 */1 * * * ? 		")//每隔1分钟执行一次
//@Scheduled(cron = "0 0 5-15 * * ?		")//每天5-15点整点触发
//@Scheduled(cron = "0 0/3 * * * ? 		")//每三分钟触发一次
//@Scheduled(cron = "0 0-5 14 * * ? 		")//在每天下午2点到下午2:05期间的每1分钟触发 
//@Scheduled(cron = "0 0/5 14 * * ? 		")//在每天下午2点到下午2:55期间的每5分钟触发
//@Scheduled(cron = "0 0/5 14,18 * * ?	")//在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
//@Scheduled(cron = "0 0/30 9-17 * * ?	")//朝九晚五工作时间内每半小时
//@Scheduled(cron = "0 0 10,14,16 * * ?   ")//每天上午10点，下午2点，4点 
//@Scheduled(cron = "0 0 12 ? * WED 				")//表示每个星期三中午12点
//@Scheduled(cron = "0 0 17 ? * TUES,THUR,SAT 	")//每周二、四、六下午五点
//@Scheduled(cron = "0 10,44 14 ? 3 				")//WED 每年三月的星期三的下午2:10和2:44触发 
//@Scheduled(cron = "0 15 10 ? * MON-FRI 			")//周一至周五的上午10:15触发
//@Scheduled(cron = "0 0 23 L * ? 				")//每月最后一天23点执行一次
//@Scheduled(cron = "0 15 10 L * ? 				")//每月最后一日的上午10:15触发 
//@Scheduled(cron = "0 15 10 ? * 6L 				")//每月的最后一个星期五上午10:15触发 
//@Scheduled(cron = "0 15 10 * * ? 2019 			")//2019年的每天上午10:15触发 
//@Scheduled(cron = "0 15 10 ? * 6L 2019-2020		")// 2019年至2020年的每月的最后一个星期五上午10:15触发 
//@Scheduled(cron = "0 15 10 ? * 6#3 				")//每月的第三个星期五上午10:15触发
//@Scheduled(cron = "30 * * * * ?					")//每半分钟触发任务
//@Scheduled(cron = "30 10 * * * ? 				")//每小时的10分30秒触发任务
//@Scheduled(cron = "30 10 1 * * ? 				")//每天1点10分30秒触发任务
//@Scheduled(cron = "30 10 1 20 * ? 				")//每月20号1点10分30秒触发任务
//@Scheduled(cron = "30 10 1 20 10 ? *			")//每年10月20号1点10分30秒触发任务
//@Scheduled(cron = "30 10 1 20 10 ? 2019			")//2019年10月20号1点10分30秒触发任务
//@Scheduled(cron = "30 10 1 ? 10 * 2019 			")//2019年10月每天1点10分30秒触发任务
//@Scheduled(cron = "30 10 1 ? 10 SUN 2019		")//2019年10月每周日1点10分30秒触发任务
//@Scheduled(cron = "15,30,45 * * * * ? 			")//每15秒，30秒，45秒时触发任务
//@Scheduled(cron = "15-45 * * * * ?				")//15到45秒内，每秒都触发任务
//@Scheduled(cron = "15/5 * * * * ? 				")//每分钟的每15秒开始触发，每隔5秒触发一次
//@Scheduled(cron = "15-30/5 * * * * ?			")//每分钟的15秒到30秒之间开始触发，每隔5秒触发一次
//@Scheduled(cron = "0 0/3 * * * ? 				")//每小时的第0分0秒开始，每三分钟触发一次
//@Scheduled(cron = "0 15 10 ? * MON-FRI 			")//星期一到星期五的10点15分0秒触发任务
//@Scheduled(cron = "0 15 10 L * ? 				")//每个月最后一天的10点15分0秒触发任务
//@Scheduled(cron = "0 15 10 LW * ? 				")//每个月最后一个工作日的10点15分0秒触发任务
//@Scheduled(cron = "0 15 10 ? * 5L 				")//每个月最后一个星期四的10点15分0秒触发任务
//@Scheduled(cron = "0 15 10 ? * 5#3 				")//每个月第三周的星期四的10点15分0秒触发任务

@Configuration
public class PrintTask {

    @Autowired
    InitLoaderAsync loaderAsync;


    //添加定时任务，例如：10秒打印一次s
    @Scheduled(cron = "0/30 * * * * ?")
    public void configureTasks() {
        LogUtil.info("PrintTask执行定时任务时间: " + DateUtil.now());
        //loaderAsync.getList();

    }
}
