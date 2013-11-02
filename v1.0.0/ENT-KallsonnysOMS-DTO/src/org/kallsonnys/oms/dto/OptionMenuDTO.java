package org.kallsonnys.oms.dto;

public class OptionMenuDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String option;
	private String url;
		
	public OptionMenuDTO() {

	}
	
		
	public OptionMenuDTO(Long id, String option, String url) {
		super();
		this.id = id;
		this.option = option;
		this.url = url;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
