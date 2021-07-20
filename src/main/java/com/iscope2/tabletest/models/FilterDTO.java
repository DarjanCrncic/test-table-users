package com.iscope2.tabletest.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {

	int page;
	int perPage;
	String order;
	String orderBy;
	
	List<FilterObject> filters;

}
