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
          <ListItemDiv $align="" color="black">
            {history.tradeType === 'PURCHASE_PORTFOLIO' ? '구매' : '판매'}
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.price.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.endingCash.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
        </ColumnGrid>
      </AssetChangeListItemContainer>
    </ListItemContainer>
  )
}

export default PortfolioAssetListItem
