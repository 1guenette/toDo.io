/**
 * Created by Sydney on 3/11/19.
 */
public class GroupController {
    private Group group;

    GroupController(Group group){
        this.group = group;
    }

    public Group getGroupInstance() {
        return group;
    }

    public String getName() {
        return getGroupInstance().groupName;
    }

}
