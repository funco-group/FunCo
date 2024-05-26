import styled from 'styled-components'
import palette from '@/lib/palette'

export const TradeListItemContainer = styled.div<{ $column: string }>`
  /* background-color: red; */
  border-bottom: 1px solid ${palette.borderGray};
  display: grid;
  grid-template-columns: ${(props) => props.$column};
  font-size: 0.8rem;
  color: ${palette.brandDarkGray};
  align-items: center;
`

export const TradeItemDiv = styled.div<{ $last: boolean }>`
  border-right: ${(props) => !props.$last && `1px solid ${palette.borderGray}`};
  padding: 0.5rem;
  height: 2.2rem;
`

export const TypeColumnDiv = styled.div<{ type: string }>`
  text-align: center;

  div {
    margin-top: 0.4rem;
    color: ${(props) =>
      props.type === 'BUY' ? palette.brandRed : palette.brandBlue};
  }
`

export const CancleButton = styled.div`
  background-color: ${palette.brandColor};
  color: ${palette.brandWhite};
  width: 2.5rem;
  padding: 0.4rem 0;
  text-align: center;
  border-radius: 0.3rem;
  margin-top: 0.2rem;
  cursor: pointer;
`

export const OrderPriceDiv = styled.div<{ color?: string }>`
  text-align: right;

  color: ${(props) => {
    if (props.color === 'red') {
      return palette.brandRed
    }
    if (props.color === 'blue') {
      return palette.brandBlue
    }
    return palette.brandBlack
  }};

  div {
    margin-top: 0.4rem;
  }

  span {
    font-size: 0.75rem;
    color: ${palette.brandDarkGray};
    margin-left: 0.2rem;
  }
`

export const TradeVolumeDiv = styled.div`
  text-align: right;

  line-height: 2.2rem;
`

export const TradeDateDiv = styled.div`
  /* background-color: blue; */
  margin-top: 0.2rem;
`
