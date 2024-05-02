import { CommonComponent } from "@/styles/CommonStyled";
import styled from "styled-components";

export const ReturnRateGraphContainer = styled.div`
  height: 29rem;
  ${CommonComponent};
  padding: 1rem;
`;

export const NoDataDiv = styled.div`
  color: #dfe1e6;
  text-align: center;
  font-size: 0.9rem;
  /* line-height: 25rem; */
  /* background-color: red; */
`;

export const ReturnRateGraphContentContainer = styled.div<{ $flex?: boolean }>`
  align-items: center;
  justify-content: center;
  height: 23rem;
  display: ${(props) => !props.$flex && "flex"};
`;
