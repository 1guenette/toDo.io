import java.text.ParseException;
import java.util.*;


public class UserController{
    private int count;
    private User user;

    //not sure where to put these
    private ArrayList<Group> groups = new ArrayList<>();

    //This is the "Database" holding all system users
    private ArrayList<User> users = new ArrayList<>();

    static User fakeUser = new User("Alice", "Test", "1");
    static User fakeUser2 = new User("Bob", "Test2", "2");

    public UserController()
    {
        count = 0;
        users.add(fakeUser);
        users.add(fakeUser2);
    }

    /**
     * Method that lets a user login using a username and password
     */
    public void login() {
        Scanner in = new Scanner(System.in);

        //Get the user's username and check if it is valid
        boolean validUsername = false;
        while(!validUsername) {
            System.out.println("Please enter your username:");
            String name = in.next();
            for (User u : users) {
                if (u.getName().equals(name)) {
                    validUsername = true;
                    this.user = u;
                }
            }
            if(!validUsername) {
                System.out.println("Entered username not found in system");
            }
        }

        //Get the user's password and check if it matches the username
        boolean validPassword = false;
        while(!validPassword) {
            System.out.println("Please enter your password:");
            String password = in.next();
            if (user.getPassword().equals(password)) {
                validPassword = true;
            }
            if(!validPassword) {
                System.out.println("Entered password was incorrect");
            }
        }
    }

    /**
     * This lets a user logout and return to the main screen of the application
     * @return This returns true if the user has selected to logout and false if not
     */
    public boolean logout() {
        Scanner in = new Scanner(System.in);
        System.out.println("Are you sure you wish to logout? y/n");
        String response = in.next();

        if(response.equals("y") || response.equals("Y")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void createUser()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your username");
        String name = in.next();
        System.out.println("Enter a password");
        String password = in.next();
        user = new User(name, password, Integer.toString(count));
        users.add(user);
        count++;

        //After account is created, the user must login
        System.out.println("Now, please login with your new username and password");
        login();
    }

    public User getUser(){
        return user;
    }
    public boolean categorizeTask(User u)
    {
        u.categorizeTask();
        return true;
    }

    /**
     * prints out username, password and ID
     */
    public void viewProfile() {
        System.out.println("Profile:");
        System.out.println("Username: " + user.getName());
        System.out.println("Password: " + user.getPassword());
        System.out.println("ID: " + user.getID());
    }

    /**
     * ask user for new username/password and update user instance
     */
    public void updateSettings() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your new username: ");
        String input = sc.next();
        String name = input;

        System.out.println("Enter your new password: ");
        input = sc.next();
        String password = input;

        user.updateSettings(name, password);

        System.out.println("Your account has been successfully updated!");
    }

    public boolean setPrioritiesOfTasks() {
        int priority = 1, chosenTask;
        int size = user.getTasks().size();
        if(size == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
            return true;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("Enter the number of the task you would like to have priority of " + priority + ", or enter -1 to quit");
            printTaskNamesNumbered();

            Scanner sc = new Scanner(System.in);
            chosenTask = sc.nextInt();
            if (chosenTask == -1) {
                break;
            }
            chosenTask--;
            if (user.setTaskPriority(chosenTask, priority) == false) {
                return false;
            }

            priority++;
        }

        return true;
    }

    public void printTaskNamesNumbered() {
        ArrayList<TaskController> t = user.getTasks();
        for(int i = 0; i< t.size(); i++) {
            System.out.println(i+1 + ": "  + t.get(i).getName());
        }
    }

    /**
     * method that calls the updateTaskStatus method in the task controller
     * @return true if task is updated successfully, otherwise false
     */
    public boolean setTaskStatus(){
        int priority = 1, chosenTask;
        int size = user.getTasks().size();
        if(size == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
            return true;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("Enter the number of the task you would like set the status of, or enter -1 to quit");
            printTaskNamesNumbered();
        }

        Scanner sc = new Scanner(System.in);
        chosenTask = sc.nextInt();
        if (chosenTask == -1) {
            return false;
        }

        chosenTask--;
        if (user.getTasks().get(chosenTask).updateTaskStatus() == false) {
            return false;
        }

        return true;
    }

