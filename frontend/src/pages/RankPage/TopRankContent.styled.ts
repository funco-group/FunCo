import styled from "styled-components";
import palette from "@/lib/palette";

export const TopRankContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
  padding: 0.75rem;
  width: 100%;

  text-align: center;

  border: none;
  background-color: ${palette.brandColor2};
`;

export const TopRankProfileDiv = styled.div<{ $isTop: boolean }>`
  margin-bottom: 0.625rem;
  img {
    width: ${({ $isTop }) => ($isTop ? "3.875rem" : "3rem")};
    height: ${({ $isTop }) => ($isTop ? "3.875rem" : "3rem")};
    border-radius: 100%;
    margin-top: ${({ $isTop }) => ($isTop ? null : "0.875rem")};
  }
  span {
    font-size: ${({ $isTop }) => ($isTop ? "2rem" : "1.5rem")};
  }
  font-family: "NanumSquareBold";
  cursor: pointer;
`;

export const TopRankContentStatDiv = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80%;
  margin: auto;

  color: ${palette.brandDarkGray};
  font-size: 0.75rem;
`;

export const RateSpan = styled.span<{ $isProfit: string }>`
  color: ${({ $isProfit }) => {
    if ($isProfit === "red") {
      return palette.brandRed;
    }
    if ($isProfit === "blue") {
      return palette.brandBlue;
    }
  }};
  font-family: "NanumSquareBold";
  font-size: 0.875rem;
`;

export const MoneySpan = styled.span<{ $active: boolean }>`
  color: ${({ $active }) =>
    $active ? palette.brandColor : palette.brandDarkGray};
  font-family: "NanumSquareBold";
  font-size: 0.875rem;
`;
