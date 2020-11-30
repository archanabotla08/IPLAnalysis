package com.iplanalysis;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {

	public static final String  File_Path = "C:\\Users\\AB\\eclipse-workspace\\IPLAnalysis\\static\\IPL2019FactsheetMostRuns.csv";
	public static final String Wkt_File_Path = "C:\\Users\\AB\\eclipse-workspace\\IPLAnalysis\\static\\IPL2019FactsheetMostWkts.csv";
	private static IPLAnalyser iplAnalyser;

	@BeforeClass
	public static void createcensusAnalyser() {
		iplAnalyser = new IPLAnalyser();
		}

	@Test
	public void givenIPLInfo_ShouldReturnNumberOfRecords() throws CensusAnalyserException {
		assertEquals(101, iplAnalyser.loadCSVData(File_Path));
	}

	@Test
	public void givenIPLInfo_ShouldReturnTopBattingAverages() {
		String sortedCensusData = null;
		try {
			sortedCensusData = iplAnalyser.getTopBattingAverages(File_Path);
			IPLMostRunsCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IPLMostRunsCSV[].class);
			assertEquals(83.2, censusCSV[censusCSV.length - 1].avg, 0.0);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenIPLInfo_ShouldReturnTopStrikeRatings() {
		String sortedCensusData = null;
		try {
			sortedCensusData = iplAnalyser.getTopStrikingRates(File_Path);
			IPLMostRunsCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IPLMostRunsCSV[].class);
			assertNotEquals(333.33, censusCSV[censusCSV.length - 1].avg, 0.0);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void givenIPLInfo_ShouldReturnTop4sStrikerCricketer() throws IOException, CensusAnalyserException {
		List<IPLMostRunsCSV> lst = new IPLAnalyser().getTop6sStrikerCricketer(File_Path);
		assertEquals("Shikhar Dhawan", new IPLAnalyser().getTop4sStrikerCricketer(File_Path).get(0).player);
	}
	
	@Test
	public void givenIPLInfo_ShouldReturnTop6sStrikerCricketer() throws IOException, CensusAnalyserException {
		List<IPLMostRunsCSV> lst = new IPLAnalyser().getTop6sStrikerCricketer(File_Path);
		assertEquals("Andre Russell", new IPLAnalyser().getTop6sStrikerCricketer(File_Path).get(0).player);
	}
	
	

	@Test
	public void givenIPLInfo_ShouldReturnTop6sStrikerNAveragesCricketer() throws IOException, CensusAnalyserException {
		assertEquals("Andre Russell",
				new IPLAnalyser().bestStrikingRatesWith6sN4sCriketer(File_Path).get(0).player);
	}
	@Test
	public void givenIPLInfo_ShouldReturnbestStrikingRatesWithBestAverages()
			throws IOException, CensusAnalyserException {
		assertEquals("MS Dhoni", new IPLAnalyser().bestStrikingRatesWithBestAverages(File_Path).get(0).player);
	}
	
	@Test
	public void givenIPLInfo_ShouldReturnBestCricketersWhoHitMaxWithBestAverages()
			throws IOException, CensusAnalyserException {
		assertEquals("David Warner",
				new IPLAnalyser().bestCricketersWhoHitMaxWithBestAverages(File_Path).get(0).player);
	}
	
	@Test
	public void givenIPLInfo_ShouldReturnTopBowlingAverages() {
		String sortedCensusData = null;
		try {
			sortedCensusData = iplAnalyser.getTopBattingAverages(Wkt_File_Path);
			IPLMostWktsCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IPLMostWktsCSV[].class);
			assertEquals(166, censusCSV[censusCSV.length - 1].avg, 0.0);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void givenIPLInfo_ShouldReturnTopBowlingStrikeRates() {
		String sortedCensusData = null;
		try {
			sortedCensusData = iplAnalyser.getTopBowlingStrikeRates(Wkt_File_Path);
			IPLMostWktsCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IPLMostWktsCSV[].class);
			assertEquals(120, censusCSV[censusCSV.length - 1].sr, 0.0);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void givenIPLInfo_ShouldReturnBestBowlingEconomyCricketerr() throws IOException, CensusAnalyserException {
		assertEquals("Ben Cutting",
				new IPLAnalyser().getBestBowlingEconomyCricketer(Wkt_File_Path).get(0).player);
	}
	@Test
	public void givenIPLInfo_ShouldReturnBestBowlingStrikingRatesWith5wN4w()
			throws IOException, CensusAnalyserException {
		assertEquals("Lasith Malinga",
				new IPLAnalyser().bestBowlingStrikingRatesWith5wN4w(Wkt_File_Path).get(0).player);
	}
	@Test
	public void givenIPLInfo_ShouldReturnGreatBowlingAveragesWithBestStrikingRates()
			throws IOException, CensusAnalyserException {
		assertEquals("Krishnappa Gowtham",
				iplAnalyser.greatBowlingAveragesWithBestStrikingRates(Wkt_File_Path).get(0).player);
	}
	@Test
	public void givenMostWktsCSV_ShouldReturnMaximumWickets_WithBestBowlingAvgs()
			throws IOException, CensusAnalyserException {
		assertEquals("Imran Tahir",
				iplAnalyser.getBowlersMaximumWicketsWithBestBowlingAvgs(Wkt_File_Path).get(0).player);
	}
	
	@Test
	public void givenMostWktsCSV_ShouldReturnBestBatting_AndBowlingAverages() throws IOException, CensusAnalyserException {
		assertEquals("Andre Russell", iplAnalyser.BestBattingAverageWithBestBowlingAverage().get(0));
	}
	
	@Test
	public void givenMostWktsCSV_ShouldReturnBestallRounder() throws IOException, CensusAnalyserException {
		assertEquals("Andre Russell", iplAnalyser.getBestAllRounders().get(0));
	}
	@Test
	public void givenMostRunsCSV_ShouldReturnMaxHundreds_WithBestBattingAvgs()
			throws IOException, CensusAnalyserException {
		assertEquals("David Warner ",
				iplAnalyser.maximum100sWithBestBattingAverage(Wkt_File_Path).get(0).player);
	}
}
