package com.rojojun.studycafe;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.IOHandler;
import com.rojojun.studycafe.io.provider.CafeInfoFileProvider;
import com.rojojun.studycafe.io.provider.LockerInfoFileProvider;
import com.rojojun.studycafe.model.*;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {
    private final IOHandler ioHandler = new IOHandler();
    private final CafeInfoFileProvider cafeInfoFileProvider = new CafeInfoFileProvider();
    private final LockerInfoFileProvider lockerInfoFileProvider = new LockerInfoFileProvider();

    public void run() {
        try {
            ioHandler.showProgramWelcomeMessage();

            StudyCafePassType studyCafePassTypeFromUser = ioHandler.askPassTypeSelection();

            StudyCafePasses studyCafePasses = cafeInfoFileProvider.readStudyCafePasses();

            StudyCafePass selectedPass = extractStudyCafePassFromUserInput(studyCafePasses, studyCafePassTypeFromUser);
            ioHandler.showPassOrderSummary(selectedPass);

            if (StudyCafePassType.hasLockerOption(studyCafePassTypeFromUser)) {
                handleLockerOption(selectedPass);
            }
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void handleLockerOption(StudyCafePass selectedPass) {
        StudyCafeLockerPasses lockerPasses = lockerInfoFileProvider.readLockerPasses();
        Optional<StudyCafeLockerPass> lockerPass = findMatchingLockerPass(lockerPasses, selectedPass);

        boolean lockerSelection = lockerPass.isPresent() && ioHandler.askLockerSelection(lockerPass.get());

        if (lockerSelection) {
            ioHandler.showPassOrderSummary(selectedPass, lockerPass.get());
        } else {
            ioHandler.showPassOrderSummary(selectedPass);
        }
    }

    private Optional<StudyCafeLockerPass> findMatchingLockerPass(StudyCafeLockerPasses lockerPasses, StudyCafePass selectedPass) {
        return lockerPasses.findMatchingLockerPass(selectedPass);
    }

    private StudyCafePass extractStudyCafePassFromUserInput(StudyCafePasses studyCafePasses, StudyCafePassType studyCafePassType) {
        List<StudyCafePass> passList = studyCafePasses.getListBy(studyCafePassType);
        return ioHandler.askPassListForSelection(passList);
    }
}
