package com.rojojun.studycafe;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.IOHandler;
import com.rojojun.studycafe.io.OutputHandler;
import com.rojojun.studycafe.io.StudyCafeFileHandler;
import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {
    private final IOHandler ioHandler = new IOHandler();

    public void run() {
        try {
            ioHandler.showProgramWelcomeMessage();

            StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelection();

            StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
                        .toList();

                StudyCafePass selectedPass = ioHandler.askPassListForSelection(hourlyPasses);
                ioHandler.showPassOrderSummary(selectedPass);
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                List<StudyCafePass> weeklyPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.WEEKLY)
                        .toList();

                StudyCafePass selectedPass = ioHandler.askPassListForSelection(weeklyPasses);
                ioHandler.showPassOrderSummary(selectedPass);
            } else if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafePass> fixedPasses = studyCafePasses.stream()
                        .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.FIXED)
                        .toList();
                StudyCafePass selectedPass = ioHandler.askPassListForSelection(fixedPasses);

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
                    lockerSelection = ioHandler.askLockerSelection(lockerPass);
                }

                if (lockerSelection) {
                    ioHandler.showPassOrderSummary(selectedPass, lockerPass);
                } else {
                    ioHandler.showPassOrderSummary(selectedPass);
                }
            }
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

}
