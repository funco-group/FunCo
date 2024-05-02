import styled from 'styled-components'

// eslint-disable-next-line import/prefer-default-export
export const ChartDiv = styled.div<{ $flex?: boolean }>`
  padding: 1rem;
  height: 80%;
  display: ${(props) => props.$flex && 'flex'};
  justify-content: center;
  align-items: center;
`
