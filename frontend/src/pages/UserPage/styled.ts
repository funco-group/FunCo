import styled, { css } from "styled-components";
import palette from "@/lib/palette";

const ColumnLayout = css`
  /* display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 1rem; */
`;

export const ComponentTitleH3 = styled.div`
  padding-bottom: 1rem;
  font-size: 1.2rem;
  font-family: "NanumSquareBold";
  border-bottom: 1px solid ${palette.deactivatedGray};
`;

export const UserLayoutContainer = styled.div`
  /* display: grid;
  grid-template-rows: 25rem 12rem 30rem;
  grid-row-gap: 1rem; */
`;

export const UserLayoutRowDiv = styled.div`
  ${ColumnLayout}
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 1rem;
`;
