package com.rojojun.studycafe.model;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.StudyCafeFileHandler;

import java.util.Arrays;
import java.util.List;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", 1),
    WEEKLY("주 단위 이용권", 2),
    FIXED("1인 고정석", 3);

    private final String description;
    private final int inputCode;

    StudyCafePassType(String description, int inputCode) {
        this.description = description;
        this.inputCode = inputCode;
    }

    public static StudyCafePassType sendTypeBy(String userInput) {
        try {
            int inputCode = Integer.parseInt(userInput);
            return Arrays.stream(values())
                    .filter(type -> type.inputCode == inputCode)
                    .findFirst()
                    .orElseThrow(() -> new AppException("잘못된 입력입니다."));
        } catch (NumberFormatException e) {
            throw new AppException("입력값은 숫자가 되어야합니다.");
        }
    }

    public void getDescription(StudyCafePassType type) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == type)
                .toList();
    }

    public boolean isTypeEqual(StudyCafePass studyCafePass) {
        return studyCafePass.isTypeEqual(this);
    }
}
