import styled from "styled-components";
import palette from "@/lib/palette";

export const RankTableContentContainer = styled.div`
  color: ${palette.brandDarkGray};
  font-size: 0.75rem;
  text-align: center;
  border-bottom: 1px solid ${palette.deactivatedGray};
  padding: 0.625rem 0;
`;

export const RankTableContentUserDiv = styled.div`
  display: flex;
  justify-content: left;
  align-items: center;
  gap: 0.625rem;
  margin-left: 3rem;
  /* background-color: red; */

  img {
    width: 1.5rem;
    height: 1.5rem;
    border-radius: 100%;
  }

  div {
    text-align: left;
  }
  cursor: pointer;
`;

export const RankTableContentMarginDiv = styled.div`
  text-align: end;
  margin-right: 3.75rem;
`;

export const RankSpan = styled.span<{ $isTopRank: string | undefined }>`
  font-size: ${({ $isTopRank }) =>
    $isTopRank !== undefined ? "1.5rem" : null};
`;
