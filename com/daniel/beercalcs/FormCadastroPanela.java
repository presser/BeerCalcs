package com.daniel.beercalcs;

import javax.microedition.lcdui.*;
import java.util.*;
import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

public class FormCadastroPanela extends FormBase implements CommandListener {
	
	private StringItem stringItemId;
	private TextField textFieldNome;
	private TextField textFieldAltura;
	private TextField textFieldDiametro;
	private TextField textFieldCorrecao;
	private Command commandVoltar;
	private Command commandOK;
	private Vector panelas;
	private Panela panela;
	private FormListaPanelas parent;
	
	public FormCadastroPanela(ApplicationBase app, Vector panelas, Panela p, FormListaPanelas parent) {
		super(app, "Cadastro de panelas");
	
		panela = p;
		this.parent = parent;
		this.panelas = panelas;
		
		stringItemId = new StringItem("Id:", Integer.toString(p != null ? p.getId() : panelas.size() + 1));
		textFieldNome = new TextField("Nome:", p != null ? p.getNome() : "", 20, TextField.ANY);
		textFieldAltura = new TextField("Altura (cm):", p != null ? Double.toString(p.getAltura()) : "", 20, TextField.DECIMAL);
		textFieldDiametro = new TextField("Diâmetro (cm):", p != null ? Double.toString(p.getDiametro()) : "", 20, TextField.DECIMAL);
		textFieldCorrecao = new TextField("Correcao (l):", p != null ? Double.toString(p.getCorrecao()) : "", 20, TextField.DECIMAL);
		commandOK = new Command("OK", Command.OK, 1);
		commandVoltar = new Command("Cancelar", Command.EXIT, 1);
		
		append(stringItemId);
		append(textFieldNome);
		append(textFieldAltura);
		append(textFieldDiametro);
		append(textFieldCorrecao);
		addCommand(commandOK);
		addCommand(commandVoltar);		
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.OK)
			salvaPanela();
		else
			close();		
	}
	
	private double parseDouble(TextField campo) {
		double result;
		
		try {
			result = Double.parseDouble(campo.getString());
		} catch (Exception e) {
			campo.setString("");
			getDisplay().setCurrentItem(campo);
			return 0.0;
		}
		
		return result;
		
	}
	
	private void salvaPanela() {
		if (textFieldNome.getString().length() == 0) {
			getDisplay().setCurrentItem(textFieldNome);
			return;
		}
		
		double altura = parseDouble(textFieldAltura);
		if (altura <= 0.0)
			return;

		double diametro = parseDouble(textFieldDiametro);
		if (diametro <= 0.0)
			return;

		double correcao = parseDouble(textFieldCorrecao);
		
		if (panela == null) {
			panela = new Panela();
			panelas.addElement(panela);
			panela.setId(panelas.size());
		}
		
		panela.setNome(textFieldNome.getString());
		panela.setAltura(altura);
		panela.setDiametro(diametro);
		panela.setCorrecao(correcao);
		
		Panelas ps = new Panelas();
		
		try	{
		  ps.salvaPanelas(panelas);
		}
		catch (Exception ex) {
			textFieldNome.setString(ex.getMessage());
		}
		
		parent.setPanelaSelecionada(panela);
		close();
	}
	
	protected void activated() {
		
	}
}
