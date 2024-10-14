package com.rojojun.tobe.minesweeper.board.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellPositionTest {
    @DisplayName("첫번쩨 좌표가 움직일 수 있는 개수는 3개다" +
            "■ ☑︎ □" +
            "☑ ☑ □" +
            "□ □ □")
    @Test
    void canCalculatePositionBy() {
        // given
        CellPosition cellPosition = CellPosition.of(0, 0);

        // when
        List<Boolean> result = RelativePosition.SURROUNDED_POSITIONS.stream()
                .map(cellPosition::canCalculatePositionBy)
                .toList();

        // then
        long trueCount = result.stream()
                .filter(Boolean::booleanValue)
                .count();

        assertThat(trueCount)
                .isEqualTo(3);
    }

    @DisplayName("선택한 좌표가 상,하,좌,우,대각선에서 움직일 수 있는 좌표를 반환한다.")
    @Test
    void calculatePositionBy() {
        // given
        CellPosition cellPosition = CellPosition.of(1, 1);

        // when
        List<CellPosition> cellPositionList = RelativePosition.SURROUNDED_POSITIONS.stream()
                .map(cellPosition::calculatePositionBy)
                .toList();

        // then
        assertThat(cellPositionList).hasSize(8);
    }

    @DisplayName("선택한 좌표가 상,하,좌,우,대각선에서 움직일 수 있는 좌표가 무조건 움직여야한다.")
    @Test
    void calculatePositionByThrown() {
        // given
        CellPosition cellPosition = CellPosition.of(0, 1);

        // when // then
        assertThatThrownBy(() ->
                    RelativePosition.SURROUNDED_POSITIONS.stream()
                    .map(cellPosition::calculatePositionBy)
                    .toList())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("움직일 수 있는 좌표가 아닙니다.");
    }
}