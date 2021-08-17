package com.iscope2.tabletest.models.constants;

import java.util.HashMap;
import java.util.Map;

public enum Operations {
	
	UNDEFINED(-1, "%_PLACEHOLDER_%"),
	CONTAINS(0, "%_PLACEHOLDER_%"),
    EQUALS(1, "_PLACEHOLDER_"),
    STARTS(2, "_PLACEHOLDER_%"),
    ENDS(3, "%_PLACEHOLDER_"),
    GT(4, ">"),
    GTE(5, ">="),
    EQ(6, "="),
    LTE(7, "<="),
	LT(8, "<");
	
    private final Integer id;
    private final String text;

    private static Map<Integer, String> valueToTextMapping;

    private Operations(Integer id, String text){
        this.id = id;
        this.text = text;
    }

    public static String getOperation(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(Operations s : values()){
            valueToTextMapping.put(s.id, s.getText());
        }
    }

    public Integer getId(){
        return id;
    }

    public String getText(){
        return text;
    }
}
