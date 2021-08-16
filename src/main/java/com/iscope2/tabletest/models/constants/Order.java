package com.iscope2.tabletest.models.constants;

import java.util.HashMap;
import java.util.Map;

public enum Order {
	ASCENDING(0, "ASC"),
    DESCENDING(1, "DESC");

    private final Integer id;
    private final String text;

    private static Map<Integer, String> valueToTextMapping;

    private Order(Integer id, String text){
        this.id = id;
        this.text = text;
    }

    public static String getOrder(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(Order s : values()){
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
