import styled from "styled-components";
import palette from "@/lib/palette";

export const ResultContainer = styled.div`
  border-bottom: 1px solid ${palette.borderGray};
`;

export const ResultItemContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
`;
export const ResultItemDiv = styled.div<{ $right: boolean }>`
  display: flex;
  justify-content: space-between;
  padding: 0 1rem;
  margin: 1rem 0;
  border-right: ${(props) => props.$right && `1px solid ${palette.brandColor}`};
`;

export const TitleDiv = styled.div``;
export const DataDiv = styled.div<{ color: string }>`
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
    font-size: 0.75rem;
    color: ${palette.brandDarkGray};
    font-family: "NanumSquare";
  }
`;

export const TextInfoDiv = styled.div`
  font-size: 0.75rem;
  text-align: right;
  padding: 0 1rem;
  color: ${palette.brandDarkGray};
`;
