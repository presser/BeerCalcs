package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import java.util.*;
import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.Calcs;

public class FormIBU extends FormBase implements CommandListener, IListaIBU {
	
	private Command commandVoltar;
	private Command commandNovoLupulo;
	private Command commandExcluirUltimo;
	private TextField textFieldOG;
	private TextField textFieldLitros;
	private int contador;
	private int formula;
	private Vector lupulos;
	private double ultimoIbu;
	private double ibus;
	private StringItem stringItemTotal;
	private Calcs calcs;
	
	public FormIBU(ApplicationBase app) {
		super(app, "IBU");
		
		commandVoltar = new Command("Voltar", Command.EXIT, 0);
		commandNovoLupulo = new Command("Inserir lúpulo", Command.ITEM, 1);
		commandExcluirUltimo = new Command("Excluir último", Command.ITEM, 2);
		
		textFieldLitros = new TextField("Litros", "", 5, TextField.DECIMAL);
		textFieldOG = new TextField("OG esperada", "", 5, TextField.DECIMAL);
		stringItemTotal = new StringItem("IBUs totais:", "");
		
		contador = -1;
		formula = -1;
		lupulos = new Vector();
		ultimoIbu = 0.0;
		ibus = 0.0;
		calcs = new Calcs();
		
		append(textFieldOG);
		append(textFieldLitros);
		append(stringItemTotal);
		
		addCommand(commandVoltar);
		addCommand(commandNovoLupulo);
		addCommand(commandExcluirUltimo);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.EXIT)
			close();
		else if (command == commandNovoLupulo)
			novoLupulo();
		else if (command == commandExcluirUltimo)
			excluirUltimo();
	}
	
	public void setRetornoListaIBU(int retorno) {
		this.formula = retorno;
	}
	
	private double getDensidade() {
		return Calcs.parseDensidade(textFieldOG, getDisplay());
	}
	
	private double getLitros() {
		try {
			return Double.parseDouble(textFieldLitros.getString());
		}
		catch (Exception e) {
			textFieldLitros.setString("");
			getDisplay().setCurrentItem(textFieldLitros);
			return -1.0;
		}
	}
	
	public void addLupulo(int tipoLupulo, int minutos, double gramas, double acidosAlfa) {
		
		switch (formula) {
			case 0: //Tinseth
				ultimoIbu = calcs.calculaIBUTinseth(getDensidade(), getLitros(), minutos, gramas, acidosAlfa, tipoLupulo == 0);
				break;
			case 1: //Rager
				ultimoIbu = calcs.calculaIBURager(getDensidade(), getLitros(), minutos, gramas, acidosAlfa, tipoLupulo == 0);
				break;
			case 2:
				ultimoIbu = calcs.calculaIBUGaretz(getDensidade(), getLitros(), minutos, gramas, acidosAlfa, tipoLupulo == 0);
				break;			
		}
		
		contador++;
		ibus += ultimoIbu;
		stringItemTotal.setText(arredonda(ibus));
		
		textFieldOG.setConstraints(TextField.UNEDITABLE);
		textFieldLitros.setConstraints(TextField.UNEDITABLE);
		
		String descricao = Double.toString(gramas) + "gm/" + Integer.toString(minutos) + "min/" +
			Double.toString(acidosAlfa) + "%AA: " + Double.toString(ultimoIbu) + " IBU";
		
		StringItem temp = new StringItem("", descricao);
		append(temp);
		lupulos.addElement(temp);
	}
	
	private String arredonda(double valor) {
		return Double.toString(((int)(valor * 100.0)) / 100.0);
	}
	
	private void novoLupulo() {
		if (getDensidade() < 0.0)
			return;
		
		if (getLitros() < 0.0)
			return;		
		
		FormAddLupulo form = new FormAddLupulo(getApplication(), this);
		form.setReturnForm(this);
		getApplication().setActiveForm(form);		
	}
	
	private void excluirUltimo () {
		if (contador < 0)
			return;
		
		StringItem temp = (StringItem)lupulos.elementAt(contador);
		
		for (int i = 0; i < size(); i++) {
			if (get(i) == temp) {
				delete(i);
				lupulos.removeElementAt(contador);
				ibus -= ultimoIbu;
				ultimoIbu = 0.0;				
				contador--;
				break;
			}
		}
		
		stringItemTotal.setText(arredonda(ibus));
	}
	
	protected void activated() {
		if (formula < 0) {
			FormListaIBU form = new FormListaIBU(getApplication(), this, 0);
			form.setReturnForm(this);
			getApplication().setActiveForm(form);
		}		
	}
}
