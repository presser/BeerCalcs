package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormVolumePanela extends FormBase implements CommandListener {
	
	private StringItem stringItemPanela;
	private TextField textFieldEspacoLivre;
	private TextField textFieldEspacoOcupado;
	private StringItem stringItemInstrucoes;
	private StringItem stringItemVolume;
	
	private Command commandOK;
	private Command commandVoltar;
	private Panela panela;
	
	
	public FormVolumePanela(ApplicationBase app, Panela panela) {
		super(app, "Volume na panela");
		
		this.panela = panela;		
		
		stringItemPanela = new StringItem("Panela:", panela.getNome());
		textFieldEspacoLivre = new TextField("Livre:", "", 20, TextField.DECIMAL);
		textFieldEspacoOcupado = new TextField("Ocupado:", "", 20, TextField.DECIMAL);
		stringItemInstrucoes = new StringItem("Informe o espaço livre ou o ocupado", "");
		stringItemVolume = new StringItem("Resultado:", "");
		
		commandOK = new Command("OK", Command.OK, 0);
		commandVoltar = new Command("Voltar", Command.EXIT, 0);
		
		append(stringItemPanela);
		append(stringItemInstrucoes);
		append(textFieldEspacoLivre);
		append(textFieldEspacoOcupado);
		append(stringItemVolume);
		
		addCommand(commandOK);
		addCommand(commandVoltar);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.OK)
			calcula();
		else
			close();		
	}
	
	protected void activated() {
		
	}
	
    private double parseDouble(TextField campo, double min, double max)
    {
    	double valor;
    	
    	try
    	{
    		valor = Double.parseDouble(campo.getString());
    		
    		if (valor < min || valor > max)
    			throw new Exception("Valor fora da faixa!");    		
    	}
    	catch (Exception e)
    	{
    		return -1.0;
    	}
    	
    	return valor;
    }
	
	private void calcula() {
		
		double livre = parseDouble(textFieldEspacoLivre, 0.01, 200.0);
		double ocupado = parseDouble(textFieldEspacoOcupado, 0.01, 200.0);
		
		if (livre < 0 && ocupado < 0)
		{
			textFieldEspacoLivre.setString("");
			textFieldEspacoOcupado.setString("");
			getDisplay().setCurrentItem(textFieldEspacoLivre);
			stringItemVolume.setText("Valor inválido!");
			return;
		}
		
		Calcs c = new Calcs();
		
		if (ocupado > 0)
			livre = panela.getAltura() - ocupado;
		
		double litros = c.calculaVolumePanela(livre, panela);
		
		stringItemVolume.setText(Double.toString(litros));
		
		textFieldEspacoLivre.setString("");
		textFieldEspacoOcupado.setString("");
		getDisplay().setCurrentItem(textFieldEspacoLivre);
	}
	
	

}
