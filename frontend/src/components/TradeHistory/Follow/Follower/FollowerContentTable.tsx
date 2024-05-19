import { useEffect, useState } from 'react'
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { FollowerContentType } from '@/interfaces/tradeHistory/follow/FollowerContentType'
import { getFollowerList } from '@/apis/follow'
import NoData from '@/components/Common/NoData'
import {
  FollowerContentListContainer,
  FollowerContentTableContainer,
} from './FollowerContentTable.styled'
import FollowerContent from './FollowerContent'

function FollowerContentTable({
  nowTabName,
}: {
  nowTabName: 'all' | 'following' | 'settled'
}) {
  const [FollowerContentList, setFollowerContentList] = useState<
    FollowerContentType[]
  >([])
  const columnList = [
    '팔로우',
    '유저명',
    '투자금액',
    '정산금액',
    '수익률',
    '수수료',
    '정산',
  ]

  useEffect(() => {
    getFollowerList((res) => {
      const newFollowContentList = res.data.followers
      console.log(newFollowContentList)
      setFollowerContentList(newFollowContentList)
    }, nowTabName)
  }, [nowTabName])

  return (
    <FollowerContentTableContainer>
      <ColumnContainer>
        <ColumnGrid $column="7.5rem 1fr 1fr 1fr 1fr 1fr 7.5rem">
          {columnList.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <FollowerContentListContainer>
        {FollowerContentList.length > 0 ? (
          FollowerContentList.map((content) => (
            <FollowerContent key={content.followId} content={content} />
          ))
        ) : (
          <NoData content="팔로워 내역이 없습니다." height={80} />
        )}
      </FollowerContentListContainer>
    </FollowerContentTableContainer>
  )
}

export default FollowerContentTable
