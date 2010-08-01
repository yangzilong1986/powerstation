/**
 * 
 */
package org.pssframework.model.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.pssframework.base.BaseEntity;
import org.pssframework.model.archive.PsInfo;

/**
 * @author Administrator
 *
 */
public class SmsNoInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4228800947787949272L;

	@Id
	@Column(name = "SMS_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_SMS_NO")
	private Long smsId;

	@Column(name = "SMS_NO", unique = true, nullable = false)
	private Long smsNo;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@ManyToMany(mappedBy = "smsNoInfoList")
	private List<PsInfo> psInfoList;

	/**
	 * @return the smsNo
	 */
	public Long getSmsNo() {
		return smsNo;
	}

	/**
	 * @param smsNo the smsNo to set
	 */
	public void setSmsNo(Long smsNo) {
		this.smsNo = smsNo;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setPsInfoList(List<PsInfo> psInfoList) {
		this.psInfoList = psInfoList;
	}

	public List<PsInfo> getPsInfoList() {
		return psInfoList;
	}

}
