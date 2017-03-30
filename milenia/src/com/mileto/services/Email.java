package com.mileto.services;

import com.mileto.domain.AdmUsuario;


public class Email {

	//public static final String CHEMIN_SMTP = "smtp.mail.com";
	public static final String CHEMIN_FROM= "mileto@email.com";
	
	private ServiceLocator locator = ServiceLocator.instance();

	private String message;

	//private org.apache.commons.mail.Email email;
	private HtmlEmail email;

	public Email(org.apache.commons.mail.Email emailConstruido) throws Exception {
			email = new HtmlEmail();
			//email = emailConstruido;
			email.setHostName(locator.getProperty("CHEMIN_SMTP"));	
			
	}

	public void addDestinatario(AdmUsuario usuario) throws EmailException {		
		email.addTo(usuario.getEmail(), usuario.getNome());  
	}

	public void envia() {

		try {		  		  
			email.setFrom(Email.CHEMIN_FROM, "Processo Automatico Panamericana");
			email.setAuthentication(Email.CHEMIN_FROM, "miletotech");
			email.setTLS(true);
			email.setSSL(true);
			email.setSmtpPort(587);

			// set the alternative message
			//email.setTextMsg("Your email client does not support HTML messages");

			// send the email
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public void anexa(String pathAttachment, String nameAttachment, String descriptionAttachment) {
		EmailAttachment anexo = new EmailAttachment();
		anexo.setPath(pathAttachment); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo.setDisposition(EmailAttachment.ATTACHMENT);
		anexo.setDescription(descriptionAttachment);
		anexo.setName(nameAttachment);
		
		try {
			//MultiPartEmail mEmail = (MultiPartEmail)email;
			email.attach(anexo);
		} catch (EmailException ex) {
			ex.printStackTrace();
		}
	}

	public static void enviaEmailExemplo () {
		
		HtmlMessage htmlMessage = new HtmlMessage();
		htmlMessage.setMessageTitle("Titulo Job Ferias");
		htmlMessage.setMessageWelcome("Prezado Administrador, ");
		
		//MultiPartEmail email = new MultiPartEmail();

		AdmUsuario usuario = new AdmUsuario();
		usuario.setNome("Abrhaao");
		usuario.setEmail("abrhaao@gmail.com");	
		
		try {
			Email email = new Email(new MultiPartEmail());
			email.setSubject("Envio RM");
			email.setMessage(htmlMessage.buildMessage());
			email.addDestinatario(usuario);
			//email.anexa("c:\\desen\\projetos\\request.key", "req.key", "Arquivo Request");
			email.envia();
		} catch (EmailException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void setSubject(String subject) {
		email.setSubject(subject);
	}

	public void setMessage(String message) throws EmailException{
		
		try {
			HtmlEmail hEmail = (HtmlEmail)email;
			hEmail.setHtmlMsg(message);
		} catch (ClassCastException ex) {
			email.setMsg(message);
			ex.printStackTrace();
		}
	}
}
