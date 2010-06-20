/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * '1)用于记录计量点用途的信息，计量点可以有多种用途，包括售电侧结算、台区供电考核、发电上网关口、跨国输电关口等。定义了计量点与用途间的对应关系属性，本实体主要包括用途ID、计量点编号、用途代码等属性。
 * 2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。 3)该实体主要由查询计量点相关信息等业务使用。';
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_MP_USE")
@SequenceGenerator(sequenceName = "SEQ_C_MP_USE", name = "SEQ_C_MP_USE", allocationSize = 1)
public class MpUseInfo extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3440470953000205969L;

    @Column(name = "USAGE_ID", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_MP_USE")
    // METER_MP_ID NUMBER(16) not null,
    private Long usageId;

    @Column(name = "USAGE_CODE")
    // USAGE_CODE VARCHAR2(8)
    private String usageCode;

    // MP_ID NUMBER(16)
    @OneToOne(targetEntity = MpInfo.class)
    @JoinColumn(name = "MP_ID", referencedColumnName = "MP_ID")
    // MP_ID VARCHAR2(8),设备的产权说明 01 局属、 02 用户
    private MpInfo mpInfo;

    /**
     * @return the mpInfo
     */
    public MpInfo getMpInfo() {
        return mpInfo;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @param mpInfo
     *            the mpInfo to set
     */
    public void setMpInfo(MpInfo mpInfo) {
        this.mpInfo = mpInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @param usageId
     *            the usageId to set
     */
    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    /**
     * @return the usageId
     */
    public Long getUsageId() {
        return usageId;
    }

    /**
     * @param usageCode
     *            the usageCode to set
     */
    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }

    /**
     * @return the usageCode
     */
    public String getUsageCode() {
        return usageCode;
    }
}
