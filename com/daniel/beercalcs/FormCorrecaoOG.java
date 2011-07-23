package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormCorrecaoOG extends FormBase implements CommandListener {
	
    private Command exitCommand;
    private Command okCommand;
    private TextField textBoxTempDensimetro;
    private TextField textBoxOG;
    private TextField textBoxTemperatura;
    private StringItem stringItemOGCorrigida;
    private TextField[] campos;
    
	public FormCorrecaoOG(ApplicationBase app) {
		super(app, "Correção de densidade");
		
        exitCommand = new Command("Voltar", Command.EXIT, 1);
        okCommand = new Command("OK", Command.OK, 1);
        
        textBoxTempDensimetro = new TextField("Temp. calibração:", "", 20, TextField.DECIMAL);
        textBoxOG = new TextField("Densidade:", "", 20, TextField.DECIMAL);
        textBoxTemperatura = new TextField("Temperatura:", "", 20, TextField.DECIMAL);
        stringItemOGCorrigida = new StringItem("Densidade corrigida:", "");
        
        campos = new TextField[]{textBoxTempDensimetro, textBoxOG, textBoxTemperatura};
        
        append(textBoxTempDensimetro);
        append(textBoxOG);
        append(textBoxTemperatura);
        append(stringItemOGCorrigida);
        addCommand(exitCommand);
        addCommand(okCommand);
	}
	
	public void commandAction(Command command, Displayable displayable) {
        if (displayable == this) {
            if (command == exitCommand) {
                close();
            }
            else if (command == okCommand) {
            	for (int i = 0; i<campos.length; i++) {
            		if (campos[i].getString().length() == 0) {
            			getDisplay().setCurrentItem(campos[i]);
            			return;
            		}
            	}
            	
            	executaCalculo();
            }
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
    
    private void executaCalculo()
    {
    	double tempCalibracao, temperatura, og, ogCorrigida;
    	
    	ogCorrigida = -1.0;
    	
    	stringItemOGCorrigida.setText("calculando...");
    	getDisplay().getCurrent();
    	try
    	{
    		tempCalibracao = parseDouble(textBoxTempDensimetro, 0.0, 110.0);
    		if (tempCalibracao < 0.0)
    			return;
    	
    		tempCalibracao = (tempCalibracao * 1.8) + 32.0;
    	
    		og = Calcs.parseDensidade(textBoxOG, getDisplay());
    		if (og < 0.0)
    			return;
    	
    		temperatura = parseDouble(textBoxTemperatura, 0.0, 110.0);
    		if (tempCalibracao < 0.0)
    			return;
    	
    		temperatura = (temperatura * 1.8) + 32.0;    		
    		
    		Calcs c = new Calcs();
    		ogCorrigida = c.corrigeTemperaturaOg(tempCalibracao, temperatura, og);
    	}
    	finally
    	{
    		if (ogCorrigida > 0.0)
    			stringItemOGCorrigida.setText(Double.toString(ogCorrigida));
    		else
    			stringItemOGCorrigida.setText("");    			
    	}    
    	
    	textBoxTemperatura.setString("");
    	textBoxOG.setString("");
    	getDisplay().setCurrentItem(textBoxTempDensimetro);
    }	
	
	protected void activated() {
		//textBoxTempDensimetro.setString(Double.toString(20.0));
	}
}
