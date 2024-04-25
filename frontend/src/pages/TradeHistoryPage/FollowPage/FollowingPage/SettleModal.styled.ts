import styled from "styled-components";
import palette from "@/lib/palette";
import { Overflow } from "@/styles/CommonStyled";

export const SettleModalBackgroundContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(2px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
`;

export const SettleModalContainer = styled.div<{ width?: string }>`
  background-color: ${palette.brandWhite};
  width: ${(props) => props.width};
  border: none;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.16);
  padding: 0.9375rem;
  max-height: 80%;
  ${Overflow}
`;

export const SettleModalTitleDiv = styled.div`
  padding: 1rem 0.3rem;
  border-bottom: 1px solid ${palette.borderGray};
  text-align: left;
  font-family: "NanumSquareBold";
  font-size: 1.1rem;
`;

export const SettleModalContentDiv = styled.div`
  padding: 0.5rem 1rem;
  color: ${palette.brandDarkGray};
  font-size: 0.9rem;
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid ${palette.borderGray};

  span {
    font-family: "NanumSquareBold";
    color: ${palette.brandBlack};
  }
`;

export const SettleModalContentRowDiv = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  width: 30rem;
`;

export const SettleModalContentButtonRowDiv = styled.div`
  padding: 1rem 0;
  display: flex;
  justify-content: end;
  gap: 0.625rem;
  /* position: fixed; */
`;
