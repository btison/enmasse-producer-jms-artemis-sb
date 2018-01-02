package com.redhat.btison.enmasse.producer.jms.sb.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redhat.btison.enmasse.producer.jms.sb.model.SimpleMessage;
import com.redhat.btison.enmasse.producer.jms.sb.service.MessageProducerService;

@Component
@Path("/")
public class MessageResource {

    @Autowired
    MessageProducerService messageProducerService;

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response message(SimpleMessage message) {
        messageProducerService.sendText(message.getMessage());
        return Response.ok().build();
    }

}
