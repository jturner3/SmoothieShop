package org.elevenfifty.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CSVReader extends BufferedReader {

	private String[] columnNames;

	public CSVReader(Reader in) {
		super(in);
	}

	public CSVReader(Reader in, int sz) {
		super(in, sz);
	}

	public CSVReader(Reader in, String... columnNames) {
		this(in);
		this.columnNames = columnNames;
	}

	public void skipRow() throws IOException {
		super.readLine();
	}

	public String[] readRow() throws IOException {
		String line = super.readLine();
		return line == null ? null : line.split(",");
	}

	public String[] readRow(int columns) throws IOException {
		String line = super.readLine();
		return line == null ? null : line.split(",", columns);
	}

	public String[] readHeaderRow() throws IOException {
		columnNames = readRow();
		return columnNames;
	}

	public Map<String, String> readRowAsMap() throws IOException {
		if (columnNames == null) {
			throw new IllegalStateException("Must set Column Names before calling this method");
		}
		String[] row = readRow(columnNames.length);
		if (row == null) {
			return null;
		}
		if (row.length != columnNames.length) {
			throw new IOException("Mismatched column count");
		}
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < columnNames.length; i++) {
			map.put(columnNames[i], row[i]);
		}
		return map;
	}

}
