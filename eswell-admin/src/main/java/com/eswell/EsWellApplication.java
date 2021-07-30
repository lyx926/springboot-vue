package com.eswell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author eswell
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EsWellApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(EsWellApplication.class, args);
        System.out.println(" EsWell启动成功 ");
    }
}
