import {
  ColumnGrid,
  ListItemContainer,
  ListItemDiv,
} from '@/styles/CommonStyled'
import React from 'react'
import { CoinAssetType } from '@/interfaces/AssetType'
import parseDate from '@/utils/parseDate'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'

function CoinAssetListItem({ history }: { history: CoinAssetType }) {
  const nameMap = useRecoilValue(codeNameMapState)

  const getColorForTradeType = (tradeType: string) => {
    switch (tradeType) {
      case 'BUY':
        return 'red'
      case 'SELL':
        return 'blue'
      default:
        return 'black'
    }
  }

  return (
    <ListItemContainer>
      <AssetChangeListItemContainer>
        <ColumnGrid $column="repeat(7, 1fr)">
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
            $align=""
            color={getColorForTradeType(history.assetTradeType)}
          >
            {history.assetTradeType === 'BUY' ? '매수' : '매도'}
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.volume}
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

export default CoinAssetListItem
