package com.iscope2.tabletest.utils;

import java.util.List;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.FilterObject;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QueryBuilder {

	public static String generateQueryFromFilterList(List<FilterObject> filters) {

		StringBuilder str = new StringBuilder();
		int closingBracketCounter = 0;

		if (filters != null && filters.size() > 0) {
			str.append(" WHERE ");
		}

		for (FilterObject filter : filters) {
			String type = filter.getType();
			String lo = (filter.getLogicalOperand() == null) ? "AND" : filter.getLogicalOperand();

			if (lo.equalsIgnoreCase("OR")) {
				str.append(" ").append(lo).append(" (");
				closingBracketCounter++;
			} else {
				str.append(" ").append(lo);
			}

			if (type.equals("text") && filter.getValue() instanceof String) {
				String search = likeAstrixPosition(filter.getValue().toString(), filter.getOperation());
				str.append(" LOWER(").append(filter.getColumnName()).append(") ").append("LIKE '").append(search).append("' ");
			}
			if (type.equals("number") && filter.getValue() instanceof Number) {
				String search =  filter.getValue().toString();
				str.append(" LOWER(").append(filter.getColumnName()).append(") ").append(filter.getOperation()).append(" ").append(search);
			}
			if (type.equals("date") && filter.getValue() instanceof String) {
				String search = filter.getValue().toString();
				str.append(" LOWER(").append(filter.getColumnName()).append(") ").append(filter.getOperation()).append(" TO_DATE('").append(search).append("', 'RRRR-mm-dd UTC') ");
			}
		}
		if (closingBracketCounter > 0) {
			for (int i = 0; i < closingBracketCounter; i++) {
				str.append(")");
			}
		}

		return str.toString();
	}

	private static String likeAstrixPosition(String search, String operation) {
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
		String query = "SELECT * FROM " + tablename + whereQuery + " ORDER BY " + orderBy + " " + order + " LIMIT " + perPage + " OFFSET " + page * perPage;
		System.out.println(query);
		return query;
	}

	public static String genereateQueryWithoutOffset(String tablename, FilterDTO filterDTO) {
		String whereQuery = QueryBuilder.generateQueryFromFilterList(filterDTO.getFilters());
		String order = (filterDTO.getOrder() == null) ? "ASC" : filterDTO.getOrder();
		String orderBy = (filterDTO.getOrderBy() == null) ? "ID" : filterDTO.getOrderBy();
		String query = "SELECT * FROM " + tablename + whereQuery + " ORDER BY " + orderBy + " " + order;
		System.out.println(query);
		return query;
	}
}
