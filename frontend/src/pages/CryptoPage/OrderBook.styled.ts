import styled from "styled-components";
import { CommonComponent } from "@/styles/CommonStyled";
import palette from "@/lib/palette";

export const OrderBookContainer = styled.div`
  ${CommonComponent}
  height: 38rem;
`;

export const TitleContainer = styled.div<{ $top: boolean }>`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  border-bottom: ${(props) => props.$top && `1px solid ${palette.borderGray}`};
  border-top: ${(props) => !props.$top && `1px solid ${palette.borderGray}`};
`;

export const TitleDiv = styled.div<{ type: string }>`
  padding: 0.6rem 0;
  text-align: center;
  color: ${(props) => {
    if (props.type === "매수") {
      return palette.brandRed;
    }
    if (props.type === "매도") {
      return palette.brandBlue;
    }
  }};
`;

export const ContentContainer = styled.div`
  /* height: 33rem; */
`;

export const ContentDiv = styled.div`
  /* padding: 0.6rem 0; */
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  font-size: 0.8rem;
`;

export const SizeDiv = styled.div<{ $buy: boolean }>`
  padding: 0.4rem 0.5rem;
  margin: 0.05rem;
  text-align: ${(props) => (props.$buy ? "left" : "right")};
  background-color: ${(props) => (props.$buy ? "#fcf1f0" : "#eef2fb")};
  font-size: 0.75rem;
  color: ${palette.brandDarkGray};
`;

export const PriceDiv = styled.div<{ $buy: boolean }>`
  padding: 0.4rem;
  margin: 0.05rem;
  text-align: center;
  background-color: ${(props) => (props.$buy ? "#fcf1f0" : "#eef2fb")};
  color: ${(props) => (props.$buy ? palette.brandRed : palette.brandBlue)};
  font-family: "NanumSquareBold";
`;
