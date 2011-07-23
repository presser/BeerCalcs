package com.daniel.beercalcs.base;

import javax.microedition.lcdui.*;


public abstract class FormBase extends Form implements CommandListener {
	
	private Display display;
	private ApplicationBase application;
	private FormBase returnForm;
	
	public FormBase(ApplicationBase application, String title) {
		super(title);
		this.application = application;
		display = Display.getDisplay(application);	
	}
	
	protected Display getDisplay() {
		return display;
	}
	
	protected ApplicationBase getApplication() {
		return application;
	}	
	
	protected void close() {
		application.closeForm(this, returnForm);
	}
	
	public FormBase getReturnForm() {
		return returnForm;
	}
	
	public void setReturnForm(FormBase form) {
		returnForm = form;
	}	
	
	public abstract void commandAction(Command command, Displayable displayable);
	protected abstract void activated();

}
