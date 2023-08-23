package com.doceasy.worker.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class FileProcessQueueSubscriberService {

	@Autowired
	private ProcessFileService service;
	
	@RabbitListener(queues = "${mq.queues.files-to-process}")
	public void fileProcess(@Payload String payload) throws InterruptedException {
		service.processFiles(payload);
	}
	
	
} 