    /**
     * Allows the creator of a group task to change who can edit that task
     * @return Returns true if access rights are successfully set, false otherwise
     */
    public boolean setAccessRightsOnTask() {
        Group group;
        System.out.println("Which group would you like to view: ");
        Scanner in = new Scanner(System.in);
        String name = in.next();

        //Find the group with that name
        if (doesGroupExist(name)) {
            group = getGroupFromName(name);
        }
        else {
            //If that group does not exist, do nothing
            System.out.println("No group with that name");
            return false;
        }

        int size = group.getGroupTasks().size();
        //If that group has no tasks, do nothing
        if(size == 0) {
            System.out.println("This group has no tasks. Try making a task first.");
            return false;
        }

        //Get the task to change the access rights of
        int chosenTask = -1;
        while(chosenTask < 0 || chosenTask > size) {
            System.out.println("Enter the valid number of the task you would like set the status of, or enter -1 to quit");
            printTaskNamesNumbered();

            chosenTask = in.nextInt();
        }
        chosenTask--;

        //Check if you are the owner of this task
        if(!group.getGroupTasks().get(chosenTask).getUsername().equals(user.getName())) {
            System.out.println("You did not create this task, therefore you cannot edit its access rights.");
            return false;
        }

        //Get if the task should be public or private
        int access = -1;
        while(access != 0 && access != 1) {
            System.out.println("Enter 1 if you want this task to be Public and 0 if you want it to be Private.");
            access = in.nextInt();
        }

        boolean accessRights;
        if(access == 0) {
            accessRights = false;
            System.out.println("You set the task to Private, only you will be able to edit it now.");
        }
        else {
            accessRights = true;
            System.out.println("You set the task to Public, anyone in your group will be able to edit it now.");
        }
        //Set the access rights
        group.getGroupTasks().get(chosenTask).setAccessRightsOnTask(accessRights);
        return true;
    }

    /**
     * Method to gather date information from user and call setAlarm from the task file
     * @throws ParseException 
     */
    public void setAlarm() throws ParseException {
        if (getUser().numTasks() == 0) {
            System.out.println("You dont have any tasks created. Create a task first.");
            return;
        }
        System.out.println("Choose the number of the task to set an alarm for:");
        printTaskNamesNumbered();
        Scanner in = new Scanner(System.in);
        int taskNum = in.nextInt();
        getUser().getTask(taskNum - 1).addAlarm();

    }

    public void createCategories() {
        System.out.println("Type the name of a new category you would like to use:");
        Scanner in = new Scanner(System.in);
        String category = in.nextLine();
        getUser().createCategories(category);
    }

    public void createGroup(){
        ArrayList<User> usersInGroup = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        System.out.println("Choose a name for the group: ");
        String name = in.nextLine();

        if (!doesGroupExist(name)) {
            System.out.println("Who would you like to add to your group? (Format: name,name,name,...)");
            String groupList = in.nextLine();

            String[] splitList = groupList.split(",");

            usersInGroup.add(user);

            for(int i = 0; i < splitList.length; i++){
                for(User userInList: users) {
                    if (splitList[i].equals(userInList.getName())) {
                        usersInGroup.add(userInList);
                    }
                }
            }

            Group newGroup = new Group(name, usersInGroup);

            groups.add(newGroup);
            System.out.println("Group has been created");
        }else{
            System.out.println("Group with that name already exists");
        }


    }

    public void sendMessageToGroup(){
        System.out.println("Which group would you like to send a message to: ");
        Scanner in = new Scanner(System.in);
        String name = in.next();

        if (doesGroupExist(name)) {
            Group group = getGroupFromName(name);

            for(int i = 0; i < group.getMembers().size(); i++){
                if(group.getMembers().get(i).equals(user)){
                    System.out.println("Message: ");
                    String message = in.next();

                    group.getMessages().add(message);
                    return;
                }
            }
            System.out.println("You are not a member of this group");
        } else {
            System.out.println("No group with that name");
        }
        System.out.println("Message sent to group");
    }

