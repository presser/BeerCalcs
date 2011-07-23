package com.daniel.beercalcs.core;

import java.util.*;
import javax.microedition.rms.RecordStore;

public class Panelas {
	
	static final String REC_NAME = "rs_beercalcs";
	
	private int posicao;
	private String separadorDecimal;
	
	public Panelas() {
		
		separadorDecimal = ",";
		
		try
		{
			double temp = Double.parseDouble("1.5");
			if (temp > 1.4 && temp < 1.6)
				separadorDecimal = ".";
		}
		catch (Exception e) {}			
	}	
	
	public Vector getPanelas() throws Exception {
		Vector v = new Vector();
		
		RecordStore rs = null;
		try {
			rs = RecordStore.openRecordStore(REC_NAME, true);
			
			for (int i = 1; i <= rs.getNumRecords(); i++) {
				byte[] rec = new byte[rs.getRecordSize(i)];
				rs.getRecord(i, rec, 0);
				Panela p = constroiPanela(rec);
				v.addElement(p);
			}
			
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			if (rs != null) {
				try {
					rs.closeRecordStore();
				}
				catch (Exception e) {}
			}
		}
		
		return v;
	}
	
	public void salvaPanelas(Vector panelas) throws Exception {
		
		RecordStore rs = null;
		try {
			rs = RecordStore.openRecordStore(REC_NAME, true);
			rs.closeRecordStore();
			RecordStore.deleteRecordStore(REC_NAME);
			rs = RecordStore.openRecordStore(REC_NAME, true);
			
			for (int i = 0; i<panelas.size(); i++) {
				byte[] panela = serializaPanela((Panela)panelas.elementAt(i));
				rs.addRecord(panela, 0, panela.length);
			}
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			if (rs != null) {
				try {
					rs.closeRecordStore();
				}
				catch (Exception e) {}
			}
		}		
	}
	
	private byte[] serializaPanela(Panela p) {
		
		posicao = 0;
		
		byte[] result = new byte[38];
		
		append(result, pad(Integer.toString(p.getId()), 3).getBytes());
		append(result, padStr(p.getNome(), 20).getBytes());
		append(result, doubleToString(p.getAltura()).getBytes());
		append(result, doubleToString(p.getDiametro()).getBytes());
		append(result, doubleToString(p.getCorrecao()).getBytes());
		
		return result;
	}
	
	private void append(byte[] destination, byte[] source) {
		for (int i = 0; i < source.length; i++) {
			destination[posicao++] = source[i];
		}	
	}
	
	private String pad(String str, int count) {
		
		if (str.length() > count) {
			str = str.substring(0, count);
			return str;
		}
		
		for (int i = str.length(); i < count; i++) {
			str = "0" + str;
		}
		
		return str;
	}
	
	private String padR(String str, int count) {
		
		if (str.length() > count) {
			str = str.substring(0, count);
			return str;
		}
		
		for (int i = str.length(); i < count; i++) {
			str = str + "0";
		}
		
		return str;
	}
	
	
	private String padStr(String str, int count) {
		
		if (str.length() > count) {
			str = str.substring(0, count);
			return str;
		}
		
		for (int i = str.length(); i < count; i++) {
			str = str + " ";
		}
		
		return str;
	}
	
	private String doubleToString(double valor) {
		
		int intPart = (int)valor;
		int decimals = (int)((valor - intPart) * 100.0);
		
		String strIntPart = pad(Integer.toString(intPart), 3);
		String strDecimals = padR(Integer.toString(decimals), 2);
		
		return strIntPart + strDecimals;
	}
	
	private String leString(byte[] record, int tamanho) {
		String temp;
		try {
			temp = new String(record, posicao, tamanho, "UTF8");
		}
		catch (Exception e) {
			temp = "";
		}		
		
		posicao += tamanho;
		return temp;
	}
	
	private int leInt(byte[] record) {
		String temp = leString(record, 3);
		
		try {
			return Integer.parseInt(temp);			
		}
		catch (Exception ex) {
			return 0;
		}
	}
	
	private double leDouble(byte[] record) {
		String temp = leString(record, 5);
		temp = temp.substring(0, 3) + separadorDecimal + temp.substring(3);
		
		double res = 0.0;
		
		try {
			res = Double.parseDouble(temp);
		}
		catch (Exception ex) {}
		
		return res;
	}
	
	private Panela constroiPanela(byte[] record) {
		
		Panela resultado = new Panela();
		
		posicao = 0;
		
		resultado.setId(leInt(record));

		resultado.setNome(leString(record, 20).trim());
		resultado.setAltura(leDouble(record));
		resultado.setDiametro(leDouble(record));
		resultado.setCorrecao(leDouble(record));
		
		return resultado;		
	}

}
