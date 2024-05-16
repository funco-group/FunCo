import styled from "styled-components";
import palette from "@/lib/palette";

export const TabContainer = styled.div<{ columns: number }>`
  display: grid;
  /* grid-template-columns: 1fr 1fr 1fr; */
  grid-template-columns: ${(props) => `repeat(${props.columns}, 1fr)`};
  border-bottom: 1px solid ${palette.borderGray};
`;

export const TabItemDiv = styled.div<{ $active: boolean }>`
  color: ${(props) => props.$active && palette.brandColor};
  font-family: ${(props) => (props.$active ? "NanumSquareBold" : "")};
  border-bottom: ${(props) =>
    props.$active ? `4px solid ${palette.brandColor}` : ""};
  text-align: center;
  font-size: 1rem;
  padding: 0.6rem 0 0.5rem 0;
  cursor: pointer;
`;
