import styled from "styled-components";
import palette from "@/lib/palette";

interface TabButtonProps {
  width: string | null;
  height: string;
  $active: boolean;
  radius: "left" | "right" | "";
}

const TabButton = styled.button<TabButtonProps>`
  background-color: transparent;
  width: ${({ width }) => width || null};
  height: ${({ height }) => height};
  color: ${({ $active }) => ($active ? palette.brandColor : "#999999")};
  padding: 0.5rem;
  border-top: ${({ $active }) =>
    $active
      ? `1px solid ${palette.brandColor}`
      : `1px solid ${palette.deactivatedGray}`};
  border-bottom: ${({ $active }) =>
    $active
      ? `1px solid ${palette.brandColor}`
      : `1px solid ${palette.deactivatedGray}`};
  border-left: ${({ $active, radius }) => {
    if ($active) {
      return `1px solid ${palette.brandColor}`;
    }
    if (radius === "right") {
      return "none";
    }
    return `1px solid ${palette.deactivatedGray}`;
  }};
  border-right: ${({ $active, radius }) => {
    if ($active) {
      return `1px solid ${palette.brandColor}`;
    }
    if (radius === "left") {
      return "none";
    }
    return `1px solid ${palette.deactivatedGray}`;
  }};
  border-radius: ${({ radius }) => {
    if (radius === "left") {
      return "5px 0 0 5px";
    }
    if (radius === "right") {
      return "0 5px 5px 0";
    }
    return null;
  }};
  font-family: ${({ $active }) =>
    $active ? "NanumSquareBold" : "NanumSquare"};
  cursor: pointer;
`;

export default TabButton;
