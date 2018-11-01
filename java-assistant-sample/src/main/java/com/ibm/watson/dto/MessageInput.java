package com.ibm.watson.dto;

import com.ibm.watson.developer_cloud.assistant.v1.model.Context;

public class MessageInput {

    private Context context;

    private com.ibm.watson.developer_cloud.assistant.v1.model.MessageInput input;
    
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

	public com.ibm.watson.developer_cloud.assistant.v1.model.MessageInput getInput() {
		return input;
	}

	public void setInput(com.ibm.watson.developer_cloud.assistant.v1.model.MessageInput input) {
		this.input = input;
	}   
    
    
}