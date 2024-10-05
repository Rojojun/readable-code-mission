package com.rojojun.studycafe.io;

import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.List;

public class IOHandler {
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void showProgramWelcomeMessage() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
    }

    public StudyCafePassType askPassTypeSelection() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafePass askPassListForSelection(List<StudyCafePass> passList) {
        outputHandler.showPassListForSelection(passList);
        return inputHandler.getSelectPass(passList);
    }

    public boolean askLockerSelection(StudyCafeLockerPass lockerPass) {
        outputHandler.askLockerPass(lockerPass);
        return inputHandler.getLockerSelection();
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
    }

    public void showPassOrderSummary(StudyCafePass selectedPass) {
        outputHandler.showPassOrderSummary(selectedPass, null);
    }
}
