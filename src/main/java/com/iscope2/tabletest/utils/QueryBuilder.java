package com.iscope2.tabletest.utils;

import java.util.List;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.FilterObject;
import com.iscope2.tabletest.models.constants.LogicalOperand;
import com.iscope2.tabletest.models.constants.Operations;
import com.iscope2.tabletest.models.constants.Order;
import com.iscope2.tabletest.models.constants.Types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QueryBuilder {

	public static String generateQueryFromFilterList(List<FilterObject> filters) {

		StringBuilder str = new StringBuilder();
		int closingBracketCounter = 0;

		if (filters != null && filters.size() > 0) {
			str.append(" WHERE ");
		} else {
			return "";
		}

		for (int i=0; i<filters.size(); i++) {
			FilterObject filter = filters.get(i);
			String type = filter.getType();
			String lo = i != 0 ? LogicalOperand.getLogicalOperand(filter.getLogicalOperand()) : "";

			if (lo.equalsIgnoreCase(LogicalOperand.OR.getText())) {
				str.append(" ").append(lo).append(" (");
				closingBracketCounter++;
			} else {
				str.append(" ").append(lo);
			}

			if (type.equals(Types.TEXT.getText()) && filter.getValue() instanceof String) {
				String search = Operations.getOperation(filter.getOperation()).replace("_PLACEHOLDER_", filter.getValue().toString().toLowerCase());
				str.append(" LOWER(").append(filter.getField()).append(") ").append("LIKE '").append(search).append("' ");
			}
			else if (type.equals(Types.NUMBER.getText()) && filter.getValue() instanceof Number) {
				String search =  filter.getValue().toString();
				str.append(" LOWER(").append(filter.getField()).append(") ").append(Operations.getOperation(filter.getOperation()))
					.append(" ").append(search);
			}
			else if (type.equals(Types.DATE.getText()) && filter.getValue() instanceof String) {
				String search = filter.getValue().toString();
				str.append(" LOWER(").append(filter.getField()).append(") ").append(Operations.getOperation(filter.getOperation()))
					.append(" TO_DATE('").append(search).append("', 'RRRR-mm-dd UTC') ");
			} else {
				throw new RuntimeException();
			}
		}
		if (closingBracketCounter > 0) {
			for (int i = 0; i < closingBracketCounter; i++) {
				str.append(")");
			}
		}

		return str.toString();
	}

//	private static String likeAstrixPosition(String search, String operation) {
//		String output;
//
//		switch (operation) {
//		case "starts":
//			output = search.toLowerCase() + "%";
//			break;
//		case "ends":
//			output = "%" + search.toLowerCase();
//			break;
//		case "equals":
//			output = search.toLowerCase();
//			break;
//		default:
//			output = "%" + search.toLowerCase() + "%";
//			break;
//		}
//		return output;
//	}

	public static String generateQuery(String tablename, FilterDTO filterDTO, String searchAllQuery, boolean withOffset) {
		String whereQuery = QueryBuilder.generateQueryFromFilterList(filterDTO.getFilters());
		String order = Order.getOrder(filterDTO.getOrder());
		String orderBy = (filterDTO.getOrderBy() == null) ? "ID" : filterDTO.getOrderBy();
		String search = (filterDTO.getSearch() == null) ? "" : searchAllQuery.replace("__PLACEHOLDER__", filterDTO.getSearch());
		
		if (whereQuery.isEmpty() && !search.isEmpty()) {
			search = " WHERE " + search;
		} else if (!search.isEmpty()){
			search = " AND " + search;
		}
		
  		int page = (filterDTO.getPage() == 0) ? 0 : filterDTO.getPage();
		int perPage = (filterDTO.getPerPage() == 0) ? 10 : filterDTO.getPerPage();
		
		String query;
		if (withOffset) {
			query = "SELECT * FROM " + tablename + whereQuery + search + " ORDER BY " + orderBy + " " + order + " LIMIT " + perPage + " OFFSET " + page * perPage;
		} else {
			query = "SELECT * FROM " + tablename + whereQuery + search + " ORDER BY " + orderBy + " " + order;
		}
		System.out.println(query);
		return query;
	}
}
