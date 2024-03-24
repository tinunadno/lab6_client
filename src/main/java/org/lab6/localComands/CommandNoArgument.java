package org.lab6.localComands;
public interface CommandNoArgument {
	/**
	 * calls the corresponding function from LabWorkListManager class
	 */
	void execute();
	/**
	 * returns name and description of a command
	 * @return
	 */
	String getComment();
}