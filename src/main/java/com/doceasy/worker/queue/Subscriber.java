
package com.doceasy.worker.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.doceasy.worker.service.ProcessorService;

@Service
public class Subscriber {

	private Logger log = LoggerFactory.getLogger(Subscriber.class);
	
	@Autowired
	private ProcessorService service;
	
	/**
	 * Recebe as informações do registro da fila de processamento e
	 * encaminha para o serviço adequado.
	 * @param payload
	 * @throws InterruptedException
	 */
	@RabbitListener(queues = "${mq.queues.files-to-process}")
	public void process(@Payload String payload) throws InterruptedException {
		log.info("iniciando processamento de registro da fila");
		service.process(payload);
		log.info("finalizando o processamento de registro da fila");
	}
	
} 