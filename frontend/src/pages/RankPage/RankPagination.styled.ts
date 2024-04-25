import styled from "styled-components";
import palette from "@/lib/palette";

export const RankPaginationContainer = styled.div`
  padding: 0.625rem;
  width: 100%;
`;

export const RankPaginationDiv = styled.div`
  display: flex;
  justify-content: center;

  img {
    display: block;
    width: 2rem;
    height: 2rem;
  }
`;

export const RankPaginationButton = styled.button<{ $active: boolean }>`
  width: 2rem;
  height: 2rem;
  border: none;
  background-color: ${({ $active }) =>
    $active ? palette.brandColor2 : "transparent"};
  color: ${({ $active }) =>
    $active ? palette.brandColor : palette.brandDarkGray};
  cursor: pointer;
`;

export const RankArrowDiv = styled.div`
  cursor: pointer;
`;
