import styled from "styled-components";
import palette from "@/lib/palette";

export const NotiHistoryContentContainer = styled.div<{ $isRead: boolean }>`
  border-bottom: 1px solid ${palette.borderGray};
  background-color: ${({ $isRead }) =>
    $isRead ? "transparent" : palette.brandColor2};
  margin: 0;
  padding: 0.625rem;
`;

export const NotiHistoryContentTitleP = styled.p`
  font-family: "NanumSquareBold";
  font-size: 0.75rem;
  color: ${palette.brandColor};
  margin: 0;
`;

export const NotiHistoryContentMsgP = styled.p`
  font-size: 0.9rem;
  margin: 0.5rem 0;
  color: ${palette.brandBlack};
`;

export const NotiHistoryContentDateP = styled.p`
  font-size: 0.75rem;
  color: ${palette.brandDarkGray};
  margin: 0;
`;
