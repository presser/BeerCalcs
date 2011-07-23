package com.daniel.beercalcs;

import javax.microedition.lcdui.*;
import com.daniel.beercalcs.base.*;
import com.daniel.beercalcs.core.*;
 
public class FormMenu extends FormBase implements CommandListener {
	
	private ListaOperacoes lista;
	private Command exitCommand;
	private Command selectCommand;
	private Command ajudaCommand;
	private Command sobreCommand;
	
	protected class ListaOperacoes extends List implements CommandListener {
		
		private FormMenu parent;
		
		public ListaOperacoes(FormMenu form) {
			super("Operação", List.IMPLICIT);
			parent = form;
		}
	    
		public void commandAction(Command command, Displayable displayable) {
			parent.commandAction(command, displayable);			
		}
	}	
	
	public FormMenu(ApplicationBase application) {
		super(application, "Menu Principal");
		
		Image[] images = constroiImagens();
		
		lista = new ListaOperacoes(this);
		lista.setFitPolicy(List.TEXT_WRAP_ON);
		lista.append("Correção de Densidade", images[0]);
		lista.append("Graduação Alcoólica", images[1]);
		lista.append("Brix / Densidade", images[2]);
		lista.append("Refratrômetro", images[3]);
		lista.append("Volume da Panela", images[4]);
		lista.append("IBU", images[5]);
		
		exitCommand = new Command("Sair", Command.EXIT, 0);		
		selectCommand = new Command("OK", Command.OK, 2);
		sobreCommand = new Command("Sobre", Command.SCREEN, 4);
		ajudaCommand = new Command("Ajuda", Command.HELP, 3);
		
		lista.setSelectCommand(selectCommand);
		lista.addCommand(exitCommand);
		lista.addCommand(ajudaCommand);
		lista.addCommand(sobreCommand);
		lista.setCommandListener(this);
		//lista.addCommand(nextCommand);
	}
	
	private Image[] constroiImagens() {
		
		Image[] images;
		
		int h = Math.min(getDisplay().getBestImageHeight(Display.LIST_ELEMENT), getDisplay().getBestImageWidth(Display.LIST_ELEMENT));
		
		try
		{
			if (h > 32) {
				images = new Image[]{Image.createImage("/com/daniel/beercalcs/images/densimetro64.png"),
						Image.createImage("/com/daniel/beercalcs/images/alcool64.png"),
						Image.createImage("/com/daniel/beercalcs/images/densimetro-refratometro64.png"),
						Image.createImage("/com/daniel/beercalcs/images/refratometro64.png"),
						Image.createImage("/com/daniel/beercalcs/images/panela64.png"),
						Image.createImage("/com/daniel/beercalcs/images/lupulo64.png")};
			}
			else if (h > 16) {
				images = new Image[]{Image.createImage("/com/daniel/beercalcs/images/densimetro32.png"),
						Image.createImage("/com/daniel/beercalcs/images/alcool32.png"),
						Image.createImage("/com/daniel/beercalcs/images/densimetro-refratometro32.png"),
						Image.createImage("/com/daniel/beercalcs/images/refratometro32.png"),
						Image.createImage("/com/daniel/beercalcs/images/panela32.png"),
						Image.createImage("/com/daniel/beercalcs/images/lupulo32.png")};
			}
			else {
				images = new Image[]{Image.createImage("/com/daniel/beercalcs/images/densimetro16.png"),
						Image.createImage("/com/daniel/beercalcs/images/alcool16.png"),
						Image.createImage("/com/daniel/beercalcs/images/densimetro-refratometro16.png"),
						Image.createImage("/com/daniel/beercalcs/images/refratometro16.png"),
						Image.createImage("/com/daniel/beercalcs/images/panela16.png"),
						Image.createImage("/com/daniel/beercalcs/images/lupulo16.png")};
			}
		}
		catch (Exception e) {
			images = new Image[]{null,
					null,
					null,
					null,
					null,
					null};
			
			return images;
		}
		
		Utils u = new Utils();
		
		for (int i = 0; i < images.length; i++) {
			if (images[i] == null)
				continue;
			
			if (images[i].getHeight() != h || images[i].getWidth() != h)
				images[i] = u.resizeImage(images[i], h, h);
		}
		
		return images;
	}
 
	public void commandAction(Command command, Displayable displayable) {
		if (displayable == lista) {
			if (command.getCommandType() == Command.OK) {
				if (lista.getSelectedIndex() == 0) {
					FormCorrecaoOG formOG = new FormCorrecaoOG(getApplication());
					formOG.setReturnForm(this);
					getApplication().setActiveForm(formOG);
				}
				else if (lista.getSelectedIndex() == 1) {
					FormGraduacaoAlcoolica formGA = new FormGraduacaoAlcoolica(getApplication());
					formGA.setReturnForm(this);
					getApplication().setActiveForm(formGA);
				}
				else if (lista.getSelectedIndex() == 2) {
					FormBrixToSG formBrixSG = new FormBrixToSG(getApplication());
					formBrixSG.setReturnForm(this);
					getApplication().setActiveForm(formBrixSG);
				}
				else if (lista.getSelectedIndex() == 3) {
					FormRefratrometro formRefratrometro = new FormRefratrometro(getApplication());
					formRefratrometro.setReturnForm(this);
					getApplication().setActiveForm(formRefratrometro);
				}
				else if (lista.getSelectedIndex() == 4) {
					FormListaPanelas formPanela = new FormListaPanelas(getApplication());
					formPanela.setReturnForm(this);
					getApplication().setActiveForm(formPanela);
				}
				else if (lista.getSelectedIndex() == 5) {
					FormIBU formIBU = new FormIBU(getApplication());
					formIBU.setReturnForm(this);
					getApplication().setActiveForm(formIBU);
				}
			}
			else if (command == sobreCommand) {
				FormSobre formSobre = new FormSobre(getApplication());
				formSobre.setReturnForm(this);
				getApplication().setActiveForm(formSobre);
			}
			else if (command == ajudaCommand) {
				FormAjuda formAjuda = new FormAjuda(getApplication());
				formAjuda.setReturnForm(this);
				getApplication().setActiveForm(formAjuda);
			}
			else if (command.getCommandType() == Command.EXIT) {
				getApplication().exitMIDlet();
			}
		}
	}
	
	protected void activated() {
		getDisplay().setCurrent(lista);
	}
	
}