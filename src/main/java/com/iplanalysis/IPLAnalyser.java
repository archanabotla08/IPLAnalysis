package com.iplanalysis;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class IPLAnalyser {
	public static List<IPLMostRunsCSV> iplCSVList;
	public static List<IPLMostWktsCSV> iplWktsCSVList;

	public int loadCSVData(String indiaCensusCSVFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
			System.out.println("reader:"+reader.getClass());
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			iplCSVList = csvBuilder.getCSVFileList(reader, IPLMostRunsCSV.class);
			System.out.println(iplCSVList);
			for (IPLMostRunsCSV iplMostRunsCSV : iplCSVList) {
				System.out.println(iplMostRunsCSV);
			}
			return iplCSVList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
	}
	
	public int loadWktsCSVData(String indiaCensusCSVFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			iplWktsCSVList = csvBuilder.getCSVFileList(reader, IPLMostWktsCSV.class);
			return iplCSVList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
	}

	public String getTopBattingAverages(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if (iplCSVList == null || iplCSVList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<IPLMostRunsCSV> censusComparator = Comparator.comparing(census -> census.avg);
		this.sort(censusComparator);
		System.out.println("censiuc"+censusComparator);
		String sortedStateCensusJson = new Gson().toJson(this.iplCSVList);
		System.out.println("sortedStateCensusJson"+sortedStateCensusJson);
		return sortedStateCensusJson;
	}
	public String getTopStrikingRates(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if (iplCSVList == null || iplCSVList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<IPLMostRunsCSV> censusComparator = Comparator.comparing(census -> census.sr);
		this.sort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(this.iplCSVList);
		return sortedStateCensusJson;
	}
	public List<IPLMostRunsCSV> getTop4sStrikerCricketer(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		List<IPLMostRunsCSV> playerWithMax4s = iplCSVList.stream()
				.sorted((player1, player2) -> Double.compare(player1.getNum4s(), player2.getNum4s()))
				.collect(Collectors.toList());
		Collections.reverse(playerWithMax4s);
		return playerWithMax4s;
	}
	
	public List<IPLMostRunsCSV> getTop6sStrikerCricketer(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		List<IPLMostRunsCSV> playerWithMax4s = iplCSVList.stream()
				.sorted((player1, player2) -> Double.compare(player1.getNum6s(), player2.getNum6s()))
				.collect(Collectors.toList());
		Collections.reverse(playerWithMax4s);
		return playerWithMax4s;
	}
	public List<IPLMostRunsCSV> bestStrikingRatesWith6sN4sCriketer(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		Integer playerWithMax6sN4s = iplCSVList.stream().map(player -> (player.getNum4s() + player.getNum6s()))
				.max(Double::compare).get();
		List<IPLMostRunsCSV> playerWithBest6sN4s = iplCSVList.stream()
				.filter(player -> player.getNum4s() + player.getNum6s() == playerWithMax6sN4s)
				.collect(Collectors.toList());
		double playerWithBestStrike = playerWithBest6sN4s.stream().map(IPLMostRunsCSV::getSr).max(Double::compare)
				.get();
		return playerWithBest6sN4s.stream().filter(player -> player.getSr() == playerWithBestStrike)
				.collect(Collectors.toList());
	}
	public List<IPLMostRunsCSV> bestStrikingRatesWithBestAverages(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		double bestAverages = iplCSVList.stream().map(IPLMostRunsCSV::getAvg).max(Double::compare).get();
		List<IPLMostRunsCSV> playerWithBestAverages = iplCSVList.stream()
				.filter(player -> player.getAvg() == bestAverages).collect(Collectors.toList());
		double playerWithBestStrike = playerWithBestAverages.stream().map(IPLMostRunsCSV::getSr).max(Double::compare)
				.get();
		return playerWithBestAverages.stream().filter(player -> player.getSr() == playerWithBestStrike)
				.collect(Collectors.toList());
	}
	public List<IPLMostRunsCSV> bestCricketersWhoHitMaxWithBestAverages(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		int maxRuns = iplCSVList.stream().map(IPLMostRunsCSV::getRuns).max(Integer::compare).get();
		List<IPLMostRunsCSV> playerWithMaxRuns = iplCSVList.stream().filter(player -> player.getRuns() == maxRuns)
				.collect(Collectors.toList());
		double playerWithBestAverages = playerWithMaxRuns.stream().map(IPLMostRunsCSV::getAvg).max(Double::compare)
				.get();
		return playerWithMaxRuns.stream().filter(player -> player.getAvg() == playerWithBestAverages)
				.collect(Collectors.toList());
	}
	
	public String getTopBowlingAverages(String csvFilePath) throws CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		if (iplWktsCSVList == null || iplWktsCSVList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<IPLMostWktsCSV> censusComparator = Comparator.comparing(census -> census.avg);
		this.wktsSort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(this.iplWktsCSVList);
		return sortedStateCensusJson;
	}
	public String getTopBowlingStrikeRates(String csvFilePath) throws CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		if (iplWktsCSVList == null || iplWktsCSVList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<IPLMostWktsCSV> censusComparator = Comparator.comparing(census -> census.sr);
		this.wktsSort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(this.iplWktsCSVList);
		return sortedStateCensusJson;
	}
	public List<IPLMostWktsCSV> getBestBowlingEconomyCricketer(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		List<IPLMostWktsCSV> playerWithBestEconomy = iplWktsCSVList.stream()
				.sorted(Comparator.comparingDouble(IPLMostWktsCSV::getEcon)).collect(Collectors.toList());
		Collections.reverse(playerWithBestEconomy);
		return playerWithBestEconomy;
	}
	public List<IPLMostWktsCSV> bestBowlingStrikingRatesWith5wN4w(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		int playerWith4wN5w = iplWktsCSVList.stream().map(player -> (player.getNumber4w() + player.getNumber5w()))
				.max(Integer::compare).get();
		List<IPLMostWktsCSV> playerWithBest4wN5w = iplWktsCSVList.stream()
				.filter(player -> player.getNumber4w() + player.getNumber4w() == playerWith4wN5w)
				.collect(Collectors.toList());
		double playerWithBestStrikeRate = playerWithBest4wN5w.stream().map(IPLMostWktsCSV::getSr).max(Double::compare)
				.get();
		return playerWithBest4wN5w.stream().filter(player -> player.getSr() == playerWithBestStrikeRate)
				.collect(Collectors.toList());
	}

	public List<IPLMostWktsCSV> greatBowlingAveragesWithBestStrikingRates(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		double playerWithAverages = iplWktsCSVList.stream().map(IPLMostWktsCSV::getAvg).max(Double::compare).get();
		List<IPLMostWktsCSV> playerWithBestAverages = iplWktsCSVList.stream()
				.filter(player -> player.getAvg() == playerWithAverages).collect(Collectors.toList());
		double playerWithBestStrikeRate = playerWithBestAverages.stream().map(IPLMostWktsCSV::getSr)
				.max(Double::compare).get();
		return playerWithBestAverages.stream().filter(player -> player.getSr() == playerWithBestStrikeRate)
				.collect(Collectors.toList());
	}
	
	public List<IPLMostWktsCSV> getBowlersMaximumWicketsWithBestBowlingAvgs(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadWktsCSVData(csvFilePath);
		int playerWithWkts = iplWktsCSVList.stream().map(IPLMostWktsCSV::getWkts).max(Integer::compare).get();
		List<IPLMostWktsCSV> playerWithMaxWkts = iplWktsCSVList.stream()
				.filter(player -> player.getWkts() == playerWithWkts).collect(Collectors.toList());
		double playerWithBestBowlingAvgs = playerWithMaxWkts.stream().map(IPLMostWktsCSV::getAvg)
				.max(Double::compare).get();
		return playerWithMaxWkts.stream().filter(player -> player.getAvg() == playerWithBestBowlingAvgs)
				.collect(Collectors.toList());
	}
	public List<String> BestBattingAverageWithBestBowlingAverage() throws IOException, CensusAnalyserException {
		List<String> list = new ArrayList<>();
		List<IPLMostRunsCSV> playerWithBestBattingAvg = iplCSVList.stream()
				.sorted(Comparator.comparingDouble(player -> player.avg)).collect(Collectors.toList());
		Collections.reverse(playerWithBestBattingAvg);
		List<IPLMostWktsCSV> playerWithBestBowlingAvg = iplWktsCSVList.stream()
				.sorted(Comparator.comparingDouble(player -> player.avg)).collect(Collectors.toList());
		for (IPLMostRunsCSV mostRunsCSV : playerWithBestBattingAvg) {
			for (IPLMostWktsCSV mostWktsCSV : playerWithBestBowlingAvg) {
				if (mostRunsCSV.player.equals(mostWktsCSV.player)) {
					list.add(mostRunsCSV.player);
				}
			}
		}
		return list;
	}

	public void sort(Comparator<IPLMostRunsCSV> censusComparator) {
		for (int i = 0; i < iplCSVList.size(); i++) {
			for (int j = 0; j < iplCSVList.size() - i - 1; j++) {
				IPLMostRunsCSV census1 = iplCSVList.get(j);
				IPLMostRunsCSV census2 = iplCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					iplCSVList.set(j, census2);
					iplCSVList.set(j + 1, census1);
				}
			}
		}
	}
	
	public void wktsSort(Comparator<IPLMostWktsCSV> censusComparator) {
		for (int i = 0; i < iplWktsCSVList.size(); i++) {
			for (int j = 0; j < iplWktsCSVList.size() - i - 1; j++) {
				IPLMostWktsCSV census1 = iplWktsCSVList.get(j);
				IPLMostWktsCSV census2 = iplWktsCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					iplWktsCSVList.set(j, census2);
					iplWktsCSVList.set(j + 1, census1);
				}
			}
		}
	}
}
