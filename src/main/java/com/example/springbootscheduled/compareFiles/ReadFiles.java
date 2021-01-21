package com.example.springbootscheduled.compareFiles;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mickzhu
 * @version 1.0
 * @date 2021/1/21 10:09
 */
@Component
@Slf4j
public class ReadFiles {

    public List<String> getFileOrderId(String fileLocalPath,String lineEscape,Integer index){
        List<String> replans = new ArrayList<>();
        try(InputStreamReader isr  = new InputStreamReader(new FileInputStream(fileLocalPath));
            BufferedReader bufferedReader = new BufferedReader(isr);
        ) {
            int lineNum =1;
            String line;
            while (StringUtils.isNotEmpty(line = bufferedReader.readLine())){
                String[] data = line.trim().split(lineEscape);
                replans.add(data[index]);
                lineNum++;
            }
            log.info("读取结果：{}，总数据：{}",replans.size(),lineNum);
        }catch (Exception ex){
            log.error("读取文件异常：{}",ex.getMessage());
        }
        return replans;
    }

    public void  compareFile(){
        String fileupload = "C:\\Users\\mickzhu\\Desktop\\loan_3008_repay_channel_202101181.txt";
        List<String> fileOrderIdUpload = getFileOrderId(fileupload, "\\|", 1);
        fileupload="C:\\Users\\mickzhu\\Desktop\\loan_3008_repay_compare_result_202101181.txt";
        List<String> fileOrderIdResult = getFileOrderId(fileupload, "\\|", 1);
        if(fileOrderIdUpload ==null && fileOrderIdUpload.size() <=0){
            log.info("上传文件读取错误");
            return;
        }
        if(fileOrderIdResult ==null && fileOrderIdResult.size() <=0){
            log.info("结果文件读取错误");
            return;
        }
        for (String info : fileOrderIdResult){
            if (!fileOrderIdUpload.contains(info)) {
                log.error(info);
            }
        }
        log.info("比较文件结束");
    }
}
