package com.rojojun.tobe.minesweeper.board;

import com.rojojun.tobe.minesweeper.board.position.CellPosition;
import com.rojojun.tobe.minesweeper.gamelevel.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GameBoardTest {
    @DisplayName("레벨에 따른 지뢰찾기판의 전체 크기를 구한다")
    @MethodSource("provideGameLevelData")
    @ParameterizedTest
    void getRowSize(GameLevel gameLevel, int row, int col) {
        // given
        GameBoard gameBoard = new GameBoard(gameLevel);
        int expect = row * col;

        // when
        int result = gameBoard.getRowSize() * gameBoard.getColSize();

        // then
        assertThat(result).isEqualTo(expect);
    }

    @DisplayName("레벨에 따라 유효하지 않은 입력 값인지 판별한다.")
    @MethodSource("provideGameLevelData")
    @ParameterizedTest
    void isInvalidCellPosition(GameLevel gameLevel, int row, int col) {
        // given
        GameBoard gameBoard = new GameBoard(gameLevel);

        int validRowInput = row - 1;
        int validColInput = col - 1;

        int invalidRowInput = row + 1;
        int invalidColInput = col + 1;

        CellPosition validCellPosition = CellPosition.of(validRowInput, validColInput);
        CellPosition invalidCellPosition = CellPosition.of(invalidRowInput, invalidColInput);

        // when
        boolean validResult = gameBoard.isInvalidCellPosition(validCellPosition);
        boolean invalidResult = gameBoard.isInvalidCellPosition(invalidCellPosition);

        // then
        assertThat(validResult).isFalse();
        assertThat(invalidResult).isTrue();
    }

    private static Stream<Arguments> provideGameLevelData() {
        return Stream.of(
                Arguments.of(new VeryBeginner(), 4, 5),
                Arguments.of(new Beginner(), 8, 10),
                Arguments.of(new Middle(), 14, 18),
                Arguments.of(new Advanced(), 20, 24)
        );
    }
}