package com.iplanalysis;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilder<E> {
	public List<E> getCSVFileIterator(Reader reader,Class<E> csvClass) throws Exception;

	public List<IPLMostRunsCSV> getCSVFileList(Reader reader, Class<IPLMostRunsCSV> class1) throws IllegalStateException, CensusAnalyserException;
	
}

