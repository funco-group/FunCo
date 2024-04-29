import styled from "styled-components";
import palette from "@/lib/palette";

export const GreenContainer = styled.div`
  background-color: ${palette.brandColor2};
  padding: 1rem 1.5rem;
  margin: 1rem;
`;

export const TitleDiv = styled.div`
  padding: 1rem;
  font-family: "NanumSquareBold";
  border-bottom: 1px solid ${palette.borderGray};
`;

export const GreenTitleDiv = styled.div`
  font-size: 0.95rem;
`;
export const GreenDataDiv = styled.div<{ color: string }>`
  text-align: right;
  font-family: "NanumSquareBold";
  color: ${(props) => {
    if (props.color === "blue") {
      return palette.brandBlue;
    }
    if (props.color === "red") {
      return palette.brandRed;
    }
  }};
  span {
    font-size: 0.75rem;
    color: ${palette.brandDarkGray};
    font-family: "NanumSquare";
  }
`;
