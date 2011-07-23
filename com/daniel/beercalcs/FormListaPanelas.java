package com.daniel.beercalcs;

import javax.microedition.lcdui.*;

import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;

import java.util.*;

public class FormListaPanelas extends FormBase implements CommandListener {

	private List listaPanelas;
	
	private Command commandOK;
	private Command commandVoltar;
	private Command commandNovaPanela;
	private Command commandExcluir;
	private Command commandEditar;
	private Vector panelas;
	
	public FormListaPanelas(ApplicationBase app) {
		super(app, "Selecione a panela");
		
		commandOK = new Command("OK", Command.OK, 0);
		commandVoltar = new Command("Voltar", Command.EXIT, 0);
		commandNovaPanela = new Command("Nova panela", Command.SCREEN, 1);
		commandEditar = new Command("Editar", Command.SCREEN, 2);
		commandExcluir = new Command("Excluir", Command.SCREEN, 3);
		
		Panelas p = new Panelas();
		try {
			panelas = p.getPanelas();
		}
		catch (Exception ex) {
			panelas = new Vector();
		}
		
		listaPanelas = new List("Panelas", List.IMPLICIT);
		
		carregaLista();		
		
		listaPanelas.setSelectCommand(commandOK);
		
		listaPanelas.addCommand(commandNovaPanela);
		listaPanelas.addCommand(commandEditar);
		listaPanelas.addCommand(commandExcluir);
		listaPanelas.addCommand(commandOK);
		listaPanelas.addCommand(commandVoltar);
		
	}
	
	public void setPanelaSelecionada(Panela p) {
		FormVolumePanela form = new FormVolumePanela(getApplication(), p);
		form.setReturnForm(getReturnForm());
		getApplication().setActiveForm(form);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		
		if (command.getCommandType() == Command.OK) {
			if (listaPanelas.getSelectedIndex() >= panelas.size())
				exibeFormCadastroPanelas(null);
			else {
				setPanelaSelecionada((Panela)panelas.elementAt(listaPanelas.getSelectedIndex()));
			}
		}
		else if (command == commandNovaPanela) {
			exibeFormCadastroPanelas(null);
		}
		else if (command == commandEditar) {
			if (listaPanelas.getSelectedIndex() >= panelas.size())
				return;
				
			exibeFormCadastroPanelas((Panela)panelas.elementAt(listaPanelas.getSelectedIndex()));
		}
		else if (command == commandExcluir) {
			if (listaPanelas.getSelectedIndex() >= panelas.size())
				return;
				
			excluiPanela((Panela)panelas.elementAt(listaPanelas.getSelectedIndex()));
		}
		else {
			close();
		}
	}
	
	private void carregaLista() {
		listaPanelas.deleteAll();
		
		for (int i = 0; i < panelas.size(); i++) {
			listaPanelas.append(((Panela)panelas.elementAt(i)).getNome(), null);
		}
		
		listaPanelas.append("Nova panela...", null);
	}
	
	private void exibeFormCadastroPanelas(Panela panela) {
		FormCadastroPanela f = new FormCadastroPanela(getApplication(), panelas, panela, this);
		f.setReturnForm(this);
		getApplication().setActiveForm(f);
	}
	
	private void excluiPanela(Panela p) {
		panelas.removeElement(p);
		
		Panelas ps = new Panelas();
		try {
			ps.salvaPanelas(panelas);
		}
		catch (Exception ex) {
		
		}	
		
		carregaLista();
	}
	
	protected void activated() {
		getDisplay().setCurrent(listaPanelas);
		listaPanelas.setCommandListener(this);
	}

}