    public boolean anonymousRating(){
        Group group;
        System.out.println("Which group would you like to rate a member in: ");
        Scanner in = new Scanner(System.in);
        String groupName = in.next();

        //Find if the group exists
        if(doesGroupExist(groupName)) {
            group = getGroupFromName(groupName);
        }
        else {
            System.out.println("No group with that name");
            return false;
        }

        System.out.println("Who would you like to rate: ");
        String name = in.next();

        //Get the user from the list in the group
        int userIndex = -1;
        for(int i = 0; i < group.getMembers().size(); i++) {
            if(group.getMembers().get(i).getName().equals(name)) {
                userIndex = i;
                break;
            }
        }

        if(userIndex == -1) {
            System.out.println("That is not a valid user");
            return false;
        }
        else{
            //Get the rating and rate the user
            User rated = group.getMembers().get(userIndex);
            //Check if this user is trying to rate themselves
            if(rated.getName().equals(user.getName())) {
                System.out.println("You cannot give yourself a rating");
                return false;
            }
            int rating = -1;
            while(rating < 1 && rating > 5) {
                System.out.println("Rating (1-5): ");
                rating = in.nextInt();
            }
            return group.addAnonymousRating(group, user, rated, rating);
        }
    }

    public void createGroupTask(){
        System.out.println("Which group would you like to create a group task: ");
        Scanner in = new Scanner(System.in);
        String name = in.next();

        if (doesGroupExist(name)) {
            Group group = getGroupFromName(name);

            for(int i = 0; i < group.getMembers().size(); i++){
                //checks if you are in the group
                if(group.getMembers().get(i).equals(user)){
                    group.getGroupTasks().add(new TaskController(user.createTask()));

                    System.out.println("Group task has been created");
                    return;
                }
            }
        }else{
            System.out.println("No group with that name");
        }
    }

    public void deleteGroupTask(){
        System.out.println("Which group would you like to delete a group task from: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        if (doesGroupExist(name)) {
            Group group = getGroupFromName(name);

            for(int i = 0; i < group.getMembers().size(); i++){
                //checks if you are in the group
                if(group.getMembers().get(i).equals(user)) {

                    System.out.println("Enter task name: ");
                    String taskName = in.nextLine();

                    if(doesGroupTaskExist(group, taskName)){
                        deleteTask(group, taskName);
                        System.out.println("Task removed");
                    }else{
                        System.out.println("Task with that name does not exist");
                    }
                }else{
                    System.out.println("You are not a member of this group");
                    return;
                }
                return;
            }
        }else {
            System.out.println("No group with that name");
        }
    }

    public void deleteTask(Group group, String taskName){
        for(int i = 0; i < group.getGroupTasks().size(); i++) {
            if(group.getGroupTasks().get(i).getName().equals(taskName)) {
                group.getGroupTasks().remove(i);
            }
        }
    }

    public void editGroupTask(){
        System.out.println("Which group would you like to edit a group task from: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        if (doesGroupExist(name)) {
            Group group = getGroupFromName(name);

            for(int i = 0; i < group.getMembers().size(); i++){
                //checks if you are in the group
                if(group.getMembers().get(i).equals(user)) {

                    System.out.println("Enter task name: ");
                    String taskName = in.nextLine();

                    if(doesGroupTaskExist(group, taskName)){
                        editTask(group, taskName);
                    }else{
                        System.out.println("Task with that name does not exist");
                    }
                }else{
                    System.out.println("You are not a member of this group");
                    return;
                }
                return;
            }
        }else {
            System.out.println("No group with that name");
        }
    }

