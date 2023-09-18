package taskmate.tools.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import taskmate.exceptions.InvalidEventUpdateException;
import taskmate.exceptions.InvalidFromException;
import taskmate.exceptions.InvalidToException;

/**
 * The Event class is a child class of the Task class that represents a 'Event' type task specified by the user.
 */
public class Event extends Task {

    private LocalDate startDatetime;
    private LocalDate endDatetime;

    /**
     * Event constructor that allows the developer to specify the name of the task, a date that represents
     * the date that this task starts, and a date that represents the date that this task ends. These dates must
     * be String instances in the form "YYYY-mm-dd" to be parsed to `LocalDate` instances.
     *
     * @param name the name of the event task.
     * @param startDatetime the date that the event task starts. It has to be of the form "YYYY-mm-dd".
     * @param endDatetime the date that the event task ends. It has to be of the form "YYYY-mm-dd".
     */
    public Event(String name, String startDatetime, String endDatetime) {
        super(name);
        assert startDatetime != null;
        assert endDatetime != null;
        this.startDatetime = LocalDate.parse(startDatetime);
        this.endDatetime = LocalDate.parse(endDatetime);
    }

    /**
     * Event constructor that allows the developer to specify the name of the task, a date that represents
     * the date that this task starts, and a date that represents the date that this task ends. These dates must be
     * `LocalDate` instances.
     *
     * @param name the name of the event task.
     * @param startDatetime the date that the event task starts.
     * @param endDatetime the date that the event task ends.
     */
    public Event(String name, LocalDate startDatetime, LocalDate endDatetime) {
        super(name);
        assert startDatetime != null;
        assert endDatetime != null;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    /**
     * Event constructor that allows the developer to specify the name of the task, a date that represents
     * the date that this task starts, a date that represents the date that this task ends, and a boolean that
     * represents if the task has been completed. These dates must be String instances in the form "YYYY-mm-dd" to be
     * parsed to `LocalDate` instances.
     *
     * @param name the name of the event task.
     * @param startDatetime the date that the event task starts. It has to be of the form "YYYY-mm-dd".
     * @param endDatetime the date that the event task ends. It has to be of the form "YYYY-mm-dd".
     * @param isDone a boolean variable that represents if the task has been completed.
     */
    public Event(String name, String startDatetime, String endDatetime, boolean isDone) {
        super(name, isDone);
        assert startDatetime != null;
        assert endDatetime != null;
        this.startDatetime = LocalDate.parse(startDatetime);
        this.endDatetime = LocalDate.parse(endDatetime);
    }

    @Override
    String getTaskType() {
        return "Event";
    }

    String getStartDatetimeFormatted() {
        return this.startDatetime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    String getEndDatetimeFormatted() {
        return this.endDatetime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    String getStartDatetime() {
        return this.startDatetime.toString();
    }

    String getEndDatetime() {
        return this.endDatetime.toString();
    }

    @Override
    public HashMap<String, String> update(HashMap<String, String> changes) throws InvalidEventUpdateException,
            InvalidFromException, InvalidToException {
        HashMap<String, String> successfulUpdates = new HashMap<>();

        // Check if update command is valid
        for (HashMap.Entry<String, String> attributeValuePair : changes.entrySet()) {
            String attribute = attributeValuePair.getKey();
            String newValue = attributeValuePair.getValue();
            if (!attribute.equals("/name") & !attribute.equals("/from") & !attribute.equals("/to")) {
                throw new InvalidEventUpdateException();
            } else if (attribute.equals("/from") & !super.checkValidDateFormat(newValue)) {
                throw new InvalidFromException();
            } else if (attribute.equals("/to") & !super.checkValidDateFormat(newValue)) {
                throw new InvalidToException();
            }
        }

        for (HashMap.Entry<String, String> attributeValuePair : changes.entrySet()) {
            String attribute = attributeValuePair.getKey();
            String newValue = attributeValuePair.getValue();
            if (attribute.equals("/name")) {
                setName(newValue);
                successfulUpdates.put("name", newValue);
            } else if (attribute.equals("/from")) {
                setStartDatetime(newValue);
                successfulUpdates.put("from", newValue);
            } else if (attribute.equals("/to")) {
                setEndDatetime(newValue);
                successfulUpdates.put("to", newValue);
            } else {
                throw new InvalidEventUpdateException();
            }
        }
        return successfulUpdates;
    }

    private void setName(String newName) {
        this.name = newName;
    }

    private void setStartDatetime(String newStartDatetime) {
        this.startDatetime = LocalDate.parse(newStartDatetime);
    }

    private void setEndDatetime(String newEndDatetime) {
        this.endDatetime = LocalDate.parse(newEndDatetime);
    }

    @Override
    public String toString() {
        return "[E][" + (this.getIsDone() ? 'X' : ' ') + "] " + this.name + " (from: "
                + this.getStartDatetimeFormatted() + " to: " + this.getEndDatetimeFormatted() + ")";
    }

    @Override
    public String formatTaskForSaving() {
        return "[E][" + (this.getIsDone() ? 'X' : ' ') + "] " + this.name + " (from: "
                + this.getStartDatetime() + " to: " + this.getEndDatetime() + ")";
    }
}
