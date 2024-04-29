import styled from "styled-components";
import palette from "@/lib/palette";

export const RankTableContainer = styled.div`
  border-top: 1px solid ${palette.deactivatedGray};
`;

export const RankTableColumnGridDiv = styled.div`
  display: grid;
  grid-template-columns: 6.25rem 1fr 1fr 1fr 1fr;
  align-items: center;
`;
