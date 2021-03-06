package seedu.cmdo.logic.commands;

import seedu.cmdo.model.ToDoList;

/**
 * Clears CMDo.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_USAGE = COMMAND_WORD 
			+ ": Clears all stored task from the entire list.\n"
			+ "Example: clear";
								
    public static final String MESSAGE_SUCCESS = "CMDo has been cleared!";
    
    public ClearCommand() {
    	this.isUndoable = true;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDoList.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
