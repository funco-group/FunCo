import { useEffect, useState } from 'react'
import {
  ResultItemContainer,
  ResultItemDiv,
  TitleDiv,
  DataDiv,
  ResultContainer,
  TextInfoDiv,
} from '@/components/TradeHistory/ReturnResult/TotalReturnResult.styled'
import { GreenContainer } from '@/styles/TradeHistoryStyled'
import ReturnResultTab from '@/components/Common/ReturnResultTab'
import { OptionType } from '@/interfaces/AssetType'
import { getStartDate } from '@/apis/statistics'
import { AxiosResponse } from 'axios'
import { StartDateType } from '@/interfaces/StatisticsType'

interface TotalReturnResultProps {
  activeTab: string
  setActiveTab: React.Dispatch<React.SetStateAction<string>>
  setOptions: React.Dispatch<React.SetStateAction<OptionType[]>>
  options: OptionType[]
  selected: string | undefined
  setSelected: React.Dispatch<React.SetStateAction<string | undefined>>
  handleSelect: (e: any) => void
  resultData: number[]
}

function TotalReturnResult({
  activeTab,
  setActiveTab,
  setOptions,
  options,
  selected,
  setSelected,
  handleSelect,
  resultData,
}: TotalReturnResultProps) {
  const titles = ['기간 누적 손익', '기간 누적 수익률']
  const unit = ['WON', '%']
  const [startYear, setStartYear] = useState<number>()
  const [startMonth, setStartMonth] = useState<number>()

  const handleTabClick = (tab: string) => {
    setActiveTab(tab)
  }

  useEffect(() => {
    if (startYear && startMonth) {
      let year = startYear
      let month = startMonth
      const todayYear = new Date().getFullYear()
      const todayMonth = new Date().getMonth() + 1
      const dateList = []

      if (activeTab === '일별') {
        while (year < todayYear || (year === todayYear && month < todayMonth)) {
          dateList.push({
            value: `${year}-${month}`,
            name: `${year}년 ${month}월`,
          })
          month += 1
          if (month > 12) {
            month = 1
            year += 1
          }
        }
      } else {
        for (let i = startYear; i <= todayYear; i += 1) {
          dateList.push({
            value: `${year}`,
            name: `${year}년`,
          })
        }
      }
      setSelected(dateList[0]?.value)
      setOptions(dateList)
    }
  }, [startYear, startMonth, activeTab])

  useEffect(() => {
    getStartDate((response: AxiosResponse<StartDateType>) => {
      const { data } = response
      setStartYear(data.year)
      setStartMonth(data.month)
    })
  }, [])

  return (
    <>
      <ReturnResultTab
        activeTab={activeTab}
        handleTabClick={handleTabClick}
        options={options}
        selected={selected}
        handleSelect={handleSelect}
      />
      <div>
        <ResultContainer>
          <GreenContainer>
            <ResultItemContainer>
              {titles.map((title, index) => (
                <ResultItemDiv $right={index < 1} key={title}>
                  <TitleDiv>{title}</TitleDiv>
                  <DataDiv
                    color={
                      // eslint-disable-next-line no-nested-ternary
                      resultData[index] > 0
                        ? 'red'
                        : resultData[index] < 0
                          ? 'blue'
                          : 'black'
                    }
                  >
                    {resultData[index]
                      ? resultData[index].toLocaleString('ko-KR')
                      : '-'}{' '}
                    <span>{unit[index]}</span>
                  </DataDiv>
                </ResultItemDiv>
              ))}
            </ResultItemContainer>
            <TextInfoDiv>※ 기준 금액 : 초기 자금 천 만원</TextInfoDiv>
          </GreenContainer>
        </ResultContainer>
      </div>
    </>
  )
}

export default TotalReturnResult
