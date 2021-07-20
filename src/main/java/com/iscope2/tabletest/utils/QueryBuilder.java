package com.iscope2.tabletest.utils;

import java.util.List;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.FilterObject;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QueryBuilder {
	
	public static String generateQueryFromFilterList(List <FilterObject> filters) {
			
		StringBuilder str = new StringBuilder();
		int closingBracketCounter = 0;
		String search;
		
		if(filters != null && filters.size() > 0) {
			str.append(" WHERE ");
		}
		
		for(FilterObject filter : filters) {
			search = likeAstrixPosition(filter.getSearch(), filter.getOperation());
			String lo = (filter.getLo() == null) ? "AND" : filter.getLo();
			
			if (lo.equalsIgnoreCase("OR")) {
				str.append(" ").append(lo).append(" (");
				closingBracketCounter++;
			} else {
				str.append(" ").append(lo);
			}
			
			str.append(" LOWER(").append(filter.getFilter()).append(") ").append("LIKE '").append(search).append("' ");
		}
		if (closingBracketCounter > 0) {
			for(int i=0; i<closingBracketCounter; i++) {
				str.append(")");
			}
		}
		
		return str.toString();
	}	
	
	private static String likeAstrixPosition (String search, String operation) {
		String output;
		
		switch (operation) {
		case "starts":
			output = search.toLowerCase() + "%"; 
			break;
		case "ends":
			output = "%" + search.toLowerCase();
			break;
		case "equals":
			output = search.toLowerCase();
			break;
		default:
			output = "%" + search.toLowerCase() + "%";
			break;
		}
		return output;
	}
	
	public static String generateQuery(String tablename, FilterDTO filterDTO) {
		String whereQuery = QueryBuilder.generateQueryFromFilterList(filterDTO.getFilters());
		String order = (filterDTO.getOrder() == null) ? "ASC" : filterDTO.getOrder();
		String orderBy = (filterDTO.getOrderBy() == null) ? "ID" : filterDTO.getOrderBy();
		int page = (filterDTO.getPage() == 0) ? 0 : filterDTO.getPage();
		int perPage = (filterDTO.getPerPage() == 0) ? 10 : filterDTO.getPerPage();
		String query = "SELECT * FROM " + tablename + whereQuery 
				+ " ORDER BY " + orderBy + " " + order + " LIMIT " + perPage + " OFFSET " + page * perPage;
		System.out.println(query);
		return query;
	}
	
	public static String genereateQueryWithoutOffset(String tablename, FilterDTO filterDTO) {
		String whereQuery = QueryBuilder.generateQueryFromFilterList(filterDTO.getFilters());
		String order = (filterDTO.getOrder() == null) ? "ASC" : filterDTO.getOrder();
		String orderBy = (filterDTO.getOrderBy() == null) ? "ID" : filterDTO.getOrderBy();
		String query = "SELECT * FROM " + tablename + whereQuery 
				+ " ORDER BY " + orderBy + " " + order;
		System.out.println(query);
		return query;
	}
}
