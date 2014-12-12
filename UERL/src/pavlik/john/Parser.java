package pavlik.john;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Parser {

	static Pattern uicNamePattern = Pattern
			.compile("^UIC: (\\w+)\\s*Unit: (.+?)\\s{3,}.*");

	public static Unit ParseUERL(File file) throws FileNotFoundException,
			IOException {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));

		String name = null, uic = null;
		Unit unit = new Unit();
		for (int k = 0; k < wb.getNumberOfSheets(); k++) {
			HSSFSheet sheet = wb.getSheetAt(k);
			int rows = sheet.getPhysicalNumberOfRows();
			if (!wb.getSheetName(k).equalsIgnoreCase("ERC - P")
					&& !wb.getSheetName(k).equalsIgnoreCase("ERC - A")) {
				continue;
			}
			if (name == null) {
				name = parseName(sheet);
				unit.setName(name);
			}
			if (uic == null) {
				uic = parseUIC(sheet);
				unit.setUic(uic);
			}
			System.out.println("Sheet " + k + " \"" + wb.getSheetName(k)
					+ "\" has " + rows + " row(s).");
			unit.addInventory(parseInventory(sheet));
		}
		return unit;
	}

	private static List<Item> parseInventory(HSSFSheet sheet) {
		List<Item> items = new ArrayList<>();
		for (int r = 5; r < sheet.getLastRowNum(); r++) {
			HSSFRow row = sheet.getRow(r);

			// Break at the first completely empty row after row 5, this denotes
			// the end of the LINs.
			if (row == null) {
				break;
			}
			String lin = null;
			String sublin = null;
			String nomenclature = null;
			long required = -1, authorized = -1, onhand = -1, duein = -1, shortage = -1;
			String rating = null;
			String remarks = null;
			String docNum = null;

			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				HSSFCell cell = row.getCell(c);
				if (cell == null) {
					continue;
				}

				switch (c) {
				case 0: // LIN
					lin = cell.getStringCellValue();
					break;
				case 1: // SUBLIN
					sublin = cell.getStringCellValue();
					break;
				case 2: // Nomen
					nomenclature = cell.getStringCellValue();
					break;
				case 3: // REQ
					required = Math.round(cell.getNumericCellValue());
					break;
				case 4: // AUTH
					authorized = Math.round(cell.getNumericCellValue());
					break;
				case 5: // O/H
					onhand = Math.round(cell.getNumericCellValue());
					break;
				case 6: // D/I
					duein = Math.round(cell.getNumericCellValue());
					break;
				case 7: // Shortage
					shortage = Math.round(cell.getNumericCellValue());
					break;
				case 8: // Rating
					rating = cell.getStringCellValue();
					break;
				case 9: // Remarks
					remarks = cell.getStringCellValue();
					break;
				case 10: // Doc Num
					docNum = cell.getStringCellValue();
					break;
				}
			}
			items.add(new Item(lin, sublin, nomenclature, required, authorized,
					onhand, duein, shortage, rating, remarks, docNum));
		}
		return items;
	}

	private static String parseNameValueCell(HSSFSheet sheet) {
		if (sheet.getPhysicalNumberOfRows() >= 2) {
			Row row = sheet.getRow(2);
			if (row != null && row.getPhysicalNumberOfCells() >= 1) {
				Cell cell = row.getCell(0);
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
					return cell.getStringCellValue();
				}
			}
		}
		return null;
	}

	private static String parseName(HSSFSheet sheet) {
		String value = parseNameValueCell(sheet);
		if (value == null)
			return null;
		Matcher matcher = uicNamePattern.matcher(value);
		if (matcher.matches()) {
			return matcher.group(2);
		}
		return null;
	}

	private static String parseUIC(HSSFSheet sheet) {
		String value = parseNameValueCell(sheet);
		if (value == null)
			return null;
		Matcher matcher = uicNamePattern.matcher(value);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}

}
