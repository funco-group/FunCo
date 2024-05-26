import styled from "styled-components";
import palette from "@/lib/palette";

export const TotalAssetContainer = styled.div`
  border-bottom: 1px solid ${palette.borderGray};
`;

export const AssetItemContainer = styled.div<{ $top: boolean }>`
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 4rem;
  border-bottom: ${(props) => props.$top && `1px solid ${palette.brandColor}`};
  margin-bottom: ${(props) => props.$top && `0.5rem`};
  padding-bottom: ${(props) => props.$top && `0.3rem`};
`;

export const AssetItemDiv = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 1rem 0.5rem;
`;

export const ChartContainer = styled.div`
  border-left: 1px solid ${palette.borderGray};
`;
