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

  label {
    margin: 5px 0 auto auto;
    user-select: none;
  }

  .checkInput {
    position: relative;
    top: 0.0938rem;
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
