const CHO = [
  'ㄱ',
  'ㄲ',
  'ㄴ',
  'ㄷ',
  'ㄸ',
  'ㄹ',
  'ㅁ',
  'ㅂ',
  'ㅃ',
  'ㅅ',
  'ㅆ',
  'ㅇ',
  'ㅈ',
  'ㅉ',
  'ㅊ',
  'ㅋ',
  'ㅌ',
  'ㅍ',
  'ㅎ',
]

// 한글 음절에서 초성 추출
function getChosung(char: string) {
  const code = char.charCodeAt(0) - 44032 // 유니코드 한글 음절의 시작점
  if (code < 0 || code > 11171) return char // 한글 음절이 아니면 그대로 반환
  return CHO[Math.floor(code / 588)] // 초성 인덱스
}

// 문자열을 초성 문자열로 변환
function toChosungString(text: string) {
  return text.split('').map(getChosung).join('')
}

// 초성 검색 함수
function searchByChosung(query: string, coinName: string) {
  const chosungQuery = toChosungString(query)
  return toChosungString(coinName).includes(chosungQuery)
}

export default searchByChosung
