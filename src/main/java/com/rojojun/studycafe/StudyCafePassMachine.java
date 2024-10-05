package com.rojojun.studycafe;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.InputHandler;
import com.rojojun.studycafe.io.OutputHandler;
import com.rojojun.studycafe.io.StudyCafeFileHandler;
import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    // input
    private final InputHandler inputHandler = new InputHandler();
    // output
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            // 묶는게 나을 수도?
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
                        .toList();
                outputHandler.showPassListForSelection(hourlyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                List<StudyCafePass> weeklyPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.WEEKLY)
                        .toList();
                outputHandler.showPassListForSelection(weeklyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafePass> fixedPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.FIXED)
                        .toList();
                outputHandler.showPassListForSelection(fixedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                        .filter(option ->
                                option.getPassType() == selectedPass.getPassType()
                                        && option.getDuration() == selectedPass.getDuration()
                        )
                        .findFirst()
                        .orElse(null);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                } else {
                    outputHandler.showPassOrderSummary(selectedPass, null);
                }
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

}
