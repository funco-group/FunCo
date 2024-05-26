import palette from '@/lib/palette'
import styled from 'styled-components'

// eslint-disable-next-line import/prefer-default-export
export const ChartLabel = styled.div`
  display: flex;
  justify-content: space-between;
  font-size: 0.85rem;
  background-color: ${palette.tabColor};
  color: ${palette.brandDarkGray};
  padding: 0.5rem;
  margin-bottom: 0.5rem;
`
