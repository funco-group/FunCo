import { ListItemDiv, ColumnGrid } from '@/styles/CommonStyled'
import { ListItemContainer } from '@/styles/ListItemContainer'
import { AssetType } from '@/interfaces/AssetType'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import { ListItemContainerDiv } from './AssetListItem.styled'

function AssetListItem({
  imgSrc,
  type,
  name,
  volume,
  averagePrice,
  price,
  evaluationAmount,
  evaluationProfit,
}: AssetType) {
  const nameMap = useRecoilValue(codeNameMapState)

  // 색상을 결정하는 함수
  const determineColor = (profit: number | null) => {
    if (profit === null || profit === 0) return 'black'
    return profit.toString().startsWith('-') ? 'blue' : 'red'
  }

  return (
    <ListItemContainer>
      <ListItemContainerDiv>
        <ColumnGrid $column="1fr 1fr 1fr 1fr 1fr 6.5rem">
          <ListItemDiv $align="left" color="black">
            <img src={imgSrc} alt={name} width={20} />
            {name !== '현금' && name !== '팔로우' ? nameMap.get(name) : name}
            {(type === 'LONG' || type === 'SHORT') && ` (${type})`}
          </ListItemDiv>
          <ListItemDiv $align={volume ? 'right' : ''} color="black">
            {volume || '-'}
            <span> {volume && name.split('-')[1]}</span>
          </ListItemDiv>
          <ListItemDiv $align={averagePrice ? 'right' : ''} color="black">
            {averagePrice ? averagePrice.toLocaleString('ko-KR') : '-'}
            {averagePrice && <span> WON</span>}
          </ListItemDiv>
          <ListItemDiv $align={price != null ? 'right' : ''} color="black">
            {price !== null ? price.toLocaleString('ko-KR') : '-'}
            {price !== null && <span> WON</span>}
          </ListItemDiv>
          <ListItemDiv $align={evaluationAmount ? 'right' : ''} color="black">
            {evaluationAmount ? evaluationAmount.toLocaleString('ko-KR') : '-'}
            {evaluationAmount !== null && evaluationAmount !== 0 && (
              <span> WON</span>
            )}
          </ListItemDiv>
          <ListItemDiv
            $align={evaluationProfit !== null ? 'right' : ''}
            color={determineColor(evaluationProfit)}
          >
            {evaluationProfit === null ? '-' : evaluationProfit}
            {evaluationProfit !== null && <span>%</span>}
          </ListItemDiv>
        </ColumnGrid>
      </ListItemContainerDiv>
    </ListItemContainer>
  )
}

export default AssetListItem
