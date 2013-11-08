package org.kallsonnys.oms.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private String from;
	
	private String subject;
	
	private List<String> recipients;
	
	private List<String> copyTo;

	private List<String> secondCopyTo;
	
	private Boolean bccToSupport = false;
	
	private List<String> replyTo;
	
	private String templateName;
	
	private Map<String, Object> paramValues;
	
	public MailDTO() {
		
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getRecipients() {
		if(recipients==null)
			recipients = new ArrayList<String>();
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
	public void addRecipient(String recipient){
		if(recipient == null)
			return;
		getRecipients().add(recipient);	
	}
	
	public void removeRecipient(String recipient){
		if(recipient == null)
			return;
		getRecipients().remove(recipient);	
	}
	
	public void removeAllRecipients(List<String> recipients){
		if(recipients == null)
			return;
		List<String> remove = new ArrayList<String>();
		remove.addAll(recipients);
		for (String recipient : remove) {
			removeRecipient(recipient);
		}
	}

	public List<String> getCopyTo() {
		if(copyTo == null)
			copyTo = new ArrayList<String>();
		return copyTo;
	}

	public void setCopyTo(List<String> copyTo) {
		this.copyTo = copyTo;
	}
	
	public void addCopyTo(String copyTo){
		if(copyTo == null)
			return;
		getCopyTo().add(copyTo);	
	}
	
	public void removeCopyTo(String copyTo){
		if(copyTo == null)
			return;
		getCopyTo().remove(copyTo);	
	}
	
	public void removeAllCopyTo(List<String> copyTo){
		if(copyTo == null)
			return;
		List<String> remove = new ArrayList<String>();
		remove.addAll(copyTo);
		for (String recipient : remove) {
			removeCopyTo(recipient);
		}
	}

	public List<String> getSecondCopyTo() {
		if(secondCopyTo==null)
			secondCopyTo = new ArrayList<String>();
		return secondCopyTo;
	}

	public void setSecondCopyTo(List<String> secondCopyTo) {
		this.secondCopyTo = secondCopyTo;
	}
	
	public void addSecondCopyTo(String secondCopyTo){
		if(secondCopyTo == null)
			return;
		getSecondCopyTo().add(secondCopyTo);	
	}
	
	public void removeSecondCopyTo(String secondCopyTo){
		if(secondCopyTo == null)
			return;
		getSecondCopyTo().remove(secondCopyTo);	
	}
	
	public void removeAllSecondCopyTo(List<String> secondCopyTo){
		if(secondCopyTo == null)
			return;
		List<String> remove = new ArrayList<String>();
		remove.addAll(secondCopyTo);
		for (String recipient : remove) {
			removeSecondCopyTo(recipient);
		}
	}

	public Boolean getBccToSupport() {
		return bccToSupport;
	}

	public void setBccToSupport(Boolean bccToSupport) {
		this.bccToSupport = bccToSupport;
	}

	public List<String> getReplyTo() {
		if(replyTo == null)
			replyTo = new ArrayList<String>();
		return replyTo;
	}

	public void setReplyTo(List<String> replyTo) {
		this.replyTo = replyTo;
	}
	
	public void addReplyTo(String replyTo){
		if(replyTo == null)
			return;
		getReplyTo().add(replyTo);	
	}
	
	public void removeReplyTo(String replyTo){
		if(replyTo == null)
			return;
		getReplyTo().remove(replyTo);	
	}
	
	public void removeAllReplyTo(List<String> replyTo){
		if(replyTo == null)
			return;
		List<String> remove = new ArrayList<String>();
		remove.addAll(replyTo);
		for (String recipient : remove) {
			removeReplyTo(recipient);
		}
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, Object> getParamValues() {
		if(paramValues == null)
			paramValues = new HashMap<String, Object>();
		return paramValues;
	}
	
	public void addParam(String key, Object value){
		getParamValues().put(key, value);
	}

	public void setParamValues(Map<String, Object> paramValues) {
		this.paramValues = paramValues;
	}



}
