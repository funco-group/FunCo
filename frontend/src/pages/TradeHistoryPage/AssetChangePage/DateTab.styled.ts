import palette from "@/lib/palette";
import styled from "styled-components";

export const DateTabContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
`;

export const TabDiv = styled.div`
  font-size: 0.9rem;
  padding: 1.2rem 1rem;
`;
export const TabTitleDiv = styled.div`
  padding-bottom: 1em;
  font-family: "NanumSquareBold";

  span {
    font-size: 0.85rem;
    color: ${palette.brandDarkGray};
    font-family: "NanumSquar";
  }
`;
