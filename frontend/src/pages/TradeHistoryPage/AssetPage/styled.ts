import palette from "@/lib/palette";
import styled from "styled-components";

export const TotalAssetInfoContainer = styled.div`
  display: grid;
  grid-template-columns: 2.5fr 1fr;
`;

export const ChartContainer = styled.div`
  border-bottom: 1px solid ${palette.borderGray};
  /* background-color: red; */
  align-items: center;
  justify-content: center;
  display: flex;
  margin-left: -1rem;
`;
