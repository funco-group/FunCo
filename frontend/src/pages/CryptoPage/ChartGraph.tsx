import { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import { useParams } from "react-router-dom";
import { AxiosResponse } from "axios";
import { CandleType } from "@/interfaces/CryptoType";
import { getDaysCandle, getMinuteCandle } from "@/apis/upbit";
import palette from "@/lib/palette";

interface ChartGraphProps {
  button: string;
}

function ChartGraph({ button }: ChartGraphProps) {
  const [candleList, setCandleList] = useState<CandleType[]>([]);
  const { coinCode } = useParams();
  const daysMap = new Map<string, string>([
    ["일봉", "days"],
    ["주봉", "weeks"],
    ["월봉", "months"],
  ]);
  const minutesMap = new Map<string, number>([
    ["1분봉", 1],
    ["5분봉", 5],
    ["10분봉", 10],
  ]);

  useEffect(() => {
    if (["일봉", "주봉", "월봉"].includes(button)) {
      getDaysCandle(
        daysMap!.get(button)!,
        coinCode!,
        200,
        (response: AxiosResponse<CandleType[]>) => {
          const { data } = response;
          setCandleList(data);
        },
      );
    } else {
      getMinuteCandle(
        minutesMap!.get(button)!,
        coinCode!,
        200,
        (response: AxiosResponse<CandleType[]>) => {
          const { data } = response;
          setCandleList(data);
        },
      );
    }
  }, [button, coinCode]);

  const state = {
    options: {
      chart: {
        toolbar: { show: true },
        // background: "#f9fafc",
      },
      xaxis: {
        type: "datetime" as const,
        categories: candleList?.map((candle) => candle.candle_date_time_kst),
        labels: {
          style: {
            colors: "black",
          },
        },
      },
      yaxis: {
        tooltip: {
          enabled: true,
        },
        labels: {
          formatter: function (y: number) {
            return y.toLocaleString("ko-KR");
          },
        },
      },
      plotOptions: {
        candlestick: {
          colors: {
            upward: palette.brandBlue,
            downward: palette.brandRed,
          },
        },
      },
    },
    series: [
      {
        name: "price",
        data: candleList?.map((candle) => {
          return {
            x: new Date(candle.candle_date_time_kst).getTime(),
            y: [
              candle.opening_price,
              candle.high_price,
              candle.low_price,
              candle.trade_price,
            ],
          };
        }),
      },
    ],
    // optionsBar: {
    //   chart: {
    //     type: "bar",
    //     brush: {
    //       enabled: true,
    //       target: "candles",
    //     },
    //     selection: {
    //       enabled: true,
    //       xaxis: {
    //         categories: data?.map((price) => price.candle_date_time_kst),
    //       },
    //       fill: {
    //         color: "#ccc",
    //         opacity: 0.4,
    //       },
    //       stroke: {
    //         color: "#0D47A1",
    //       },
    //     },
    //   },
    //   dataLabels: {
    //     enabled: false,
    //   },
    //   plotOptions: {
    //     bar: {
    //       columnWidth: "80%",
    //       colors: {
    //         ranges: [
    //           {
    //             from: -1000,
    //             to: 0,
    //             color: "#F15B46",
    //           },
    //           {
    //             from: 1,
    //             to: 10000,
    //             color: "#FEB019",
    //           },
    //         ],
    //       },
    //     },
    //   },
    //   stroke: {
    //     width: 0,
    //   },
    //   xaxis: {
    //     type: "datetime",
    //     categories: data?.map((price) => price.candle_date_time_kst),
    //     axisBorder: {
    //       offsetX: 13,
    //     },
    //   },
    //   yaxis: {
    //     labels: {
    //       show: false,
    //     },
    //   },
    // },
  };

  return (
    <div>
      <Chart
        type="candlestick"
        options={state.options}
        series={state.series}
        height={350}
      />
      {/* <Chart
        type="bar"
        options={state.optionsBar}
        series={state.series}
        height={160}
      /> */}
    </div>
  );
}

export default ChartGraph;
