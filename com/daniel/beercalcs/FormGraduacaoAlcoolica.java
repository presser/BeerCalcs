package com.daniel.beercalcs;

import javax.microedition.lcdui.*;
import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormGraduacaoAlcoolica extends FormBase implements CommandListener {
	
    private Command exitCommand;
    private Command okCommand;
    private TextField textBoxOG;
    private TextField textBoxFG;
    private StringItem stringItemVolume;
    private StringItem stringItemPeso;
    
	public FormGraduacaoAlcoolica(ApplicationBase app) {
		super(app, "Graduação alcoólica");
		
        exitCommand = new Command("Voltar", Command.EXIT, 1);
        okCommand = new Command("OK", Command.OK, 1);
        
        textBoxOG = new TextField("Densidade inicial (OG):", "", 20, TextField.DECIMAL);
        textBoxFG = new TextField("Densidade final (FG):", "", 20, TextField.DECIMAL);
        stringItemVolume = new StringItem("Álcool por volume:", "");
        stringItemPeso = new StringItem("Álcool por peso:", "");
        
        append(textBoxOG);
        append(textBoxFG);
        append(stringItemVolume);
        append(stringItemPeso);
        addCommand(exitCommand);
        addCommand(okCommand);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		
		if (command.getCommandType() == Command.OK) {
			if (textBoxOG.getString().length() == 0) {
				getDisplay().setCurrentItem(textBoxOG);
				return;
			}
			
			if (textBoxFG.getString().length() == 0) {
				getDisplay().setCurrentItem(textBoxFG);
				return;
			}
			
			calcula();
		}
		else 
			close();	
	}
	
	protected void calcula() {
		
		double og, fg;
		
		og = Calcs.parseDensidade(textBoxOG, getDisplay());
		if (og < 0.0)
			return;
		
		fg = Calcs.parseDensidade(textBoxFG, getDisplay());
		if (fg < 0.0)
			return;
		
		Calcs c = new Calcs();
		double peso = c.calculaGraduacaoAlcoolicaPorPeso(og, fg);
		double volume = c.calculaGraduacaoAlcoolicaPorVolume(og, fg);
		
		stringItemPeso.setText(Double.toString(peso) + "%");
		stringItemVolume.setText(Double.toString(volume) + "%");
		
		textBoxFG.setString("");
		textBoxOG.setString("");
		getDisplay().setCurrentItem(textBoxOG);
	}
	
	protected void activated() {
		
	}
}
