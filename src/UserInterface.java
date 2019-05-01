import java.text.ParseException;
import java.util.Scanner;

public class UserInterface {
    static boolean done = false;
    static boolean loggedIn = false;
    static UserController uc;
    static Group group;

    public static void main(String args[]) throws ParseException {
        uc = new UserController();

        System.out.println("Welcome to ToDo! Please select one of the following:");
        printInitialMenu();

        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        handleInitialOption(chosenOption);

        System.out.println("Please select an option");
        printMenu();
        chosenOption = sc.nextInt();
        handleOption(chosenOption);

        while(!done){
            if(!loggedIn) {
                printInitialMenu();
                chosenOption = sc.nextInt();
                handleInitialOption(chosenOption);
            }
            else {
                printMenu();
                chosenOption = sc.nextInt();
                handleOption(chosenOption);
            }
        }
        sc.close();
    }

    public static void printInitialMenu() {
        System.out.println();
        System.out.println("1: Login");
        System.out.println("2: Create an Account");
        System.out.println("3: Quit");
    }

    public static void printMenu(){
        System.out.println();
        System.out.println("1: Logout");
        System.out.println("2: Task Menu");
        System.out.println("3: Group Menu");
        System.out.println("4: Settings");
    }

    public static void handleOption(int opt) throws ParseException{
        switch (opt){
            case 1:
                loggedIn = !(uc.logout());
                break;
            case 2:
                displayTaskMenu();
                break;
            case 3:
                displayGroupMenu();
                break;
            case 4:
                displaySettings();
            default:
                break;
        }
    }

    public static void handleInitialOption(int opt) {
        switch (opt) {
            case 1:
                uc.login();
                loggedIn = true;
                break;
            case 2:
                uc.createUser();
                loggedIn = true;
                break;
            case 3:
                done = true;
                break;
            default:
                break;
        }
    }

    public static void displayTaskMenu() throws ParseException{
        System.out.println("Task Menu:");
        System.out.println(" 1: Back to main menu");
        System.out.println(" 2: Add a task");
        System.out.println(" 3: Order tasks");
        System.out.println(" 4: Set an alarm for a task");
        System.out.println(" 5: Edit a task");
        System.out.println(" 6: View all tasks");
        System.out.println(" 7: Categorize a tasks");
        System.out.println(" 8: See all categories");
        System.out.println(" 9: Add suggestion");
        System.out.println("10: Update task status");
        System.out.println("11: Delete task");
        System.out.println("12: View all your TODO tasks");
        System.out.println("13: Add notes to task");
        System.out.println("14: Set task privacy");
        System.out.println("15: View another users' TODO tasks");
        System.out.println("16: Create routine task");
        System.out.println("17: Edit routine task");
        System.out.println("18: Delete routine task");
        System.out.println("19: View all routine tasks");
        System.out.println("20: Schedule tasks to be completed at the same time");
        System.out.println("21: Update Task Progress ");

        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        handleTaskOption(chosenOption);
    }
    public static void handleTaskOption(int opt) throws ParseException{
        switch (opt) {
            case 1:
                break;
            case 2:
                uc.getUser().createTask();
                uc.getUser().printAllTasks();
                break;
            case 3:
                uc.setPrioritiesOfTasks();
                break;
            case 4:
                uc.setAlarm();
                break;
            case 5:
                uc.getUser().editTask();
                break;
            case 6:
                System.out.println("All tasks:");
                uc.getUser().printAllTasks();
                break;
            case 7:
                uc.getUser().categorizeTask();
                break;
            case 8:
                uc.getUser().getAllCategories();
                break;
            case 9:
                uc.getUser().addTaskSuggestion();
                break;
            case 10:
                uc.setTaskStatus();
                break;
            case 11:
                uc.getUser().deleteTask();
                break;
            case 12:
                System.out.println("To Do:");
                uc.getUser().viewCurrentTasks();
                break;
            case 13:
                uc.addNotesToTask();
                break;
            case 14:
                uc.setTaskPrivacy();
                break;
            case 15:
                uc.viewOtherUsersTask();
                break;
            case 16:
                uc.getUser().createRoutineTask();
                uc.setAlarm();
                break;
            case 17:
                uc.editRoutineTask();
                break;
            case 18:
                uc.getUser().deleteRoutineTask();;
                break;
            case 19:
                uc.getUser().printAllRoutineTasks();
                break;
            case 20:
                uc.scheduleTasks();
                break;
            case 21:
                uc.getUser().updateTaskProgress();
            default:
                break;
        }
    }

    public static void displayGroupMenu(){
        System.out.println("Group Menu:");
        System.out.println("1: Back to main menu");
        System.out.println("2: Create a group");
        System.out.println("3: Add friend to group");
        System.out.println("4: Create a group task");
        System.out.println("5: Edit a group task");
        System.out.println("6: Delete group task");
        System.out.println("7: Set task access rights");
        System.out.println("8: Send message to group");
        System.out.println("9: Add anonymous rating to group member");
        System.out.println("10: Add suggestion to group task");
        System.out.println("11: Schedule group tasks to be completed at the same time");
        System.out.println("12: Leave a group");
        System.out.println("13: See group members");
        System.out.println("14: Update Task Progress");


        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        handleGroupOption(chosenOption);
    }
    public static void handleGroupOption(int opt){
        switch (opt) {
            case 1:
                break;
            case 2:
                uc.createGroup();
                break;
            case 3:
                uc.addFriendToGroup();
                break;
            case 4:
                uc.createGroupTask();
                break;
            case 5:
                uc.editGroupTask();
                break;
            case 6:
                uc.deleteGroupTask();
                break;
            case 7:
                uc.setAccessRightsOnTask();
                break;
            case 8:
                uc.sendMessageToGroup();
                break;
            case 9:
                uc.anonymousRating();
                break;
            case 10:
                group.addTaskSuggestion();
                break;
            case 11:
                uc.scheduleTasks();
            case 12:
                uc.leaveGroup();
                break;
            case 13:
                System.out.println("Members in group: ");
                uc.seeGroupMembers();
                break;
            case 14:
                group.updateGroupTaskProgress();
                break;
            default:
                break;
        }
    }

    /**
     * displays setting menu
     */
    public static void displaySettings() {
        System.out.println();
        System.out.println("1: Back to main menu");
        System.out.println("2: Update account settings");
        System.out.println("3: View profile");
        System.out.println("4: Delete profile");
        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        handleSettingsOption(chosenOption);
    }

    /**
     * handles choice of setting menu options
     * @param opt chosen menu item
     */
    public static void handleSettingsOption(int opt) {
        switch (opt) {
            case 1:
                break;
            case 2:
                uc.updateSettings();
                break;
            case 3:
                uc.viewProfile();
                break;
            case 4:
                uc.deleteAccount();
                loggedIn = !(uc.logout());
                break;
            default:
                displaySettings();
                break;
        }
    }
}