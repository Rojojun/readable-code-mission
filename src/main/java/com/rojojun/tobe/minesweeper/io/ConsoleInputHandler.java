package com.rojojun.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    public static final Scanner SCANNER = new Scanner(System.in);

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public UserAction getUserAction() {
        String action = SCANNER.nextLine();

        return switch (action) {
            case "1" -> UserAction.OPEN;
            case "2" -> UserAction.FLAG;
            default -> UserAction.UNKNOWN;
        };
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String input = SCANNER.nextLine();;

        int colIndex = boardIndexConverter.getSelectedColIndex(input);
        int rowIndex = boardIndexConverter.getSelectedRowIndex(input);
        return null;
    }
}
