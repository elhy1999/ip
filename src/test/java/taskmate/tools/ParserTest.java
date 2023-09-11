package taskmate.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import taskmate.exceptions.InvalidCommandTypeException;



public class ParserTest {

    @Test
    public void getCommandType_normalCommands_success() {

        String deadlineCommand = "deadline this is some long task by the user /by 2021-01-01";
        try {
            String parsedCommandType = Parser.getCommandType(deadlineCommand);
            assertEquals(parsedCommandType, "deadline");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String deleteCommand = "delete 10243";
        try {
            String parsedCommandType = Parser.getCommandType(deleteCommand);
            assertEquals(parsedCommandType, "delete");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String eventCommand = "event another event task /from 1999-12-01 /to 2035-06-30";
        try {
            String parsedCommandType = Parser.getCommandType(eventCommand);
            assertEquals(parsedCommandType, "event");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String exitCommand = "bye";
        try {
            String parsedCommandType = Parser.getCommandType(exitCommand);
            assertEquals(parsedCommandType, "bye");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String findCommand = "find 243";
        try {
            String parsedCommandType = Parser.getCommandType(findCommand);
            assertEquals(parsedCommandType, "find");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String helpCommand = "help";
        try {
            String parsedCommandType = Parser.getCommandType(helpCommand);
            assertEquals(parsedCommandType, "help");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String listCommand = "list";
        try {
            String parsedCommandType = Parser.getCommandType(listCommand);
            assertEquals(parsedCommandType, "list");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String markCommand = "mark 90";
        try {
            String parsedCommandType = Parser.getCommandType(markCommand);
            assertEquals(parsedCommandType, "mark");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String todoCommand = "todo go to buy groceries";
        try {
            String parsedCommandType = Parser.getCommandType(todoCommand);
            assertEquals(parsedCommandType, "todo");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String unmarkCommand = "unmark 90";
        try {
            String parsedCommandType = Parser.getCommandType(unmarkCommand);
            assertEquals(parsedCommandType, "unmark");
        } catch (InvalidCommandTypeException e) {
            fail();
        }
    }

    @Test
    public void getCommandType_normalCommandsWithTrailingWhitespaces_success() {

        String deadlineCommand = " deadline this is some long task by the user /by 2021-01-01";
        try {
            String parsedCommandType = Parser.getCommandType(deadlineCommand);
            assertEquals(parsedCommandType, "deadline");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String deleteCommand = " delete 10243";
        try {
            String parsedCommandType = Parser.getCommandType(deleteCommand);
            assertEquals(parsedCommandType, "delete");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String eventCommand = " event another event task /from 1999-12-01 /to 2035-06-30";
        try {
            String parsedCommandType = Parser.getCommandType(eventCommand);
            assertEquals(parsedCommandType, "event");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String exitCommand = " bye ";
        try {
            String parsedCommandType = Parser.getCommandType(exitCommand);
            assertEquals(parsedCommandType, "bye");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String findCommand = " find 243";
        try {
            String parsedCommandType = Parser.getCommandType(findCommand);
            assertEquals(parsedCommandType, "find");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String helpCommand = " help ";
        try {
            String parsedCommandType = Parser.getCommandType(helpCommand);
            assertEquals(parsedCommandType, "help");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String listCommand = " list ";
        try {
            String parsedCommandType = Parser.getCommandType(listCommand);
            assertEquals(parsedCommandType, "list");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String markCommand = " mark 90";
        try {
            String parsedCommandType = Parser.getCommandType(markCommand);
            assertEquals(parsedCommandType, "mark");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String todoCommand = " todo go to buy groceries";
        try {
            String parsedCommandType = Parser.getCommandType(todoCommand);
            assertEquals(parsedCommandType, "todo");
        } catch (InvalidCommandTypeException e) {
            fail();
        }

        String unmarkCommand = " unmark 90";
        try {
            String parsedCommandType = Parser.getCommandType(unmarkCommand);
            assertEquals(parsedCommandType, "unmark");
        } catch (InvalidCommandTypeException e) {
            fail();
        }
    }


    @Test
    public void getCommandType_invalidCommand_exceptionThrown() {
        String invalidCommand = "goodbye";
        try {
            Parser.getCommandType(invalidCommand);
            fail();
        } catch (InvalidCommandTypeException e) {
            assertNull(e.getMessage());
        }

        String typoCommand = "umark";
        try {
            Parser.getCommandType(typoCommand);
            fail();
        } catch (InvalidCommandTypeException e) {
            assertNull(e.getMessage());
        }

        String emptyCommand = "";
        try {
            Parser.getCommandType(emptyCommand);
            fail();
        } catch (InvalidCommandTypeException e) {
            assertNull(e.getMessage());
        }
    }

}
