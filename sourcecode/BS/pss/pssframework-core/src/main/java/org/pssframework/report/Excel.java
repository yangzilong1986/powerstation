/**
 * 
 */
package org.pssframework.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.pssframework.report.model.ExcelModel;
import org.springside.modules.utils.EncodeUtils;

/**
 * @author Administrator
 *
 */
public class Excel implements ExcelProcess {
	public static final String contentType = "application/vnd.ms-excel";

	public boolean multiSheet = false;
	public String listName = null;
	public String sheetName = null;
	public String beanName = null;

	public static String EXCEL_TEMPLATE_PATH = "EXCEL_TEMPLATE_PATH";

	private ExcelModel excelModel;

	private Excel() {

	}

	public Excel(ExcelModel excelModel) {
		super();
		this.excelModel = excelModel;
	}

	@Override
	public void importData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ParsePropertyException, InvalidFormatException {

		String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String filename = getExcelModel().getTitle() + "_" + date;

		response.reset();
		response.setContentType(contentType);
		//we use action name plus timestamp as the filename
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ new String(filename.getBytes("gbk"), "iso8859-1") + ".xls\"");
		InputStream in = null;
		Workbook workbook = new HSSFWorkbook();
		try {
			in = getTemplate(getExcelModel().getTemplatePath(), request.getSession().getServletContext());

			XLSTransformer transformer = new XLSTransformer();
			transformer.setJexlInnerCollectionsAccess(true);

			if (multiSheet) {

			} else {
				workbook = transformer.transformXLS(in, getExcelModel().getDataMap());
			}

			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public void setExcelModel(ExcelModel excelModel) {
		this.excelModel = excelModel;
	}

	public ExcelModel getExcelModel() {
		return excelModel;
	}

	protected InputStream getTemplate(String location, ServletContext servletContext) {
		return servletContext.getResourceAsStream("pages" + location + ".xls");
	}

}
