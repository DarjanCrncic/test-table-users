package com.iscope2.tabletest.models;

import lombok.Data;

@Data
public class FilterObject {
	
	Object value;
	int logicalOperand;
	String field;
	int operation;
	String type;
}
