package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
// 기존 적용해두었던 setter 가 있으면 그게 적용된다.
@Getter
@NoArgsConstructor
public class PagingVO<T> implements Serializable{
	//PagingVO<T> : 타입 변수
	public PagingVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}
	
	private int totalRecord; // db 전체 레코드 수
	private int screenSize = 10;  // 화면의 디자인에 따라 임의로 정함 
	private int blockSize = 5;   // 화면의 디자인에 따라 임의로 정함
	private int currentPage; // 클라이언트의 요청에 따라 변경
	
	private int totalPage;
	private int startRow;
	private int endRow;
	private int startPage;
	private int endPage;
	private List<T> dataList;
	// T 자리에 어떤게 들어오냐에 따라 달라짐.
	private SearchVO simpleSearch;
	
	// Map 생성 Object : 검색 조건을 담는다. // controller 와 jsp 와 xml 설정을 바꾼다.
	private Map<String, Object> searchMap;
	
	// ProdVO 
	// private ProdVO detailSearch;
	 private T detailSearch;
	 // ProdVO를 사용하면 다른곳에서 사용이 불가능하기때문에 
	 // T 로 잡아서 재내릭타입	
	 // 객체가 생성될때 
	
	public void setTotalRecord(int totalRecord) {
		// 이게 호출된다면 외부에서 레코드 수를 호출한 것.
		this.totalRecord = totalRecord;
		totalPage = totalRecord % screenSize == 0 ?
						totalRecord/screenSize :
						totalRecord/screenSize +1;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		startRow = (currentPage -1)*(screenSize) + 1;
		endRow = currentPage * screenSize;
		endPage = (currentPage+(blockSize-1))/blockSize * blockSize;
		startPage = endPage - (blockSize-1);
	}
	
	private static String aPattern = "<a href='#' data-page='%d'>[%s]</a>";
	private static String currentPagePattern = "<a href='#'>[%s]</a>";
	
	public String getPagingHTML() {
		StringBuffer html = new StringBuffer();
		if(startPage > 1 ) {
			html.append(
				String.format(aPattern,(startPage-1),"이전")
			);
			
		}
		endPage = endPage < totalPage ? endPage : totalPage;
		for(int page = startPage; page<=endPage; page++) {
			if(page==currentPage) {
				html.append(
					String.format(currentPagePattern,page+"")
					);
			}else {
				html.append(
					String.format(aPattern,page,page+"")
					);
			}
		}
		if(endPage < totalPage) {
			html.append(
					String.format(aPattern,(endPage+1),"다음")
				);
		}
		return html.toString();
		
		
	}
	private static String pageItem = "<li class='page-item %s' %s>"
			+"<a class='page-link' href='#' data-page='%d'>%s</a>"
			+ "</li>";

	public String getPagingHTMLBS() {
		StringBuffer html = new StringBuffer();
		html.append("<nav aria-label='...' class='mt-3'>");
		html.append("<ul class='pagination'>");
		String first = null;
		String second = null;
		int third = -1;
		String fourth = "<<";
		if(startPage > 1) {
			first = "";
			second = "";
			third = startPage - 1;
		}else {
			first ="disabled";
			second = "tabindex='-1' aria-disabled='true'";
			third = -1;
		}
		html.append(
			String.format(pageItem, first, second, third, fourth)	
		);
		endPage = endPage < totalPage ? endPage : totalPage;
		for(int page=startPage; page<=endPage; page++) {
			second = "";
			third = page;
			fourth = page + "";
			
			if(page==currentPage) {
				first = "active";
			}else {
				first = "";
			}
			html.append(
				String.format(pageItem, first, second, third, fourth)	
			);
		}
		fourth = ">>";
		if(endPage < totalPage) {
			first = "";
			second = "";
			third = endPage + 1;
		}else {
			first ="disabled";
			second = "tabindex='-1' aria-disabled='true'";
			third = -1;
		}
		html.append(
			String.format(pageItem, first, second, third, fourth)	
		);
		html.append("</ul>");
		html.append("</nav>");
		return html.toString();
	}
}









