/**
 * 
 */
package org.pssframework.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "C_SMS_NO")
public class SmsNoInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4228800947787949272L;

	@Column(name = "SMS_NO", unique = true, nullable = false)
	@Id
	private Long smsNo;
	@Column(name = "CONTACT_NAME")
	private String contactName;

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

}
