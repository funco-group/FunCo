import styled from "styled-components";
import palette from "@/lib/palette";

export const FollowTradeHistoryContainer = styled.div`
  margin: 0.625rem;
  border-top: 1px solid ${palette.deactivatedGray};
  border-inline: 1px solid ${palette.deactivatedGray};
`;

export const FollowTradeHistoryColumnGridDiv = styled.div`
  display: grid;
  grid-template-columns: 5.5rem 7rem 8rem 8rem;
`;

export const FollowTradeHistoryContentDiv = styled.div``;

export const FollowTradeHistoryDateDiv = styled.div`
  padding: 0.625rem 0;
  border-bottom: 1px solid ${palette.deactivatedGray};
`;

export const FollowTradeHistoryContentInnerDiv = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 0.3125rem;

  border-left: 1px solid ${palette.deactivatedGray};
  border-bottom: 1px solid ${palette.deactivatedGray};
  padding: 0 0.625rem;
`;

export const FollowTradeHistoryTextAlignDiv = styled.div`
  text-align: end;
`;
