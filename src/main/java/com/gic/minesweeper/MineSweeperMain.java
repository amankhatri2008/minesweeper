package com.gic.minesweeper;

import com.gic.minesweeper.service.GameInitiateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MineSweeperMain {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MineSweeperMain.class, args);
        GameInitiateService gameInitiateService = ctx.getBean(GameInitiateService.class);
        gameInitiateService.start();
    }

}

