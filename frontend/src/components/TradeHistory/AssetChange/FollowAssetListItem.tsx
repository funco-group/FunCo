import {
  ColumnGrid,
  ListItemContainer,
  ListItemDiv,
} from '@/styles/CommonStyled'
import React from 'react'
import parseDate from '@/utils/parseDate'
import { FollowAssetType } from '@/interfaces/AssetType'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'

function FollowAssetListItem({ history }: { history: FollowAssetType }) {
  return (
    <ListItemContainer>
      <AssetChangeListItemContainer>
        <ColumnGrid $column="repeat(7, 1fr)">
          <ListItemDiv $align="left" color="black">
            {parseDate(history.date)}
          </ListItemDiv>
          <ListItemDiv $align="center" color="black">
            {history.tradeType === 'FOLLOWER' ? '팔로워' : '팔로잉'}
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.investment?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.returnRate?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.commission?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="right" color="black">
            {history.settlement?.toLocaleString('ko-KR')}
            <span>WON</span>
          </ListItemDiv>
          <ListItemDiv $align="left" color="black">
            {parseDate(history.settleDate)}
          </ListItemDiv>
        </ColumnGrid>
      </AssetChangeListItemContainer>
    </ListItemContainer>
  )
}

export default FollowAssetListItem
