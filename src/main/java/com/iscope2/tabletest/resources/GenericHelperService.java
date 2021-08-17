package com.iscope2.tabletest.resources;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.ResultDTO;
import com.iscope2.tabletest.utils.QueryBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenericHelperService<T> {
	
	private JdbcTemplate jdbcTemplate;
	
	public ResultDTO<T> executeFilterQuery(FilterDTO filterDTO, Class<T> entity, String searchAllQuery, String tableName) {
		int perPage = (filterDTO.getPerPage() == 0) ? 10 : filterDTO.getPerPage();
		String query = QueryBuilder.generateQuery(tableName, filterDTO, searchAllQuery, true);	
		List<T> usersList = jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(entity));
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM (" 
				+ QueryBuilder.generateQuery(tableName, filterDTO, searchAllQuery, false) + ")", Integer.class);

		ResultDTO<T> resultDTO = new ResultDTO<>();
		resultDTO.setTotal(count);
		resultDTO.setPages(count/perPage);
		resultDTO.setData(usersList);
		
		return resultDTO;
	}
}
