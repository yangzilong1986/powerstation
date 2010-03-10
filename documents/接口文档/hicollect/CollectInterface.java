package peis.interfaces.hicollect;

import java.util.List;
import java.util.Map;

import com.hisun.client.HiInvokeService;
import com.hisun.grv.interfaces.HiConstants;
import com.hisun.grv.interfaces.HiNationHelper;
import com.hisun.grv.interfaces.HiSouthHelper;
import com.hisun.grv.interfaces.HiTagName;
import com.hisun.message.HiETF;

/**
 * @see peis.interfaces.hicollect.ICollectInterface
 * @author zhanyi
 * @version 1.0 Create Date : 20090625
 *          <p>
 *          modified by zhan_yi at 2009.7.12 删除日志开关
 *          </p>
 */
public class CollectInterface implements ICollectInterface {

	/**
	 * 任务超时时间
	 */
	private int tmOut = 300;

	public Map<String, Map<String, String>> getReturnByREP102(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public Map<String, Map<String, String>> getReturnByRRD102(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public Map<String, String> getReturnByWEP102(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
	}

	public boolean readEquipmentParameters102(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception {
		//因为命令项数据有
		return HiSouthHelper.write(81, collectObjects, commandItems, null, appId, tmOut);
	}

	public boolean readRealtimeData102(List<CollectObject> collectObjects, List<CommandItem> commandItems, List<AssistParam> assistParams, long appId) throws Exception {
		return HiSouthHelper.write(82, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean writeEquipmentParameters102(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception {
		return HiSouthHelper.write(80, collectObjects, commandItems, null, appId, tmOut);
	}

	public boolean writeEquipmentParameters(List collectObjects, List commandItems, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_WRITE_PARAM, appId, collectObjects, commandItems, tmOut);
	}

	public boolean readEquipmentParameters(List collectObjects, String[] commandItems, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_READ_PARAM, appId, collectObjects, commandItems, null, tmOut);
	}

	public boolean writeResetCommands(List collectObjects, List commandItems, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_WRITE_RESET, appId, collectObjects, commandItems, tmOut);
	}

	public boolean writeControlCommands(List collectObjects, List commandItems, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_WRITE_CONTROL, appId, collectObjects, commandItems, tmOut);
	}

	public boolean readRealtimeData(List collectObjects, String[] commandItems, List assistParams, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_READ_REALTIME_DATA, appId, collectObjects, commandItems,
				assistParams, tmOut);
	}

	public Map getReturnByWEP(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
		// return HiNationHelper.callHiUpService(appId, HiConstants.FLAG_WR_W);
	}

	public Map getReturnByREP(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
		// return HiNationHelper.callHiUpService(appId, HiConstants.FLAG_WR_RP);
	}

	public Map getReturnByWRC(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
		// return HiNationHelper.callHiUpService(appId, HiConstants.FLAG_WR_W);
	}

	public Map getReturnByWCC(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
		// return HiNationHelper.callHiUpService(appId, HiConstants.FLAG_WR_W);
	}

	public Map getReturnByRRD(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
		// return HiNationHelper.callHiUpService(appId, HiConstants.FLAG_WR_RD);
	}

	// 小圈轮召启动
	public boolean callLittliCycle(List collectObjects, String[] commandItems, long appId, int cyclCount, int cycleTime)
			throws Exception {
		return HiNationHelper.callDownServiceOfCallLittleCycle(appId, collectObjects, commandItems, cyclCount, cycleTime, tmOut);
	}

	public boolean sendCustomFrame(CollectObject collectObject, String customFrame) throws Exception {
		boolean rCode = false;
		HiInvokeService service = HiInvokeService.createService();
		HiETF etf = service.getETF();
		etf.appendNode(HiTagName.LOGICAL_ADDR, collectObject.getLogicalAddr());
		etf.appendNode(HiTagName.CHANNEL_TYPE, collectObject.getChannelType());
		etf.appendNode(HiTagName.PROTOCOL_NO, collectObject.getEquipProtocol());
		etf.appendNode(HiTagName.CUST_FRAME, customFrame);
		etf = service.invokeRetETF(HiConstants.TXNCODE_CUSTOM_FRAME);

		rCode = HiConstants.RSP_NOMARL.equals(etf.getChildValue(HiTagName.RESPONSE_CODE));

		return rCode;
	}

	// 小圈轮召停止
	public boolean stopLittliCycle(long appId) throws Exception {
		return HiNationHelper.callDownServiceOfStopLittleCycle(appId);
	}

	public int getTmOut() {
		return tmOut;
	}

	public void setTmOut(int tmOut) {
		this.tmOut = tmOut;
	}

	public ServerStatus getServerStatus(String machNo) throws Exception {
		return HiNationHelper.getServerStatus(machNo);
	}
	
	public boolean gdConcRTMR(List<Concentrator> concentratorList, String[] commandItems, List<AssistParam> assistParams, long appId)
			throws Exception {
		return HiSouthHelper.read(15, concentratorList, commandItems, assistParams, appId, tmOut);
	}

	public boolean cancelGdConcRTMR(List<CollectObject> collectObjects, long appId) throws Exception {
		return HiSouthHelper.read(16, collectObjects, null, null, appId, tmOut);
	}

	public boolean gdConcCRUM(List<CollectObject> collectObjects, List<CommandItem> commandItems, List<AssistParam> assistParams,
			long appId) throws Exception {
		return HiSouthHelper.write(18, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean gdConcCTL(List<Concentrator> concentratorObjects, List<CommandItem> commandItems,
			List<AssistParam> assistParams, long appId) throws Exception {
		return HiSouthHelper.write(17, concentratorObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean gdConcRDSD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId)
			throws Exception {
		return HiSouthHelper.read(11, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean gdConcRFHD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId)
			throws Exception {
		return HiSouthHelper.read(12, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean gdConcRGOD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId)
			throws Exception {
		return HiSouthHelper.read(13, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean gdConcRMI(List<CollectObject> collectObjects, String[] commandItem, List<AssistParam> assistParams, long appId)
			throws Exception {
		return HiSouthHelper.read(14, collectObjects, commandItem, assistParams, appId, tmOut);
	}

	public Map<String, String> getReturnByCGCRTMR(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public Map<String, String> getReturnByGCCRUM(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
	}

	public Map<String, String> getReturnByGCCTL(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
	}

	public Map<String, Map<String, String>> getReturnByGCRMI(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public Map<String, Map<String, String>> getReturnByREPZJ(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public Map<String, String> getReturnByWEPZJ(long appId) throws Exception {
		return HiSouthHelper.writeCallBack(appId);
	}

	// 读当前参数
	public boolean readEquipmentParametersZJ(List<CollectObject> collectObjects, String[] commandItems, long appId)
			throws Exception {
		return HiSouthHelper.read(20, collectObjects, commandItems, null, appId, tmOut);
	}

	// 非实时写参数
	public boolean writeEquipmentParametersZJ(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId)
			throws Exception {
		return HiSouthHelper.write(24, collectObjects, commandItems, null, appId, tmOut);
	}

	public Map<String, Map<String, String>> getReturnByRRDZJ(long appId) throws Exception {
		return HiSouthHelper.readCallBack(appId);
	}

	public boolean readRealtimeDataZJ(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams,
			long appId) throws Exception {
		return HiSouthHelper.read(20, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	// 实时
	public boolean writeEquipmentParametersZJ(List<CollectObject> collectObjects, List<CommandItem> commandItems,
			List<AssistParam> assistParams, long appId) throws Exception {
		return HiSouthHelper.write(23, collectObjects, commandItems, assistParams, appId, tmOut);
	}

	public boolean readEquipmentParameters(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception {
		return HiNationHelper.callDownService(HiConstants.TXNCODE_READ_PARAM, appId, collectObjects, commandItems, tmOut);
	}

}
