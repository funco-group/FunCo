import styled from "styled-components";
import palette from "@/lib/palette";

export const FollowModalContentDiv = styled.div`
  padding: 1rem 0;
  color: ${palette.brandDarkGray};
  font-size: 0.9rem;
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid ${palette.borderGray};
  gap: 0.3125rem;

  /* 커스텀 체크박스 스타일 */
  .custom-checkbox {
    margin: 5px 0 auto auto;
    display: inline-block;
    position: relative;
    padding-left: 24px; /* 체크박스 크기와 여백을 고려하여 적절한 크기 설정 */
    cursor: pointer;
    user-select: none; /* 텍스트 선택 방지 */
  }

  /* 실제 체크박스 요소 숨기기 */
  .custom-checkbox input[type="checkbox"] {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    height: 0;
    width: 0;
  }

  /* 가상 체크박스 디자인 */
  .custom-checkbox .checkmark {
    position: absolute;
    top: -0.7px;
    left: 0;
    height: 0.9375rem; /* 체크박스 크기 */
    width: 0.9375rem; /* 체크박스 크기 */
    border: 1px solid ${palette.brandColor};
    border-radius: 0.25rem; /* 둥근 모서리 */
  }

  /* 체크박스 체크 시 스타일 */
  .custom-checkbox input:checked ~ .checkmark {
    background-color: ${palette.brandColor}; /* 체크 시 배경색 */
  }

  /* 체크 표시 스타일 */
  .custom-checkbox .checkmark:after {
    content: "";
    position: absolute;
    display: none;
  }

  .custom-checkbox input:checked ~ .checkmark:after {
    display: block;
  }

  .custom-checkbox .checkmark:after {
    left: 0.3438rem;
    top: 0.1rem;
    width: 0.1875rem;
    height: 0.4375rem;
    border: solid ${palette.brandWhite}; /* 체크 표시 색상 */
    border-width: 0 0.125rem 0.125rem 0;
    transform: rotate(45deg); /* 체크 표시 각도 */
  }
`;

export const FollowModalCheckWrapDiv = styled.div``;

export const FollowModalTermsDiv = styled.div`
  /* height: 18.75rem; */
  background-color: ${palette.brandGray};
  padding: 0.625rem;
  white-space: pre-line;
  line-height: 1.5rem;
`;

export const CashDiv = styled.div`
  text-align: right;
  font-size: 0.8rem;
  margin-top: 0.5rem;
  span {
    font-size: 0.6rem;
  }
`;

export const InputDiv = styled.div<{ $validation: boolean }>`
  display: flex;
  align-items: end;
  justify-content: end;
  border: 1px solid
    ${(props) => {
      if (props.$validation) {
        return "red";
      }
      return palette.borderGray;
    }};
  padding: 0.4rem 0.5rem;
  margin-left: 0.5rem;

  span {
    font-size: 0.8rem;
    color: ${palette.borderGray};
  }

  input {
    width: 13rem;
    font-size: 1rem;
    border: none;
    text-align: right;
    margin-right: 0.5rem;
    &::placeholder {
      color: ${palette.borderGray};
    }

    &:focus {
      border: none;
      outline: none;
    }
  }
`;

export const InvestDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: end;
`;

export const ValidationText = styled.div<{ $validation: boolean }>`
  color: red;
  text-align: right;
  font-size: 0.7rem;
  visibility: ${(props) => !props.$validation && "hidden"};
`;
