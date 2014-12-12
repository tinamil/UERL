package pavlik.john;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.WorkbookUtil;

public class Writer {

	public static Workbook writeUnits(List<Unit> units) {

		Set<Item> allItems = new TreeSet<>();
		for (Unit u : units) {
			allItems.addAll(u.inventory.values());
		}

		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet sheet = wb.createSheet();
		// declare a row object reference
		Row row = null;
		// declare a cell object reference
		Cell cell = null;

		// Style the cell with borders all around.
		CellStyle borderStyle = wb.createCellStyle();
		borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
		borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
		borderStyle.setBorderRight(CellStyle.BORDER_THIN);
		borderStyle.setBorderTop(CellStyle.BORDER_THIN);

		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.cloneStyleFrom(borderStyle);
		titleStyle.setFont(boldFont);

		// set the sheet name in Unicode
		wb.setSheetName(0, WorkbookUtil.createSafeSheetName("UERL Output"));
		// create a row
		row = sheet.createRow(0);
		Row row2 = sheet.createRow(1);
		Iterator<Unit> unitIterator = units.iterator();
		int cellNum = 1;
		List<Integer> shortageColumns = new ArrayList<>();
		while (unitIterator.hasNext()) {
			cell = row.createCell(cellNum);
			cell.setCellValue(unitIterator.next().name);
			String range = getExcelColumnName(cellNum) + "1:"
					+ getExcelColumnName(cellNum + 4) + "1";
			CellRangeAddress region = CellRangeAddress.valueOf(range);
			sheet.addMergedRegion(region);
			RegionUtil
					.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, wb);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, wb);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, wb);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, wb);
			CellUtil.setAlignment(cell, wb, CellStyle.ALIGN_CENTER);

			cell = row2.createCell(cellNum++);
			cell.setCellStyle(borderStyle);
			cell.setCellValue("Authorized");

			cell = row2.createCell(cellNum++);
			cell.setCellStyle(borderStyle);
			cell.setCellValue("On Hand");

			shortageColumns.add(cellNum);
			cell = row2.createCell(cellNum++);
			cell.setCellStyle(borderStyle);
			cell.setCellValue("Shortage");

			cell = row2.createCell(cellNum++);
			cell.setCellStyle(borderStyle);
			cell.setCellValue("SubLIN\\OH");

			cell = row2.createCell(cellNum++);
			cell.setCellStyle(borderStyle);
			cell.setCellValue("Remarks");

			// Blank separator cell
			cellNum += 1;
		}

		sheet.createFreezePane(0, 2);

		Iterator<Item> itemIter = allItems.iterator();
		int rownum = 2;
		while (itemIter.hasNext()) {
			// create a row
			row = sheet.createRow(rownum++);
			cellNum = 0;
			cell = row.createCell(cellNum++);
			Item allItem = itemIter.next();
			cell.setCellValue(allItem.lin + " - " + allItem.nomenclature);
			cell.setCellStyle(borderStyle);
			for (Unit u : units) {
				Item item = u.getInventory().get(allItem.lin);
				cell = row.createCell(cellNum++);
				cell.setCellValue(item == null ? 0 : item.authorized);
				cell.setCellStyle(borderStyle);
				cell = row.createCell(cellNum++);
				cell.setCellValue(item == null ? 0 : item.onhand);
				cell.setCellStyle(borderStyle);
				cell = row.createCell(cellNum++);
				cell.setCellValue(item == null ? 0 : item.authorized
						- item.onhand);
				cell.setCellStyle(borderStyle);
				cell = row.createCell(cellNum++);
				cell.setCellValue(item == null ? "" : item.sublin);
				cell.setCellStyle(borderStyle);
				cell = row.createCell(cellNum++);
				cell.setCellValue(item == null ? "" : item.remarks);
				cell.setCellStyle(borderStyle);
				cellNum += 1;
			}
		}

		SheetConditionalFormatting sheetCF = sheet
				.getSheetConditionalFormatting();

		ConditionalFormattingRule shortageRule = sheetCF
				.createConditionalFormattingRule(ComparisonOperator.GT, "0");

		PatternFormatting patternFmt = shortageRule.createPatternFormatting();
		patternFmt.setFillBackgroundColor(IndexedColors.RED.index);

		ConditionalFormattingRule excessRule = sheetCF
				.createConditionalFormattingRule(ComparisonOperator.LT, "0");

		patternFmt = excessRule.createPatternFormatting();
		patternFmt.setFillBackgroundColor(IndexedColors.GREEN.index);

		FontFormatting fontFmt = excessRule.createFontFormatting();
		fontFmt.setFontColorIndex(IndexedColors.WHITE.index);

		ConditionalFormattingRule[] cfRules = { shortageRule, excessRule };

		List<CellRangeAddress> cellRangeAddresses = new ArrayList<>();
		for (Integer shortageColumn : shortageColumns) {
			String columnChar = getExcelColumnName(shortageColumn);
			cellRangeAddresses.add(CellRangeAddress.valueOf(columnChar + "3:"
					+ columnChar + rownum));
		}

		sheetCF.addConditionalFormatting(
				cellRangeAddresses.toArray(new CellRangeAddress[0]), cfRules);

		for (int i = 0; i < cellNum; ++i) {
			sheet.autoSizeColumn(i);
		}
		return wb;
	}

	private static String getExcelColumnName(int columnNumber) {
		int dividend = columnNumber + 1;
		String columnName = "";
		int modulo;

		while (dividend > 0) {
			modulo = (dividend - 1) % 26;
			columnName = ((char) ('A' + modulo)) + columnName;
			dividend = (int) ((dividend - modulo) / 26);
		}

		return columnName;
	}
}
