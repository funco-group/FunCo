import styled, { css } from "styled-components";
import { ToastContainer } from "react-toastify";
import palette from "@/lib/palette";

export const CommonComponent = css`
  border: 1px solid ${palette.deactivatedGray};
  background-color: ${palette.brandWhite};
`;

export const ColumnContainer = styled.div`
  background-color: #f9fafc;
  padding: 0.5rem 0;
`;

export const ColumnGrid = styled.div<{ column: string }>`
  display: grid;
  grid-template-columns: ${(props) => props.column};
`;

export const ColumnTitleDiv = styled.div`
  text-align: center;
  font-size: 0.75rem;
  font-family: "NanumSquareAcb";
  color: ${palette.brandDarkGray};
`;

export const StyledContainer = styled(ToastContainer)`
  margin-top: 4rem;
  width: 25rem;

  .Toastify__toast-body {
    background: ${palette.brandWhite} !important;
    color: ${palette.brandBlack};
  }

  .Toastify__progress-bar {
    background: ${palette.brandColor} !important;
  }
`;

export const ListItemContainer = styled.div`
  font-size: 0.85rem;
  padding: 0.7rem 0;
  border-bottom: 1px solid ${palette.borderGray};
  /* display: flex; */
  align-items: center;
  justify-content: center;
  text-align: center;
`;

export const ListItemDiv = styled.div<{ align: string; color: string }>`
  /* background-color: red; */
  display: flex;
  align-items: center;
  padding: 0 0.5rem;
  color: ${(props) => {
    if (props.color === "red") {
      return palette.brandRed;
    }
    if (props.color === "blue") {
      return palette.brandBlue;
    }
    return palette.brandBlack;
  }};

  justify-content: ${(props) => {
    if (props.align === "left") {
      return "left";
    }
    if (props.align === "right") {
      return "right";
    }
    return "center";
  }};

  img {
    margin-right: 0.4rem;
  }

  span {
    font-size: 0.7rem;
    color: ${palette.brandDarkGray};
  }
`;

export const Overflow = css`
  overflow-y: auto;
  -ms-overflow-style: none; /* 인터넷 익스플로러 */
  scrollbar-width: none; /* 파이어폭스 */
`;
