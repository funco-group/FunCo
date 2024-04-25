import { useState } from "react";
import ReactApexChart from "react-apexcharts";

function MonochromePieChart({
  investmentList,
  isLegend,
}: {
  investmentList: (string | number)[][];
  isLegend: boolean;
}) {
  const [chartData] = useState({
    series: investmentList.map((investment) => Math.floor(+investment[1])),

    options: {
      chart: {
        width: "100%",
        type: "pie" as const,
      },
      labels: investmentList.map((investment) => String(investment[0])),
      theme: {
        monochrome: {
          enabled: true,
        },
      },
      plotOptions: {
        pie: {
          dataLabels: {
            offset: -25,
          },
        },
      },
      legend: {
        show: isLegend,
        position: "bottom" as const,
        fontSize: "10px",
      },
      tooltip: {
        enabled: true,
        y: {
          show: true,
          formatter(val: number) {
            if (val >= 1000) {
              return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 천단위 콤마
            }
            return val.toFixed(0).toString();
          },
        },
      },
    },
  });
  return (
    <div>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        type="pie"
      />
    </div>
  );
}

export default MonochromePieChart;
