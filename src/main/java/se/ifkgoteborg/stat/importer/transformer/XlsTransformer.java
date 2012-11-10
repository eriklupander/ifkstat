package se.ifkgoteborg.stat.importer.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import se.ifkgoteborg.stat.util.StringUtil;


public class XlsTransformer {

	private static final String OUTPUT_FILE = "c:\\java\\workspace\\ifkstat\\data\\master.txt";
	//private static final String OUTPUT_FILE = "c:\\tankesmedja\\ifkstat\\ifkstat\\data\\master.txt";

	public static final String ROOT_FOLDER = "H:\\Dropbox\\Statistik till webben";
	//public static final String ROOT_FOLDER = "C:\\Users\\Erik\\Dropbox\\Statistik till webben";
	
	private static final String GOAL_TOKEN = "•";
	private static final String N = "IFK statistik";
	private static final int PLAYERS_STARTINDEX = 8;
	private static final int PLAYERS_STARTINDEX_AFTER_2003 = 9;
	private static final int MAX_CELL = 49;

	private StringBuilder buf = new StringBuilder();
	
	private static final List<String> blockList = new ArrayList<String>();
	static {
		blockList.add(N + " 1904");
		blockList.add(N + " 1905");
		blockList.add(N + " 1906");
		blockList.add(N + " 1907");
		blockList.add(N + " 1908n");
		blockList.add(N + " 1909n");		
	}
	
	private static final NumberFormat nf = NumberFormat.getIntegerInstance();
	private static final int ATTENDANCE_INDEX = 7;

