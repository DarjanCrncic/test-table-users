package com.iscope2.tabletest.models.constants;

import java.util.HashMap;
import java.util.Map;

public enum LogicalOperand {
	
	UNDEFINED(-1, ""),
    AND(0, "AND"),
    OR(1, "OR");

    private final Integer id;
    private final String text;

    private static Map<Integer, String> valueToTextMapping;

    private LogicalOperand(Integer id, String text){
        this.id = id;
        this.text = text;
    }

    public static String getLogicalOperand(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(LogicalOperand s : values()){
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
