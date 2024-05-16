import palette from '@/lib/palette'
import styled from 'styled-components'

// eslint-disable-next-line import/prefer-default-export
export const FuturesTradeButton = styled.div<{ name: string }>`
  background-color: ${(props) => {
    if (props.name === 'Long') {
      return palette.brandRed
    }
    return palette.brandBlue
  }};
  color: ${palette.brandWhite};
  text-align: center;
  padding: 0.6rem;
  cursor: pointer;
  width: 100%;
  margin: 0.2rem;
`
