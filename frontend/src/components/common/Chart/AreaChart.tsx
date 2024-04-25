import { useState } from "react";
import ReactApexChart from "react-apexcharts";
import { ChartLabel } from "./Chart.styled";

interface AreaChartProps {
  dataSet: (string | number)[][];
  chartName: string;
  unit: string;
}

function AreaChart({ dataSet, chartName, unit }: AreaChartProps) {
  const [chartData] = useState({
    series: [
      {
        name: "STOCK ABC",
        data: dataSet.map((data) => Number(data[1])),
      },
    ],
    options: {
      chart: {
        type: "area" as const,
        height: 350,
        zoom: {
          enabled: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: "straight" as const,
      },
      labels: dataSet.map((data) => String(data[0])),
      xaxis: {
        type: "datetime" as const,
      },
      yaxis: {
        labels: {
          formatter: function (y: number) {
            return `${y.toFixed(0)}%`;
          },
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
        type="area"
      />
    </div>
  );
}

export default AreaChart;
