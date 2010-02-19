package com.hzjbbis.db.initrtu.dao.jdbc;

import com.hzjbbis.db.initrtu.dao.ComRtuDao;
import com.hzjbbis.db.resultmap.ResultMapper;
import com.hzjbbis.fk.model.ComRtu;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcComRtuDao implements ComRtuDao {
    private String sqlLoadRtu;
    private String sqlLoadGwRtu;
    private ResultMapper<ComRtu> mapperLoadRtu;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public List<ComRtu> loadComRtu() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public ComRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((ComRtu) JdbcComRtuDao.this.mapperLoadRtu.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadRtu, rowMap, new Object[0]);
    }

    public List<ComRtu> loadComGwRtu() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public ComRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((ComRtu) JdbcComRtuDao.this.mapperLoadRtu.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadGwRtu, rowMap, new Object[0]);
    }

    public void setSqlLoadRtu(String sqlLoadRtu) {
        this.sqlLoadRtu = sqlLoadRtu.trim();
    }

    public void setMapperLoadRtu(ResultMapper<ComRtu> resultMap) {
        this.mapperLoadRtu = resultMap;
    }

    public void setSqlLoadGwRtu(String sqlLoadGwRtu) {
        this.sqlLoadGwRtu = sqlLoadGwRtu;
    }
}