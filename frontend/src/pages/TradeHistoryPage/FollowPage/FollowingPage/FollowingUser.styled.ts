import styled from "styled-components";
import { CommonComponent } from "@/styles/CommonStyled";
import palette from "@/lib/palette";

export const FollowingUserContainer = styled.div`
  ${CommonComponent}
  padding-bottom: 1rem;
  margin-bottom: 1rem;
`;

export const FollowingTitleDiv = styled.div`
  padding: 1.5rem 0 0 1.3rem;
  font-family: "NanumSquareBold";
  font-size: 1.1rem;
`;

export const FollowingDetailFlexDiv = styled.div`
  display: flex;
  align-items: center;
`;

export const FollowingDetailDiv = styled.div`
  padding-left: 1rem;
  width: 75%;
`;

export const FollowingDetailInnerDiv = styled.div`
  /* margin-top: 1.5rem; */
  ${CommonComponent}
`;

export const FollowingColumnGridDiv = styled.div`
  display: grid;
  grid-template-columns: 7.5rem 1fr 1fr 1fr;
  padding: 0 1.4rem;
`;

export const FollowingContentDiv = styled.div`
  color: ${palette.brandDarkGray};
  font-size: 0.85rem;
  text-align: end;

  span {
    font-family: "NanumSquareBold";
    color: ${palette.brandBlack};
  }
`;

export const FollowingDateDiv = styled.div`
  text-align: left;
  margin: 1.2rem auto;
  /* padding-left: 1.4rem; */
`;

export const FollowingContentMarginDiv = styled.div<{ color: string }>`
  margin: auto 0.625rem;

  span {
    color: ${(props) => {
      if (props.color === "red") {
        return palette.brandRed;
      }
      if (props.color === "blue") {
        return palette.brandBlue;
      }
    }};
  }
`;

export const FollowingUserGraphDiv = styled.div`
  width: 25%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const FollowingUserGraphInnerDiv = styled.div`
  width: 90%;
`;

export const FollowingButtonDiv = styled.div`
  padding-left: 1rem;
  display: flex;
  justify-content: start;
`;

export const FollowingLeftButtonDiv = styled.div`
  margin-right: 0.5rem;
`;
