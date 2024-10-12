package com.rojojun.tobe;

import com.rojojun.tobe.minesweeper.Minesweeper;
import com.rojojun.tobe.minesweeper.config.GameConfig;
import com.rojojun.tobe.minesweeper.gamelevel.Beginner;
import com.rojojun.tobe.minesweeper.io.ConsoleInputHandler;
import com.rojojun.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(
            new Beginner(),
            new ConsoleInputHandler(),
            new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(gameConfig);
        minesweeper.initialize();
        minesweeper.run();
    }

}
