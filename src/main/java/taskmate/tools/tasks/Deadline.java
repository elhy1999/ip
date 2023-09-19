package taskmate.tools.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import taskmate.exceptions.InvalidByException;
import taskmate.exceptions.InvalidDeadlineUpdateException;

/**
 * The Deadline class is a child class of the Task class that represents a 'Deadline' type task specified by the user.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Deadline constructor that allows the developer to specify the name of the task, and a date that represents
     * the date that this task must be completed. This date must be a String in the form "YYYY-mm-dd" to be parsed to a
     * `LocalDate` object.
     *
     * @param name the name of the deadline task.
     * @param by the date that the deadline task has to be completed. It has to be of the form "YYYY-mm-dd".
     */
    public Deadline(String name, String by) {
        super(name);
        assert by != null;
        this.by = LocalDate.parse(by);
    }

    /**
     * Deadline constructor that allows the developer to specify the name of the task, and a date that represents
     * the date that this task must be completed. This date must be a `LocalDate` instance.
     *
     * @param name the name of the deadline task.
     * @param by the date that the deadline task has to be completed.
     */
    public Deadline(String name, LocalDate by) {
        super(name);
        assert by != null;
        this.by = by;
    }

    /**
     * Deadline constructor that allows the developer to specify the name of the task, a date that represents the date
     * that this task must be completed, and a boolean that represents if the task has been completed. This date must be
     * a String in the form "YYYY-mm-dd" to be parsed to a `LocalDate` object.
     *
     * @param name the name of the deadline task.
     * @param by the date that the deadline task has to be completed. It has to be of the form "YYYY-mm-dd".
     * @param isDone a boolean variable that represents if the task has been completed.
     */
    public Deadline(String name, String by, boolean isDone) {
        super(name, isDone);
        assert by != null;
        this.by = LocalDate.parse(by);
    }

    String getTaskType() {
        return "Deadline";
    }

    String getByFormatted() {
        return this.by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    String getBy() {
        return this.by.toString();
    }

    @Override
    public HashMap<String, String> update(HashMap<String, String> changes) throws InvalidDeadlineUpdateException,
            InvalidByException {
        HashMap<String, String> successfulUpdates = new HashMap<>();

        // Check if update command is valid
        for (HashMap.Entry<String, String> attributeValuePair : changes.entrySet()) {
            String attribute = attributeValuePair.getKey();
            String newValue = attributeValuePair.getValue();
            if (!attribute.equals("/name") & !attribute.equals("/by")) {
                throw new InvalidDeadlineUpdateException();
            } else if (attribute.equals("/by") & !super.checkValidDateFormat(newValue)) {
                throw new InvalidByException();
            }
        }

        for (HashMap.Entry<String, String> attributeValuePair : changes.entrySet()) {
            String attribute = attributeValuePair.getKey();
            String newValue = attributeValuePair.getValue();
            if (attribute.equals("/name")) {
                setName(newValue);
                successfulUpdates.put("name", newValue);
            } else if (attribute.equals("/by")) {
                setBy(newValue);
                successfulUpdates.put("by", newValue);
            }
        }
        return successfulUpdates;
    }

    private void setName(String newName) {
        this.name = newName;
    }

    private void setBy(String newBy) {
        this.by = LocalDate.parse(newBy);
    }

    @Override
    public String toString() {
        return "[D][" + (this.getIsDone() ? 'X' : ' ') + "] " + this.name + " (by: " + this.getByFormatted() + ")";
    }

    @Override
    public String formatTaskForSaving() {
        return "[D][" + (this.getIsDone() ? 'X' : ' ') + "] " + this.name + " (by: " + this.getBy() + ")";
    }


}
