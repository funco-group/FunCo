import styled from "styled-components";
import palette from "@/lib/palette";

export const NavBarContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 3.375rem;
  background-color: ${palette.brandWhite};
  padding: 0.75rem 1.5rem;
  z-index: 1000;

  display: flex;
`;

export const NavBarLeftDiv = styled.div`
  img {
    width: 6.25rem;
    height: 1.875rem;
    margin: 0.625rem 0;
    cursor: pointer;
  }

  display: flex;
`;

export const NavComponent = styled.div<{ $active: boolean }>`
  text-decoration: none;
  color: ${(props) => {
    if (props.$active) {
      return palette.brandColor;
    } else {
      return "black";
    }
  }};
  font-family: ${(props) => props.$active && "NanumSquareBold"};
  font-size: 1rem;
  margin-right: 1.5rem;
  cursor: pointer;
`;

export const NavBarLeftLinkDiv = styled.div`
  margin: auto;
  margin-left: 1.5rem;
  display: flex;

  .nav-link {
    text-decoration: none;
    color: black;
    font-size: 1rem;
    margin-right: 1.5rem;
  }

  .active {
    color: ${palette.brandColor};
    font-family: "NanumSquareBold";
  }
`;

export const NavBarRightDiv = styled.div`
  margin: auto;
  margin-right: 0;

  display: flex;
`;

export const NavBarNotiProfileDiv = styled.div`
  width: 2.5rem;
  height: 2.5rem;

  margin: 0 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;

  cursor: pointer;
`;

export const NavBarNotiImg = styled.img`
  margin: auto;
  width: 1.25rem;
  height: 1.5625rem;
  user-select: none;
`;

export const NavBarNotiPointDiv = styled.div`
  width: 1rem;
  height: 1rem;

  position: absolute;
  top: 0.25rem;
  right: 0.125rem;

  color: ${palette.brandWhite};
  background-color: red;
  border-radius: 100%;
  user-select: none;

  p {
    margin: auto;
    margin-top: 0.125rem;
    font-size: 0.7rem;

    height: 1rem;

    display: flex;
    justify-content: center;
    align-content: center;
  }
`;

export const NavBarNotiPointImg = styled.img`
  width: 0.5rem;
  height: 0.5rem;

  position: absolute;
  top: 0.4375rem;
  right: 0.625rem;
`;

export const NavBarProfileImg = styled.img`
  margin: auto;
  width: 2.5rem;
  height: 2.5rem;

  border-radius: 100%;
  object-fit: cover;
`;

export const NavBarLoginButton = styled.button`
  color: ${palette.brandDarkGray};
  border: 1px solid ${palette.brandColor};
  border-radius: 5px;

  background-color: ${palette.brandWhite};

  margin: auto 0.625rem auto auto;
  height: 2.1875rem;

  display: flex;
  align-items: center;

  img {
    display: block;
    width: 1rem;
    height: 1rem;
    margin-right: 0;
  }

  p {
    font-size: 0.9rem;
    margin: auto 0.3125rem auto 0.625rem;
  }

  cursor: pointer;

  /* box-shadow: 0 1px 8px rgba(0, 0, 0, 0.3); */

  &:active {
    transform: translate(2px, 2px);
    box-shadow: none;
  }
`;
