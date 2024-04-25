import { TitleDiv } from "@/styles/TradeHistoryStyled";
import { ChartGraphContainer, ChartDiv } from "./ChartGraph.styled";
import AreaChart from "@/components/common/Chart/AreaChart";
import ColumnChart from "@/components/common/Chart/ColumnChart";

interface ChartGraphProps {
  accReturnRate: (string | number)[][];
  returnResult: (string | number)[][];
}

function ChartGraph({ accReturnRate, returnResult }: ChartGraphProps) {
  return (
    <>
      <TitleDiv>투자손익 그래프</TitleDiv>
      <ChartGraphContainer>
        <ChartDiv $left>
          <AreaChart chartName="누적 수익률" unit="%" dataSet={accReturnRate} />
        </ChartDiv>
        <ChartDiv $left={false}>
          <ColumnChart chartName="손익" unit="WON" dataSet={returnResult} />
        </ChartDiv>
      </ChartGraphContainer>
    </>
  );
}

export default ChartGraph;
