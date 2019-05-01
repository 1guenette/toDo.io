import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * This is a facade controller because it is based on the system.
 *
 * This only delegates
 */
public class TaskController {
    /**This class stores an instance of Task to call the Task object methods*/
    private Task task;

    TaskController(Task t){
        task = t;
    }

    /**
     * This method adds an alarm to a task by getting date for alarm from user and 
     * calling the addAlarm method of the Task class
     * @return True if successful, otherwise false
     * @throws ParseException 
     */
    public boolean addAlarm() throws ParseException {
        System.out.println("Enter date in format: dd/mm/yyyy");
        System.out.println("For routine tasks, this will be the first day your routine task is started.");
        Scanner in = new Scanner(System.in);
        String dateString = in.nextLine();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        Date d;
        try {
            if(dateString.length() != 10) {
                System.out.println("Sorry that's an invalid date. 1");
                return false;
            }
            int i = Integer.parseInt(dateString.substring(0, 2));
            if(0 == i) {
                System.out.println("Sorry that's an invalid date. 2 ");
                return false;
            }
            i = Integer.parseInt(dateString.substring(3, 5));
            if(0 > i || 12< i) {
                System.out.println("Sorry that's an invalid date. 3");
                return false;
            }
            d = formatter1.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Sorry that's an invalid date. 4");
            return false;
        }
        
        
        if (this.getTaskInstance().isRoutine == true) {
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	Calendar c = Calendar.getInstance();
        	c.setTime(sdf.parse(dateString));
        	
        	int numberOfDays = 0;
        	
        	if (this.getTaskInstance().frequencyOfRoutine.equals("daily")) {
        		for (int i = 0; i < 30; i++) {
        			numberOfDays++;
        			
        			c.add(Calendar.DATE, numberOfDays);  // number of days to add
                	dateString = sdf.format(c.getTime());  // dateString is now the new date
                	
                	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);  
                	
                	this.getTaskInstance().addAlarm(date);
        		}

        	} else if ((this.getTaskInstance().frequencyOfRoutine.equals("weekly"))) {
        		for (int i = 0; i < 30; i++) {
        			numberOfDays += 7;
        			
        			c.add(Calendar.DATE, numberOfDays);  // number of days to add
                	dateString = sdf.format(c.getTime());  // dateString is now the new date
                	
                	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);  
                	
                	this.getTaskInstance().addAlarm(date);
        		}
        	} else if (this.getTaskInstance().frequencyOfRoutine.equals("monthly")) {
        		for (int i = 0; i < 30; i++) {
        			numberOfDays += 30;
        			
        			c.add(Calendar.DATE, numberOfDays);  // number of days to add
                	dateString = sdf.format(c.getTime());  // dateString is now the new date
                	
                	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);  
                	
                	this.getTaskInstance().addAlarm(date);
        		}
        	}    	
        }
        
        return this.getTaskInstance().addAlarm(d);
    }

    /**
     * Adds tasks to the list of tasks that this one is scheduled to be completed simultaneously with
     * @param t task to be scheduled with this one
     */
    public void addScheduledTask(Task t) {
        task.addScheduledTask(t);
    }

    /**
     * This method adds a priority to a task by calling the setPriority of the Task class
     * @param p priority of task
     * @return true if successful, otherwise false
     */
    public boolean setPriority(int p) {
        return this.getTaskInstance().setPriority(p);
    }

    /**
     * This sets the access rights on this task
     * @param access access rights of the task: true = public, false = private
     */
    public void setAccessRightsOnTask(boolean access) { this.getTaskInstance().setAccessRightsOnTask(access); }

    /**
     * Method to define one instance of the class Task to use in calling Task methods.
     * @return instance of task
     */
    public Task getTaskInstance() {
        return task;
    }

    public String getName() {
        return getTaskInstance().name;
    }

    public String getUsername() { return getTaskInstance().username; }

    /**
     * This method allows user to set the task name
     */
    public void setName(String n) {
        task.name = n;
    }


    /**
     * This method allows user to set the task category
     */
    public void setCategory(String c) {
        task.category = c;
    }

    /**
     * This method allows user to set the task alarm
     */
    public void setAlarm(Date a) {
        task.alarm = a;
    }

    /**
     * Updates the task status based on user input
     * @return true if task updated successfully. False if user enters quit
     */
    public boolean updateTaskStatus(){
        System.out.println("Enter the number of the status that you would like to set for task " + task.name +", or -1 to quit");
        System.out.println("1: " + task.TODO);
        System.out.println("2: " + task.DOING);
        System.out.println("3: " + task.DONE);
        Scanner sc = new Scanner(System.in);
        int opt = sc.nextInt();
        switch (opt){
            case 1:
                task.setStatusTodo();
                break;
            case 2:
                task.setStatusDoing();
                break;
            case 3:
                task.setStatusDone();
                break;
            case -1:
                return false;
            default:
                System.out.println("Invalid selection. Try again.");
                return updateTaskStatus();
        }
        return true;
    }

    /**
     * get note from user and call set setNote method in task to add note to task
     */
    public void setNote(){
        Scanner sc = new Scanner (System.in);
        System.out.println("Enter your note: ");
        String note = sc.nextLine();
        this.getTaskInstance().setNote(note);
    }

    public void setPrivacy(){
        Scanner sc = new Scanner (System.in);
        System.out.println("To set task to PRIVATE enter 1, to set task to PUBLIC enter 2, or enter -1 to quit");
        int choice = sc.nextInt();
        switch(choice) {
            case -1:
                return;
            case 1:
                this.getTaskInstance().setIsPrivate(true);
                break;
            case 2:
                this.getTaskInstance().setIsPrivate(false);
                break;
            default:
                break;
        }
    }

    public boolean isPrivate(){
        return task.isPrivate;
    }
}
