package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.EditCommand.MESSAGE_EDITED_TASK_SUCCESS;

// @@author A0141128R tested and passed
public class EditCommandTest extends ToDoListGuiTest {
	
    @Test
    public void edit() {
    	TestTask[] currentList = td.getTypicalTasks();
        
    	//edit the time of the first task in the list  
    	int targetIndex = 1;
    	execute(targetIndex, currentList, "'Eat Buffet'",td.editedGrocery);


        //edit the priority of the last task in the list
        targetIndex = currentList.length;
        execute(targetIndex, currentList, "/low",td.editedZika);
        
        //make first task floating
        targetIndex = 1;
        execute(targetIndex, currentList, "floating",td.floatingGrocery);
        
        //change tags of last task
        targetIndex = currentList.length;
        execute(targetIndex, currentList, "-dangerous",td.taggedZika);
        
        //remove priority of first task using 'rp' or 'remove priority'
        targetIndex = 1;
        execute(targetIndex, currentList, "remove priority",td.noPriorityGrocery);
        
        //change time of task 2
        targetIndex = 2;
        execute(targetIndex, currentList, "1120",td.editedHouse1);
        
        //change date of task 2
        execute(targetIndex, currentList, "10/20/2016",td.editedHouse2);
        
        //change task 3 to a range task
        targetIndex = 3;
        execute(targetIndex, currentList, "11/12/2016 1300 to 12/12/2016 1500",td.editedCar);
        
        //invalid priority parameter
        runEditCommand("edit", 1, "/yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        
        //invalid index
        runEditCommand("edit", currentList.length + 1, "/high");
        assertResultMessage("The task index provided is invalid");
        
        //cannot edit a done task
        commandBox.runCommand("done 1");
        commandBox.runCommand("ld");
        runEditCommand("edit", 1, "/high");
        assertResultMessage("Cannot edit a done task!");
        
        //edit something from an empty list
        commandBox.runCommand("clear");
        targetIndex = 1;
        runEditCommand("edit", targetIndex, "/high");
        assertResultMessage("The task index provided is invalid");

    }
    
    //slap for successful cases, need to comment
    private void execute(int targetIndex, TestTask[] currentList, String change, TestTask editedTask){
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = updateList(currentList,editedTask,targetIndex);
    }
    
    //run commands
    private void runEditCommand(String command, int index, String change){
    	commandBox.runCommand("edit " + index +" " + change);
    }
    
    //update list
    private TestTask[] updateList(TestTask[] listToUpdate, TestTask editedTask, int targetIndex){
    	TestTask[] list = TestUtil.replaceTaskFromList(listToUpdate,editedTask,targetIndex-1);
    	return list;
    }
    
     //confirm the new card contains the right data
    private void checkCard(TestTask editedTask){
    	TaskCardHandle EditedCard = taskListPanel.navigateToTask(editedTask.getDetail().details);
        assertMatching(editedTask, EditedCard);
    }

    /**
     * Runs the edit command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before edit).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String change, TestTask ed) {
    	
        runEditCommand("edit", targetIndexOneIndexed, change);
        
        //updateList
        TestTask[] expectedRemainder = updateList(currentList,ed,targetIndexOneIndexed);
        
        //confirm the new card contains the right data
        checkCard(ed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        
        //confirm the result message is correct
        assertResultMessage(MESSAGE_EDITED_TASK_SUCCESS);
    }

}