package com.daniel.beercalcs;

import javax.microedition.lcdui.*;
import java.io.*;
import java.util.*;

import com.daniel.beercalcs.base.*;

public class FormAjuda extends FormBase implements CommandListener {
	
	private Command commandClose;
	
	private class ItemAjuda {
		public String Nome;
		public String arquivoAjuda;
		public String arquivoImagem;
	}
	
	private Vector ajudas;
	private boolean exibiu;
	private int indiceAtual;
	
	private ImageItem imagem;
	private StringItem texto;
	

	public FormAjuda(ApplicationBase app) {
		super(app, "Ajuda");
		
		exibiu = false;		
		commandClose = new Command("Voltar", Command.EXIT, 0);
		addCommand(commandClose);
		
		carregaAjudas();
		indiceAtual = 0;
		
		for (int i = 0; i < ajudas.size(); i++) {
			ItemAjuda temp = (ItemAjuda)ajudas.elementAt(i);
			Command cmd = new Command(temp.Nome, Command.SCREEN, i);
			addCommand(cmd);
		}
	}
	
	private void carregaAjudas()
	{
		String tamanho = "64.png";
		
		if (getDisplay().getBestImageHeight(Display.ALERT) <= 32)
			tamanho = "32.png";
			
		
		ajudas = new Vector();
		
		ItemAjuda item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-main.txt";
		item.Nome = "Principal";
		item.arquivoImagem = "icon.png";
		
		ajudas.addElement(item);
		
		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-densidade.txt";
		item.Nome = "Correção de Densidade";
		item.arquivoImagem = "densimetro" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);
		
		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-alcool.txt";
		item.Nome = "Graduação Alcoólica";
		item.arquivoImagem = "alcool" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);
		
		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-brix.txt";
		item.Nome = "Brix / Densidade";
		item.arquivoImagem = "densimetro-refratometro" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);
		
		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-refratrometro.txt";
		item.Nome = "Refratrômetro";
		item.arquivoImagem = "refratometro" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);

		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-panela.txt";
		item.Nome = "Volume da Panela";
		item.arquivoImagem = "panela" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);
		
		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-ibu.txt";
		item.Nome = "IBU";
		item.arquivoImagem = "lupulo" + tamanho;
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);

		item = new ItemAjuda();
		item.arquivoAjuda = "ajuda-geral.txt";
		item.Nome = "Geral";
		item.arquivoImagem = "icon.png";
		//item.imagem = getImagem(item.arquivoImagem);
		ajudas.addElement(item);
	}
	
	private Image getImagem(String nome) {
		Image im = null;
		try {
			im = Image.createImage("/com/daniel/beercalcs/images/" + nome);
		} catch (Exception e) {}
		
		return im;		
	}
	
	private void exibeAjuda(int indice) {
		ItemAjuda temp = (ItemAjuda)ajudas.elementAt(indice);
		
		deleteAll();
		
		String message = readFile(temp.arquivoAjuda);
		
		setTitle(temp.Nome);
		
		Image im = getImagem(temp.arquivoImagem);
		
		imagem = new ImageItem(temp.Nome, im, ImageItem.LAYOUT_CENTER, "");
		texto = new StringItem("", "\n\n" + message, StringItem.LAYOUT_LEFT);
		
		append(imagem);
		append(texto);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command.getCommandType() == Command.EXIT) {
			if (indiceAtual == 0)
				close();
			else
				exibeAjuda(0);
		}
		else {
			exibeAjuda(command.getPriority());
		}
	}
	
	protected void activated() {
		if (!exibiu) {
			exibiu = true;
			exibeAjuda(0);
		}
	}		
	
	private String readFile(String fileName){
		fileName = "/com/daniel/beercalcs/images/" + fileName;
		
	    InputStream is = getClass().getResourceAsStream(fileName);
	    StringBuffer sb = new StringBuffer();
	    try {
	      int chars = 0;
	      
	      while ((chars = is.read()) != -1) {
	        sb.append((char) chars);
	      }
	      
	      is.close();
	      
	      return sb.toString();
	    } 
	    catch (Exception e){}
	    finally {
	    }
	    
	    return "Erro ao carregar arquivo " + fileName;
	  }	
}
