package org.pssframework.service.eparam;

import java.util.HashMap;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.dao.eparam.TermParamInfoDao;
import org.pssframework.model.eparam.TermParamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.MTO_376;

/**
 * @author Zhangyu
 * 
 */
@Service
public class TermParamManager extends BaseManager<TermParamInfo, Long> {
    @Autowired
    private TermParamInfoDao termParamInfoDao;

    @Autowired
    private ICollectInterface realTimeProxy376;

    @Override
    protected TermParamInfoDao getEntityDao() {
        return this.termParamInfoDao;
    }

    @Override
    public TermParamInfo getById(Long id) throws DataAccessException {
        return termParamInfoDao.getById(id);
    }

    /**
     * 
     * 一键设置终端参数F10
     * 
     * @param mapCO
     *     *<"logicalAddr", String>     : 逻辑地址 C_TERMINAL.LOGICAL_ADDR
     *     *<"equipProtocol", String>   : 设备规约号 C_TERMINAL.PROTOCOL_NO
     *     ---------------------------------------------------------------------
     *      <"channelType", String>     : 通道类型 【默认为1(TCP)】
     *      <"pwAlgorith", String>      : 密码算法 【默认为0】
     *      <"pwContent", String>       : 密码内容 【默认为8888】
     *      <"mpExpressMode", String>   : 测量点表示方式 【默认为3(列表方式)】
     *      <"mpSn", String>            : 测量点序号组 【默认为[0]】
     *     ---------------------------------------------------------------------
     * @param mapParams
     *     *<"1004001001", String>      : 本次电能表/交流采样装置配置数量
     *     *<"10040010020001", String>  : 本次配置第0001块电能表/交流采样装置序号 【默认与所属测量点号相同】
     *     *<"10040010030001", String>  : 本次配置第0001块电能表/交流采样装置所属测量点号
     *     *<"10040010040001", String>  : 本次配置第0001块电能表/交流采样装置通信波特率 C_METER.BAUDRATE
     *     *<"10040010050001", String>  : 本次配置第0001块电能表/交流采样装置通信端口号 C_GP.PORT
     *     *<"10040010060001", String>  : 本次配置第0001块电能表/交流采样装置通信协议类型 C_GP.PROTOCOL_NO
     *     *<"10040010070001", String>  : 本次配置第0001块电能表/交流采样装置通信地址  C_GP.GP_ADDR
     *     ---------------------------------------------------------------------
     *      <"10040010080001", String>  : 本次配置第0001块电能表/交流采样装置通信密码 【默认为000000000000】
     *      <"10040010100001", String>  : 本次配置第0001块电能表/交流采样装置电能费率个数 【默认为000100(4个费率)】
     *      <"10040010120001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数 【默认为10(6位)】
     *      <"10040010130001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数 【默认为11(4位)】
     *      <"10040010140001", String>  : 本次配置第0001块电能表/交流采样装置所属采集器通信地址 【默认为000000000000】
     *      <"10040010150001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户大类号 【默认为0000】
     *      <"10040010160001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户小类号 【默认为0000】
     *     ---------------------------------------------------------------------
     * @return map
     *     <"collectId", Integer>      : 采集ID
     */
    public Map<String, Object> akeySetupTermParamF10(Map<String, Object> mapCO, Map<String, String> mapParams) {
        Map<String, Object> map = new HashMap<String, Object>();

        MTO_376 mto376 = new MTO_376();
        CollectObject co = new CollectObject();
        co.setLogicalAddr((String) mapCO.get("logicalAddr"));
        co.setEquipProtocol((String) mapCO.get("equipProtocol"));
        co.setChannelType("1");
        co.setPwAlgorith("0");
        co.setPwContent("8888");
        co.setMpExpressMode(3);
        int[] mpSn = { 0 };
        co.setMpSn(mpSn);
        CommandItem ci = new CommandItem();
        ci.setIdentifier("10040010");
        ci.setDatacellParam(mapParams);
        co.AddCommandItem(ci);
        mto376.addCollectObject(co);

        return map;
    }
}
