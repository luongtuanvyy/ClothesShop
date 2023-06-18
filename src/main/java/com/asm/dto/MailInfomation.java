package com.asm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfomation {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	String[] attachments;
	public MailInfomation(String to, String subject, String body) {
		super();
		this.from = "ltvy910@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}

