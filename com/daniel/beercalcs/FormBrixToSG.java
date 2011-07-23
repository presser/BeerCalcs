package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormBrixToSG extends FormBase implements CommandListener {
	
    private Command exitCommand;
    private Command okCommand;
    private TextField textBoxBrix;
    private TextField textBoxDensidade;
    private StringItem stringItemBrix;
    private StringItem stringItemDensidade;
    
    public FormBrixToSG(ApplicationBase app) {
    	super(app, "Brix / Densidade");
    	
        exitCommand = new Command("Voltar", Command.EXIT, 1);
        okCommand = new Command("OK", Command.OK, 1);
        
        textBoxBrix = new TextField("Brix:", "", 20, TextField.DECIMAL);
        stringItemBrix = new StringItem("Densidade convertida:", "");
        textBoxDensidade = new TextField("Densidade:", "", 20, TextField.DECIMAL);
        stringItemDensidade = new StringItem("Brix convertido:", "");
        
        append(textBoxBrix);
        append(stringItemBrix);
        append(textBoxDensidade);
        append(stringItemDensidade);
        addCommand(exitCommand);
        addCommand(okCommand);
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
    		return -1.0;
    	}
    	
    	return valor;
    }
    
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.EXIT)
			close();
		else
		{
			calcula();
		}
	}
	
	private void calcula() {
		double densidade, brix;
		
		brix = parseDouble(textBoxBrix, 0.0, 100.0);
		densidade = Calcs.parseDensidade(textBoxDensidade, getDisplay());
		
		Calcs c = new Calcs();
		
		if (brix > 0)
			stringItemBrix.setText(Double.toString(c.brixToSG(brix)));
		else
			stringItemBrix.setText("");
			
		if (densidade > 0)
			stringItemDensidade.setText(Double.toString(c.densidadeToBrix(densidade)));
		else
			stringItemDensidade.setText("");
		
		textBoxBrix.setString("");
		textBoxDensidade.setString("");
		getDisplay().setCurrentItem(textBoxBrix);		
	}
	
	
	protected void activated() {
		
	}   
    

}
