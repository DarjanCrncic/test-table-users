package com.iscope2.tabletest.models;

import lombok.Data;

@Data
public class FilterObject {
	
	Object value;
	String logicalOperand;
	String columnName;
	String operation;
	String type;
}
