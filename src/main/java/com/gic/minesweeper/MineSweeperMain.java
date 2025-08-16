package com.gic.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MineSweeperMain {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MineSweeperMain.class, args);
        GameInitiate gameInitiate = ctx.getBean(GameInitiate.class);
        gameInitiate.start();
    }

}

