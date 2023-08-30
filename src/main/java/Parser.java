import java.awt.event.HierarchyListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    static Command parse(String userInput) throws InvalidCommandTypeException, EmptyDescriptionException, EmptyByException, InvalidByException, InvalidToException, EmptyToException, InvalidFromException, EmptyFromException, NotAnIntegerException {
        String commandType = getCommandType(userInput);
        // bye
        if (commandType.equals(TaskMate.CommandTypes.bye.toString())) {
            return new ExitCommand();
        // help
        } else if (commandType.equals(TaskMate.CommandTypes.help.toString())) {
            return new HelpCommand();
        // list
        } else if (commandType.equals(TaskMate.CommandTypes.list.toString())) {
            return new ListCommand();
        // unmark i
        } else if (commandType.equals(TaskMate.CommandTypes.unmark.toString())) {
            checkValidUnmarkCommand(userInput);
            int indexToUnmark = Integer.parseInt(userInput.substring(TaskMate.CommandTypes.unmark.toString().length()).trim());
            indexToUnmark -= 1;
            return new UnmarkCommand(indexToUnmark);
        // mark i
        } else if (commandType.equals(TaskMate.CommandTypes.mark.toString())) {
            checkValidMarkCommand(userInput);
            int indexToMark = Integer.parseInt(userInput.substring(TaskMate.CommandTypes.mark.toString().length()).trim());
            indexToMark -= 1;
            return new MarkCommand(indexToMark);
        // to-do description
        } else if (commandType.equals(TaskMate.CommandTypes.todo.toString())) {
            checkValidTodoCommand(userInput);
            return new TodoCommand(userInput.substring(TaskMate.CommandTypes.todo.toString().length()+1)); // +1 because we do not want the task name to start from the space character
        // deadline description /by date
        } else if (commandType.equals(TaskMate.CommandTypes.deadline.toString())) {
            checkValidDeadlineCommand(userInput);
            userInput = userInput.substring(TaskMate.CommandTypes.deadline.toString().length()+1); // +1 because we do not want the task name to start from the space character
            String[] splitUserInput = userInput.split(" /");
            return new DeadlineCommand(
                    splitUserInput[0],
                    splitUserInput[1].replace("by ", "")
            );
        // event description /from date /to date
        } else if (commandType.equals(TaskMate.CommandTypes.event.toString())) {
            checkValidEventCommand(userInput);
            userInput = userInput.substring(TaskMate.CommandTypes.event.toString().length()+1); // +1 because we do not want the task name to start from the space character
            String[] splitUserInput = userInput.split(" /");
            return new EventCommand(
                    splitUserInput[0],
                    splitUserInput[1].replace("from ", ""),
                    splitUserInput[2].replace("to ", "")
            );
        // delete i
        } else if (commandType.equals(TaskMate.CommandTypes.delete.toString())) {
            checkValidDeleteCommand(userInput);
            int indexToDelete = Integer.parseInt(userInput.substring(TaskMate.CommandTypes.delete.toString().length()).trim());
            indexToDelete -= 1; // subtract 1 as the arraylist is zero-indexed
            return new DeleteCommand(indexToDelete);
        // Invalid input
        } else {
            throw new InvalidCommandTypeException();
        }
    }

    static String getCommandType(String userInput) throws InvalidCommandTypeException {
        // Returns the type of command input by the user
        // Possible values: "to\-do", "deadline", "event", "bye", "list", "mark", "unmark"
        for (TaskMate.CommandTypes type : TaskMate.CommandTypes.values()) {
            String typeString = type.toString();
            if (userInput.startsWith(typeString)) {
                return typeString;
            }
        }
        throw new InvalidCommandTypeException();
    }

    static void checkValidTodoCommand(String userInput) throws InvalidCommandTypeException, EmptyDescriptionException {
        // Checks if "to-do" command is valid by checking if there is text coming after the word "to-do"
        if (!userInput.startsWith(TaskMate.CommandTypes.todo.toString())) {
            throw new InvalidCommandTypeException();
        } else if (userInput.substring(TaskMate.CommandTypes.todo.toString().length()).isEmpty()) {
            throw new EmptyDescriptionException();
        }
    }

    static void checkValidDeadlineCommand(String userInput) throws InvalidCommandTypeException, EmptyDescriptionException, EmptyByException, InvalidByException {
        // Checks if "deadline" command is valid by checking if there is text coming after the word "deadline"
        // Additionally, checks if there is a "/by " substring within userInput, and if the date after "/by " substring
        // can be parsed into a date
        if (!userInput.startsWith(TaskMate.CommandTypes.deadline.toString())) {
            throw new InvalidCommandTypeException();
        } else if (userInput.substring(TaskMate.CommandTypes.deadline.toString().length()).isEmpty()) {
            throw new EmptyDescriptionException();
        } else if (!userInput.contains("/by ")) {
            throw new EmptyByException();
        } else {
            try {
                String delimiter = "/by ";
                String byInput = userInput.substring(userInput.indexOf(delimiter) + delimiter.length());
                LocalDate.parse(byInput);
            } catch (DateTimeParseException e) {
                throw new InvalidByException();
            }
        }
    }

    static void checkValidEventCommand(String userInput) throws InvalidCommandTypeException, EmptyDescriptionException, EmptyFromException, InvalidFromException, EmptyToException, InvalidToException {
        // Checks if "deadline" command is valid by checking if there is text coming after the word "deadline"
        // Additionally, checks if there are "/from " and "/to " substrings within userInput, and if the dates after
        // "/from " and "/to " substrings can be parsed into a date
        if (!userInput.startsWith(TaskMate.CommandTypes.event.toString())) {
            throw new InvalidCommandTypeException();
        } else if (userInput.substring(TaskMate.CommandTypes.event.toString().length()).isEmpty()) {
            throw new EmptyDescriptionException();
        } else if (!userInput.contains("/from ")) {
            throw new EmptyFromException();
        } else if (!userInput.contains("/to ")) {
            throw new EmptyToException();
        } else {
            String fromDelimiter = "/from ";
            String toDelimiter = "/to ";

            // Testing from clause
            try {
                String fromInput = userInput.substring(userInput.indexOf(fromDelimiter) + userInput.indexOf(toDelimiter)).trim();
                LocalDate.parse(fromInput);
            } catch (DateTimeParseException e) {
                throw new InvalidFromException();
            }

            // Testing to clause
            try {
                String toInput = userInput.substring(userInput.indexOf(toDelimiter)).trim();
                LocalDate.parse(toInput);
            } catch (DateTimeParseException e) {
                throw new InvalidToException();
            }
        }
    }

    static void checkValidMarkCommand(String userInput) throws InvalidCommandTypeException, NotAnIntegerException {
        // Checks if the user input command is a valid "mark" or "unmark" command
        // by checking if the command starts with "mark"/"unmark", followed by a whitespace,
        // followed by an integer
        // Note: Does not check if the integer is within the size of TaskList object
        String indexWithinList;
        if (!userInput.startsWith(TaskMate.CommandTypes.mark.toString())) {
            throw new InvalidCommandTypeException();
        }
        indexWithinList = userInput.substring(TaskMate.CommandTypes.mark.toString().length()).trim();
        if (!checkStringIsInteger(indexWithinList)) {
            throw new NotAnIntegerException();
        }
    }

    static void checkValidUnmarkCommand(String userInput) throws InvalidCommandTypeException, NotAnIntegerException {
        // Checks if the user input command is a valid "mark" or "unmark" command
        // by checking if the command starts with "mark"/"unmark", followed by a whitespace,
        // followed by an integer
        // Note: Does not check if the integer is within the size of TaskList object
        String indexWithinList;
        if (!userInput.startsWith(TaskMate.CommandTypes.unmark.toString())) {
            throw new InvalidCommandTypeException();
        }
        indexWithinList = userInput.substring(TaskMate.CommandTypes.unmark.toString().length()).trim();
        if (!checkStringIsInteger(indexWithinList)) {
            throw new NotAnIntegerException();
        }
    }

    static void checkValidDeleteCommand(String userInput) throws InvalidCommandTypeException, NotAnIntegerException {
        // Checks if the user input command is a valid "mark" or "unmark" command
        // by checking if the command starts with "mark"/"unmark", followed by a whitespace,
        // followed by an integer
        // Note: Does not check if the integer is within the size of TaskList object
        String indexWithinList;

        if (userInput.startsWith(TaskMate.CommandTypes.delete.toString())) {
            indexWithinList = userInput.substring(TaskMate.CommandTypes.delete.toString().length()).trim();
        } else {
            throw new InvalidCommandTypeException();
        }

        if (!checkStringIsInteger(indexWithinList)) {
            throw new NotAnIntegerException();
        }
    }

    static boolean checkStringIsInteger(String s) {
        // Returns true if String s can be parsed into an Integer object, and false otherwise
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
