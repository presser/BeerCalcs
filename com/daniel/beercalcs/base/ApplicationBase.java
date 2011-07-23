package com.daniel.beercalcs.base;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;


public class ApplicationBase extends MIDlet implements CommandListener {
	
	private Display display;
	private FormBase activeForm;
	private FormBase lastForm;
	
	public ApplicationBase() {
		display = Display.getDisplay(this);
		lastForm = null;
	}
	
	public FormBase getActiveForm() {
		return activeForm;
	}
	
	public FormBase setActiveForm(FormBase form) {
		lastForm = activeForm;
		activeForm = form;
		display.setCurrent(form);
		form.setCommandListener(form);
		form.activated();
		return form;
	}	

    public void commandAction(Command command, Displayable displayable) {
    	if (activeForm != null)
    		activeForm.commandAction(command, displayable);
    	else
    	{
    		if (command.getCommandType() == Command.EXIT)
    			exitMIDlet();
    	}
    }
    
    public void closeForm(FormBase form, FormBase returnForm) {
    	if (activeForm != form)
    		return;
    	
    	if (returnForm == null)
    		returnForm = lastForm;
    	
    	if (returnForm != null)
    		setActiveForm(returnForm);
    	else
    		exitMIDlet();
    }
    
 
	
    public void startApp() {
    	
    }
    
    public void pauseApp() {
    	
    }
 
    // Your MIDlet should not call destroyApp(), only system will call this life-cycle method    
    public void destroyApp(boolean unconditional) {
    	
    }
 
    public void exitMIDlet() {
        display.setCurrent(null);
        notifyDestroyed();
    }
}
