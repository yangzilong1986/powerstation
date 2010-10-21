/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.AnalogueInfoDao;
import org.pssframework.model.archive.AnalogueInfo;
import org.pssframework.model.archive.GpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * @author djs-baocj
 *
 */
@Service
public class AnalogueInfoManager extends BaseManager<AnalogueInfo, Long> {

	private static List<Port> lstPrt = Lists.newArrayList();

	static {
		setUpList();
	}

	public static void setUpList() {

		for (int i = 1; i <= 8; i++) {
			Port port = new Port();
			port.setKey(String.valueOf(i));
			port.setValue(String.valueOf(i));
			lstPrt.add(port);
		}

	}

	@Autowired
	private AnalogueInfoDao analogueInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		return analogueInfoDao;
	}

	@Override
	public void saveOrUpdate(AnalogueInfo entity) throws DataAccessException {
		GpInfo gpInfo = entity.getGpInfo();
		gpInfo.setGpSn(Long.parseLong(gpInfo.getPort()));
		super.saveOrUpdate(entity);
	}

	public List<AnalogueInfo> findByPageRequest(Map mapRequest) {

		return analogueInfoDao.findByPageRequest(mapRequest);
	}

	public List<Port> get1To8Ports() {

		return lstPrt;
	}

	public static class Port {
		private String key;
		private String value;

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
	}
}
