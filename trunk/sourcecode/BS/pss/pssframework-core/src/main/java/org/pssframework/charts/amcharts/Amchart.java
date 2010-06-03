/**
 * 
 */
package org.pssframework.charts.amcharts;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.pssframework.charts.IChartData;

/**
 * @author PPT
 *
 */
public abstract class Amchart<T, PK extends Serializable> implements IChartData {

	/**
	 * 
	 */
	public Amchart() {
		// TODO Auto-generated constructor stub
	}

	public abstract Class<?> getEntityClass();

	/**
	 * 使用Dom4j生成XML格式内容.
	 * 示例:
	 * <chart>
	 * 	<series>
	 * 		<value xid="1">1950</value>
	 * 	</series>
	 * 	<graphs>
	 * 		<graph gid="1">
	 * 			<value xid="1" color="#318DBD" url="/showcase/report/flashchart/report2-drilldown.action?id=100">-0.307</value>
	 * 		</graph>
	 * 		<graph gid="2">
	 * 			<value xid="1">-0.171</value>
	 * 		</graph>
	 * 	</graphs>
	 * </chart>
	 */
	public String renderXml(final List<T> list) throws IOException {

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("chart");
		Element series = root.addElement("series");
		Element graphs = root.addElement("graphs");
		Element graphAnomaly = graphs.addElement("graph").addAttribute("gid", "1");
		Element graphSmoothed = graphs.addElement("graph").addAttribute("gid", "2");

		//		for (int i = 0; i < list.size(); i++) {
		//			String xid = String.valueOf(i);
		//			//TemperatureAnomaly data = temperatureAnomalyArray[i];
		//
		//			series.addElement("value").addAttribute("xid", xid).addText(String.valueOf(data.getYear()));
		//
		//			Element graphAnomalyValue = graphAnomaly.addElement("value").addAttribute("xid", String.valueOf(i))
		//					.addText(String.valueOf(data.getAnomaly()));
		//
		//			//graphAnomalyValue.addAttribute("url", url + i);
		//
		//			if (data.getAnomaly() < 0) {
		//				graphAnomalyValue.addAttribute("color", "#318DBD");
		//			}
		//
		//			graphSmoothed.addElement("value").addAttribute("xid", xid).addText(String.valueOf(data.getSmoothed()));
		//		}
		//
		//		HttpServletResponse response = Struts2Utils.getResponse();
		//		response.setContentType("text/xml;charset=utf-8");
		//
		//		OutputFormat format = OutputFormat.createCompactFormat();
		//		XMLWriter xmlWriter = new XMLWriter(response.getWriter(), format);
		//		xmlWriter.write(document);
		//		xmlWriter.close();

		return null;
	}

	class chartData {
		private String chartX;
		private String chartY;

		/**
		 * @return the chartX
		 */
		public String getChartX() {
			return chartX;
		}

		/**
		 * @param chartX the chartX to set
		 */
		public void setChartX(String chartX) {
			this.chartX = chartX;
		}

		/**
		 * @return the chartY
		 */
		public String getChartY() {
			return chartY;
		}

		/**
		 * @param chartY the chartY to set
		 */
		public void setChartY(String chartY) {
			this.chartY = chartY;
		}
	}
}
