package com.iplanalysis;

import com.opencsv.bean.CsvBindByName;

public class IPLMostWktsCSV {
	public int getNumber4w() {
		return number4w;
	}

	public double getSr() {
		return sr;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public void setSr(double sr) {
		this.sr = sr;
	}

	public void setNumber4w(int number4w) {
		this.number4w = number4w;
	}

	public int getNumber5w() {
		return number5w;
	}

	public void setNumber5w(int number5w) {
		this.number5w = number5w;
	}

	public double getEcon() {
		return econ;
	}

	public void setEcon(double econ) {
		this.econ = econ;
	}

	@CsvBindByName(column = "POS")
	public int pos;

	@CsvBindByName(column = "PLAYER")
	public String player;

	@CsvBindByName(column = "Mat")
	public int mat;

	@CsvBindByName(column = "Inns")
	public int inns;

	@CsvBindByName(column = "Ov")
	public double ov;

	@CsvBindByName(column = "Runs")
	public int runs;

	@CsvBindByName(column = "Wkts")
	public int wkts;

	@CsvBindByName(column = "BBI")
	public int bbi;

	@CsvBindByName(column = "Avg")
	public double avg;

	@CsvBindByName(column = "Econ")
	public double econ;

	@CsvBindByName(column = "SR")
	public double sr;

	@CsvBindByName(column = "4w")
	public int number4w;

	@CsvBindByName(column = "5w")
	public int number5w;

}
