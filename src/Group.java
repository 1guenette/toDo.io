import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sydney on 3/11/19.
 */
public class Group {

    public String groupName;
    private ArrayList<User> members;
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<TaskController> groupTasks = new ArrayList<>();
    private ArrayList<TaskController> finishedTasks = new ArrayList<>();

    private ArrayList<Group> groups = new ArrayList<>();

    public Group(String name, ArrayList<User> users) {
        this.groupName = name;
        this.members = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<User> getMembers() {
        return members;
    }
    public void removeMembers(User u)
    {
        for(int i = 0; i<members.size(); i++)
        {
            if(members.get(i).getID() == u.getID())
            {
                members.remove(i);
            }
        }
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<TaskController> getGroupTasks() {
        return groupTasks;
    }

    public void setGroupTasks(ArrayList<TaskController> groupTasks) {
        this.groupTasks = groupTasks;
    }

    public void printGroupTaskNamesNumbered() {
        for(int i = 0; i< groupTasks.size(); i++) {
            System.out.println(i+1 + ": "  + groupTasks.get(i).getName());
        }
    }

    public boolean addAnonymousRating(Group groupName, User rater, User rated, int rating) {
        return rated.addAnonymousRating(rater, rating);
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
	    
		for (int i = 0; i < groupTasks.size(); i++) {	 
			if (groupTasks.get(i).getTaskInstance().name.equals(name)) 
			{
				groupTasks.get(i).getTaskInstance().addSuggestion(suggestion);
			}
		}
	}

    public boolean updateGroupTaskProgress()
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

        for(int i=0; i<groupTasks.size(); i++)
        {
            if(groupTasks.get(i).getTaskInstance().getID().equals(taskID))
            {
                groupTasks.get(i).getTaskInstance().updateProgress(progress);
                if(groupTasks.get(i).getTaskInstance().progress == 100)
                {
                    groupTasks.remove(i);
                }
            }
        }

        return true;
    }
	
	/**
     * This method returns true if a task with a given name already exists
     * @param name: the task name which we are comparing to
     * @return true if the task does exist and false if it does not
     */
    public boolean doesTaskExist(String name) {

        for (TaskController task: groupTasks) {
            if (task.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
