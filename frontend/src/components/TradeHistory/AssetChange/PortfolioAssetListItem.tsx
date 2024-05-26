import {
  ColumnGrid,
  ListItemContainer,
  ListItemDiv,
} from '@/styles/CommonStyled'
import React from 'react'
import { PortfolioAssetType } from '@/interfaces/AssetType'
import parseDate from '@/utils/parseDate'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'

function PortfolioAssetListItem({ history }: { history: PortfolioAssetType }) {
  const getColorForTradeType = (tradeType: string) => {
    switch (tradeType) {
      case 'PURCHASE_PORTFOLIO':
        return 'red'
      case 'SELL_PORTFOLIO':
        return 'blue'
      default:
        return 'black'
    }
  }

  return (
    <ListItemContainer>
      <AssetChangeListItemContainer>
        <ColumnGrid $column="repeat(5, 1fr)">
          <ListItemDiv $align="left" color="black">
            {parseDate(history.date)}
          </ListItemDiv>
          <ListItemDiv $align="center" color="black">
            {history.portfolioName}
          </ListItemDiv>
          <ListItemDiv
            $align=""
            color={getColorForTradeType(history.assetTradeType)}
          >
            {history.assetTradeType === 'PURCHASE_PORTFOLIO' ? '구매' : '판매'}
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.price?.toLocaleString('ko-KR')}
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

export default PortfolioAssetListItem
