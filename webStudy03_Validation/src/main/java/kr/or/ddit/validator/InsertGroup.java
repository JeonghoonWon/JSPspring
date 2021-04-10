package kr.or.ddit.validator;

import javax.validation.groups.Default;

// markup interface 로만 사용할 것.
//식별 용도로만 사용. 등록하는 그룹 
public interface InsertGroup extends Default {
// extends Default  : 기본 그룹 이면서 insertGroup 
}
