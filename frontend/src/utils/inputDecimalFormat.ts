const inputDecimalFormat = (
  e: React.ChangeEvent<HTMLInputElement>,
  setFormatted: React.Dispatch<React.SetStateAction<string>>,
  setOrder: React.Dispatch<React.SetStateAction<number>>,
  name: string,
) => {
  let input = e.target.value;
  let inputNum;

  const parts = input.split(".");
  if (parts.length > 1 && parts[1].length > 10) {
    parts[1] = parts[1].substring(0, 10); // 소수점 이하를 10자리로 제한
    input = parts.join(".");
  }

  if (name === "매수") {
    inputNum = Number(input.replace(/,/g, ""));
  } else {
    inputNum = Number(
      input.replace(/[^0-9.]/g, "").replace(/(\..*?)\..*/g, "$1"),
    );
  }

  if (Number.isNaN(inputNum)) {
    setFormatted("0");
  } else {
    if (name === "매수") {
      setFormatted(inputNum.toLocaleString("ko-KR"));
    } else {
      setFormatted(input);
    }
    setOrder(inputNum);
  }
};
export default inputDecimalFormat;
