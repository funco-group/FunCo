import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { RankType } from "@/interfaces/rank/RankType";
import { RankTableColumnGridDiv, RankTableContainer } from "./RankTable.styled";
import { ColumnContainer, ColumnTitleDiv } from "@/styles/CommonStyled";
import RankTableContent from "./RankTableContent";
import { getRankList } from "@/apis/rank";

interface RankTableProps {
  setTopRankList: Dispatch<SetStateAction<RankType[] | undefined>>;
  nowTabName: string;
  nowPage: number;
  totalPage: number;
  setTotalPage: Dispatch<SetStateAction<number>>;
}

function RankTable({
  setTopRankList,
  nowTabName,
  nowPage,
  totalPage,
  setTotalPage,
}: RankTableProps) {
  const [rankList, setRankList] = useState<RankType[]>();
  const [isLoading, setIsLoading] = useState(false);

  const rankTableColumnList = [
    "랭크",
    "유저",
    "수익률",
    "총 팔로워 금액",
    "총 자산",
  ];

  useEffect(() => {
    setIsLoading(true);
    getRankList(nowTabName, nowPage, (res) => {
      const { data } = res;
      setRankList(data.content);
      if (totalPage !== data.totalPages) {
        setTotalPage(data.totalPages);
      }
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [nowPage, nowTabName]);

  useEffect(() => {
    if (rankList !== undefined && nowPage === 0) {
      setTopRankList(rankList.slice(0, 3));
    }
    setIsLoading(false);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [rankList]);

  if (isLoading) {
    return <></>;
  }
  return (
    <RankTableContainer>
      <ColumnContainer>
        <RankTableColumnGridDiv>
          {rankTableColumnList.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </RankTableColumnGridDiv>
      </ColumnContainer>
      {rankList && rankList.length > 0 ? (
        rankList.map((rank) => (
          <RankTableContent
            key={rank.rank}
            rank={rank}
            nowTabName={nowTabName}
          />
        ))
      ) : (
        <div></div>
      )}
    </RankTableContainer>
  );
}

export default RankTable;
