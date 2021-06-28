package com.algaworks.alganews.common.email.infrastructure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailSenderService extends SmtpEmailSenderService {

	@Override
	public void send(Message message) {
		String body = processTemplate(message);

		log.info("[FAKE E-MAIL] To: {}\n{}", message.getRecipient(), body);
	}

}
