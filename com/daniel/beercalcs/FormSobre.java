package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;

public class FormSobre extends FormBase implements CommandListener{

	private ImageItem image;
	private StringItem stringItemBeerCalcs;
	private StringItem stringItemWWW;
	private Command commandOK;
	
	public FormSobre(ApplicationBase app) {
		super(app, "Sobre");
		
		try
		{
			Image im = Image.createImage("/com/daniel/beercalcs/images/icon.png");
			image = new ImageItem("Beer Calcs", im, ImageItem.LAYOUT_CENTER, "image");
			
			append(image);
		}
		catch (Exception ex) {}
		
		stringItemBeerCalcs = new StringItem("\n\nversão 1.0.0\n", "");
		stringItemWWW = new StringItem("", "www.presser.net.br/beercalcs");
		commandOK = new Command("OK", Command.OK, 0);
		
		append(stringItemBeerCalcs);
		append(stringItemWWW);
		addCommand(commandOK);		
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.OK)
			close();		
	}
	
	protected void activated() {
		
	}	
}
