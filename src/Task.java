import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *Task is the information expert that knows about alarms, priorities, and which users have access to a task
 */
public class Task {
    final String DOING = "Doing";
    final String DONE = "Done";
    final String TODO = "Todo";
    
    final String DAILY = "Daily";
    final String WEEKLY = "Weekly";
    final String MONTHLY = "Monthly";

    Date alarm = null;
    int priority = -1;
    boolean access = false;
    String id;
    String name;
    String username;
    String category;
    String status;
    String note;
    int progress;
    String frequencyOfRoutine;
    boolean isPrivate;
    boolean isRoutine;
    

    ArrayList<String> suggestions;
    ArrayList<Task> scheduledTasks;
    ArrayList<Date> alarms;


    public Task(String id, String name, String username, String category) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.category = category;
        this.status = TODO;
        this.note = null;
        this.frequencyOfRoutine = DAILY;
        this.isPrivate = true;
        this.isRoutine = false;
        suggestions = new ArrayList<String>();
        progress = 0;
        scheduledTasks = new ArrayList<Task>();
    }

    public String getID()
    {
        return this.id;
    }

    /**
     * Add an alarm to this task by checking validity of inputs and calling the putAlarm method of the
     * DatabaseSupport class
     * @param s suggestion
     * @return true, added suggestion
     */
    public boolean addSuggestion(String s)
    {
        String sugg = "\t" + s;
        suggestions.add(sugg);
        return true;
    }
    /**
     * prints suggestions
     */
    public void getSuggestions()
    {
        for(int i = 0; i<suggestions.size(); i++)
        {
            System.out.println(suggestions.get(i));
        }
    }

    /**
     * Add an alarm to this task by checking validity of inputs
     * @param d Date input for the date of the alarm
     * @return true if valid inputs, otherwise false
     */
    public boolean addAlarm(Date d){
        if(isValid(d)){
            alarm = d;
            return true;
        }
        return false;
    }

    /**
     * Adds tasks to the list of tasks that this one is scheduled to be completed simultaneously with
     * @param t task to be scheduled with this one
     */
    public void addScheduledTask(Task t) {
        scheduledTasks.add(t);
    }

    /**
     * Return the tasks scheduled with this one
     */
    public ArrayList<Task> getScheduledTasks(){
        return scheduledTasks;
    }
    /**
     * This method gives the task a priority number by calling the putPrioity method of the
     * DatabaseSupport class
     * @param p priority to set to task
     * @return true if valid priority, otherwise false
     */
    public boolean setPriority(int p) {
        if(p >= 0) {
            this.priority = p;
            return true;
        }
        return false;
    }

    /**
     * Sets the access rights for this task
     * @param access the access of the task: true = pubic, false = private
     */
    public void setAccessRightsOnTask(boolean access) {
        this.access = access;
    }

    /**
     * @return priority of this task. If priority == -1, it has not been set yet.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return alarm of the task. If alarm == null, it has not been set yet.
     */
    public Date getAlarm() {
        return alarm;
    }

    /**
     * Check the validity of the date input
     * @param d Date variable
     * @return true if date is in the future and valid, otherwise false
     */
    private boolean isValid(Date d){
        Date today = new Date();
        if(d.after(today)) {
            return true;
        }
        System.out.println("Sorry thats an invalid date. Try a date in the future");
        return false;
    }

    public String getTaskID() throws FileNotFoundException {
        File file = new File("task1.txt");
        @SuppressWarnings("resource")

        Scanner scan = new Scanner(file);
        String id = "";

        while (scan.hasNext()) {
            id = scan.next();

            if (id.equals("ID:")) {
                id = scan.next();
                System.out.println("ID: " + id);
            }
        }

        return id;
    }

    /**
     * Return the Alarm as a readable string
     * @param alarm the alarm to be turned into a string
     * @return string which represents alarm and its properties
     */
    public static String alarmToString(Date alarm) {
        return alarm.toString();
    }

    /**
     * This method allows user to set the task name
     */
    public void setName(String n) {
        name = n;
    }


    /**
     * This method allows user to set the task category
     */
    public void setCategory(String c) {
        category = c;
    }

    /**
     * This method allows user to set the task alarm
     */
    public void setAlarm(Date a) {
        alarm = a;
    }

    public void setStatusTodo() {status = TODO;}

    public void setStatusDone() {status = DONE;}

    public void setStatusDoing() {status = DOING;}

    public String getStatus(){ return status; }
    
    public String getFrequencyOfRoutine(){ return frequencyOfRoutine; }

    public void setNote(String note){ this.note = note;}

    public void setIsPrivate (boolean isPrivate){this.isPrivate = isPrivate;}

    public void updateProgress(int progressVal)
    {
        progress = progressVal;
    }

    public String getProgress()
    {
        String progressString = progress + "%";
        return progressString;
    }
}