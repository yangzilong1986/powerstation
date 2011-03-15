/**
 * 
 */
package org.pssframework.report;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * @author Administrator
 *
 */
public interface ExcelProcess {

	void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ParsePropertyException, InvalidFormatException;

	void importData();

}
