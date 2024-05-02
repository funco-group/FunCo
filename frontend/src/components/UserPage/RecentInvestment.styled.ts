import styled from "styled-components";
import { CommonComponent } from "@/styles/CommonStyled";
import palette from "@/lib/palette";

export const RecentInvestmentContainer = styled.div`
  ${CommonComponent};
  padding: 1rem;
`;

export const RecentInvestmentContentDiv = styled.div`
  width: 100%;
  margin-top: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.25rem;
`;

export const RecentInvestmentRowDiv = styled.div`
  width: 100%;
  color: ${palette.brandBlack};
  display: flex;
  align-items: center;
  justify-content: space-between;

  div {
    display: flex;
  }
`;

export const CoinNameDiv = styled.div`
  margin-left: 0.5rem;
  display: flex;
  align-items: center;
  font-family: "NanumSquareBold";

  img {
    margin-right: 0.3rem;
  }
`;

export const DateDiv = styled.div`
  font-size: 0.8rem;
`;

export const NumberDiv = styled.div`
  color: ${palette.brandColor};
  font-family: "NanumSquareBold";
`;

export const RecentInvestmentContentContainer = styled.div`
  height: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
`;
