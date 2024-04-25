import styled from "styled-components";
import { CommonComponent } from "@/styles/CommonStyled";
import palette from "@/lib/palette";

export const ChartContainer = styled.div`
  ${CommonComponent}
`;

export const ChartInfoContainer = styled.div`
  /* background-color: red; */
`;

export const CoinNameDiv = styled.div`
  align-items: center;
  display: flex;
  font-size: 0.7rem;
  padding: 0.5rem 0.7rem;
  border-bottom: 1px solid ${palette.borderGray};

  img {
    /* vertical-align: middle; */
  }

  span {
    font-size: 1.1rem;
    font-family: "NanumSquareExtraBold";
    margin-left: 0.5rem;
    margin-right: 0.2rem;
  }
`;

export const PriceContainer = styled.div`
  /* background-color: red; */
  padding: 1.3rem;
  display: flex;
  justify-content: space-between;
`;

export const CurPriceDiv = styled.div<{ change: string }>`
  color: ${(props) => {
    if (props.change === "RISE") {
      return palette.brandRed;
    }
    if (props.change === "FALL") {
      return palette.brandBlue;
    }
  }};
`;

export const PriceDiv = styled.div`
  font-size: 1.9rem;
  font-family: "NanumSquareExtraBold";

  span {
    font-size: 0.7rem;
    font-family: "NanumSquareBold";
  }
`;

export const ChangeDiv = styled.div`
  display: flex;
  margin-top: 0.3rem;
`;

export const ChangeTextDiv = styled.div`
  font-size: 0.75rem;
  color: ${palette.brandDarkGray};
`;

export const ChangeInfoDiv = styled.div`
  margin: 0 0.2rem;
  font-size: 0.9rem;
`;

export const TradePriceContainer = styled.div`
  display: flex;
  /* background-color: yellow; */
`;
export const TradePriceItemDiv = styled.div`
  /* background-color: orange; */
  margin-left: 1rem;
  font-size: 0.85rem;
  width: 16rem;
`;

export const TradeTitleDiv = styled.div<{ $top: boolean }>`
  display: flex;
  justify-content: space-between;
  border-bottom: ${(props) => props.$top && `1px solid ${palette.borderGray}`};
  /* background-color: blue; */
  padding: 0.5rem 0;
`;
export const TradePriceDiv = styled.div<{ color: string }>`
  font-family: "NanumSquareBold";
  color: ${(props) => {
    if (props.color === "red") {
      return palette.brandRed;
    }
    if (props.color === "blue") {
      return palette.brandBlue;
    }
  }};

  span {
    font-size: 0.65rem;
    font-family: "NanumSquare";
    color: ${palette.brandDarkGray};
  }
`;

export const ButtonsContainer = styled.div`
  padding: 0.5rem 1.3rem;
  display: flex;
`;

export const TypeButton = styled.div<{ $selected: boolean }>`
  background-color: ${(props) =>
    props.$selected ? palette.brandColor : "whitesmoke"};
  padding: 0.3rem;
  font-size: 0.8rem;
  margin-right: 0.5rem;
  border-radius: 0.3rem;
  color: ${(props) =>
    props.$selected ? palette.brandWhite : palette.brandDarkGray};
  cursor: pointer;
`;
