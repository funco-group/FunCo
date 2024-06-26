import { TitleDiv } from '@/styles/TradeHistoryStyled'
import AreaChart from '@/components/Common/Chart/AreaChart'
import ColumnChart from '@/components/Common/Chart/ColumnChart'
import { ChartGraphContainer, ChartDiv } from './ChartGraph.styled'

interface ChartGraphProps {
  accReturnRate: (string | number)[][]
  returnResult: (string | number)[][]
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
  )
}

export default ChartGraph
