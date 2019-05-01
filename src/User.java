import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class User {
    private String id;
    private HashMap<User, Integer> ratings;
    private ArrayList<String> categories;
    private ArrayList<TaskController> tasks;
    private ArrayList<TaskController> finishedTasks;
    private String name;
    private String password;


    public User(String name, String password, String id) {
        this.name = name;
        this.password = password;
        tasks = new ArrayList<TaskController>();
        categories = new ArrayList<String>();
        this.id = id;
    }

    public boolean setTaskPriority(int taskNum, int p){
        return tasks.get(taskNum).setPriority(p);
    }

    public ArrayList<TaskController> getTasks(){
        return tasks;
    }
    
    /**
     * This method gets called when the user chooses to add a task to their task list
     * @return
     */
    public Task createTask() {

        String name = "";
        String username = "";
        String category = "";
        int priorityInt = 0;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter task name");
        name = sc.nextLine();

        if (doesTaskExist(name) == true) {
            System.out.println("Task name already exists. Please enter a new task name");

            name = sc.nextLine();
        }

        System.out.println("Enter a task category");
        category = sc.nextLine();

        System.out.println("Enter your username");
        username = sc.nextLine();

        Random random = new Random();
        int id = random.nextInt(1000) + 1;
        String idString = Integer.toString(id);

        Task task = new Task(idString, name, username, category);
        categories.add(category);

        tasks.add(new TaskController(task));

        return task;
    }

    /**
     * Asks user which task they would like to delete. Then deletes the task from the user's task list
     */
    public void deleteTask() {
        if(tasks.size() == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
        }


        System.out.println("Which task would you like to delete?");
        printTaskNamesNumbered();
        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        chosenOption--;

        System.out.println("Are you sure you want to delete task " + tasks.get(chosenOption).getName() + "? (y/n)");
        String yn = sc.next();

        if(yn.toUpperCase().equals("Y")) {
            tasks.remove(tasks.get(chosenOption));
        } else {
            System.out.println ("Exiting without deleting a task");
        }
    }

    /**
     * Sets the priority of a task
     * @return true if proirity was set successfully. Otherwise false
     */
    public void addTaskSuggestion() {
        String name = "";
        String suggestion = "";

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter task name to add suggestion");
        name = sc.nextLine();

        while (doesTaskExist(name) == false) {
            System.out.println("Task does not exist. Please enter a task that exists");
            name = sc.nextLine();
        }

        System.out.println("Enter suggestion");
        suggestion = sc.nextLine();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskInstance().name.equals(name))
            {
                tasks.get(i).getTaskInstance().addSuggestion(suggestion);
            }
        }
    }



    /**
     * This method allows the user to edit an already created task
     */
    public void editTask() {
        String name = "";
        String category = "";
        String newName = "";
        String username = "";

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter task name to edit");
        name = sc.nextLine();

        if (doesTaskExist(name) == false) {
            System.out.println("Task does not exist. Please enter a task that exists");

            name = sc.nextLine();
        }

        System.out.println("Enter new task name");
        newName = sc.nextLine();

        System.out.println("Enter new task category");
        category = sc.nextLine();

        System.out.println("Enter your username");
        username = sc.nextLine();

        for (int i = 0; i < tasks.size(); i ++) {
            if (tasks.get(i).getTaskInstance().name.equals(name)) {
                tasks.get(i).getTaskInstance().setName(newName);
                tasks.get(i).getTaskInstance().setCategory(category);
            }
        }
    }
    
    // Routine Tasks
    
    /**
     * This method gets called when the user chooses to add a task to their task list
     * @return
     * @throws ParseException 
     */
    public Task createRoutineTask() throws ParseException {
        String name = "";
        String username = "";
        String category = "";
        String routine = "";

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter routine task name");
        name = sc.nextLine();

        if (doesTaskExist(name) == true) {
            System.out.println("Task name already exists. Please enter a new task name");

            name = sc.nextLine();
        }

        System.out.println("Enter a task category");
        category = sc.nextLine();

        System.out.println("Enter your username");
        username = sc.nextLine();
        
        System.out.println("When would you like this routine task to occur?");
        System.out.println("Enter either daily, weekly, or monthly");
        routine = sc.nextLine();

        Random random = new Random();
        int id = random.nextInt(1000) + 1;
        String idString = Integer.toString(id);

        Task task = new Task(idString, name, username, category);
        task.isRoutine = true;
        
        categories.add(category);

        tasks.add(new TaskController(task));
        
        // Now, since we have a routine task, we need to set an alarm that will alert the user based on when they want it to re-occur
        if (routine.equals("daily")) {
        	task.frequencyOfRoutine = "daily";
        	
        } else if (routine.equals("weekly")) {
        	task.frequencyOfRoutine = "weekly";
        	
        } else if (routine.equals("monthly")) {
        	task.frequencyOfRoutine = "monthly";
        	
        } else {
        	System.out.println("You did not enter a correct routine phrase. Please enter either daily, weekly, or monthly");
        }
        
        
        return task;
    }
    
    /**
     * Asks user which task they would like to delete. Then deletes the task from the user's task list
     */
    public void deleteRoutineTask() {
    	
    	int numRoutine = 0;
    	
    	for (int i = 0; i < tasks.size();  i ++) {
    		if (tasks.get(i).getTaskInstance().isRoutine == false) {
    			numRoutine++;
    		}
    	}
    	
    	// no routine tasks
    	if (numRoutine == tasks.size()) {
    		System.out.println("You don't have any routine tasks. Try making a task first.");
    	} else {
    		System.out.println("Which task would you like to delete?");
            printTaskNamesNumbered();
            Scanner sc = new Scanner(System.in);
            int chosenOption = sc.nextInt();
            chosenOption--;

            System.out.println("Are you sure you want to delete task " + tasks.get(chosenOption).getName() + "? (y/n)");
            String yn = sc.next();

            if(yn.toUpperCase().equals("Y")) {
                tasks.remove(tasks.get(chosenOption));
            } else {
                System.out.println ("Exiting without deleting a task");
            }
    	}
    }


    /**
     * This method returns true if a task with a given name already exists
     * @param name: the task name which we are comparing to
     * @return true if the task does exist and false if it does not
     */
    public boolean doesTaskExist(String name) {

        for (TaskController task: tasks) {
            if (task.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns the Task as a readable string
     * @param task the task which is going to be turned into a string
     * @return string which represents task and its properties
     */
    public String taskToString(Task task) {
        String taskObject = " Username: " + task.username;
        if(task.alarm != null) {
            taskObject = taskObject + "\n Alarm: " + task.alarm;
        }
        if(task.priority != -1) {
            taskObject = taskObject + "\n Priority: " + task.priority;
        }

        if(task.note != null){
            taskObject = taskObject + "\n Note: " + task.note;
        }
        taskObject = taskObject + "\n ID: " + task.id + "\n Name: " + task.name + "\n Category: " + task.category + "\n Status: " + task.getStatus();

        if(task.isPrivate){
            taskObject = taskObject +"\n This task is PRIVATE";
        }else{
            taskObject = taskObject +"\n This task is PUBLIC";
        }

        if(task.isRoutine) {
            taskObject = taskObject +"\n Frequency of task: " + task.frequencyOfRoutine;
        }

        taskObject = taskObject + "\n Scheduled Tasks: ";
        for(int i = 0; i < task.getScheduledTasks().size(); i++) {
            taskObject = taskObject + task.getScheduledTasks().get(i).name;
            if(i < task.getScheduledTasks().size() - 1) {
                taskObject = taskObject + ", ";
            }
        }
        return taskObject;
    }

    public void printAllTasks() {
        for(int i = 0; i< tasks.size(); i++) {
            System.out.println(taskToString(tasks.get(i).getTaskInstance()));
            System.out.println("Progress: " + tasks.get(i).getTaskInstance().getProgress());
            System.out.println("Suggestions");
            tasks.get(i).getTaskInstance().getSuggestions();
            System.out.println();
        }
    }
    
    public void printAllAlarms() {
        for(int i = 0; i< tasks.size(); i++) {
            System.out.println(tasks.get(i).getTaskInstance().alarms.get(i));
            System.out.println();
        }
    }
    
    public void printAllRoutineTasks() {
        for (int i = 0; i< tasks.size(); i++) {
        	if (tasks.get(i).getTaskInstance().isRoutine == true) {
        		System.out.println(taskToString(tasks.get(i).getTaskInstance()));
        		
                System.out.println("Suggestions");
                tasks.get(i).getTaskInstance().getSuggestions();
                System.out.println();
        	} else {
        		System.out.println("No routine tasks found.");
        	}
        }
    }

    /*
     * This will print a list of tasks that have the status set to TODO
     */
    public void viewCurrentTasks() {
        int count = 0;

        for(int i = 0; i< tasks.size(); i++) {

            if (tasks.get(i).getTaskInstance().status.equals("Todo")) {
                System.out.println(taskToString(tasks.get(i).getTaskInstance()));
                System.out.println();
            } else {
                count++;
            }

            if (count == tasks.size()) {
                System.out.println("Hooray! You don't have any tasks to do");
            }
        }
    }

    public boolean createCategories(String category) {
        //If category already exists, return false
        if(categories.contains(category)) {
            return false;
        }
        //Else, add the new category name
        categories.add(category);
        return true;
    }

    public boolean addAnonymousRating(User rater, int rating) {
        //This will either create a new rating or update the old one from the rater
        ratings.put(rater, rating);
        return true;
    }

    public TaskController getTask(int i) {

        return tasks.get(i);
    }

    public int numTasks() {

        return tasks.size();
    }

    public void getAllCategories()
    {
        System.out.println("Categories:\n");
        for(int i=0; i<categories.size(); i++)
        {
            System.out.println(categories.get(i));
        }
    }

    public boolean categorizeTask()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter task id");
        String taskID = scan.next();
        System.out.println("Select Category:");
        String selCategory = scan.next();

        boolean exists = false;
        for(int j = 0; j<categories.size(); j++)
        {
            if(categories.get(j).equals(selCategory))
            {
                exists = true;
            }
        }
        if(!categories.contains(selCategory))
        {
            createCategories(selCategory);
        }



        for(int i=0; i<tasks.size(); i++)
        {
            if(tasks.get(i).getTaskInstance().getID().equals(taskID))
            {
                tasks.get(i).getTaskInstance().setCategory(selCategory);
            }
        }

        return true;
    }

    public void printTaskNamesNumbered() {
        for(int i = 0; i< tasks.size(); i++) {
            System.out.println(i+1 + ": "  + tasks.get(i).getName());
        }
    }

    public void updateSettings(String n, String pass) {
        this.name = n;
        this.password = pass;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void printPublicTasksOnly(){
        int index = 1;
        for(int i = 0; i<tasks.size(); i++){
            if(!tasks.get(i).isPrivate()){
                System.out.println(index + ": " + tasks.get(i).getName());
                index++;
            }
        }
    }

    public boolean updateTaskProgress()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter task id");



        String taskID = scan.next();

        System.out.println("Select Progress, (0-100):");
        int progress = scan.nextInt();
        while(progress>100 || progress<0)
        {
            System.out.println("Invalid percentage value: \n");
            System.out.println("Select Progress, (0-100):");
            progress = scan.nextInt();
        }

        boolean exists = false;
        int index = 0;
        for(int i=0; i<tasks.size(); i++)
        {
            if(tasks.get(i).getTaskInstance().getID().equals(taskID))
            {
                tasks.get(i).getTaskInstance().updateProgress(progress);
                if(tasks.get(i).getTaskInstance().progress == 100)
                {
                    tasks.remove(i);
                }
            }
        }

        return true;
    }
}
