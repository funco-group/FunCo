import styled from "styled-components";
import { CommonComponent } from "@/styles/CommonStyled";
import palette from "@/lib/palette";

export const UserPageProfileContainer = styled.div`
  ${CommonComponent};
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ProfileDetailContainer = styled.div`
  border: none;
  background-color: ${palette.brandColor2};
  padding: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  img {
    display: block;
    width: 3.75rem;
    height: 3.75rem;
    border-radius: 100%;
    object-fit: cover;
    margin-bottom: 1rem;
  }

  p {
    width: 21.875rem;
    word-wrap: break-word;
    text-align: center;
    margin: auto;
  }
`;

export const ProfileRankFlexDiv = styled.div`
  display: flex;
  gap: 1.25rem;
`;

export const ProfileRankOuterDiv = styled.div`
  width: 8rem;
  text-align: center;
  font-size: 0.9rem;
  color: ${palette.brandDarkGray};
`;

export const ProfileRankDiv = styled.div`
  color: black;
  font-family: "NanumSquareBold";
  font-size: 1rem;
  margin-top: 0.4rem;
  display: flex;
  align-items: center;
  justify-content: center;

  span {
    font-family: "NanumSquareBold";
    /* color: ${palette.brandDarkGray}; */

    /* font-size: 1.1rem; */
  }
`;

export const ProfileInput = styled.input`
  font-size: 1rem;
  padding: 0.5rem 0.3rem;
  background-color: transparent;
  border: none;
  border-bottom: 1px solid ${palette.brandColor};
  width: 14rem;

  &:focus {
    border-bottom: 1px solid ${palette.brandColor};
    outline: none;
  }
`;

export const ProfileTextArea = styled.textarea`
  width: 100%;
  resize: none;
  outline: none;
  font-size: 1rem;
  padding: 0.5rem 0.3rem;
  background-color: transparent;
  border: none;
  border-bottom: 1px solid ${palette.brandColor};
  height: 1.1rem;

  &:focus {
    outline: none;
  }
`;

export const ProfileButtonDiv = styled.div`
  display: flex;
  justify-content: center;
`;

export const ProfileEditButtonDiv = styled.div`
  display: flex;
  gap: 0.625rem;
`;

export const NicknameDiv = styled.div`
  height: 3rem;
  font-size: 1.2rem;
  font-family: "NanumSquareBold";
`;

export const IntroductionDiv = styled.div`
  margin-top: 1rem;
  height: 3rem;
  display: flex;
  align-items: center;
  width: 20rem;
  justify-content: center;
`;
