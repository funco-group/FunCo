import { Dispatch, SetStateAction, useEffect, useState } from 'react'
import {
  RankArrowDiv,
  RankPaginationButton,
  RankPaginationContainer,
  RankPaginationDiv,
} from './RankPagination.styled'

interface RankPaginationProps {
  nowPage: number
  setNowPage: Dispatch<SetStateAction<number>>
  totalPage: number
}

function RankPagination({
  nowPage,
  setNowPage,
  totalPage,
}: RankPaginationProps) {
  const [pageList, setPageList] = useState([1])
  const [startPage, setStartPage] = useState(0)

  useEffect(() => {
    const newPageList = []

    for (let i = startPage; i < startPage + 5 && i < totalPage; i += 1) {
      newPageList.push(i + 1)
    }

    setPageList(newPageList)
  }, [startPage, totalPage])

  useEffect(() => {
    setStartPage(Math.floor(nowPage / 5) * 5)
  }, [nowPage])

  const handlePaginationClick = (selectedPageNum: number) => {
    setNowPage(selectedPageNum - 1)
  }

  const handlePaginationPrevArrowClick = () => {
    const prevPage = Math.floor(nowPage / 5) * 5 - 1
    setNowPage(prevPage)
  }

  const handlePaginationNextArrowClick = () => {
    const nextPage = Math.ceil((nowPage + 1) / 5) * 5
    setNowPage(nextPage)
  }

  return (
    <RankPaginationContainer>
      <RankPaginationDiv>
        {startPage !== 0 ? (
          <RankArrowDiv onClick={handlePaginationPrevArrowClick}>
            <img
              src="/icon/pagination-arrow-left.png"
              alt="rank-pagination-left-arrow"
              draggable={false}
            />
          </RankArrowDiv>
        ) : null}
        {pageList.map((pageNum) => (
          <RankPaginationButton
            key={pageNum}
            $active={pageNum - 1 === nowPage}
            onClick={() => handlePaginationClick(pageNum)}
          >
            {pageNum}
          </RankPaginationButton>
        ))}
        {startPage !== Math.floor(totalPage / 5) * 5 ? (
          <RankArrowDiv onClick={handlePaginationNextArrowClick}>
            <img
              src="/icon/pagination-arrow-right.png"
              alt="rank-pagination-right-arrow"
              draggable={false}
            />
          </RankArrowDiv>
        ) : null}
      </RankPaginationDiv>
    </RankPaginationContainer>
  )
}

export default RankPagination
