package com.iplanalysis;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {

	public static final String  File_Path = "C:\\Users\\AB\\eclipse-workspace\\IPLAnalysis\\static\\IPL2019FactsheetMostRuns.csv";
	
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
}
