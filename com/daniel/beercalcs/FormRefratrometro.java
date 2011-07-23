package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormRefratrometro extends FormBase implements CommandListener {
	
    private Command exitCommand;
    private Command okCommand;
    private TextField textBoxRefratrometro;
    private TextField textBoxOG;
    private StringItem stringItemFG;
	
	public FormRefratrometro(ApplicationBase app) {
		super(app, "Refratrômetro");
		
        exitCommand = new Command("Voltar", Command.EXIT, 1);
        okCommand = new Command("OK", Command.OK, 1);
        
        textBoxRefratrometro = new TextField("Leitura do refratrômetro:", "", 20, TextField.DECIMAL);
        textBoxOG = new TextField("Densidade inicial (OG):", "", 20, TextField.DECIMAL);
        stringItemFG = new StringItem("Densidade corrigida:", "");
        
        append(textBoxRefratrometro);
        append(textBoxOG);
        append(stringItemFG);

        addCommand(exitCommand);
        addCommand(okCommand);
	}

	public void commandAction(Command command, Displayable displayable) {
		
		if (command.getCommandType() == Command.EXIT)
			close();
		else {
			if (textBoxRefratrometro.getString().length() == 0) {
				getDisplay().setCurrentItem(textBoxRefratrometro);
				return;
			}
			
			if (textBoxOG.getString().length() == 0) {
				getDisplay().setCurrentItem(textBoxOG);
				return;				
			}
			
			calcula();
		}
	}
	
    private double parseDouble(TextField campo, double min, double max)
    {
    	double valor;
    	
    	try
    	{
    		valor = Double.parseDouble(campo.getString());
    		
    		if (valor < min || valor > max)
    			throw new Exception("Valor fora do limite!");
    	}
    	catch (Exception e)
    	{
    		campo.setString("");
    		getDisplay().setCurrentItem(campo);
    		return -1.0;
    	}
    	
    	return valor;
    }
	
	private void calcula() {
		
		double brix, og;
		
		brix = parseDouble(textBoxRefratrometro, 0.0, 100.0);
		if (brix < 0.0)
			return;
		
		og = Calcs.parseDensidade(textBoxOG, getDisplay());
		if (og < 0.0)
			return;
		
		Calcs c = new Calcs();
		
		stringItemFG.setText(Double.toString(c.densidadeFinalFromBrix(brix, og)));
		
		textBoxRefratrometro.setString("");
		textBoxOG.setString("");
		getDisplay().setCurrentItem(textBoxRefratrometro);
	}
	
	
	protected void activated() {
		
	}
	
}
