function useParseDate(date: string) {
  const parsedDate = new Date(date);

  // 연, 월, 일, 시간, 분, 초 추출
  const year = parsedDate.getFullYear();
  const month = String(parsedDate.getMonth() + 1).padStart(2, "0");
  const day = String(parsedDate.getDate()).padStart(2, "0");
  const hours = String(parsedDate.getHours()).padStart(2, "0");
  const minutes = String(parsedDate.getMinutes()).padStart(2, "0");
  const seconds = String(parsedDate.getSeconds()).padStart(2, "0");

  // "yyyy-mm-dd hh:mm" 형식으로 반환
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

export default useParseDate;
