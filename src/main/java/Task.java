public abstract class Task {
//    static ArrayList<Task> allTasks = new ArrayList<Task>();
//    static int numIncompleteTasks = 0;

    String name;
    boolean isDone;

    Task(String name) {
//        allTasks.add(this);
        this.name = name;
        this.isDone = false;
//        Task.numIncompleteTasks++;
    }

//    static ArrayList<Task> getAllTasks() {
//        return allTasks;
//    }

//    static Task removeTask(int index) {
//        // Takes in an index (starting from 0 to n-1) and removes that task from Task.allTasks
//        Task removedTask = allTasks.get(index);
//        allTasks.remove(index);
//        return removedTask;
//    }

    void markAsDone() {
        this.isDone = true;
//        Task.numIncompleteTasks--;
    }

    void markAsNotDone() {
        this.isDone = false;
//        Task.numIncompleteTasks++;
    }

    boolean getIsDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return this.name;
    }

//    static int getNumIncompleteTasks() {
//        return Task.numIncompleteTasks;
//    }

    abstract String getTaskType();

//    static String formatAllTasksForSaving() {
//        String returnString = "";
//        for (Task t : getAllTasks()) {
//            returnString += t.formatTaskForSaving();
//            returnString += "\n";
//        }
//        return returnString;
//    }

    abstract String formatTaskForSaving();
    // String format to save the task to disk



}
