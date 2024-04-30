import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import AssetListItem from "./AssetListItem";
import { AssetType } from "@/interfaces/AssetType";
import AssetListContainer from "./AssetList.styled";

interface AssetListProps {
  assets: AssetType[];
}

function AssetList({ assets }: AssetListProps) {
  const columns = [
    "보유자산",
    "보유수량",
    "매수 평균가",
    "매수 금액",
    "평가금액",
    "평가수익률(%)",
  ];

  return (
    <>
      <ColumnContainer>
        <ColumnGrid column="repeat(6, 1fr)">
          {columns.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <AssetListContainer>
        {assets.map((coin: AssetType) => {
          return (
            <AssetListItem
              imgSrc={coin.imgSrc}
              name={coin.name}
              volume={coin.volume}
              averagePrice={coin.averagePrice}
              price={coin.price}
              evaluationAmount={coin.evaluationAmount}
              evaluationProfit={coin.evaluationProfit}
            />
          );
        })}
      </AssetListContainer>
    </>
  );
}

export default AssetList;
