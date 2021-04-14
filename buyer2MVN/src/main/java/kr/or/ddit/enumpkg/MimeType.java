package kr.or.ddit.enumpkg;

public enum MimeType {
	JSON("application/json;charset=UTF-8"),
	XML("application/xml;charset=UTF-8"), 
	PLAIN("text/plain;charset=UTF-8"), 
	HTML("text/html;charset=UTF-8");
	
	private MimeType(String mime) {
		this.mime = mime;
	}

	private String mime;
	public String getMime() {
		return mime;
	}
	
	public static MimeType searchMimeType(String accept) {
		//밑에서는 mimeText를 가져가고, 이 코드에서는 mime상수를 가져가기 위함
		accept = accept.toUpperCase();
		MimeType searched = HTML;
		for(MimeType tmp : MimeType.values()) {
			if(accept.contains(tmp.name())) {
				searched = tmp;
				break;
			}
		}
		return searched;
	}
	
	public static String getMimeText(String accept) {
		return searchMimeType(accept).getMime();
	}
}