	public void parseXlsToText(String outputFile) {
		nf.setMaximumFractionDigits(0);
		

		File rootFolder = new File(ROOT_FOLDER);
		if (!rootFolder.isDirectory()) {
			System.err.println("Error: Root folder is not a directory");
			return;
		}

		processDirectory(rootFolder);
		
		// Clean up some stuff in the stringbuffer
		String data = buf.toString().replaceAll("–", "-").replaceAll("\\[", "(").replaceAll("\\]", ")");
		
		//System.out.println("Total file:\n" + buf.toString());
		File f = new File(OUTPUT_FILE);
		try {
			
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(data);
			osw.close();
			fos.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	private void processDirectory(File folder) {
		
		//System.out.println("Processing directory" + folder.getAbsolutePath());
		for (File f : folder.listFiles()) {
			if (f.isDirectory()) {
				processDirectory(f);
			} else if (!f.isDirectory()
					&& f.getName().startsWith("IFK statistik")) {
				processFile(f);
			}
		}
	}

	public void processFile(File f) {
		System.out.println("Processing file:" + f.getName());
		if(inBlockList(f.getName())) {
			return;
		}
		
		String season =  f.getName().replaceAll("[^\\d]", " ").trim();
		int startYear = StringUtil.parseSeasonStringToStartYear(season);
		if(startYear > 2011)
			return;
		buf.append("$$$$" + season + "\n");
		
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found in the specified path.");
			e.printStackTrace();
		}

		POIFSFileSystem fileSystem = null;

		try {
			fileSystem = new POIFSFileSystem(inputStream);
			
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(1);
			HSSFRow playersRow = sheet.getRow(1);
			
			// First, read player numbers. (probably not present)
			int index = startYear < 2004 ? PLAYERS_STARTINDEX : PLAYERS_STARTINDEX_AFTER_2003;
			System.out.println("Start index: " + index);
			HSSFCell cell = playersRow.getCell(index);
			short lastCellNum = playersRow.getLastCellNum();
			String cellData = cell.toString();
			
			while(index < lastCellNum) {				
				buf.append("\t");
				++index;
			}
			buf.append("\n");
			
			// Then, write the player names.
			index = startYear < 2004 ? PLAYERS_STARTINDEX : PLAYERS_STARTINDEX_AFTER_2003;
			cell = playersRow.getCell(index);
			cellData = cell.toString();
			int maxIndex = 0;
			//System.out.println("cellData: " + cellData);
			while(index < lastCellNum && index < MAX_CELL && cellData.trim().length() > 0 && !cellData.trim().equalsIgnoreCase("SUMMA") && !cellData.trim().startsWith("För att")) {				
				buf.append(StringUtil.capitalize(cellData.trim()) + "\t");
				cell = playersRow.getCell(++index);
				if(cell != null) {
					cellData = cell.toString();
				}
				maxIndex++;				
			}
			buf.append("\n");
			
			// Börja läsa ut turneringar, radindex 6
			
			
			int lastRow = readTournament(sheet, maxIndex, 4, startYear);
			while(lastRow < sheet.getLastRowNum()) {
				if(rowHasData(sheet, lastRow)) {
					lastRow = readTournament(sheet, maxIndex, lastRow, startYear);
				} else {
					lastRow++;
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error parsing season: " + season + " : " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			if(inputStream != null) {
				try { inputStream.close(); } catch (IOException e) {}
			}
		}
	}

	private boolean inBlockList(String name) {
		return blockList.contains(name);
	}

	private int readTournament(HSSFSheet sheet, int maxIndex, int startRow, int startYear) {
		HSSFRow tournamentRow = sheet.getRow(startRow);
		HSSFCell tournamentCell = tournamentRow.getCell(1);
		
		// Try to find digit, e.g. season
		int index = tournamentCell.toString().indexOf("19");
		if(index == -1) {
			index = tournamentCell.toString().indexOf("20");
		}
		
		if(index > 0) {
			buf.append("####" + tournamentCell.toString().substring(0, index - 1));
			buf.append("\t" + tournamentCell.toString().substring(index));			
		} else {
			buf.append("####" + tournamentCell.toString());
		}
		
		buf.append("\n");
		int rowIndex = startRow+1;
		
		do {
			if(startYear < 2004) {
				rowIndex = readGame(sheet, maxIndex, rowIndex, startYear);
			} else {
				rowIndex = readGamePost2003(sheet, maxIndex, rowIndex, startYear);
			}
		} while(rowHasData(sheet, rowIndex));
		
		return rowIndex;
	}
	
	private int readGamePost2003(HSSFSheet sheet, int maxIndex, int rowIndex, int startYear) {
		HSSFRow gameRow = sheet.getRow(rowIndex);
		int cellIndex = 1;
		do {
			cellIndex = readCellPost2003(gameRow, cellIndex, startYear);
		} while(cellIndex < (maxIndex+PLAYERS_STARTINDEX_AFTER_2003));
		
		buf.append("\n");
		rowIndex++;
		return rowIndex;
	}
	
	
	private int readCellPost2003(HSSFRow gameRow, int cellIndex, int startYear) {
		
		if(cellIndex >= PLAYERS_STARTINDEX_AFTER_2003) {
			HSSFCell cell = gameRow.getCell(cellIndex);
			if(cell != null) {		
				if(gameRow.getCell(cellIndex).getCellType() == HSSFCell.CELL_TYPE_STRING) {
					buf.append(gameRow.getCell(cellIndex).toString() + "\t");
				} else {
					if(gameRow.getCell(cellIndex).getNumericCellValue() != 0) {
						buf.append(nf.format(gameRow.getCell(cellIndex).getNumericCellValue()) + "\t");
					} else {
						buf.append("\t");
					}
				}
			} else {
				buf.append("\t");
				//System.err.println("Error writing, cell null: Index: " + cellIndex);
			}
		} else {
			if(gameRow.getCell(cellIndex) != null) {
				writeCellData(gameRow, cellIndex);				
			}
			buf.append("\t");
		}
		
		cellIndex++;
		return cellIndex;
	}

	private int readGame(HSSFSheet sheet, int maxIndex, int rowIndex, int startYear) {
		HSSFRow gameRow = sheet.getRow(rowIndex);
		int cellIndex = 1;
				
		do {
			cellIndex = readCell(gameRow, cellIndex, startYear);
		} while(cellIndex < (maxIndex+PLAYERS_STARTINDEX));
		
		buf.append("\n");
		rowIndex++;
		return rowIndex;
	}

	
	
	
	
	private int readCell(HSSFRow gameRow, int cellIndex, int startYear) {
		int numOfGoals = 0;
		if(cellIndex > PLAYERS_STARTINDEX) {			
			// Check formatting for goals for all player indexes
			numOfGoals = calculateNumberOfGoals(gameRow, cellIndex, numOfGoals, startYear);
		}			
			
		if(cellIndex == PLAYERS_STARTINDEX-1) {
			buf.append(formatNumberCell(gameRow, cellIndex) + "\t");
			
			if(startYear < 2004) {
				buf.append(getFormationByYear(startYear));
			}
		} else {
			if(gameRow.getCell(cellIndex) != null) {
				writeCellData(gameRow, cellIndex);				
			}
			if(cellIndex > PLAYERS_STARTINDEX) {
				writeGoals(numOfGoals);
			}
			buf.append("\t");
		}
		

		
		cellIndex++;
		return cellIndex;
	}

	private String getFormationByYear(int year) {
		 
		if(year < 1971) {
			return "2-3-5\t";
		} else {
			return "4-4-2\t";
		}
	}

	private void writeGoals(int numOfGoals) {
		if(numOfGoals > 0) {
			// Strip off the last digit.
			buf.setLength(buf.length() - 1);
			for(int i = 0; i < numOfGoals; i++) {
				buf.append(GOAL_TOKEN);
			}
		}
	}

	private int calculateNumberOfGoals(HSSFRow gameRow, int cellIndex,
			int numOfGoals, int startYear) {
		HSSFCell cell2 = gameRow.getCell(cellIndex);
		if(cell2 != null) {
			if(cell2.getCellType() == 3) {
				// Text-based
				HSSFRichTextString r = cell2.getRichStringCellValue();
				for(int a = 0; a < r.getString().length(); a++) {
					if(r.getFontAtIndex(a) == 12) {
						// Found goals.
						numOfGoals = Integer.parseInt(new String(new char[]{cell2.toString().charAt(a)}));
						
					}							
				}
			} else {
				// Numeric based, never any goals then
				
			}
			
		
			
		}
		return numOfGoals;
	}

	private void writeCellData(HSSFRow gameRow, int cellIndex) {
		if(cellIndex == 2) {
			// Away team
			if(gameRow.getCell(cellIndex).toString().indexOf("(") > 0) {
				buf.append(gameRow.getCell(cellIndex).toString().substring(0, gameRow.getCell(cellIndex).toString().indexOf("(")));
			} else {
				buf.append(gameRow.getCell(cellIndex).toString());
			}
		} else if(cellIndex == ATTENDANCE_INDEX) {
			try {
				if(gameRow.getCell(cellIndex).toString().startsWith("--")) {
					buf.append("-1");
				} else {
					//System.out.println("writing attendance: " + nf.format(gameRow.getCell(cellIndex).getNumericCellValue()) + " type: " + gameRow.getCell(cellIndex).getCellType());
					if(gameRow.getCell(cellIndex).getCellType() == 0) {
						buf.append(nf.format(gameRow.getCell(cellIndex).getNumericCellValue()));
					} else {
						buf.append(gameRow.getCell(cellIndex).toString().replaceAll("[^\\d]", "").trim());
					}
				}
			} catch (Exception e) {
				System.err.println("At gamerow: "  + gameRow.getRowNum() + " Error writing attendance: " + gameRow.getCell(cellIndex).toString() + ": " + e.getMessage() );
			}
		} else {
		
			// try to write all other cell data as string
			if(gameRow.getCell(cellIndex).getCellType() == 3) {
				if(gameRow.getCell(cellIndex).getNumericCellValue() == 0) {
					buf.append("");
				} else {
					buf.append(nf.format(gameRow.getCell(cellIndex).getNumericCellValue()));
				}
			} else {
				buf.append(gameRow.getCell(cellIndex).toString());
			}
		}
	}

	private String formatNumberCell(HSSFRow gameRow, int cellIndex) {
		if(gameRow.getCell(cellIndex) == null) {
			System.err.println("Problem at index: " + cellIndex);
			return "";
		}
		if(gameRow.getCell(cellIndex).getCellType() == 1) {			
			return gameRow.getCell(cellIndex).toString().replaceAll("[^\\d]", "").trim();
		}
		return nf.format(gameRow.getCell(cellIndex).getNumericCellValue()).replaceAll("[^\\d]", "").trim();
	}

	private boolean rowHasData(HSSFSheet sheet, int row) {
		if(sheet.getRow(row) == null) {
			return false;
		}
		try {
			return !isEmpty(sheet.getRow(row).getCell(1));
		} catch (Exception e) {
			System.err.println("Error checking rowHasData for row: " + row);
			throw new RuntimeException(e);
		}
	}

	private boolean isEmpty(HSSFCell cell) {
		return cell == null || cell.toString() == null || cell.toString().trim().length() == 0;
	}
	
	public String getBuffer() {
		return buf.toString();
	}

	public static void main(String[] args) {
		new XlsTransformer().parseXlsToText("c:\\data_output.txt");
	}
}
