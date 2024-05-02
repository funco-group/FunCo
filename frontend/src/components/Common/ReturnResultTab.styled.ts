import palette from "@/lib/palette";
import styled from "styled-components";

export const ReturnResultTabContainer = styled.div`
  /* background-color: red; */
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 1rem;
  border-bottom: 1px solid ${palette.borderGray};

  select {
    margin-left: 1rem;
    padding: 0.7rem;
    border: 1px solid ${palette.deactivatedGray};
    border-radius: 5px;
    color: #999999;
    width: 7rem;

    &:focus {
      border-color: ${palette.mainColor};
      outline: none;
    }
  }
`;
export const DateDiv = styled.div`
  font-size: 0.9rem;
  font-family: "NanumSquareBold";
`;

export const DateSetDiv = styled.div`
  display: flex;
`;
