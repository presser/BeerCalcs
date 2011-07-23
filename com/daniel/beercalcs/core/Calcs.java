package com.daniel.beercalcs.core;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.TextField;

import com.daniel.beercalcs.math.MathUtil;

public class Calcs {
	
	public double corrigeTemperaturaOg(double tempCalibracaoDensimetro, double tempLida, double ogLida) {
		double ogCorrigida = ogLida * (sgRatio(tempLida) / sgRatio(tempCalibracaoDensimetro));
		
		ogCorrigida = ((int)(ogCorrigida * 1000.0)) / 1000.0;
		
		return ogCorrigida;		
	}
	
	public double calculaGraduacaoAlcoolicaPorPeso(double og, double fg) {
		
		double platoIni = densidadeToPlato(og);
		double platoFim = densidadeToPlato(fg);
		double RE = (0.1808 * platoIni) + (0.8192 * platoFim);
		
		double res = (platoIni - RE) / (2.0665 - (0.010665 * platoIni));
		
		return ((int)(res * 100.0)) / 100.0;
	}
	
	public double calculaGraduacaoAlcoolicaPorVolume(double og, double fg) {
		double res = (og - fg) * 131.0;
		
		return ((int)(res * 100.0)) / 100.0;
	}
	
	public static double stringToDensidade(String densidade) throws Exception {
		
		double valor = Double.parseDouble(densidade);
		
		if (densidade.length() < 3)
			valor = (valor / 1000.0) + 1.0;
		else if (valor > 1000.0 && valor < 1500.0) 
			valor = valor / 1000.0; 
			
		if (valor < 0.500 || valor > 1.500)
			throw new Exception("Valor fora da faixa!");
		
		return valor;
	}
	
	public static double parseDensidade(TextField campo, Display display) {
		try
		{
			return stringToDensidade(campo.getString());
		}
		catch (Exception ex) {
			campo.setString("");
			display.setCurrentItem(campo);
			return -1.0;
		}		
	}
	
	public double calculaVolumePanela(double espacoLivre, Panela panela) {
		double h = panela.getAltura() - espacoLivre;
		double r = (panela.getDiametro() / 2.0);
		
		double res = Math.PI * (r * r) * h;
		res = res / 1000.0;
		
		res = res + panela.getCorrecao();
		
		return ((int)(res * 100.0)) / 100.0;
	}
	
	public double brixToSG(double brix) {
		double SG = 1.000898 + 0.003859118 * brix + 0.00001370735 * brix * brix + 0.00000003742517 * brix * brix * brix;
		
		return ((int)(SG * 1000.0)) / 1000.0;
	}
	
	private double densidadeToPlato(double densidade) {
		return -676.67 + 1286.4 * densidade - 800.47 * densidade * densidade + 190.74 * densidade * densidade * densidade;		
	}
	
	public double densidadeToBrix(double densidade) {
		return ((int)(densidadeToPlato(densidade) * 100.0)) / 100.0;		
	}
	
	
	public double densidadeFinalFromBrix(double brix, double og) {
		
		og = corrigeTemperaturaOg(15.0, 20.0, og);
		double ob = densidadeToPlato(og);
		double fg = 1.001843 - 0.002318474 * ob - 0.000007775 * ob * ob - 0.000000034 * ob * ob * ob + 0.00574 * brix + 0.00003344 * brix * brix + 0.000000086 * brix * brix * brix;
		
		return ((int)(fg * 1000.0)) / 1000.0;
	}
	
	public double calculaIBUTinseth(double densidade, double litros, int minutos, double gramas, double acidosAlfa, boolean pellets) {
		double peso = gramasToOz(gramas);
		double volume = litrosToGaloes(litros);
		
		double utilizacao = (1.65 * MathUtil.pow(0.000125, (densidade - 1.0))) * ((1.0 - MathUtil.pow(2.72, -0.04 * minutos)) / 4.14);
		double ibuTemp = (peso * (acidosAlfa / 100.0)* 7490.0) / volume;
		
		double ibu = utilizacao * ibuTemp;
		
		if (pellets)
			ibu = ibu * 1.1;
		
		return ((int)(ibu * 100.0)) / 100.0;		
	}
	
	public double calculaIBURager(double densidade, double litros, int minutos, double gramas, double acidosAlfa, boolean pellets) {
		//double utilization = 18.11 + (13.86 * tanh((minutos - 31.32) / 18.27));
		
		double utilization;
		
		if (minutos > 0 && minutos <=  5)
			utilization = 5.0;
		else if (minutos >= 6 && minutos <=  10)
			utilization = 6.0;
		else if (minutos >= 11 && minutos <= 15)
			utilization = 8.0;
		else if (minutos >= 16 && minutos <= 20)
			utilization = 10.1;
		else if (minutos >= 21 && minutos <= 25)
			utilization = 12.1;
		else if (minutos >= 26 && minutos <= 30)
			utilization = 15.3;
		else if (minutos >= 31 && minutos <= 35)
			utilization = 18.8;
		else if (minutos >= 36 && minutos <= 40)
			utilization = 22.8;
		else
			utilization = 26.9;
		
		double ga = densidade > 1.050 ? (densidade - 1.050) / 0.2 : 0.0;
		double ibu = (gramas * (utilization / 100.0) * (acidosAlfa / 100.0) * 1000.0) / (litros * (1.0 + ga));
		
		if (pellets)
			ibu = ibu * 1.1;
		
		return ((int)(ibu * 100.0)) / 100.0;		
	}
	
	public double tanh(double x) {
		double e1 = MathUtil.exp(x * 2.0) - 1.0;
		double e2 = MathUtil.exp(x * 2.0) + 1.0;
		
		return e1 / e2;
	}

	public double calculaIBUGaretz(double densidade, double litros, int minutos, double gramas, double acidosAlfa, boolean pellets) {
		return 0.0;
	}

	private double sgRatio(double temp) {
    	return 1.00130346 - 0.000134722124 * temp + 0.00000204052596 * MathUtil.pow(temp,2) - 2.32820948E-09 * MathUtil.pow(temp, 3); 
    }
	
	private double gramasToOz(double gramas) {
		return gramas * 0.0353;
	}
	
	private double litrosToGaloes(double litros) {
		return litros * 0.264172052;
	}
}