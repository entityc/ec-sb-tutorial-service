package org.entityc.tutorial.util;

import org.entityc.tutorial.exception.ServiceException;
import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.service.ExerciseService;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

@Service
public class MarkdownImporter {

    private final ModuleService moduleService;
    private final SessionService sessionService;
    private final ExerciseService exerciseService;
    private final StepService stepService;

    @Autowired
    public MarkdownImporter(
            ModuleService moduleService,
            SessionService sessionService,
            ExerciseService exerciseService,
            StepService stepService
    ) {
        this.moduleService = moduleService;
        this.sessionService = sessionService;
        this.exerciseService = exerciseService;
        this.stepService = stepService;
    }

    public static Vector<Object> ParseHeadingLine(String line, final String prefix, boolean expectingNumber, boolean expectingTitle) {
        Vector<Object> headingParts = new Vector<>();
        int runningIndex = 0;
        if (prefix != null) {
            if (!line.startsWith(prefix)) {
                return null;
            }
            runningIndex = prefix.length() + 1;
        }
        if (expectingNumber) {
            int colonIndex;
            if (expectingTitle) {
                colonIndex = line.indexOf(":");
                if (colonIndex == -1) {
                    return null;
                }
            } else {
                colonIndex = line.length();
            }
            String numberString = line.substring(runningIndex, colonIndex);
            Integer numberValue = null;
            try {
                numberValue = Integer.valueOf(numberString);
            } catch (NumberFormatException e) {
                return null;
            }
            if (numberValue == null) {
                return null;
            }
            headingParts.add(numberValue);
            runningIndex = colonIndex + 1;
        }
        if (expectingTitle) {
            String title = line.substring(runningIndex).trim();
            headingParts.add(title);
        }
        return headingParts;
    }

