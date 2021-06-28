package com.algaworks.alganews.common.email.infrastructure;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.alganews.common.email.config.EmailProperties;
import com.algaworks.alganews.common.email.domain.EmailException;
import com.algaworks.alganews.common.email.domain.EmailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SmtpEmailSenderService implements EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void send(Message message) {
		try {
			MimeMessage mimeMessage = createMimeMessage(message);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}
	
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String body = processTemplate(message);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getFrom());
		helper.setTo(message.getRecipient().toArray(new String[0]));
		helper.setSubject(message.getSubject());
		helper.setText(body, true);
		
		return mimeMessage;
	}
	
	protected String processTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, message.getData());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}

}
