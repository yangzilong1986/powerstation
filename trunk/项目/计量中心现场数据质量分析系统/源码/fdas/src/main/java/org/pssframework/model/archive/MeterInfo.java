/**
 *
 */
package org.pssframework.model.archive;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 1)用于记录计量点下安装的电能表运行设备信息，定义了电能表的运行属性，本实体主要包括电能表资产编号、综合倍率、安装日期、安装位置、是否参考表、参考表资产编号等属性。
 * 2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。 3)该实体主要由查询计量点相关信息等业务使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_METER")
@SequenceGenerator(sequenceName = "SEQ_C_METER", name = "SEQ_C_METER", allocationSize = 1)
public class MeterInfo extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -6414145806175327349L;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "meterInfo")
    private MpInfo mpInfo;

    @Column(name = "METER_ID", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_METER")
    // METER_ID NUMBER(16) not null,is '本实体记录的唯一标识号，取自营销设备域的电能表信息实体。';
    private Long meterId;

    public String getAssertNo() {
        return assertNo;
    }

    public void setAssertNo(String assertNo) {
        this.assertNo = assertNo;
    }

    @Column(name = "ASSET_NO", length = 32)
    // ASSET_NO VARCHAR2(32),is '资产号';
    private String assertNo;

    @Column(name = "INST_LOC", length = 256)
    // INST_LOC VARCHAR2(256),is '电能表安装的物理位置';
    private String instLoc;

    @Column(name = "INST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    // INST_DATE DATE,is '安装日期';
    private Date instDate;

    @Column(name = "T_FACTOR", precision = 10, scale = 2)
    // T_FACTOR NUMBER(10,2),is '电能表综合倍率=电能表自身倍率*同一计量点下电流互感器的倍率*同一计量点下电压互感器的倍率';
    private Double tFactor;

    @Column(name = "REF_METER_FLAG", length = 8)
    // REF_METER_FLAG VARCHAR2(8),is '标明电能表是否是参考表，包括01是、02否';
    private String refMeterFlag;

    @Column(name = "REF_METER_ID", length = 16)
    // REF_METER_ID NUMBER(16),is '标明当前电能表用于结算用途时，参考那块电能表计量结算。';
    private Long refMeterId;

    @Column(name = "MODULE_NO", length = 32)
    // MODULE_NO VARCHAR2(32),is '定义电能表是集抄时，对应的模块编号';
    private String moduleNo;

    @Column(name = "ORG_ID", length = 16)
    // ORG_ID NUMBER,is '供电管理单位的代码';
    private Long orgId;

    @Column(name = "COMM_NO", length = 8)
    // COMM_NO VARCHAR2(8),is '通讯规约：645……';
    private String commNo;

    @Column(name = "BAUDRATE", length = 16)
    // BAUDRATE VARCHAR2(16),is '电能表的波特率';
    private String baudrate;

    @Column(name = "COMM_MODE", length = 5)
    // COMM_MODE VARCHAR2(8)is '通讯方式：485、gprs、红外……';
    private String commMode;

    /**
     * @return the baudrate
     */
    public String getBaudrate() {
        return baudrate;
    }

    /**
     * @return the commMode
     */
    public String getCommMode() {
        return commMode;
    }

    /**
     * @return the commNo
     */
    public String getCommNo() {
        return commNo;
    }

    /**
     * @return the instDate
     */
    public Date getInstDate() {
        return instDate;
    }

    /**
     * @return the instLoc
     */
    public String getInstLoc() {
        return instLoc;
    }

    /**
     * @return the meterId
     */
    public Long getMeterId() {
        return meterId;
    }

    /**
     * @return the moduleNo
     */
    public String getModuleNo() {
        return moduleNo;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @return the refMeterFlag
     */
    public String getRefMeterFlag() {
        return refMeterFlag;
    }

    /**
     * @return the refMeterId
     */
    public Long getRefMeterId() {
        return refMeterId;
    }

    /**
     * @return the tFactor
     */
    public Double gettFactor() {
        return tFactor;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @param baudrate the baudrate to set
     */
    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    /**
     * @param commMode the commMode to set
     */
    public void setCommMode(String commMode) {
        this.commMode = commMode;
    }

    /**
     * @param commNo the commNo to set
     */
    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    /**
     * @param instDate the instDate to set
     */
    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    /**
     * @param instLoc the instLoc to set
     */
    public void setInstLoc(String instLoc) {
        this.instLoc = instLoc;
    }

    /**
     * @param meterId the meterId to set
     */
    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    /**
     * @param moduleNo the moduleNo to set
     */
    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @param refMeterFlag the refMeterFlag to set
     */
    public void setRefMeterFlag(String refMeterFlag) {
        this.refMeterFlag = refMeterFlag;
    }

    /**
     * @param refMeterId the refMeterId to set
     */
    public void setRefMeterId(Long refMeterId) {
        this.refMeterId = refMeterId;
    }

    /**
     * @param tFactor the tFactor to set
     */
    public void settFactor(Double tFactor) {
        this.tFactor = tFactor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @param mpInfo the mpInfo to set
     */
    public void setMpInfo(MpInfo mpInfo) {
        this.mpInfo = mpInfo;
    }

    /**
     * @return the mpInfo
     */
    public MpInfo getMpInfo() {
        return mpInfo;
    }

}
