package com.linkedin.learning.webservices.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("sse")
public class MySSEResource {

	@Path("/guest/{guest}/salute")
	@GET
	public void saluteSSE(@Context SseEventSink sseEventSink, @Context Sse sseUtil, @PathParam("guest") String guest) {
		buildAndSendMessage(sseEventSink, sseUtil, guest);
	}

	public void buildAndSendMessage(SseEventSink sink, Sse sseUtil, String guest) {

		new Thread(() -> {
			// Per ogni nuovo Thread mandiamo 10 messaggi
			for (int count = 0; count < 10; count++) {
				OutboundSseEvent sseEvent = sseUtil.newEvent("Hello " + guest);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				sink.send(sseEvent);
			}
		}).start();
		;
	}

}
