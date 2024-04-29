import styled from "styled-components";
import palette from "@/lib/palette";
import { DropdownContainerProps, modalSettings } from "./NotiDropdown.styled";

export const ProfileDropdownContainer = styled.div<DropdownContainerProps>`
  width: 12.5rem;
  padding: 0.75rem;
  background-color: ${palette.brandWhite};

  border: none;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.16);

  position: fixed;
  z-index: 1000;

  top: 4.375rem;
  right: 1.875rem;

  ${(props) => modalSettings(props.$visible)}
`;

export const ProfileDiv = styled.div`
  padding: 0 10px;
  p {
    font-family: "NanumSquareBold";
    color: ${palette.brandBlack};
  }

  border-bottom: 1px solid ${palette.borderGray};
`;

export const ProfileDropdownButton = styled.button`
  display: block;
  width: 100%;

  border: none;
  margin-top: 10px;
  padding: 10px;

  background-color: transparent;

  text-align: left;

  cursor: pointer;
  &:hover {
    background-color: ${palette.brandColor2};
  }
`;