    public void editTask(Group group, String taskName){
        Scanner in = new Scanner(System.in);

        for(int i = 0; i < group.getGroupTasks().size(); i++) {
            if(group.getGroupTasks().get(i).getName().equals(taskName)){
                Task task = group.getGroupTasks().get(i).getTaskInstance();

                System.out.println("Enter new task name");
                String newName = in.nextLine();

                System.out.println("Enter new task category");
                String category = in.nextLine();

                task.setName(newName);
                task.setCategory(category);
            }
        }
    }

    /**
     * This method allows the user to edit an already created routine task
     */
    public void editRoutineTask() {
        String name = "";
        String category = "";
        String newName = "";
        String username = "";

        Scanner sc = new Scanner(System.in);

        printTaskNamesNumbered();
        System.out.println("Enter task name to edit");
        name = sc.nextLine();

        if (user.doesTaskExist(name) == false) {
            System.out.println("Task does not exist.");
            return;
        }
        //Get this task and check if it a routine task
        ArrayList<TaskController> tasks = user.getTasks();
        TaskController thisTask = tasks.get(0);
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getTaskInstance().name.equals(name)) {
                thisTask = tasks.get(i);
            }
        }
        if(!thisTask.getTaskInstance().isRoutine) {
            System.out.println("This is not a routine task. Please slect a routine task to edit.");
            return;
        }

        //Get the new task info
        System.out.println("Enter new task name");
        newName = sc.nextLine();

        System.out.println("Enter new task category");
        category = sc.nextLine();

        System.out.println("Enter your username");
        username = sc.nextLine();

        System.out.println("When would you like this routine task to occur?");
        System.out.println("Enter either daily, weekly, or monthly");
        String routine = sc.nextLine();

        if (routine.equals("daily")) {
            thisTask.getTaskInstance().frequencyOfRoutine = "daily";

        } else if (routine.equals("weekly")) {
            thisTask.getTaskInstance().frequencyOfRoutine = "weekly";

        } else if (routine.equals("monthly")) {
            thisTask.getTaskInstance().frequencyOfRoutine = "monthly";

        } else {
            System.out.println("You did not enter a correct routine phrase. Please enter either daily, weekly, or monthly");
            return;
        }

        thisTask.getTaskInstance().setName(newName);
        thisTask.getTaskInstance().setCategory(category);
    }

    public void scheduleTasks() {
        //Print off tasks, then ask user which they would like to schedule together
        printTaskNamesNumbered();
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> taskNums = new ArrayList<>();
        int size = user.getTasks().size();
        if(size == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
            return;
        }

        System.out.println("Which tasks would you like to schedule together? Type one at a time by number and press enter. Type -1 to end the list.");
        int response = sc.nextInt();
        while(response >= 0) {
            if(taskNums.contains(response)) { //Tasks cannot be repeated in the list
                System.out.println("This task has already been chosen. Choose another or type -1 to end your list.");
            }
            else if(response > size) {
                System.out.println("This is not a task number listed. Choose another or type -1 to end your list.");
            }
            else { //Add task to list of ones to be scheduled together
                taskNums.add(response);
                System.out.println("Type another task number to be scheduled together or type -1 to stop adding to the list.");
            }
            response = sc.nextInt();
        }

        //Check if list has more than one task in it
        if(taskNums.size() <= 1) {
            System.out.println("Not enough tasks were chosen to be scheduled together. 2 or more must be selected.");
            return;
        }
        //Otherwise, add each task to the list of scheduled tasks in each of the other tasks
        for(int i : taskNums) {
            TaskController t = getUser().getTask(i - 1);
            for(int j : taskNums) {
                if(i != j){ //add if these are not the same tasks
                    t.addScheduledTask(getUser().getTask(j - 1).getTaskInstance());
                }
            }
        }
    }

    public boolean doesGroupTaskExist(Group group, String taskName) {

        //loop through group tasks
        for(int i = 0; i < group.getGroupTasks().size(); i++) {
            if(group.getGroupTasks().get(i).getName().equals(taskName)){
                return true;
            }
        }
        return false;
    }

    public void leaveGroup() {
        String groupName = "";
        String username = "";

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your username");
        username = sc.nextLine();

        System.out.println("Please enter the name of the group you'd like to leave");
        groupName = sc.nextLine();

        if (doesGroupExist(groupName)) {
            Group group = getGroupFromName(groupName);

            ArrayList<User> users = new ArrayList<User>();
            users = group.getMembers();

            for (int i = 0; i < users.size(); i ++) {
                if (users.get(i).getName().equals(username)) {
                    group.getMembers().remove(i);
                } else {
                    System.out.println("Sorry, you are not a part of this group. Please enter a group you are a part of");
                }
            }

            System.out.println("You have been removed from the group.");

        } else {
            System.out.println("No group with that name");
        }
    }


    public void seeGroupMembers() {

        String groupName = "";

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the group you'd like to see the members of");
        groupName = sc.nextLine();

        Group group = getGroupFromName(groupName);

        if (group.getMembers().size() != 0) {
            for (int i = 0; i < group.getMembers().size(); i ++) {
                System.out.println(group.getMembers().get(i).getName());
                System.out.println();
            }
        } else {
            System.out.println("This group has no members.");
        }

    }
    public void addFriendToGroup(){
        System.out.println("Which group would you like to add another user: ");
        Scanner in = new Scanner(System.in);
        String groupName = in.next();

        //check if there is a group with that name
        if (doesGroupExist(groupName)) {
            Group group = getGroupFromName(groupName);

            for(int i = 0; i < group.getMembers().size(); i++){
                //checks if you're in the group
                if(group.getMembers().get(i).equals(user)){
                    System.out.println("Who would you like to add to the group: ");
                    String name = in.next();

                    //checks if the user is valid
                    for(User user: users){
                        if(user.getName().equals(name)){
                            group.getMembers().add(user);
                        }
                    }

                    System.out.println(name + " has been added to " + group.getGroupName());
                    return;
                }
            }
        } else {
            System.out.println("No group with that name");
        }
    }

    public boolean doesGroupExist(String groupName) {
        for (Group group: groups) {
            if (group.getGroupName().equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    public Group getGroupFromName(String groupName){
        for (Group group: groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    /**
     * get task user wants to add note to
     * call setNote for this task
     */
    public void addNotesToTask(){
        int chosenTask = -1;
        int size = user.getTasks().size();
        if(size == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the task you would like to add a note to, or enter -1 to quit");
        printTaskNamesNumbered();
        chosenTask = sc.nextInt();

        if (chosenTask == -1) {
            return;
        }

        chosenTask--;

        user.getTasks().get(chosenTask).setNote();


    }

    public void setTaskPrivacy(){
        int chosenTask = -1;
        int size = user.getTasks().size();
        if(size == 0) {
            System.out.println("You don't have any tasks. Try making a task first.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the task you would like to set privacy of, or enter -1 to quit");
        printTaskNamesNumbered();
        chosenTask = sc.nextInt();

        if (chosenTask == -1) {
            return;
        }

        chosenTask--;

        user.getTasks().get(chosenTask).setPrivacy();

    }

    /**
     * Gets the user of the tasks that should be printed then calls the print public
     * tasks function of that user.
     */
    public void viewOtherUsersTask(){
        System.out.println("Which user's tasks would you like to view? Enter -1 to quit");
        int index = 1;
        for (int i = 0; i<users.size(); i++){
            String name = users.get(i).getName();
            if(!name.equals(user.getName())){
                System.out.println(index + " " + users.get(i).getName());
                index++;
            }
        }
        Scanner sc = new Scanner(System.in);
        int userNum = sc.nextInt();
        if(userNum == -1){
            return;
        }
        userNum--;
        users.get(userNum).printPublicTasksOnly();
    }

    public void deleteAccount()
    {
        for(int i=0; i<users.size(); i++)
        {
            if(users.get(i).getID() == user.getID())
            {
                users.remove(i);
            }
        }

        for(int j = 0; j<groups.size(); j++)
        {

            if(groups.get(j).getMembers().contains(user))
            {
                groups.get(j).removeMembers(user);
            }
        }
    }
}

