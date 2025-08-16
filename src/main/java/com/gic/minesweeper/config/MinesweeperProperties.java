package com.gic.minesweeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minesweeper")
public class MinesweeperProperties {
    public int defaultSize;
    public int defaultMines;
    public char bomb;
    public char empty;
    public char hidden;
    public char blank;
    public double defaultMaxMinePercentage;


    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultMines() {
        return defaultMines;
    }

    public void setDefaultMines(int defaultMines) {
        this.defaultMines = defaultMines;
    }

    public char getBomb() {
        return bomb;
    }

    public void setBomb(char bomb) {
        this.bomb = bomb;
    }

    public char getEmpty() {
        return empty;
    }

    public void setEmpty(char empty) {
        this.empty = empty;
    }

    public char getHidden() {
        return hidden;
    }

    public void setHidden(char hidden) {
        this.hidden = hidden;
    }

    public char getBlank() {
        return blank;
    }

    public void setBlank(char blank) {
        this.blank = blank;
    }

    public double getDefaultMaxMinePercentage() {
        return defaultMaxMinePercentage;
    }

    public void setDefaultMaxMinePercentage(double defaultMaxMinePercentage) {
        this.defaultMaxMinePercentage = defaultMaxMinePercentage;
    }
}
