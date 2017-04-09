<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" 
xmlns:rich="http://richfaces.org/rich" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml">
<h:head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Calm down, I get a setup</title>
</h:head>
<h:body bgcolor="#454599">
<f:view>
	<h:outputText value="Inside setup.jsf!!!!!!!"></h:outputText>
	
	    <h:form>
        <h:outputText value="Hello World!" />
        
        		<h:outputText value="Not Inside RichPanel" />
        		
        		        <rich:calendar/>      
        <rich:panel>
        		<h:outputText value="Inside RichPanel" />
        </rich:panel>    
                 
    </h:form>
</f:view>
</h:body>
</html>
</jsp:root>