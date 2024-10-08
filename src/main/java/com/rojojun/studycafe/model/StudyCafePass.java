package com.rojojun.studycafe.model;

public class StudyCafePass implements DisplayablePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public boolean isTypeEqual(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isPassTypeEqual(StudyCafeLockerPass passType) {
        return passType.isPassTypeEqual(this.passType);
    }

    public boolean isDurationEqual(StudyCafeLockerPass option) {
        return option.isDurationEqual(this.duration);
    }

    public int getDiscountPrice() {
        return (int) (this.price * this.discountRate);
    }

    public int getTotalCafeUsagePrice() {
        return this.price - getDiscountPrice();
    }

    @Override
    public String display() {
        return passType.createDisplayMessage(duration, price);
    }
}
