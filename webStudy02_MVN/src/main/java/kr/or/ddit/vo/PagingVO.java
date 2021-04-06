package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

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

	
	
	
}
