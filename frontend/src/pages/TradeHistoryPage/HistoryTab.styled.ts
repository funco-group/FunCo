import styled from "styled-components";
import palette from "@/lib/palette";

interface HistoryTabDivProps {
  $active: boolean;
}

export const HistoryTabContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  border-bottom: 1px solid ${palette.borderGray};
`;

export const HistoryTabDiv = styled.div<HistoryTabDivProps>`
  border-bottom: ${({ $active }) =>
    $active ? `4px solid ${palette.brandColor}` : ""};
  font-family: ${({ $active }) => ($active ? "NanumSquareBold" : "")};
  color: ${({ $active }) => ($active ? `${palette.brandColor}` : "")};
  text-align: center;
  font-size: 1rem;
  padding: 0.6rem 0;
  cursor: pointer;
`;
