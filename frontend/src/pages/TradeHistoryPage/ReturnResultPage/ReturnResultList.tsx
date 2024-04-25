import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import { TitleDiv } from "@/styles/TradeHistoryStyled";
import ReturnResultListItem from "./ReturnResultListItem";
import { StatisticsType } from "@/interfaces/StatisticsType";
import ReturnResultListContainer from "./ReturnResultList.styled";

interface ReturnResultListProps {
  resultList: StatisticsType[];
}

function ReturnResultList({ resultList }: ReturnResultListProps) {
  const columns = [
    "일자",
    "손익",
    "수익률",
    "누적 손익",
    "누적 수익률",
    "기초 자산",
    "기말 자산",
  ];

  return (
    <div>
      <TitleDiv>투자손익 상세</TitleDiv>
      <ColumnContainer>
        <ColumnGrid column="1fr 0.8fr 0.6fr 1fr 0.6fr 1fr 1fr">
          {columns.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <ReturnResultListContainer>
        {resultList.map((result: StatisticsType) => {
          return <ReturnResultListItem key={result.date} result={result} />;
        })}
      </ReturnResultListContainer>
    </div>
  );
}

export default ReturnResultList;
