package com.rojojun.studycafe;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.IOHandler;
import com.rojojun.studycafe.io.StudyCafeFileHandler;
import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {
    private final IOHandler ioHandler = new IOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showProgramWelcomeMessage();

            StudyCafePassType studyCafePassTypeFromUser = ioHandler.askPassTypeSelection();

            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            StudyCafePass selectedPass = extractStudyCafePassFromUserInput(studyCafePasses, studyCafePassTypeFromUser);
            ioHandler.showPassOrderSummary(selectedPass);

            if (StudyCafePassType.hasLockerOption(studyCafePassTypeFromUser)) {
                handleLockerOption(studyCafeFileHandler, selectedPass);
            }
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void handleLockerOption(StudyCafeFileHandler studyCafeFileHandler, StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        Optional<StudyCafeLockerPass> lockerPass = findMatchingLockerPass(lockerPasses, selectedPass);

        boolean lockerSelection = lockerPass.isPresent() && ioHandler.askLockerSelection(lockerPass.get());

        if (lockerSelection) {
            ioHandler.showPassOrderSummary(selectedPass, lockerPass.get());
        } else {
            ioHandler.showPassOrderSummary(selectedPass);
        }
    }

    private Optional<StudyCafeLockerPass> findMatchingLockerPass(List<StudyCafeLockerPass> lockerPasses, StudyCafePass selectedPass) {
        return lockerPasses.stream()
                .filter(option -> selectedPass.isPassTypeEqual(option) && selectedPass.isDurationEqual(option))
                .findFirst();
    }

    private StudyCafePass extractStudyCafePassFromUserInput(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        List<StudyCafePass> passList = studyCafePasses.stream()
                .filter(studyCafePassType::isTypeEqual)
                .toList();
        return ioHandler.askPassListForSelection(passList);
    }

}
