import { Overflow } from "@/styles/CommonStyled";
import styled from "styled-components";

export const AssetChangeListContainer = styled.div`
  margin-top: 5rem;
`;

export const HistoryListContainer = styled.div`
  max-height: 30rem;
  ${Overflow};
`;
