import styled from "styled-components";
import palette from "@/lib/palette";

export const FollowingStatisticsContainer = styled.div`
  border-bottom: 1px solid ${palette.deactivatedGray};
  padding: 1rem;
  display: grid;
  grid-template-columns: 2.5fr 1fr;
`;

export const FollowingStatisticsDetailDiv = styled.div`
  /* width: 80%; */
  border: none;
  background-color: ${palette.brandColor2};
  padding: 1.6rem 1.5rem;
`;

export const FollowingStatisticsDetailInnerDiv = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  border-bottom: 1px solid ${palette.brandColor};
  padding-bottom: 1rem;
  margin-bottom: 1rem;
`;

export const StatisticsRowDiv = styled.div`
  display: flex;
  justify-content: space-between;

  span {
    font-size: 0.9rem;
    color: ${palette.brandDarkGray};
  }
`;

export const StatisticsGraphDiv = styled.div`
  display: flex;
  align-items: center;
`;
