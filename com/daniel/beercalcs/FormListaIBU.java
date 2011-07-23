package com.daniel.beercalcs;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

import javax.microedition.lcdui.*;
import com.daniel.beercalcs.base.*;

public class FormListaIBU extends FormBase implements CommandListener {
	private Command commandOK;
	private List lista;
	private IListaIBU formIBU;
	
	public FormListaIBU(ApplicationBase app, IListaIBU parent, int tipo) {
		super(app, "IBU");
		
		formIBU = parent;
		
		commandOK = new Command("OK", Command.OK, 0);
		
		addCommand(commandOK);
		lista = new List("Fórmula", List.IMPLICIT);
		lista.setSelectCommand(commandOK);
		
		if (tipo == 0)
			montaListaTipoFormula();
		else
			montaListaTipoLupulo();
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.OK) {
			formIBU.setRetornoListaIBU(lista.getSelectedIndex());
			close();				
		}
	}
	
	protected void activated() {
		getDisplay().setCurrent(lista);
		lista.setCommandListener(this);
	}
	
	private void montaListaTipoFormula() {
		lista.setTitle("Fórmula");
		lista.deleteAll();
		lista.append("Tinseth", null);
		lista.append("Rager", null);
		//lista.append("Garetz", null);
		lista.setSelectedIndex(0, true);
	}
	
	private void montaListaTipoLupulo() {
		lista.setTitle("Estado do lúpulo");
		lista.deleteAll();
		lista.append("Pellets", null);
		lista.append("Inteiro", null);
		lista.setSelectedIndex(0, true);
	}
	
	


}
