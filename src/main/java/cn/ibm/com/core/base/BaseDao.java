package cn.ibm.com.core.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BaseDao extends JdbcDaoSupport {


	/**
	 * Multiple SQL updates on a single JDBC Statement using batching.
	 * @param sql defining an array of SQL statements that will be executed.
	 * @return an array of the number of rows affected by each statement
	 * @throws DataAccessException if there is any problem executing the batch
	 */
	public int[] batchUpdate(String[] sql) {
		try {
			return getJdbcTemplate().batchUpdate(sql);
		} catch (DataAccessException e) {
			log.error("" + e.getMessage());
			throw e;
		}
	}
	

	/**
	 * Query given SQL to create a prepared statement from SQL and a list of
	 * arguments to bind to the query,
	 * @param sql
	 * @param args objects binding the given arguments
	 * @return result list
	 */
	public List<?> query(String sql, Object[] args) {
		try {
			log.info("Query SQL---->>>>" + sql);
			log.info("Query paramters---->>>>" + args);
			return getJdbcTemplate().queryForList(sql, args);
		} catch (Exception e) {
			log.error("SQL Query failed" + e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * Query given SQL to create a prepared statement from SQL and a list of
	 * @param sql
	 * @param args
	 * @param classInput
	 * @return
	 */
    public List<?> query(String sql, Object[] args, Class objClass) {  
    	try {
    		log.info("Query SQL---->>>>" + sql);
    		log.info("Query paramters---->>>>" + args);
			return getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper(objClass));
		} catch (Exception e) {
			log.error("SQL Query failed" + e.getMessage());
			throw e;
		}  
    }  
	

	/**
	 * Issue a single SQL update operation (such as an insert, update or delete
	 * statement) via a prepared statement
	 * @param sql
	 * @param objects binding the given arguments
	 * @return
	 */
	public int update(String sql, Object[] objects) {
		try {
			log.info("Update SQL---->>>> : " + sql);
			log.info("Query paramters---->>>>" + objects);
			return getJdbcTemplate().update(sql, objects);
		} catch (Exception e) {
			log.error("SQL Update failed" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	

	/**
	 * Get tatol num for data
	 * @param pageSize
	 * @param querySql
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public int getPageCount(int pageSize, String querySql, Object[] objects) {
		try {
			String countStr = "SELECT COUNT(*) FROM ("+ querySql +") TEMP";
			List<?> result = getJdbcTemplate().queryForList(countStr, objects);
			log.info("Total rows sql--->>>" + countStr);
			Map map = (Map) result.get(0);
			int rowsCount = Integer.valueOf(map.get("COUNT(*)").toString());
			// int pageCount = (int) Math.ceil(1.0 * rowsCount / pageSize);
			log.info("Total rows number--->>>" + rowsCount);
			return rowsCount;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Pagecount query failed:" + e.getMessage());
			return 0;
		}
	}
	
	
	/**
	 * Query data by page
	 * @param sql
	 * @param args
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<?> query(String sql, List args, int pageNo, int pageSize) {
		try {
			String executesql = "SELECT * FROM " +
								"(SELECT M.*, ROWNUM RN FROM (" + sql + ") M )" +
								" WHERE RN BETWEEN ? AND ?";
			args.add(Integer.valueOf((pageNo - 1) * pageSize + 1));
			args.add(Integer.valueOf(pageSize * pageNo));
			log.info("PageQuerySql---->>>>" + executesql);
			log.info("PageQUeryParameters---->>>>" + args);
			return getJdbcTemplate().queryForList(executesql, args.toArray());
		} catch (Exception e) {
			log.error("SQL Query failed" + e.getMessage());
			throw e;
		}
	}
	
	

	@Resource
	public void initDatasource(JdbcTemplate jdbcTemplate) {
		super.setJdbcTemplate(jdbcTemplate);
	}

}
