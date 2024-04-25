import styled from "styled-components";
import palette from "@/lib/palette";

interface BrandButtonProps {
  color: string | null;
  $cancel: boolean;
}

const BrandButton = styled.button<BrandButtonProps>`
  display: block;
  border: none;
  border-radius: 0.3125rem;
  min-width: 6.25rem;
  color: ${({ $cancel }) => ($cancel ? "#999999" : palette.brandWhite)};
  background-color: ${({ color, $cancel, disabled }) => {
    if ($cancel) {
      if (disabled) {
        return `${palette.brandGray}80`;
      }
      return palette.brandGray;
    }
    if (color) {
      if (disabled) {
        return `${color}80`;
      }
      return color;
    }
    if (disabled) {
      return `${palette.brandColor}80`;
    }
    return palette.brandColor;
  }};

  font-size: 0.9rem;
  font-family: "NanumSquareBold";
  padding: 0.7rem 2rem;
  cursor: pointer;
`;

export default BrandButton;
