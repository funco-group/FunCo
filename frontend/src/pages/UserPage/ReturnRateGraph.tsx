import ReturnResultTab from "@/components/common/ReturnResultTab";
import { useEffect, useState } from "react";
import { OptionType } from "@/interfaces/AssetType";
import {
  getUserDailyStatistics,
  getUserMonthlyStatistics,
  getUserStartDate,
} from "@/apis/statistics";
import { AxiosResponse } from "axios";
import { StartDateType, StatisticsType } from "@/interfaces/StatisticsType";
import {
  ReturnRateGraphContainer,
  NoDataDiv,
  ReturnRateGraphContentContainer,
} from "./ReturnRateGraph.styled";
import {
  ChartDiv,
  ChartGraphContainer,
} from "../TradeHistoryPage/ReturnResultPage/ChartGraph.styled";
import AreaChart from "@/components/common/Chart/AreaChart";
import ColumnChart from "@/components/common/Chart/ColumnChart";
import { ComponentTitleH3 } from "./styled";

interface ReturnRateGraphProps {
  memberId: number;
}

function ReturnRateGraph({ memberId }: ReturnRateGraphProps) {
  const [activeTab, setActiveTab] = useState<string>("일별");
  const [selected, setSelected] = useState<string>();
  const [startYear, setStartYear] = useState<number>();
  const [startMonth, setStartMonth] = useState<number>();
  const [accReturnRate, setAccReturnRate] = useState<(string | number)[][]>();
  const [returnResult, setReturnResult] = useState<(string | number)[][]>();
  const [options, setOptions] = useState<OptionType[]>([
    { value: "-", name: "-" },
  ]);

  const handleTabClick = (tab: string) => {
    setActiveTab(tab);
  };

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelected(e.target.value);
  };

  useEffect(() => {
    if (selected) {
      if (activeTab === "일별") {
        getUserDailyStatistics(
          memberId,
          selected?.split("-")[0],
          selected?.split("-")[1],
          (response: AxiosResponse<StatisticsType[]>) => {
            const { data } = response;
            setAccReturnRate(
              data.map((item) => [item.date, item.accReturnRate]),
            );
            setReturnResult(data.map((item) => [item.date, item.returnResult]));
          },
        );
      } else {
        getUserMonthlyStatistics(
          memberId,
          selected,
          (response: AxiosResponse<StatisticsType[]>) => {
            const { data } = response;
            setAccReturnRate(
              data.map((item) => [item.date, item.accReturnRate]),
            );
            setReturnResult(data.map((item) => [item.date, item.returnResult]));
          },
        );
      }
    }
  }, [activeTab, selected]);

  useEffect(() => {
    if (startYear && startMonth) {
      let year = startYear;
      let month = startMonth;
      const todayYear = new Date().getFullYear();
      const todayMonth = new Date().getMonth() + 1;
      let dateList = [];

      if (activeTab === "일별") {
        while (year < todayYear || (year === todayYear && month < todayMonth)) {
          dateList.push({
            value: `${year}-${month}`,
            name: `${year}년 ${month}월`,
          });
          month++;
          if (month > 12) {
            month = 1;
            year++;
          }
        }
      } else {
        for (let i = startYear; i <= todayYear; i++) {
          dateList.push({
            value: `${year}`,
            name: `${year}년`,
          });
        }
      }
      setSelected(dateList[0]?.value);
      setOptions(dateList);
    }
  }, [startYear, startMonth, activeTab]);

  useEffect(() => {
    getUserStartDate(memberId, (response: AxiosResponse<StartDateType>) => {
      const { data } = response;
      setStartYear(data.year);
      setStartMonth(data.month);
    });
  }, []);

  return (
    <ReturnRateGraphContainer>
      <ComponentTitleH3>수익률</ComponentTitleH3>
      <ReturnResultTab
        activeTab={activeTab}
        handleTabClick={handleTabClick}
        options={options}
        selected={selected}
        handleSelect={handleSelect}
      />
      <ReturnRateGraphContentContainer
        $flex={
          accReturnRate !== undefined &&
          accReturnRate.length !== 0 &&
          returnResult !== undefined &&
          returnResult.length !== 0
        }
      >
        {accReturnRate && returnResult ? (
          <ChartGraphContainer>
            <ChartDiv $left>
              <AreaChart
                key={accReturnRate?.length}
                chartName="누적 수익률"
                unit="%"
                dataSet={accReturnRate}
              />
            </ChartDiv>
            <ChartDiv $left={false}>
              <ColumnChart
                key={returnResult?.length}
                chartName="손익"
                unit="WON"
                dataSet={returnResult}
              />
            </ChartDiv>
          </ChartGraphContainer>
        ) : (
          <NoDataDiv>집계된 투자 내역 없습니다.</NoDataDiv>
        )}
      </ReturnRateGraphContentContainer>
    </ReturnRateGraphContainer>
  );
}

export default ReturnRateGraph;
