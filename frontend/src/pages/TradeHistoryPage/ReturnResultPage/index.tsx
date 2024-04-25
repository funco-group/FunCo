import { useEffect, useState } from "react";
import ChartGraph from "./ChartGraph";
import ReturnResultList from "./ReturnResultList";
import TotalReturnResult from "./TotalReturnResult";
import { OptionType } from "@/interfaces/AssetType";
import { getDailyStatistics, getMonthlyStatistics } from "@/apis/statistics";
import { AxiosResponse } from "axios";
import { StatisticsType } from "@/interfaces/StatisticsType";

export default function index() {
  const [activeTab, setActiveTab] = useState<string>("일별");
  const [options, setOptions] = useState<OptionType[]>([
    { value: "-", name: "-" },
  ]);
  const [selected, setSelected] = useState<string>();
  const [resultList, setResultList] = useState<StatisticsType[]>([]);
  const [accReturnRate, setAccReturnRate] = useState<(string | number)[][]>();
  const [returnResult, setReturnResult] = useState<(string | number)[][]>();
  const [resultData, setResultData] = useState<number[]>([]);

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelected(e.target.value);
  };

  useEffect(() => {
    if (selected) {
      if (activeTab === "일별") {
        getDailyStatistics(
          selected?.split("-")[0],
          selected?.split("-")[1],
          (response: AxiosResponse<StatisticsType[]>) => {
            const { data } = response;
            setResultList(data);
            setAccReturnRate(
              data.map((item) => [item.date, item.accReturnRate]),
            );
            setReturnResult(data.map((item) => [item.date, item.returnResult]));
            setResultData([
              data[data.length - 1].accReturnResult,
              data[data.length - 1].accReturnRate,
            ]);
          },
        );
      } else {
        getMonthlyStatistics(
          selected,
          (response: AxiosResponse<StatisticsType[]>) => {
            const { data } = response;
            setResultList(data);
            setAccReturnRate(
              data.map((item) => [item.date, item.accReturnRate]),
            );
            setReturnResult(data.map((item) => [item.date, item.returnResult]));
          },
        );
      }
    }
  }, [activeTab, selected]);

  if (!options) return null;

  return (
    <div>
      <TotalReturnResult
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        options={options}
        setOptions={setOptions}
        selected={selected}
        setSelected={setSelected}
        handleSelect={handleSelect}
        resultData={resultData}
      />
      {accReturnRate && returnResult && (
        <ChartGraph
          key={accReturnRate?.length + returnResult?.length}
          accReturnRate={accReturnRate}
          returnResult={returnResult}
        />
      )}
      <ReturnResultList resultList={resultList} />
    </div>
  );
}
