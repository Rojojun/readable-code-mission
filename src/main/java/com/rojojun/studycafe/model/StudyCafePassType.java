package com.rojojun.studycafe.model;

import com.rojojun.studycafe.exception.AppException;

import java.util.Arrays;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", 1, false),
    WEEKLY("주 단위 이용권", 2, false),
    FIXED("1인 고정석", 3, true);

    private final String description;
    private final int inputCode;
    private final boolean hasLocker;

    StudyCafePassType(String description, int inputCode, boolean hasLocker) {
        this.description = description;
        this.inputCode = inputCode;
        this.hasLocker = hasLocker;
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

    public static boolean hasLockerOption(StudyCafePassType studyCafePassTypeFromUser) {
        return studyCafePassTypeFromUser.hasLocker;
    }

    public boolean isTypeEqual(StudyCafePass studyCafePass) {
        return studyCafePass.isTypeEqual(this);
    }

    public String createDisplayMessage(int duration, int price) {
        return switch (this) {
            case FIXED, WEEKLY -> String.format("%s주권 - %d원", duration, price);
            case HOURLY -> String.format("%s시간권 - %d원", duration, price);
        };
    }
}
