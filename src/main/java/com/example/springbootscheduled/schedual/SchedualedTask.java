package com.example.springbootscheduled.schedual;

import com.example.springbootscheduled.ScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
/**
 * @program: springboot-scheduled
 * @description: 定时器类
 * @author: sjk
 * @create: 2018-08-02 14:40
 **/
@Component//加入spring的bean
@Slf4j
public class SchedualedTask {

    //private Log log = LogFactory.getLog(SchedualedTask.class);

    @Autowired
    private ScheduleConfig scheduleConfig;

    /**
     * "0 0 12 * * ?"    每天中午十二点触发
     * "0 15 10 ? * *"    每天早上10：15触发
     * "0 15 10 * * ?"    每天早上10：15触发
     * "0 15 10 * * ? *"    每天早上10：15触发
     * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
     */
    @Scheduled(cron = "*/6 * * * * ?")
    public void task1(){
        log.info("task1========执行任务开始==================================================，删除天数："+scheduleConfig.delDay);
        String[] cmds={"curl","-XDELETE",""};//必须分开写，不能有空格
        LocalDate delDate = LocalDate.now().minusDays(Long.parseLong(scheduleConfig.delDay));
        log.info("删除截止的日期：{}",delDate);
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String strDate3 = dtf3.format(delDate);
        String url = MessageFormat.format("http://127.0.0.1:9200/*-{0}",strDate3);
        cmds[2] = url;
        log.info(Arrays.toString(cmds));
        log.info(execCurl(cmds));
        log.info("task1========执行任务结束==================================================，删除天数："+scheduleConfig.delDay);
    }

    //这里执行cmds命令
    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;
    }
    //@Scheduled(fixedDelay = 1000*10)
    //public void task2(){
    //    log.info("task2=================================================================");
    //}

}
