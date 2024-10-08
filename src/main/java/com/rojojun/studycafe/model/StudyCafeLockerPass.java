package com.rojojun.studycafe.model;

public class StudyCafeLockerPass implements DisplayablePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public int getPrice() {
        return price;
    }

    public boolean isPassTypeEqual(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isDurationEqual(int duration) {
        return this.duration == duration;
    }

    @Override
    public String display() {
        return passType.createDisplayMessage(duration, price);
    }
}
