package com.rojojun.studycafe;

import com.rojojun.studycafe.exception.AppException;
import com.rojojun.studycafe.io.IOHandler;
import com.rojojun.studycafe.io.OutputHandler;
import com.rojojun.studycafe.io.StudyCafeFileHandler;
import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {
    private final IOHandler ioHandler = new IOHandler();

    public void run() {
        try {
            ioHandler.showProgramWelcomeMessage();

            StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelection();

            StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            StudyCafePass selectedPass = extractStudyCafePassFromUserInput(studyCafePasses, studyCafePassType);
            ioHandler.showPassOrderSummary(selectedPass);
            if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                Optional<StudyCafeLockerPass> lockerPass = lockerPasses.stream()
                        .filter(option -> selectedPass.isPassTypeEqual(option) && selectedPass.isDurationEqual(option))
                        .findFirst();
//                        .orElse(null);


                lockerPass.ifPresent(ioHandler::askLockerSelection);

                lockerPass.ifPresent(studyCafeLockerPass -> ioHandler.askLockerSelection(selectedPass, studyCafeLockerPass));

                boolean lockerSelection = false;
                if (lockerPass.isPresent()) {
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

    private StudyCafePass extractStudyCafePassFromUserInput(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        List<StudyCafePass> passList = studyCafePasses.stream()
                .filter(studyCafePassType::isTypeEqual)
                .toList();
        return ioHandler.askPassListForSelection(passList);
    }

}
