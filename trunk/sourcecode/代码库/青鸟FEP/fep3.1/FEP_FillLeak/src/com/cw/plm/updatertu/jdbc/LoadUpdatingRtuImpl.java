package com.cw.plm.updatertu.jdbc;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cw.plm.updatertu.LoadUpdatingRtuDao;
import com.cw.plm.updatertu.RtuStatus;
import com.cw.plm.updatertu.UpdateRtuModule;

public class LoadUpdatingRtuImpl implements LoadUpdatingRtuDao {
	private SimpleJdbcTemplate simpleJdbcTemplate;		//∂‘”¶dataSource Ù–‘
	private String sqlLoad = "select * from ZD_SJ where ZT=0";
	
	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public List<RtuStatus> load() {
		final Map<String,ByteBuffer> bufs = new HashMap<String,ByteBuffer>();
		final List<RtuStatus> list = new ArrayList<RtuStatus>();
		ParameterizedRowMapper<RtuStatus> rowMap = new ParameterizedRowMapper<RtuStatus>(){
			public RtuStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
				int rtua = (int)Long.parseLong(rs.getString("ZDLJDZ"),16);
				String batchId = rs.getString("PC");
				int curPacket = rs.getInt("CUR")-1;
				if( curPacket<0 )
					curPacket = 0;
				ByteBuffer content = bufs.get("batchId");
				RtuStatus obj = null;
				if( null == content ){
					content = UpdateRtuModule.getInstance().getContent(batchId);
					if( null != content ){
						obj = new RtuStatus(batchId,rtua,content);
						obj.setCurPacket(curPacket);
						list.add(obj);
					}
				}
				return obj;
			}
		};
		this.simpleJdbcTemplate.query(sqlLoad, rowMap);
		return list;
	}

	public void setSqlLoad(String sqlLoad) {
		this.sqlLoad = sqlLoad;
	}

}
