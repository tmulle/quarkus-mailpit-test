/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.acme;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.List;
import org.jboss.logging.Logger;

/**
 *
 * @author tmulle
 */
@Path("/mail")
public class MailResource {

    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;
    
    @Inject
    Logger log;

    @GET
    @Blocking
    public void sendEmail() {

        Mail m = new Mail();
        m.setFrom("tmulle@gmail.com");
        m.setTo(List.of("quarkus@quarkus.io"));
        m.setText("A simple email sent from a Quarkus application.");
        m.setSubject("Ahoy from Quarkus");
        
        log.infof("Regular Mail is about to send %s", m);
        mailer.send(m);
        log.infof("Done sending regular mail");
       
    }

    @GET
    @Path("/reactive")
    public Uni<Void> sendEmailUsingReactiveMailer() {
         Mail m = new Mail();
        m.setFrom("tmulle@gmail.com");
        m.setTo(List.of("quarkus@quarkus.io"));
        m.setText("A simple email sent from a Quarkus application.");
        m.setSubject("Ahoy from Quarkus");
        return reactiveMailer.send(m).onItem().invoke(() -> log.infof("Reactive Mail is about to send %s", m));
    }
}
