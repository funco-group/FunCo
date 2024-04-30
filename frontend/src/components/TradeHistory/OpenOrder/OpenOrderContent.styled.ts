import styled from "styled-components";
import palette from "@/lib/palette";

export const OpenOrderContentDiv = styled.div`
  color: ${palette.brandDarkGray};
  font-size: 0.8rem;
  text-align: center;

  border-bottom: 1px solid ${palette.borderGray};
`;

export const OrderTypeSpan = styled.span<{ type: string }>`
  color: ${({ type }) =>
    type === "BUY" ? palette.brandRed : palette.brandBlue};
  font-family: "NanumSquareBold";
  font-size: 0.9rem;
`;

export const MoneySpan = styled.span`
  font-family: "NanumSquareBold";
  color: ${palette.brandBlack};
`;

export const DateDiv = styled.div`
  margin-left: 1rem;
`;