    public static int GetLevelOfLine(String line) {
        int level = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
                level++;
            } else {
                break;
            }
        }
        return level;
    }

    public boolean validateModuleMarkdown(final UUID tutorialId, final String markdownText) throws ServiceException {
        return importModuleMarkdown(tutorialId, markdownText, true);
    }

    public boolean importModuleMarkdown(final UUID tutorialId, final String markdownText) throws ServiceException {
        return importModuleMarkdown(tutorialId, markdownText, false);
    }

    private boolean importModuleMarkdown(final UUID tutorialId, final String markdownText, final boolean validateOnly) throws ServiceException {

        MarkdownImportState importState = MarkdownImportState.ModuleHeading;
        Module currentModule = null;
        Session currentSession = null;
        Exercise currentExercise = null;
        Step currentStep = null;

        StringBuilder bodyBuilder = new StringBuilder();
        Scanner scanner = new Scanner(markdownText);
        while (true) {
            String line = scanner.hasNextLine() ? scanner.nextLine() : null;
            int lineLevel = line != null ? MarkdownImporter.GetLevelOfLine(line) : -1;
            int repeat = 0;
            do {
                switch (importState) {
                    case ModuleHeading:
                        if (lineLevel == 1) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Module", true, true);
                            if (results == null) {
                                return false;
                            }
                            if (!validateOnly) {
                                String title = (String) results.elementAt(1);
                                currentModule = new Module();
                                currentModule.setNumber((Integer) results.elementAt(0));
                                currentModule.setTitle(title);
                                currentModule = moduleService.createModuleWithTutorial(tutorialId, currentModule);
                            }
                            importState = MarkdownImportState.ModuleOverviewBody;
                            bodyBuilder = new StringBuilder();
                        } else {
                            return false; // expecting to find this level
                        }
                        break;

                    case ModuleOverviewBody: {
                        boolean foundOutside = lineLevel == -1 || lineLevel == 1;
                        if (!foundOutside && lineLevel == 2) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            if (lineMinusNotation.startsWith("Session")) {
                                foundOutside = true;
                            }
                        }
                        if (foundOutside) {
                            if (!validateOnly) {
                                String overview = bodyBuilder.toString();
                                currentModule.setOverview(overview);
                                moduleService.updateModule(currentModule);
                            }
                            importState = stateForLevel(lineLevel);
                            repeat = 1;
                        }
                        if (bodyBuilder.length() == 0 && (line == null || line.trim().isEmpty())) {
                            continue;
                        }
                        bodyBuilder.append(line);
                        bodyBuilder.append("\n");
                    }
                    break;

                    case SessionHeading:
                        if (lineLevel == 2) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Session", true, true);
                            if (results == null) {
                                return false;
                            }
                            if (!validateOnly) {
                                String title = (String)results.elementAt(1);
                                currentSession = new Session();
                                currentSession.setNumber((Integer) results.elementAt(0));
                                currentSession.setTitle(title);
                                currentSession.setObjective("");
                                currentSession.setDiscussion("");
                                currentSession = sessionService.createSessionWithModule(currentModule.getId(), currentSession);
                            }
                            importState = MarkdownImportState.SessionObjectiveHeading;
                        } else {
                            return false; // expecting to find this level
                        }
                        break;
                    case SessionObjectiveHeading:
                        if (lineLevel == 0 && line.isEmpty()) {
                            // just skip blank lines before we get to the heading
                        } else if (lineLevel == 3) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Objective", false, false);
                            if (results == null) {
                                return false;
                            }
                            importState = MarkdownImportState.SessionObjectiveBody;
                            bodyBuilder = new StringBuilder();
                        } else {
                            return false; // expecting to find this level
                        }
                        break;
                    case SessionObjectiveBody: {
                        boolean foundOutside = lineLevel == -1 || (lineLevel > 0 && lineLevel <= 2);
                        if (!foundOutside && lineLevel == 3) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            if (lineMinusNotation.startsWith("Discussion")) {
                                foundOutside = true;
                            }
                        }
                        if (foundOutside) {
                            if (!validateOnly) {
                                String objective = (bodyBuilder.toString());
                                currentSession.setObjective(objective);
                                sessionService.updateSession(currentSession);
                            }
                            importState = MarkdownImportState.SessionDiscussionHeading;
                            bodyBuilder = new StringBuilder();
                            repeat = 1;
                        } else {
                            if (bodyBuilder.length() == 0 && line.trim().isEmpty()) {
                                continue;
                            }
                            bodyBuilder.append(line);
                            bodyBuilder.append("\n");
                        }
                    }
                    break;
                    case SessionDiscussionHeading:
                        if (lineLevel == 0 && line.isEmpty()) {
                            // just skip blank lines before we get to the heading
                        } else if (lineLevel == 3) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Discussion", false, false);
                            if (results == null) {
                                return false;
                            }
                            importState = MarkdownImportState.SessionDiscussionBody;
                            bodyBuilder = new StringBuilder();
                        } else {
                            return false; // expecting to find this level
                        }
                        break;
                    case SessionDiscussionBody: {
                        boolean foundOutside = lineLevel == -1 || (lineLevel > 0 && lineLevel <= 2);
                        if (!foundOutside && lineLevel == 3) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            if (lineMinusNotation.startsWith("Exercise")) {
                                foundOutside = true;
                            }
                        }
                        if (foundOutside) {
                            if (!validateOnly) {
                                String discussion = (bodyBuilder.toString());
                                currentSession.setDiscussion(discussion);
                                sessionService.updateSession(currentSession);
                            }
                            importState = stateForLevel(lineLevel);
                            bodyBuilder = new StringBuilder();
                            repeat = 1;
                        } else {
                            if (bodyBuilder.length() == 0 && line.trim().isEmpty()) {
                                continue;
                            }
                            bodyBuilder.append(line);
                            bodyBuilder.append("\n");
                        }
                    }
                    break;
                    case ExerciseHeading:
                        if (lineLevel == 0 && line.isEmpty()) {
                            // just skip blank lines before we get to the heading
                        } else if (lineLevel == 3) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Exercise", false, false);
                            if (results == null) {
                                return false;
                            }
                            if (!validateOnly) {
                                currentExercise = new Exercise();
                                currentExercise.setNumber(1);
                                currentExercise = exerciseService.createExerciseWithSession(currentSession.getId(), currentExercise);
                            }
                            importState = MarkdownImportState.ExerciseSummaryBody;
                            bodyBuilder = new StringBuilder();
                        } else {
                            return false; // expecting to find this level
                        }
                        break;
                    case ExerciseSummaryBody: {
                        boolean foundOutside = lineLevel == -1 || (lineLevel > 0 && lineLevel <= 3);
                        if (!foundOutside && lineLevel == 4) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            if (lineMinusNotation.startsWith("Step")) {
                                foundOutside = true;
                            }
                        }
                        if (foundOutside) {
                            if (!validateOnly) {
                                String overview = (bodyBuilder.toString());
                                currentExercise.setOverview(overview);
                                exerciseService.updateExercise(currentExercise);
                            }
                            importState = stateForLevel(lineLevel);
                            bodyBuilder = new StringBuilder();
                            repeat = 1;
                        } else {
                            if (bodyBuilder.length() == 0 && line.trim().isEmpty()) {
                                continue;
                            }
                            bodyBuilder.append(line);
                            bodyBuilder.append("\n");
                        }
                    }
                    break;
                    case StepHeading:
                        if (lineLevel == 4) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            Vector<Object> results = MarkdownImporter.ParseHeadingLine(lineMinusNotation, "Step", true, false);
                            if (results == null) {
                                return false;
                            }
                            if (!validateOnly) {
                                currentStep = new Step();
                                currentStep.setNumber((Integer) results.elementAt(0));
                                currentStep.setInstructions("");
                                currentStep = stepService.createStepWithExercise(currentExercise.getId(), currentStep);
                            }
                            importState = MarkdownImportState.StepInstructionsBody;
                            bodyBuilder = new StringBuilder();
                        } else {
                            return false; // expecting to find this level
                        }
                        break;
                    case StepInstructionsBody: {
                        boolean foundOutside = lineLevel == -1 || (lineLevel > 0 && lineLevel <= 4);
                        if (!foundOutside && lineLevel == 4) {
                            String lineMinusNotation = line.substring(lineLevel).trim();
                            if (lineMinusNotation.startsWith("Step")) {
                                foundOutside = true;
                            }
                        }
                        if (foundOutside) {
                            if (!validateOnly) {
                                String instructions = (bodyBuilder.toString());
                                currentStep.setInstructions(instructions);
                                stepService.updateStep(currentStep);
                            }
                            importState = stateForLevel(lineLevel);
                            repeat = 1;
                            bodyBuilder = new StringBuilder();
                        } else {
                            if (bodyBuilder.length() == 0 && line.trim().isEmpty()) {
                                continue;
                            }
                            bodyBuilder.append(line);
                            bodyBuilder.append("\n");
                        }
                    }
                    break;
                }
            } while (lineLevel != -1 && repeat-- > 0);
            if (lineLevel == -1) {
                break;
            }
        }
        scanner.close();

        return true;
    }

    private MarkdownImportState stateForLevel(int level) {
        MarkdownImportState importState = MarkdownImportState.ModuleHeading;
        switch (level) {
            case 1:
                importState = MarkdownImportState.ModuleHeading;
                break;
            case 2:
                importState = MarkdownImportState.SessionHeading;
                break;
            case 3:
                importState = MarkdownImportState.ExerciseHeading;
                break;
            case 4:
                importState = MarkdownImportState.StepHeading;
                break;
        }
        return importState;
    }

    private enum MarkdownImportState {
        ModuleHeading,
        ModuleOverviewBody,
        SessionHeading,
        SessionObjectiveHeading,
        SessionObjectiveBody,
        SessionDiscussionHeading,
        SessionDiscussionBody,
        ExerciseHeading,
        ExerciseSummaryBody,
        StepHeading,
        StepInstructionsBody,
    }
}
