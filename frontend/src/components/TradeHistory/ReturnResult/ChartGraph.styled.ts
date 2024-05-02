import styled from "styled-components";
import palette from "@/lib/palette";

export const ChartGraphContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  border-bottom: 1px solid ${palette.borderGray};
`;
export const ChartDiv = styled.div<{ $left: boolean }>`
  /* background-color: red; */
  padding: 1rem;
  height: 20rem;
  border-right: ${(props) => props.$left && `1px solid ${palette.borderGray}`};
`;
