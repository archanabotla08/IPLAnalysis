package com.iplanalysis;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder{

	@Override
	public List<E> getCSVFileList(Reader reader, Class csvClass) throws IllegalStateException, CensusAnalyserException {
		return this.getCSVBean(reader, csvClass).parse();
	}

	private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CensusAnalyserException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass).withIgnoreLeadingWhiteSpace(true);
			return csvToBeanBuilder.build();
		} catch (RuntimeException runtimeException) {
			throw new CensusAnalyserException("Field Exception", CensusAnalyserException.ExceptionType.NO_SUCH_FIELD);
		}
	}

	@Override
	public List getCSVFileIterator(Reader reader, Class csvClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
