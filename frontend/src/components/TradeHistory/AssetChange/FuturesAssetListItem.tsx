import {
  ColumnGrid,
  ListItemContainer,
  ListItemDiv,
} from '@/styles/CommonStyled'
import React from 'react'
import { FuturesAssetType } from '@/interfaces/AssetType'
import parseDate from '@/utils/parseDate'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'

function FuturesAssetListItem({ history }: { history: FuturesAssetType }) {
  const nameMap = useRecoilValue(codeNameMapState)

  const getColorForTradeType = (tradeType: string) => {
    switch (tradeType) {
      case 'LONG':
        return 'red'
      case 'SHORT':
        return 'blue'
      default:
        return 'black'
    }
  }

  return (
    <ListItemContainer>
      <AssetChangeListItemContainer>
        <ColumnGrid $column="repeat(6, 1fr)">
          <ListItemDiv $align="left" color="black">
            {parseDate(history.date)}
          </ListItemDiv>
          <ListItemDiv $align="left" color="black">
            <>
              <img
                src={`https://static.upbit.com/logos/${history.ticker?.split('-')[1]}.png`}
                alt={history.ticker}
                width={13}
              />
              {nameMap.get(history.ticker)}
            </>
          </ListItemDiv>
          <ListItemDiv
            $align="center"
            color={getColorForTradeType(history.tradeType)}
          >
            {history.tradeType}
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.price?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.orderCash?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.endingCash?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
        </ColumnGrid>
      </AssetChangeListItemContainer>
    </ListItemContainer>
  )
}

export default FuturesAssetListItem
