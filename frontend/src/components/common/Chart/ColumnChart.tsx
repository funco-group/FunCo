import { useState } from "react";
import ReactApexChart from "react-apexcharts";
import { ChartLabel } from "./Chart.styled";
import palette from "@/lib/palette";

interface ColumnChartProps {
  dataSet: (string | number)[][];
  chartName: string;
  unit: string;
}

function ColumnChart({ dataSet, chartName, unit }: ColumnChartProps) {
  const [chartData] = useState({
    series: [
      {
        name: "Cash Flow",
        data: dataSet.map((data) => Number(data[1])),
      },
    ],
    options: {
      chart: {
        type: "bar" as const,
        height: 350,
      },
      plotOptions: {
        bar: {
          colors: {
            ranges: [
              {
                from: 0,
                to: 100000000000000,
                color: palette.brandRed,
              },
              {
                from: 0,
                to: -100000000000000,
                color: palette.brandBlue,
              },
            ],
          },
          columnWidth: "80%",
        },
      },
      dataLabels: {
        enabled: false,
      },
      yaxis: {
        labels: {
          formatter: function (y: number) {
            return `${y.toLocaleString("ko-KR")}`;
          },
        },
      },
      xaxis: {
        type: "datetime" as const,
        categories: dataSet.map((data) => data[0]),
        labels: {
          rotate: -90,
        },
      },
    },
  });

  return (
    <div>
      <ChartLabel>
        <div>{chartName}</div>
        <div>단위: {unit}</div>
      </ChartLabel>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        type="bar"
      />
    </div>
  );
}

export default ColumnChart;
