package org.pssframework.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class FusionChartsUtils {
    public static final String LINE = "MSLine.swf";         // 曲线图
    public static final String COLUMN = "MSColumn2D.swf";   // 柱状图
    
    /**
     * 
     * @param results
     * @param mapSeriesNames
     * @param mapParams
     * @return
     */
    public static String getChart(List<?> results, Map<String, String> mapSeriesNames, Map<String, String> mapParams,
            String chartId, boolean isRealTime, String xAxisName, String yAxisName) {
        String chart = getChartData(results, mapSeriesNames, mapParams, isRealTime, xAxisName, yAxisName);
        String chartCode = "";
        chartCode = FusionChartsCreator.createChart(mapParams.get("contextPath") + "/FusionCharts/" + LINE, "", chart,
                chartId, "100%", "100%", false, true); // 生成图形
        return chartCode;
    }

    /**
     * 
     * @param results
     * @param mapSeriesNames
     * @param mapParams
     * @return
     */
    public static String getChart(List<?> results, Map<String, String> mapSeriesNames, Map<String, String> mapParams) {
        String chart = getChartData(results, mapSeriesNames, mapParams, false, null, null);
        String chartCode = "";
        chartCode = FusionChartsCreator.createChart(mapParams.get("contextPath") + "/FusionCharts/" + LINE, "", chart,
                "chart", "100%", "100%", false, true); // 生成图形
        return chartCode;
    }

    /**
     * 
     * @param results
     * @param mapSeriesNames
     * @param mapParams
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getChartData(List<?> results, Map<String, String> mapSeriesNames,
            Map<String, String> mapParams, boolean isRealTime, String xAxisName, String yAxisName) {
        if(mapSeriesNames == null)
            return null;
        int msnCnt = mapSeriesNames.keySet().size();
        String[] seriesCodes = new String[msnCnt];
        String[] seriesNames = new String[msnCnt];
        String[] colors = new String[msnCnt];
        Iterator itsn = mapSeriesNames.keySet().iterator();
        int i = 0;
        while(itsn.hasNext()) {
            seriesCodes[i] = (String) itsn.next();
            seriesNames[i] = mapSeriesNames.get(seriesCodes[i]);
            colors[i] = "#" + seriesNames[i].split("#")[1];
            seriesNames[i] = seriesNames[i].split("#")[0];
            i++;
        }
        
        List<String[]> values = null;
        int k = 0;
        if(results != null) {
            values = new ArrayList<String[]>();
            Iterator iterator = results.iterator();
            if(iterator != null) {
                while(iterator.hasNext() && k < 1000) {
                    k++;
                    String[] ss = new String[seriesCodes.length + 1];
                    Object o = (Object) iterator.next();
                    try {
                        if(mapParams.get("timelabel") != null && !"".equals(mapParams.get("timelabel"))) {
                            ss[0] = regularDataTime((Date) invokeMethod(o, "getDataTime", null), mapParams.get("timelabel"));
                        }
                        else {
                            ss[0] = regularDataTime((Date) invokeMethod(o, "getDataTime", null), "[dd日HH时]");
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    for(int j = 0; j < seriesCodes.length; j++) {
                        try {
                            ss[j + 1] = (invokeMethod(o, "get" + StringUtil.capital(seriesCodes[j]), null) != null ? invokeMethod(o, "get" + StringUtil.capital(seriesCodes[j]), null).toString() : "");
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    values.add(ss);
                }
            }
        }
        
        int labelStep = 1;
        if(k > 12) {
            labelStep = k / 12;
        }
        

        return getChartData(mapParams.get("caption"), labelStep, seriesNames, colors, values, isRealTime, xAxisName,
                yAxisName);
    }

    private static String regularDataTime(Date d, String r) {
        SimpleDateFormat sdf = new SimpleDateFormat(r);
        return sdf.format(d);
    }

    /**
     * 
     * @param owner
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class ownerClass = owner.getClass();

        Class[] argsClass = null;
        if(args != null) {
            argsClass = new Class[args.length];
            for(int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(owner, args);
    }

    /**
     * 
     * @param caption
     * @param labelStep
     * @param contextPath
     * @param seriesNames
     * @param colors
     * @param values
     * @param width
     * @param height
     * @param xAxisName
     * @param yAxisName
     * @return
     */
    public static String getChartData(String caption, int labelStep, String[] seriesNames, String[] colors,
            List<?> values, boolean isRealTime, String xAxisName, String yAxisName) {
        StringBuffer chart = new StringBuffer();
        String chartDis = "";
        if(values != null && values.size() > 0) {           // 有数据初始化图形数据
            if(isRealTime) {
                chartDis = "<chart caption='" + caption + "'"
                        + " labelStep='" + labelStep + "'"
                        + addChartUtils(xAxisName, yAxisName)
                        + " anchorRadius='2' animation='0' lineThickness='2' showRealTimeValue='1' numDisplaySets='8' showValues='0' showLabels='1'>";
            }
            else {
                chartDis = "<chart caption='" + caption + "'"
                        + " labelStep='" + labelStep + "'"
                        + addChartUtils(xAxisName, yAxisName)
                        + " anchorRadius='2' animation='1' lineThickness='2' showValues='0' showLabels='1'>";
            }
            chart.append(chartDis);
            chart.append("<categories>");
            StringBuffer[] lines = new StringBuffer[seriesNames.length];
            for(int i = 0; i < seriesNames.length; i++) {   // 循环生成每个图形元素的数据
                StringBuffer line = new StringBuffer();
                line.append("<dataset seriesName='" + seriesNames[i] + "' color='" + colors[i]
                        + "' anchorBorderColor='" + colors[i] + "' anchorBgColor='" + colors[i] + "'>");
                for(int j = 0; j < values.size(); j++) {
                    String[] items = (String[]) values.get(j);
                    if(i == 0) {
                        chart.append("<category label='" + items[0] + "' />");      // 横坐标
                    }
                    /** 为空不显示 */
                    line.append("<set value='" + (items[i + 1] == null ? "" : items[i + 1]) + "' />");
                }
                line.append("</dataset>");
                lines[i] = line;
            }
            chart.append("</categories>");
            for(int i = 0; i < lines.length; i++) { // 将每个图形元素的数据添加到整个图形数据中
                chart.append(lines[i].toString());
            }
        }
        else { // 没有数据则初始化空白图形
            if(isRealTime) {
                chartDis = "<chart caption='" + caption + "'"
                        + " labelStep='1' anchorRadius='0' animation='0' lineThickness='2' showRealTimeValue='1' numDisplaySets='8' showValues='0' showLabels='1'>";
            }
            else {
                chartDis = "<chart caption='" + caption + "'"
                        + " labelStep='1' anchorRadius='0' animation='1' lineThickness='2' showValues='0' showLabels='1'>";
            }
            chart.append(chartDis);
            chart.append("<categories>");
            StringBuffer[] lines = new StringBuffer[seriesNames.length];
            for(int i = 0; i < seriesNames.length; i++) {
                if(i == 0) {
                    chart.append("<category label='' />");
                }
                StringBuffer line = new StringBuffer();
                line.append("<dataset seriesName='" + seriesNames[i] + "' color='" + colors[i]
                        + "' anchorBorderColor='" + colors[i] + "' anchorBgColor='" + colors[i] + "'>");
                line.append("<set value='' />");
                line.append("</dataset>");
                lines[i] = line;
            }
            chart.append("</categories>");
            for(int i = 0; i < lines.length; i++) {
                chart.append(lines[i].toString());
            }
        }
        chart.append("<styles>");
        chart.append("<definition>");
        chart.append("<style name='CaptionFont' type='font' size='12' />");
        chart.append("</definition>");
        chart.append("<application>");
        chart.append("<apply toObject='CAPTION' styles='CaptionFont' />");
        chart.append("<apply toObject='SUBCAPTION' styles='CaptionFont' />");
        chart.append("</application>");
        chart.append("</styles>");
        chart.append("</chart>");
        return chart.toString();
    }

    /**
     * 
     * @param xAxisName
     * @param yAxisName
     * @return
     */
    private static String addChartUtils(String xAxisName, String yAxisName) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.isEmpty(xAxisName) ? "" : " xAxisName='" + xAxisName + "'");
        sb.append(StringUtils.isEmpty(yAxisName) ? "" : " yAxisName='" + yAxisName + "'");
        return sb.toString();
    }
}
