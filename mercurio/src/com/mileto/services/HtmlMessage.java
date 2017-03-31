package com.mileto.services;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class HtmlMessage {

	private StringBuffer buffer;

	private Vector vListagem;
	private String messageWelcome;
	private String messageTitle;
	private String messageEvento;

	private String sistema;
	private String modulo;
	private String programa;
	private String evento;

	private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");


	private StringBuilder messageBody;

	public String buildMessage() {

		buffer = new StringBuffer();
		buffer.append("	<html lang=\"en\"> ");
		buffer.append(" <head> ");
		buffer.append(" <link rel=\"stylesheet\" href=\"http://community.jboss.org/4.5.5/styles/jive-global.css\" type=\"text/css\" media=\"all\" /> ");		    
		buffer.append(" <link rel=\"stylesheet\" href=\"http://community.jboss.org/4.5.5/styles/jive-icons.css\" type=\"text/css\" media=\"all\" /> ");
		buffer.append(" </head> ");


		buffer.append(" 		<body bgcolor=\"#111111\" style='font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Geneva, Verdana, Arial, sans-serif;	font-size:12px;'>	 ");

		buffer.append(" 		<div id=\"pagina\">	 ");


		buffer.append(" 		<!-- COMMON HEADER -- Se n�o for esse id ORGheader, nada funciona-->	 ");

		buffer.append(" 		<div style=\"margin: 0px auto;	 ");
		buffer.append(" 					width: 99%;	 ");
		buffer.append(" 					height:42px;	 ");
		buffer.append(" 				  background: url(topbarra.gif);	 ");
		buffer.append(" 				  background-color:#3b4f66;	 ");
		buffer.append(" 				  background-position: -422px -57px;>	 ");


		buffer.append("<a href=\"http://www.jboss.org/\"><font color=white>" + this.messageTitle + "</font></a>	 ");

		buffer.append("</div> ");

		buffer.append("<!-- END of ORGheader --> ");










		buffer.append("<div id=\"contentcontainer\">	");


		buffer.append("<div id=\"container_grey\">	");


		buffer.append("<div id=\"sub_nav\"></div><!-- Nao remover -->	");



		buffer.append("<!-- BEGIN user bar -->	");

		buffer.append("<div id=\"user-bar-wrapper\">	");

		buffer.append("<div id=\"jive-userbar\">	");

		buffer.append("<div id=\"jive-userbar-login\">	");

		buffer.append("     <span class=\"jive-userbar-login-welcome\" id=\"Welcome\">	");
		buffer.append("          <span class=\"jive-userbar-login-guest\">	");
		buffer.append(this.messageWelcome);
		buffer.append("          </span>	");
		buffer.append("      </span>	");
		buffer.append("</div>        	");
		buffer.append("</div>	");


		buffer.append("</div>	");

		buffer.append("<!-- END user bar -->	");




		buffer.append("<div style=\"background-color:#FFFFFF;\">	");








		buffer.append("<!-- BEGIN main body --> ");
		buffer.append("<div id=\"mileto-main\" style=\"padding: 20px 20px 20px;\"> ");

		if (vListagem != null) {
			Iterator itJiveWidgets = vListagem.iterator();
			while (itJiveWidgets.hasNext()) {
				Object oJive = itJiveWidgets.next();
				if (String.class.isInstance(oJive)) {
					buffer.append(" <div class=\"jive-widget jive-box jive-widget-recentactivitywidget  \">	");		    
					buffer.append(" <div class=\"jive-widgetsize-small\">	");		          
					buffer.append(" <div class=\"jive-widget-header jive-box-header jive-widget-header-refresh  \">	");		              
					buffer.append("	<h4 class=\"jive-widget-handle\">	");
					buffer.append("   <span>" + oJive.toString() + "</span>	");
					buffer.append(" </h4>	");		              
					buffer.append(" </div>	");		          
					buffer.append(" <div class=\"jive-widget-body jive-box-body\">	");
					buffer.append(" <div class=\"content-small\">	");
				}
				if (List.class.isInstance(oJive)) {
					buffer.append(" <div class=\"activity-container\"> ");
					buffer.append(" <ul class=\"jive-icon-list jive-recent-activity jive-activity-list\"> ");

					for (String s : (List<String>)oJive) {

						buffer.append(" <li class=\"clearfix\"> ");


						buffer.append(s);

						buffer.append(" </li> ");
					}

					buffer.append(" </div></div></div></div></div> ");				  
				}			  
			}
		}









		buffer.append("	</div> ");
		buffer.append("<!-- END main body --> ");





		buffer.append("</div>");

		buffer.append("<!-- END jive body -->   ");


		buffer.append("</div>");
		buffer.append("<!-- END container_grey-->");

		buffer.append("</div>");

		buffer.append("<!-- END contentcontainer-->");

		buffer.append("</div>");
		buffer.append("<!-- END pagina-->");

		buffer.append("</body>");
		buffer.append("</html>");
		return buffer.toString();			
	}


	public String buildMessage(int modelo) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<DIV bgcolor=\"#FFFFFF\">");
		sBuilder.append("<TABLE cellSpacing=0 cellPadding=0 width=\"99%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR bgColor=#252c3e>");
		sBuilder.append("<TD vAlign=center>");
		sBuilder.append("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD width=15 bgColor=#aab5d4><IMG height=10 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=10></TD> "); 			
		sBuilder.append("<TD width=5><IMG height=10 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=5></TD>");
		sBuilder.append("<TD><SPAN style=\"FONT-WEIGHT: bold; FONT-SIZE: 18px; COLOR: rgb(244,244,255); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\"><A name=12fbbf7297b51b6f_top></A>");

		//Coloque aqui o t�tulo da baga�a
		sBuilder.append(this.sistema + "<BR>" + this.programa + "</SPAN><BR>");

		sBuilder.append("<SPAN style=\"FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: rgb(244,244,255); FONT-STYLE: normal; FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\">");

		//Coloque aqui o evento que ocorreu		
		sBuilder.append(this.messageEvento+"</SPAN>"); 

		sBuilder.append("</TD></TR></TBODY></TABLE></TD>");
		sBuilder.append("<TD width=80>");
		sBuilder.append("<DIV align=right><IMG src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/logo_protheus.gif\"></DIV></TD></TR></TBODY></TABLE>");

		sBuilder.append("<TABLE cellPadding=10 width=\"99%\" bgColor=#f5f5ff>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD>");
		sBuilder.append("<TABLE width=\"100%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD style=\"BORDER-RIGHT: 0px; FONT-WEIGHT: bolder; FONT-SIZE: 11px; BORDER-LEFT: rgb(74,134,138) 1px; COLOR: rgb(166,166,166); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\""); 
		sBuilder.append("colSpan=2><BR>.: <A href=\"#12fbbf7297b51b6f_anc_msg\">Mensagem</A><BR></TD></TR>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD width=\"15%\">&nbsp;</TD>");
		sBuilder.append("<TD width=\"85%\">&nbsp;</TD>");
		sBuilder.append("</TR>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD style=\"BORDER-RIGHT: rgb(204,204,204) 0px; BORDER-TOP: rgb(204,204,204) 0px; FONT-WEIGHT: normal; FONT-SIZE: 11px; BORDER-LEFT: rgb(204,204,204) 0px; COLOR: rgb(0,87,89); BORDER-BOTTOM: rgb(204,204,204) 0px; FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif; BACKGROUND-COLOR: rgb(226,226,249); TEXT-ALIGN: right\">Empresa:</TD>");
		sBuilder.append("<TD style=\"BORDER-RIGHT: 0px; FONT-WEIGHT: bolder; FONT-SIZE: 11px; BORDER-LEFT: rgb(74,134,138) 1px; COLOR: rgb(102,102,102); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif; BACKGROUND-COLOR: rgb(226,226,249)\">PAN-AMERICANA S/A INDUSTRIAS QUIMICAS </TD></TR>");
		sBuilder.append("<TR>");
		Iterator itJiveWidgets = vListagem.iterator();
		while (itJiveWidgets.hasNext()) {
			String[] sWidget = (String[])itJiveWidgets.next();
			sBuilder.append("<TR>");
			sBuilder.append("<TD style=\"BORDER-RIGHT: rgb(204,204,204) 0px; BORDER-TOP: rgb(204,204,204) 0px; FONT-WEIGHT: normal; FONT-SIZE: 11px; BORDER-LEFT: rgb(204,204,204) 0px; COLOR: rgb(0,87,89); BORDER-BOTTOM: rgb(204,204,204) 0px; FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif; BACKGROUND-COLOR: rgb(226,226,249); TEXT-ALIGN: right\">" + sWidget[0] + "</TD>");
			sBuilder.append("<TD style=\"BORDER-RIGHT: 0px; FONT-WEIGHT: bolder; FONT-SIZE: 11px; BORDER-LEFT: rgb(74,134,138) 1px; COLOR: rgb(102,102,102); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif; BACKGROUND-COLOR: rgb(226,226,249)\">" + sWidget[1] + " </TD></TR>");
			sBuilder.append("<TR>");
		}
		sBuilder.append("</TBODY></TABLE></TD></TR>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD>");
		sBuilder.append("<TABLE cellPadding=10 width=\"100%\" bgColor=#f5f5ff>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD style=\"FONT-WEIGHT: bolder; FONT-SIZE: 12px; COLOR: rgb(102,102,102); FONT-FAMILY: Tahoma\"><A name=12fbbf7297b51b6f_anc_msg></A>");

		sBuilder.append(this.messageBody.toString());

		sBuilder.append("<BR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>");
		sBuilder.append("<P></P>");
		sBuilder.append("<TABLE cellSpacing=0 cellPadding=0 width=\"99%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR bgColor=#252c3e>");
		sBuilder.append("<TD vAlign=center>");
		sBuilder.append("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD width=15 bgColor=#aab5d4><IMG height=10 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=10></TD>");
		sBuilder.append("<TD width=5><IMG height=10 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=5></TD>");
		sBuilder.append("<TD><SPAN style=\"FONT-WEIGHT: bold; FONT-SIZE: 18px; COLOR: rgb(244,244,255); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\">");
		sBuilder.append("<A name=12fbbf7297b51b6f_info></A>INFORMA��ES T�CNICAS</SPAN><BR>");
		sBuilder.append("<SPAN style=\"FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: rgb(244,244,255); FONT-STYLE: normal; FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\">Dados Complementares</SPAN> </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>");
		sBuilder.append("<TABLE cellPadding=10 width=\"99%\" bgColor=#f5f5ff>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD>");
		sBuilder.append("<TABLE style=\"FONT-SIZE: 10px; COLOR: rgb(51,51,51); FONT-FAMILY: Verdana,Arial,Helvetica,sans-serif\" cellSpacing=0 cellPadding=0 width=\"99%\" border=0>");
		sBuilder.append("<TBODY>");
		sBuilder.append("<TR>");
		sBuilder.append("<TD vAlign=top><B>PANAMERICANA Industrias Quimicas - " + yearFormat.format(new GregorianCalendar())+ "</B><BR>Esta mensagem refere-se a disparo autom�tico do servidor Protheus.<BR>");
		sBuilder.append("<a href='http://netsaint.panamericana.com.br/helpdesk'>http://netsaint.panamericana.com.br/helpdesk</a>");
		sBuilder.append("</TD>");
		sBuilder.append("<TD><IMG height=5 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=10></TD>");
		sBuilder.append("<TD><IMG height=5 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=10></TD>");
		sBuilder.append("<TD><IMG height=5 src=\"http://ora9ias.panamericana.com.br:8080/Repositorio/images/10px_transp.gif\" width=10></TD>");
		sBuilder.append("<TD vAlign=top align=right>");
		sBuilder.append("<P>Equipe de Desenvolvimento de Sistemas<BR>Telefone: (21) 2472-7338"); 
		sBuilder.append("<BR>Website: <A href=\"http://www.panamericana.com.br\" target=_blank>www.panamericana.com.br</A></P></TD></TR></TBODY></TABLE></DIV></DIV>");

		return sBuilder.toString() ;
	}



	public StringBuilder getMessageBody() {
		return messageBody;
	}


	public void setMessageBody(StringBuilder messageBody) {
		this.messageBody = messageBody;
	}


	public void setVListagem(Vector listagem) {
		vListagem = listagem;		
	}

	public void setMessageWelcome(String messageWelcome) {
		this.messageWelcome = messageWelcome;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}


	public String getMessageEvento() {
		return messageEvento;
	}


	public void setMessageEvento(String messageEvento) {
		this.messageEvento = messageEvento;
	}


	public String getSistema() {
		return sistema;
	}


	public void setSistema(String sistema) {
		this.sistema = sistema;
	}


	public String getModulo() {
		return modulo;
	}


	public void setModulo(String modulo) {
		this.modulo = modulo;
	}


	public String getPrograma() {
		return programa;
	}


	public void setPrograma(String programa) {
		this.programa = programa;
	}


	public String getEvento() {
		return evento;
	}


	public void setEvento(String evento) {
		this.evento = evento;
	}


	public String getMessageWelcome() {
		return messageWelcome;
	}


	public String getMessageTitle() {
		return messageTitle;
	}





}

