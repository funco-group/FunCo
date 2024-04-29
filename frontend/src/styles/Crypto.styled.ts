import styled from "styled-components";
import palette from "@/lib/palette";

export const ButtonContainer = styled.div`
  display: flex;
  padding: 1.2rem 0.7rem;
`;

export const TradeContainer = styled.div`
  /* background-color: red; */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 30.5rem;
`;

export const TradeItem = styled.div`
  display: flex;
  justify-content: space-between;
  /* margin-bottom: 1rem; */
  /* background-color: blue; */
  align-items: center;
  padding: 0.8rem;
`;

export const AmountDiv = styled.div`
  display: flex;
  align-items: center;
  /* background-color: red; */
  width: 20.8rem;
`;

export const TitleDiv = styled.div`
  font-size: 0.95rem;
  color: ${palette.brandBlack};
`;

export const ContentDiv = styled.div`
  font-size: 0.9rem;
  display: flex;

  div {
    font-size: 0.7rem;
    color: ${palette.brandDarkGray};
    /* background-color: red; */
    min-width: 2.7rem;
    text-align: right;
  }
`;

export const PriceInput = styled.input`
  background-color: ${palette.background};
  border: 1px solid ${palette.deactivatedGray};
  /* width: 20rem; */
  padding: 0.5rem;
  text-align: right;
  width: 100%;

  &:focus {
    border-color: ${palette.mainColor};
    outline: none;
  }
`;

export const UpdownButton = styled.div`
  align-items: center;
  padding: 0.47rem 0.6rem;
  text-align: center;
  border: 1px solid ${palette.deactivatedGray};
  font-size: 0.9rem;
  cursor: pointer;
`;

export const PriceButtons = styled.div`
  display: flex;
  flex-wrap: wrap;
  /* background-color: blue; */
  width: 22rem;
  justify-content: flex-end;
`;

export const PriceButton = styled.div`
  background-color: ${palette.brandWhite};
  color: ${palette.brandDarkGray};
  border: 1px solid ${palette.brandDarkGray};
  font-size: 0.8rem;
  width: 4rem;
  padding: 0.4rem;
  text-align: center;
  border-radius: 0.3rem;
  margin-left: 0.4rem;
  margin-bottom: 0.4rem;
  cursor: pointer;

  &:hover {
    opacity: 0.8;
  }
`;

export const GreenDiv = styled.div<{ $last: boolean }>`
  background-color: ${palette.brandColor2};
  border-bottom: ${(props) =>
    !props.$last && `1px solid ${palette.mainColor};`};
`;

export const TradeInfo = styled.div`
  font-size: 0.7rem;
  text-align: right;
  padding: 0 0.7rem 0.7rem 0.7rem;
  color: ${palette.brandDarkGray};
`;
