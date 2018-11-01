package com.ibm.watson.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.exception.NotFoundException;
import com.ibm.watson.developer_cloud.service.exception.RequestTooLargeException;
import com.ibm.watson.developer_cloud.service.exception.ServiceResponseException;
import com.ibm.watson.dto.MessageInput;

@RestController
public class ConversationResource {

    @Value("${ibm.assistant.version.date}")
    private String assistantVersionDate;

    @Value("${ibm.assistant.workspace.id}")
    private String assistantWorkspace;

    @Value("${ibm.assistant.username}")
    private String assistantUser;

    @Value("${ibm.assistant.password}")
    private String assistantPass;

	private Assistant service;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {
		service = new Assistant( assistantVersionDate );
		service.setUsernameAndPassword( assistantUser , assistantPass );
	}

	@PostMapping("/api/message")
	public MessageResponse postMessage(@RequestBody MessageInput messageInput) {
		try {
			
			String text = (messageInput.getInput() == null || messageInput.getInput().getText() == null) ? "" : messageInput.getInput().getText();
			
			InputData input = new InputData.Builder(text).build();
			Context context = messageInput.getContext();
			
			MessageOptions options = 
					new MessageOptions.Builder( assistantWorkspace )
					.input(input)
					.context(context).build();
			
			MessageResponse response = service.message(options).execute();
			return response;
			
		} catch (NotFoundException e) {
			log.error("NotFoundException", e);
			throw e;
		} catch (RequestTooLargeException e) {
			log.error("RequestTooLargeException", e);
			throw e;
		} catch (ServiceResponseException e) {
			log.error("ServiceResponseException", e);
			throw e;
		}
	}

}