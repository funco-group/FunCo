import styled from "styled-components";
import palette from "@/lib/palette";

export const HomePageButton = styled.button<{
  direction: string;
  $active: boolean;
  number: number;
}>`
  display: block;
  width: 28rem;
  img {
    width: ${({ $active }) => ($active ? "40px" : "1.25rem")};
    height: ${({ $active }) => ($active ? "40px" : "1.25rem")};
    margin: ${({ direction }) =>
      direction === "right" ? "0 0 50px 50px" : "0 50px 50px 0"};
  }

  margin-right: 0;
  margin-top: ${({ number }) =>
    number === 2 || number === 3 ? "100px" : null};
  text-align: ${({ direction }) => direction};
  border: none;
  background-color: transparent;
  cursor: pointer;
`;

export const HomePageButtonFlexDiv = styled.div<{ direction: string }>`
  display: flex;
  justify-content: ${({ direction }) => direction};
`;

export const HomePageButtonTitleDiv = styled.div`
  color: ${palette.mainColor2};
  font-size: 1.25rem;
  margin-bottom: 0.625rem;
`;

export const HomePageButtonContentDiv = styled.div<{ $active: boolean }>`
  display: flex;
  flex-direction: column;
  /* justify-content: end; */
  color: ${({ $active }) =>
    $active ? palette.brandColor : palette.borderGray};
  font-size: ${({ $active }) => ($active ? "1.25rem" : "1rem")};
`;
