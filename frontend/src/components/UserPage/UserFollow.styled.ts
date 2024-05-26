import styled from "styled-components";

export const UserFollowContentDiv = styled.div`
  width: 100%;
  height: calc(100% - 1.25rem);
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.875rem;
  min-height: 6rem;
`;

export const UserFollowRowDiv = styled.div`
  display: flex;
  justify-content: space-between;
  font-size: 1rem;
  width: 100%;
`;

export const ColorSpan = styled.span<{ color: string }>`
  color: ${({ color }) => color};
  font-size: 0.8rem;
`;
