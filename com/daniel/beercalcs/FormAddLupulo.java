package com.daniel.beercalcs;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;

import com.daniel.beercalcs.base.ApplicationBase;
import com.daniel.beercalcs.base.FormBase;

public class FormAddLupulo extends FormBase implements CommandListener, IListaIBU {
	
	private FormIBU formIBU;
	private TextField textFieldGramas;
	private TextField textFieldMinutos;
	private TextField textFieldAA;
	private int tipoLupulo;
	
	private Command commandOK;
	private Command commandVoltar;
	
	public FormAddLupulo(ApplicationBase app, FormIBU parent) {
		super(app, "Adicionar Lúpulo");
		
		formIBU = parent;
		
		commandOK = new Command("OK", Command.OK, 0);
		commandVoltar = new Command("Cancelar", Command.EXIT, 0);
		
		tipoLupulo = -1;
		
		textFieldGramas = new TextField("Peso (gramas)", "", 20, TextField.DECIMAL);
		textFieldMinutos = new TextField("Minutos em fervura", "", 20, TextField.NUMERIC);
		textFieldAA = new TextField("% Ácidos Alfa", "", 20, TextField.DECIMAL);
		
		append(textFieldGramas);
		append(textFieldMinutos);
		append(textFieldAA);
		
		addCommand(commandOK);
		addCommand(commandVoltar);
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
	

	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.OK)
			calcula();
		else
			close();		
	}
	
	public void setRetornoListaIBU(int retorno) {
		tipoLupulo = retorno;	
	}
	
	protected void activated() {
		if (tipoLupulo < 0) {
			FormListaIBU listaIBU = new FormListaIBU(getApplication(), this, 1);
			listaIBU.setReturnForm(this);
			getApplication().setActiveForm(listaIBU);
		}
	}	
	
	private void calcula() {
		double gramas, aa;
		int minutos;
		
		gramas = parseDouble(textFieldGramas, 0.001, 5000.0);
		if (gramas < 0)
			return;
		
		minutos = (int)parseDouble(textFieldMinutos, 1, 500.0);
		if (minutos < 0)
			return;
		
		aa = parseDouble(textFieldAA, 0.001, 100.0);
		if (aa < 0)
			return;
		
		formIBU.addLupulo(tipoLupulo, minutos, gramas, aa);
		close();
	}
}

