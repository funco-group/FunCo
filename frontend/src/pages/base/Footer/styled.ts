import palette from "@/lib/palette";
import styled from "styled-components";

export const TeamInfo = styled.div`
  font-size: 0.8rem;
  padding: 0.3rem;
  color: ${palette.brandDarkGray};
  font-family: "NanumSquareBold";
`;

export const MemberInfoContainer = styled.div`
  display: flex;
  justify-content: end;
`;

export const MemberInfo = styled.div`
  font-size: 0.75rem;
  display: flex;
  align-items: center;
  padding: 0.3rem;
  color: #adb5bd;
  text-decoration: underline;
  cursor: pointer;

  img {
    margin-right: 0.1rem;
  }
`;

export const FooterContainer = styled.div`
  display: flex;
  flex-direction: column;
  text-align: right;
  /* background-color: red; */
  /* background-color: ${palette.brandWhite}; */
  padding: 4rem 3rem 4rem 0;
`;
